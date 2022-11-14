#!/usr/bin/env python3

from pathlib import Path
import json

import blobconverter
import cv2
import depthai as dai
import numpy as np
import argparse
from time import monotonic
import mysql.connector

# Labels (vehicle-detection-0202 only detects cars)
labelMap = ["car"]

CAMERA_ID = "1"

# Get argument first
parser = argparse.ArgumentParser()
parser.add_argument('-nn', '--nn', type=str, help=".blob path")
parser.add_argument('-vid', '--video', type=str,
                    help="Path to video file to be used for inference (conflicts with -cam)")
parser.add_argument('-spi', '--spi', action='store_true', default=False, help="Send tracklets to the MCU via SPI")
parser.add_argument('-cam', '--camera', action="store_true",
                    help="Use DepthAI RGB camera for inference (conflicts with -vid)")
parser.add_argument('-t', '--threshold', default=0.25, type=float,
                    help="Minimum distance a car has to move (across the x/y axis) to be considered a real movement")  # default was=0.25
args = parser.parse_args()

parentDir = Path(__file__).parent

# If VIDEO = FALSE below, this does not do anything.
videoPath = args.video or parentDir / Path('demo/UCFv2.mp4')

# shaves are like cores that can run code:
# In our current pipeline configuration we can use up to 9 when the camera is set at 4K.
# but, up to 12, when the camera is set for 1080.
nnPath = args.nn or blobconverter.from_zoo(name="vehicle-detection-0202", shaves=9)  # 9 for 4K camera setting.

# Whether we want to use video from host or rgb camera
# This can be hardcoded as FALSE: to use the oak camera | TRUE: to use a video.
# VIDEO = not args.camera
VIDEO = False


class TextHelper:
    def __init__(self) -> None:
        # Definition of Constants
        self.bg_color = (0, 0, 0)
        self.color = (255, 255, 255)
        self.text_type = cv2.FONT_HERSHEY_SIMPLEX
        self.line_type = cv2.LINE_AA

    def putText(self, frame, text, coords):
        # Creates the effect of a black shadow to the text
        cv2.putText(frame, text, coords, self.text_type, 1, self.bg_color, 6, self.line_type)
        # Writes the text in white color
        cv2.putText(frame, text, coords, self.text_type, 1, self.color, 2, self.line_type)


# Start defining a pipeline (dai = depthai since depthai was imported as dai (import depthai as dai)
pipeline = dai.Pipeline()

# Create and configure the detection network
detectionNetwork = pipeline.create(dai.node.MobileNetDetectionNetwork)
detectionNetwork.setBlobPath(str(Path(nnPath).resolve().absolute()))

# Filter out the detections that are below a confidence threshold.
# Confidence can be anywhere between 0 and 1
detectionNetwork.setConfidenceThreshold(0.5)

if VIDEO:
    # Configure XLinkIn - we will send video through it
    videoIn = pipeline.create(dai.node.XLinkIn)
    videoIn.setStreamName("video_in")

    # Link video in with the detection network
    videoIn.out.link(detectionNetwork.input)

else:  # Camera configuration

    # Create and configure the color camera
    colorCam = pipeline.create(dai.node.ColorCamera)
    colorCam.setPreviewSize(512, 512)
    # colorCam.setResolution(dai.ColorCameraProperties.SensorResolution.THE_1080_P)
    colorCam.setResolution(dai.ColorCameraProperties.SensorResolution.THE_4_K)
    colorCam.setInterleaved(False)
    colorCam.setColorOrder(dai.ColorCameraProperties.ColorOrder.BGR)

    # Connect RGB preview to the detection network
    # Link the camera 'preview' output to the neural network detection input,
    # so that it can produce detections
    colorCam.preview.link(detectionNetwork.input)

# Create and configure the object tracker
objectTracker = pipeline.create(dai.node.ObjectTracker)

# possible tracking types: ZERO_TERM_COLOR_HISTOGRAM, ZERO_TERM_IMAGELESS, SHORT_TERM_IMAGELESS, SHORT_TERM_KCF
objectTracker.setTrackerType(dai.TrackerType.ZERO_TERM_COLOR_HISTOGRAM)

# Take the smallest ID when new object is tracked, possible options: SMALLEST_ID, UNIQUE_ID
objectTracker.setTrackerIdAssignmentPolicy(dai.TrackerIdAssignmentPolicy.SMALLEST_ID)

# Link detection network's outputs to the object tracker
detectionNetwork.passthrough.link(objectTracker.inputTrackerFrame)
detectionNetwork.passthrough.link(objectTracker.inputDetectionFrame)
detectionNetwork.out.link(objectTracker.inputDetections)

script = pipeline.create(dai.node.Script)
objectTracker.out.link(script.inputs['tracklets'])

with open("script.py", "r") as f:
    s = f.read()
    s = s.replace("THRESH_DIST_DELTA", str(args.threshold))
    script.setScript(s)

# Send tracklets to the host
trackerOut = pipeline.create(dai.node.XLinkOut)
trackerOut.setStreamName("out")
script.outputs['out'].link(trackerOut.input)

# Send RGB preview frames to the host
xlinkOut = pipeline.create(dai.node.XLinkOut)
xlinkOut.setStreamName("preview")
objectTracker.passthroughTrackerFrame.link(xlinkOut.input)

if args.spi:
    # Send tracklets via SPI to the MCU
    spiOut = pipeline.create(dai.node.SPIOut)
    spiOut.setStreamName("tracklets")
    spiOut.setBusId(0)
    objectTracker.out.link(spiOut.input)

# Pipeline defined, now the device is connected to
with dai.Device(pipeline) as device:
    previewQ = device.getOutputQueue("preview")
    outQ = device.getOutputQueue("out")

    counters = None
    frame = None
    text = TextHelper()


    def update():
        global counters, frame, text
        if previewQ.has():
            frame = previewQ.get().getCvFrame()

        if outQ.has():
            jsonText = str(outQ.get().getData(), 'utf-8')
            counters = json.loads(jsonText)
            print(counters)
            x = counters['data']

            if x != 0:  # if x = 0, it means there is nothing to send to the database.
                # connection = mysql.connector.connect(host="192.168.1.114",
                connection = mysql.connector.connect(host="localhost",
                                                     database="parkingsystem",
                                                     user="python",
                                                     password="camerapython3")
                try:
                    my_sql_insert_query = "INSERT INTO `camera_log` (`camera_id`, `changed_spaces`) VALUES (%s , %s)"
                    cursor = connection.cursor()
                    # cursor.execute(my_sql_insert_query)
                    cursor.execute(my_sql_insert_query, (CAMERA_ID, str(x)))
                    connection.commit()
                    print(cursor.rowcount, "Record inserted successfully into camera_log table")
                    cursor.close()

                except mysql.connector.Error as error:
                    print("Failed to insert record into camera_log table. \n {}".format(error))

                finally:
                    if connection.is_connected():
                        connection.close()
                        print("MySQL connection is closed.\n")

                counters['data'] = 0  # Reset data

        if counters is not None:
            # text.putText(frame, f"Up: {counters['up']}, Down: {counters['down']}", (3, 30))
            text.putText(frame, f"Leaving: {counters['left']}, Entering: {counters['right']}", (3, 30))
        if frame is not None:
            cv2.imshow("frame", frame)


    def to_planar(arr: np.ndarray, shape: tuple) -> np.ndarray:
        return cv2.resize(arr, shape).transpose(2, 0, 1).flatten()


    if VIDEO:  # When using a video, reduce it and send it to the camera.
        videoQ = device.getInputQueue("video_in")

        cap = cv2.VideoCapture(str(Path(videoPath).resolve().absolute()))
        while cap.isOpened():
            read_correctly, video_frame = cap.read()
            if not read_correctly:
                break

            img = dai.ImgFrame()
            # Reshape video (vehicle-detection-0202 needs 512x512 input)
            img.setData(to_planar(video_frame, (512, 512)))
            img.setType(dai.RawImgFrame.Type.BGR888p)
            img.setTimestamp(monotonic())
            img.setWidth(512)
            img.setHeight(512)
            videoQ.send(img)

            update()

            if cv2.waitKey(1) == ord('q'):
                break
        print("End of the video")

    else:
        while True:
            update()
            if cv2.waitKey(1) == ord('q'):
                break

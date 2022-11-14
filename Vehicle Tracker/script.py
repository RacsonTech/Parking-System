import json

data = {}
counter = {'up': 0, 'down': 0, 'left': 0, 'right': 0, 'data': 0}  # Y axis (up/down), X axis (left/right)


def send():
    b = Buffer(50)
    b.setData(json.dumps(counter).encode('utf-8'))
    node.io['out'].send(b)


def tracklet_removed(car, lastLocation):
    originalLocation = car['coords']  # Last location

    deltaX = lastLocation[0] - originalLocation[0]   # Change in X
    deltaY = lastLocation[1] - originalLocation[1]   # Change in Y

    # If the car traveled in the X direction more than in the Y direction
    # then, it means the car is going either left or right. Also, count only if
    # the car traveled at least a certain distance (more than the threshold value); this
    # avoids cars that have moved a little to be counted as leaving or entering.
    if abs(deltaX) > abs(deltaY) and abs(deltaX) > THRESH_DIST_DELTA:

        # if deltax is negative then
        #     car is moving left
        # else
        #     cas is moving right

        direction = "left" if 0 > deltaX else "right"
        counter[direction] += 1

        if direction == "left":
            counter['data'] = 1
        elif direction == "right":
            counter['data'] = -1

        send()

        # node.warn(f"Person moved {direction}")
        # node.warn("DeltaX: " + str(abs(deltaX)))

        # Note from Oscar: Here is where we should push data to the DB.
        # Don't send the count; send -1 if car is entering, or +1 if car is leaving.

    # Determine if the car is traveling up or down.
    # elif abs(deltaY) > abs(deltaX) and abs(deltaY) > THRESH_DIST_DELTA:
    #     direction = "up" if 0 > deltaY else "down"
    #     counter[direction] += 1
    #     send()
    #     # node.warn(f"Person moved {direction}")
    #     node.warn("DeltaY: " + str(abs(deltaY)))
    # else: node.warn("Invalid movement")

# Compute the center point of the car from the given coordinates.
def get_centroid(roi):
    x1 = roi.topLeft().x
    y1 = roi.topLeft().y
    x2 = roi.bottomRight().x
    y2 = roi.bottomRight().y
    return (  (x2 - x1) / 2 + x1  , (y2 - y1) / 2 + y1)    # (X, Y)

# Send dictionary initially (all counters 0)
send()

while True:
    # Get all the objects detected (cars in this case)
    tracklets = node.io['tracklets'].get()

    # For every car in the list of detected cars
    for t in tracklets.tracklets:

        # Reset the lost count when the car is tracked
        if t.status == Tracklet.TrackingStatus.TRACKED:
            data[str(t.id)]['lostCnt'] = 0

        # When a car is detected for the firs time, store its location
        elif t.status == Tracklet.TrackingStatus.NEW:
            data[str(t.id)] = {}  # Reset
            # data["1"] = {}   // data = {"1": }

            # Calculate the center of the object from the coordinates, and store it.
            data[str(t.id)]['coords'] = get_centroid(t.roi)  # ROI = Region of Interest
            # node.warn(f"new person Tracked. ID: {str(t.id)}")


        # When a car is lost, increase its lost counter.
        elif t.status == Tracklet.TrackingStatus.LOST:
            data[str(t.id)]['lostCnt'] += 1

            # If the car has been "LOST" for more than 10 frames, remove it and determine
            # whether it is entering or leaving the parking row.
            if 10 < data[str(t.id)]['lostCnt'] and "lost" not in data[str(t.id)]:
                # node.warn(f"Tracklet {t.id} lost: {data[str(t.id)]['lostCnt']}")

                # int (x,y) = get_centroid(t.roi)
                # data[str(t.id)], get_centroid(t.roi)
                # data["1",(x,y)]
                # data["2", (x,y)]
                tracklet_removed(data[str(t.id)], get_centroid(t.roi) )

                data[str(t.id)]["lost"] = True

        # When the car is in the state "Removed," remove it, and determine
        # whether it is entering or leaving the parking row.
        elif (t.status == Tracklet.TrackingStatus.REMOVED) and "lost" not in data[str(t.id)]:
            tracklet_removed(data[str(t.id)], get_centroid(t.roi))
            # node.warn(f"Tracklet {t.id} removed")

# Oscar's Notes:
# - node.warn(f"Enter text here")  <-- This prints to the terminal, like print("text")
# - Everytime a car is detected (new or not) the vehicle-detection-0202 model returns the id and
# its location among other things. The location is a decimal number between 0 and 1.
# - When the car is detected for the first time, its state is NEW. When the same car (same id) is
# recognized again in the next frames, its state is TRACKED. So, cars will be in the TRACKED state
# for the majority of their time.
# - Sometimes a car is suddenly detected twice, getting two ids, but it seems one of them gets removed
# - A car (Car A) can be "LOST" if there is another car (Car B) in front of it in a way that the camera
# does not have full visual of Car A. If Car A is "LOST" for more than 10 frames, it gets removed from
# the data array, and it may be counted as leaving or entering, when in fact the car is still in there.


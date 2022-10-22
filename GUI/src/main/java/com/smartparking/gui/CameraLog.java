package com.smartparking.gui;

public class CameraLog {
    private String timeStamp;
    private int cameraId;
    private int changedSpaces;

    public CameraLog(String timeStamp, int cameraId, int changedSpaces) {
        this.timeStamp = timeStamp;
        this.cameraId = cameraId;
        this.changedSpaces = changedSpaces;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public int getCameraId() {
        return cameraId;
    }

    public int getChangedSpaces() {
        return changedSpaces;
    }
}

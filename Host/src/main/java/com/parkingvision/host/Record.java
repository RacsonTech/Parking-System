package com.parkingvision.host;

public class Record {
    private final int recordId;
    private final int cameraId;
    private final int changedSpaces;

    // Constructor
    public Record(int id1, int id2, int spaces) {
        recordId = id1;
        cameraId = id2;
        changedSpaces = spaces;
    }

    public int getRecordId() {
        return recordId;
    }

    public int getCameraId() {
        return cameraId;
    }

    public int getChangedSpaces() {
        return changedSpaces;
    }


}

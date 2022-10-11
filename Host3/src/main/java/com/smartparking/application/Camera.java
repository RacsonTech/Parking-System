package com.smartparking.application;

public class Camera {
    private int id;
    private int sectionId;

    private String ipAddress;

    // Constructor
    public Camera(int cameraId, int sectionId, String ipAddress) {
        id = cameraId;
        this.sectionId = sectionId;
        this.ipAddress = ipAddress;
    }

    public int getId() {
        return id;
    }

    public int getSectionId() {
        return sectionId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

}

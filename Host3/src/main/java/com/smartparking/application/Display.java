package com.smartparking.application;

public class Display {
    private final int id;
    private final int sectionId;
    private final int number;
    private final String ipAddress;

    public Display(int id, int sectionId, int number, String ipAddress) {
        this.id = id;
        this.sectionId = sectionId;
        this.number = number;
        this.ipAddress = ipAddress;
    }

    public int getId() {
        return id;
    }

    public int getSectionId() {
        return sectionId;
    }

    public int getNumber() {
        return number;
    }

    public String getIpAddress() {
        return ipAddress;
    }
}

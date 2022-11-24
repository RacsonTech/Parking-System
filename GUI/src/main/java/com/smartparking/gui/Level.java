package com.smartparking.gui;

public class Level {
    private final int id;
    private int totalSpaces;
    private int availableSpaces;
    private int percentFull;
    private int numOfSections;

    public Level(int id, int totalSpaces, int availableSpaces, int numOfSections) {
        this.id = id;
        this.totalSpaces = totalSpaces;
        this.availableSpaces = availableSpaces;
        percentFull = calculatePercentFull();
        this.numOfSections = numOfSections;
    }

    private int calculatePercentFull() {
        return (int)Math.ceil( 100.0 * (((double)totalSpaces - availableSpaces) / totalSpaces));
    }

    public int getPercentFull() {
        return percentFull;
    }

    public int getId() {
        return id;
    }

    public int getTotalSpaces() {
        return totalSpaces;
    }

    public int getAvailableSpaces() {
        return availableSpaces;
    }

    public int getNumOfSections() {
        return numOfSections;
    }
    public void updateTotalSpaces(int capacity) {
        totalSpaces = capacity;
        percentFull = calculatePercentFull();
    }

    public void updateAvailableSpaces(int changedSpaces) {
        availableSpaces += changedSpaces;
        percentFull = calculatePercentFull();
    }

    public void setTotalSpaces(int capacity) {
        totalSpaces = capacity;
        percentFull = calculatePercentFull();
    }

    public void setAvailableSpaces(int freeSpaces) {
        availableSpaces = freeSpaces;
        percentFull = calculatePercentFull();
    }
}

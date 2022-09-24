package com.smartparking.host;

public class Section {
    private final int id;
    private final int levelId;
    private final int totalSpaces;
    private int availableSpaces;

    public Section(int id, int levelId, int totalSpaces, int availableSpaces) {
        this.id = id;
        this.levelId = levelId;
        this.totalSpaces = totalSpaces;
        this.availableSpaces = availableSpaces;
    }

    public int getId() {
        return id;
    }

    public int getLevelId() {
        return levelId;
    }

    public int getTotalSpaces() {
        return totalSpaces;
    }

    public int getAvailableSpaces() {
        return availableSpaces;
    }

    public void updateAvailableSpaces(int changedSpaces) {
        availableSpaces += changedSpaces;
    }
}

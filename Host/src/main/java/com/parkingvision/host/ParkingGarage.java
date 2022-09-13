package com.parkingvision.host;

import java.util.ArrayList;

public class ParkingGarage {
    private int id;
    private String garageName;
    private int totalSpaces;
    private int numLevels;
    private int availableSpaces;
    private ArrayList<Camera> cameraArrayList;
    private ArrayList<Display> displayArrayList;
    private ArrayList<Section> sectionArrayList;
    private ArrayList<Level> levelArrayList;
    private ArrayList<Record> recordArrayList;

    // Constructor
    public ParkingGarage() {
    }

    public int getId() {
        return id;
    }

    public String getGarageName() {
        return garageName;
    }

    public int getTotalSpaces() {
        return totalSpaces;
    }

    public int getAvailableSpaces() {
        return availableSpaces;
    }

    public int getNumLevels() {
        return numLevels;
    }

    public Camera getCamera(int cameraId) {

        // For each camera in the camera list...
        for (Camera camera : cameraArrayList) {

            if (cameraId == camera.getId()) {
                return camera;
            }
        }
        return null;
    }

    public Section getSection(int cameraId) {
        int sectionId = -1;

        // For every camera in the list...
        // Find the sectionId where the camera with id cameraId is located.
        for (Camera camera : cameraArrayList) {
            if (cameraId == camera.getId()) {
                sectionId = camera.getSectionId();
            }
        }

        // For every section in the list...
        for (Section section : sectionArrayList) {
            if (sectionId == section.getId()) {
                return section;
            }
        }
        return null;
    }

    public int getSectionAvailableSpaces(int id) {
        // For every section in the list,
        for (Section section : sectionArrayList) {
            if (id == section.getId()) {
                return section.getAvailableSpaces();
            }
        }
        return -1;
    }

    public int getLevelAvailableSpaces(int id) {
        // For every level in the list...
        for (Level level : levelArrayList) {
            if (id == level.getId()) {
                return level.getAvailableSpaces();
            }
        }
        return -1;
    }

    public int getGarageAvailableSpaces() {
        return availableSpaces;
    }

    public ArrayList<Display> getDisplayList(int sectionId) {
        ArrayList<Display> displaysFoundList = new ArrayList<>();

        // For every display in the list...
        for (Display display : displayArrayList) {
            if (sectionId == display.getSectionId()) {
                displaysFoundList.add(display);
            }
        }
        return displaysFoundList;
    }

    public ArrayList<Record> getRecordArrayList() {
        return recordArrayList;
    }

    public void setId(int garageId) {
        id = garageId;
    }

    public void setGarageName(String garageName) {
        this.garageName = garageName;
    }

    public void setTotalSpaces(int totalSpaces) {
        this.totalSpaces = totalSpaces;
    }

    public void setNumLevels(int numLevels) {
        this.numLevels = numLevels;
    }

    public void setAvailableSpaces(int availableSpaces) {
        this.availableSpaces = availableSpaces;
    }

    public void setCameraArrayList(ArrayList<Camera> cameraArrayList) {
        this.cameraArrayList = cameraArrayList;
    }

    public void setDisplayArrayList(ArrayList<Display> displayArrayList) {
        this.displayArrayList = displayArrayList;
    }

    public void setSectionArrayList(ArrayList<Section> sectionArrayList) {
        this.sectionArrayList = sectionArrayList;
    }

    public void setLevelArrayList(ArrayList<Level> levelArrayList) {
        this.levelArrayList = levelArrayList;
    }

    public void setSpaceChangeRecordArrayList(ArrayList<Record> record) {
        recordArrayList = record;
    }

    public void addCamera(Camera camera) {
        cameraArrayList.add(camera);
    }

    public void updateSectionAvailableSpaces(int id, int changedSpaces) {
        for (Section section : sectionArrayList) {
            if (id == section.getId()) {
                section.updateAvailableSpaces(changedSpaces);
                break;
            }
        }
    }

    public void updateLevelAvailableSpaces(int id, int changedSpaces) {
        for (Level level : levelArrayList) {
            if (id == level.getId()) {
                level.updateAvailableSpaces(changedSpaces);
                break;
            }
        }
    }

    public void updateGarageAvailableSpaces(int changedSpaces) {
        availableSpaces += changedSpaces;
    }

    public boolean removeCamera(int cameraId) {
        Camera camera = getCamera(cameraId);
        cameraArrayList.remove(camera);
        return true;
    }

    public void printGarageInfo() {
        System.out.println("Parking Garage: \t\t\t" + garageName);
        System.out.println("No. of Total Spaces: \t\t" + totalSpaces);
        System.out.println("No. of Available Spaces: \t" + availableSpaces);
        System.out.println("No. of Levels: \t\t\t\t" + numLevels);
    }

    public void printCameraList() {
        System.out.println("\n------ Camera List ------");
        System.out.format("%2s %12s %12s\n", "Camera ID", "Section ID", "IP Address");

        // For each camera in the camera list...
        for (Camera camera : cameraArrayList) {
            System.out.format("%4s %13s %19s\n", camera.getId(), camera.getSectionId(), camera.getIpAddress());
        }
    }

    public void printDisplayList() {
        System.out.println("\n------ Display List ------");
        System.out.format("%2s %12s %15s %12s\n", "DisplayID", "SectionID", "DisplayNumber", "IP Address");

        // For each camera in the camera list...
        for (Display display : displayArrayList) {
            System.out.format("%4s %13s %13s %19s \n", display.getId(), display.getSectionId(), display.getNumber(), display.getIpAddress());
        }
    }

    public void printSectionList() {
        System.out.println("\n------ Section List ------");
        System.out.format("%2s %8s %13s %16s\n", "SectionID", "LevelID", "TotalSpaces", "AvailableSpaces");

        // For each camera in the camera list...
        for (Section section : sectionArrayList) {
            System.out.format("%4s %10s %12s %14s \n", section.getId(), section.getLevelId(), section.getTotalSpaces(), section.getAvailableSpaces());
        }
    }

    public void printLevelList() {
        System.out.println("\n------ Level List ------");
        System.out.format("%2s %13s %16s\n", "LevelID", "TotalSpaces", "AvailableSpaces");

        // For each camera in the camera list...
        for (Level level : levelArrayList) {
            System.out.format("%4s %12s %14s \n", level.getId(), level.getTotalSpaces(), level.getAvailableSpaces());
        }
    }

    public void printSpaceChangeRecords() {
        System.out.println("\n------ Recently Changed Spaces ------");

        System.out.format("%2s %12s\n", "Camera ID", "ChangedSpaces");

        for (Record record : recordArrayList) {
            System.out.format("%4s %13s\n", record.getCameraId(), record.getChangedSpaces());
        }
    }

}

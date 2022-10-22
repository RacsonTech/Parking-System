package com.smartparking.gui;

import java.sql.*;
import java.util.ArrayList;

public class ParkingGarage {
    private int id;
    private String garageName;
    private int totalSpaces;
    private int numLevels;
    private int availableSpaces;
    private int percentFull;
    private ArrayList<Camera> cameraArrayList;
    private ArrayList<Display> displayArrayList;
    private ArrayList<Section> sectionArrayList;
    private ArrayList<Level> levelArrayList;
    private ArrayList<CameraRecord> recordArrayList;
    private ArrayList<String> levelIdList;
    private ArrayList<ArrayList<String>> sectionIdListByLevel;
    private ArrayList<ArrayList<String>> cameraIdListByLevel;


    // Constructor 1
    public ParkingGarage() {
    }

    // Constructor 2
    public ParkingGarage(MySqlConnection mySqlConnection) throws SQLException {

        // Load parking garage data from the database
        loadData(mySqlConnection.getConnection());

        // Prepare Level ID, Section ID, and Camera ID lists.
        createLevelIdList();
    }

    // Get a list that includes only the level IDs as String.
    public ArrayList<String> getLevelIdList () {
        levelIdList = new ArrayList<>();

        // For every level in the garage, add it to the list
        for(Level currentLevel : levelArrayList) {
            levelIdList.add(String.valueOf(currentLevel.getId()));
        }

        return levelIdList;
    }

    private void createLevelIdList() {
        getLevelIdList();
    }

    public ArrayList<String> getSectionIdListByLevel(int levelId) {

        sectionIdListByLevel = new ArrayList<>();

        // Initialize the array list
        for (int i = 0; i < 10; i++) {
            sectionIdListByLevel.add(i, null);
        }

        for(Level currentLevel : levelArrayList) {

            ArrayList<String> sectionList = new ArrayList<>();

            for(Section currentSection : sectionArrayList) {
                if(currentSection.getLevelId() == currentLevel.getId()) {
                    sectionList.add(String.valueOf(currentSection.getId()));
                }
            }

            sectionIdListByLevel.add(currentLevel.getId(), sectionList);
        }
        return sectionIdListByLevel.get(levelId);
    }

    public ArrayList<String> getCameraIdListBySection(int sectionId) {
        cameraIdListByLevel = new ArrayList<>();

        // Initialize the array list
        for (int i = 0; i < 150; i++) {
            cameraIdListByLevel.add(i, null);
        }

        for(Section currentSection : sectionArrayList) {

            ArrayList<String> cameraList = new ArrayList<>();

            for( Camera currentCamera : cameraArrayList) {
                if(currentCamera.getSectionId() == currentSection.getId()) {
                    cameraList.add(String.valueOf(currentCamera.getId()));
                }
            }
            cameraIdListByLevel.add(currentSection.getId(), cameraList);
        }
        return cameraIdListByLevel.get(sectionId);
    }


    private int calculatePercentFull() {
        return (int)Math.ceil((double)(totalSpaces - availableSpaces)  / totalSpaces);
    }

    public int getPercentFull() {
        return percentFull;
    }

    private void loadData(Connection connection) throws SQLException {

        System.out.println("Initializing the Parking Garage Program ");
        Statement statement = connection.createStatement();

        getGarageInfo(statement);
        getCameraList(statement);
        getDisplayList(statement);
        loadSectionList(statement);
        getLevelList(statement);
        statement.close();
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
        // Find the sectionId where the camera located.
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

    public ArrayList<CameraRecord> getRecordArrayList() {
        return recordArrayList;
    }

    public ArrayList<Level> getLevelList() {
        return levelArrayList;
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

    public void setSpaceChangeRecordArrayList(ArrayList<CameraRecord> record) {
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

    public void updateLevel(int id, int totalSpaces, int availableSpaces) {
        for (Level level : levelArrayList) {
            if (id == level.getId()) {
                level.setTotalSpaces(totalSpaces);
                level.setAvailableSpaces(availableSpaces);
                break;
            }
        }
    }

    public void updateGarageAvailableSpaces(int changedSpaces) {
        availableSpaces += changedSpaces;
        percentFull = calculatePercentFull();
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

        for (CameraRecord record : recordArrayList) {
            System.out.format("%4s %13s\n", record.getCameraId(), record.getChangedSpaces());
        }
    }

    private void getGarageInfo(Statement statement) throws SQLException {

        // Prepare the query
        String query = ("select id, name, total_spaces, available_spaces, levels from garage");

        // Execute the query statement
        System.out.println("Obtaining garage information from the database");
        ResultSet resultSet = statement.executeQuery(query);

        // Store the result
        System.out.println("\n--- Garage Information----");
        resultSet.next();
        setId(resultSet.getInt("id"));
        setGarageName(resultSet.getString("name"));
        setTotalSpaces(resultSet.getInt("total_spaces"));
        setAvailableSpaces(resultSet.getInt("available_spaces"));
        setNumLevels(resultSet.getInt("levels"));

        percentFull = calculatePercentFull();
        printGarageInfo();
        resultSet.close();
    }

    private void getCameraList(Statement statement) throws SQLException {
        int cameraId;
        int sectionId;
        String ipAddress;
        ArrayList<Camera> cameraArrayList = new ArrayList<>();

        // Prepare a new query
        String query = ("select id, section, ipaddress from cameras");

        // Execute the query
        ResultSet resultSet = statement.executeQuery(query);

        // Store the result
        while (resultSet.next()) {
            cameraId = resultSet.getInt("id");
            sectionId = resultSet.getInt("section");
            ipAddress = resultSet.getString("ipaddress");

            Camera camera = new Camera(cameraId, sectionId, ipAddress);

            cameraArrayList.add(camera);
        }

        setCameraArrayList(cameraArrayList);
        printCameraList();
        resultSet.close();
    }

    private void getDisplayList(Statement statement) throws SQLException {
        int displayId;
        int sectionId;
        int displayNumber;
        String ipAddress;
        ArrayList<Display> displayArrayList = new ArrayList<>();

        // Prepare a new query
        String query = ("SELECT id, section, number, ipAddress from displays");

        // Execute the query
        ResultSet resultSet = statement.executeQuery(query);

        // Store the result
        while (resultSet.next()) {
            displayId = resultSet.getInt("id");
            sectionId = resultSet.getInt("section");
            displayNumber = resultSet.getInt("number");
            ipAddress = resultSet.getString("ipaddress");

            Display display = new Display(displayId, sectionId, displayNumber, ipAddress);
            displayArrayList.add(display);
        }

        setDisplayArrayList(displayArrayList);
        printDisplayList();
        resultSet.close();
    }

    private void loadSectionList(Statement statement) throws SQLException {
        int id;
        int levelId;
        int totalSpaces;
        int availableSpaces;
        ArrayList<Section> sectionArrayList = new ArrayList<>();

        // Prepare a new query
        String query = ("SELECT id, level_id, total_spaces, available_spaces from sections");

        // Execute the query
        ResultSet resultSet = statement.executeQuery(query);

        // Store the result
        while (resultSet.next()) {
            id = resultSet.getInt("id");
            levelId = resultSet.getInt("level_id");
            totalSpaces = resultSet.getInt("total_spaces");
            availableSpaces = resultSet.getInt("available_spaces");

            Section section = new Section(id, levelId, totalSpaces, availableSpaces);

            sectionArrayList.add(section);
        }

        setSectionArrayList(sectionArrayList);
        printSectionList();
        resultSet.close();
    }

    private void getLevelList(Statement statement) throws SQLException {
        int id;
        int totalSpaces;
        int availableSpaces;
        ArrayList<Level> levelArrayList = new ArrayList<>();

        // Prepare a new query
        String query = ("SELECT id, total_spaces, available_spaces from levels");

        // Execute the query
        ResultSet resultSet = statement.executeQuery(query);

        // Store the result
        while (resultSet.next()) {
            id = resultSet.getInt("id");
            totalSpaces = resultSet.getInt("total_spaces");
            availableSpaces = resultSet.getInt("available_spaces");

            Level level = new Level(id, totalSpaces, availableSpaces);

            levelArrayList.add(level);
        }

        setLevelArrayList(levelArrayList);
        printLevelList();
        resultSet.close();
    }

    public void reloadOverviewData(Connection dbConnection) throws SQLException {
        // Note: the overview table used in the query is a VIEW table constructed
        // specifically for this query. Every row returned contains the garage info
        // plus the info of one level. So, if there are four levels, then four rows
        // are returned from the DB query. This also means that each row has the
        // garage information fields Name, Capacity, and free spaces repeated in
        // every row. So, instead of running two queries, one of parking garage info
        // and one for the level info, one query is run that obtains both data, with
        // the caveat of returning the garage info multiple times (once per row)
        // unnecessarily. Since there will ever be a few levels in a garage, this
        // does not seem like a big deal.

//        System.out.println("\n--- Reload Garage Overview Data----");
        Statement statement = dbConnection.createStatement();

        // Prepare the query
        String query = ("SELECT * FROM overview");

        // Execute the query statement
        ResultSet resultSet = statement.executeQuery(query);

        resultSet.next();
        // Extract the garage info from the first row in the resultSet.
        garageName =  resultSet.getString("name");
        totalSpaces = resultSet.getInt("garage_capacity");
        availableSpaces = resultSet.getInt("garage_free");
        percentFull  = calculatePercentFull();
//        System.out.println("Garage Name: " + garageName);
//        System.out.println("Capacity: " + totalSpaces);
//        System.out.println("Available Spaces: " + availableSpaces);
//        System.out.println("Percent Full: " + percentFull + "% full");

        // Extract the level info also in the first row of the result set.
        int levelId = Integer.parseInt(resultSet.getString("level_id"));
        int levelCapacity = Integer.parseInt(resultSet.getString("level_capacity"));
        int levelFreeSpaces = Integer.parseInt(resultSet.getString("level_free"));

        updateLevel(levelId, levelCapacity, levelFreeSpaces );

        // Extract the level information from the remaining rows in the resultSet.
        while(resultSet.next()) {
            levelId = Integer.parseInt(resultSet.getString("level_id"));
            levelCapacity = Integer.parseInt(resultSet.getString("level_capacity"));
            levelFreeSpaces = Integer.parseInt(resultSet.getString("level_free"));

            updateLevel(levelId, levelCapacity, levelFreeSpaces );
        }
    }

    public ArrayList<Section> getSectionArrayList() {
        return sectionArrayList;
    }
}

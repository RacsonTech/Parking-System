import java.sql.*;
import java.util.ArrayList;


public class ParkingSystem {


    public static void main(String[] args) throws SQLException {

        // Create a connection to the local MySQL DB
        MySqlConnection mySqlConnection = new MySqlConnection();

        ParkingGarage parkingGarage = new ParkingGarage();

        iniParkingSystem(parkingGarage, mySqlConnection.getConnection());

        getNewCameraLogRecords(parkingGarage, mySqlConnection.getConnection());

        // Close the connection
        (mySqlConnection.getConnection()).close();

//      try {
//        Thread.sleep(5000);
//      } catch (InterruptedException e) {
//        throw new RuntimeException(e);
//      }
//    } // While Loop
    }

    static void iniParkingSystem(ParkingGarage parkingGarage, Connection connection) throws SQLException {

        System.out.println("Initializing the Parking Garage Program ");

        Statement statement = connection.createStatement();
//        Statement statement = connection.createStatement(       // Allows to move up and down the rows and update them
//                ResultSet.TYPE_SCROLL_SENSITIVE,
//                ResultSet.CONCUR_UPDATABLE);

        getGarageInfo(statement, parkingGarage);
        getCameraList(statement, parkingGarage);
        getDisplayList(statement, parkingGarage);
        getSectionList(statement, parkingGarage);
        getLevelList(statement, parkingGarage);
        statement.close();
    }

    static void getLevelList(Statement statement, ParkingGarage parkingGarage) throws SQLException {
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

        parkingGarage.setLevelArrayList(levelArrayList);

        parkingGarage.printLevelList();

        resultSet.close();
    }

    static void getSectionList(Statement statement, ParkingGarage parkingGarage) throws SQLException {
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

        parkingGarage.setSectionArrayList(sectionArrayList);

        parkingGarage.printSectionList();

        resultSet.close();
    }

    static void getGarageInfo(Statement statement, ParkingGarage parkingGarage) throws SQLException {

        // Prepare the query
        String query = ("select name, total_spaces, available_spaces, levels from garage");

        // Execute the query statement
        System.out.println("Obtaining garage information from the database");
        ResultSet resultSet = statement.executeQuery(query);

        // Store the result
        System.out.println("\n--- Garage Information----");
        resultSet.next();
        parkingGarage.setGarageName(resultSet.getString("name"));
        parkingGarage.setTotalSpaces(resultSet.getInt("total_spaces"));
        parkingGarage.setAvailableSpaces(resultSet.getInt("available_spaces"));
        parkingGarage.setNumLevels(resultSet.getInt("levels"));

        parkingGarage.printGarageInfo();

        resultSet.close();
    }

    static void getCameraList(Statement statement, ParkingGarage parkingGarage) throws SQLException {
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

        parkingGarage.setCameraArrayList(cameraArrayList);

        parkingGarage.printCameraList();

        resultSet.close();
    }

    static void getDisplayList(Statement statement, ParkingGarage parkingGarage) throws SQLException {
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

        parkingGarage.setDisplayArrayList(displayArrayList);

        parkingGarage.printDisplayList();

        resultSet.close();
    }

    static void getNewCameraLogRecords(ParkingGarage parkingGarage, Connection connection) throws SQLException {

        Statement statement = connection.createStatement();

        // Prepare the query
        String query = ("select * from new_camera_log_record");

        // Execute the query statement
        ResultSet resultSet = statement.executeQuery(query);

        // Store the result
        ArrayList<SpaceChangeRecord> spaceChangeRecordArrayList = new ArrayList<>();
        int cameraId;
        int changedInSpaces;
        int recordId;

        while (resultSet.next()) {
            recordId = resultSet.getInt("id");
            cameraId = resultSet.getInt("camera_id");
            changedInSpaces = resultSet.getInt("changed_spaces");
            SpaceChangeRecord spaceChangeRecord = new SpaceChangeRecord(recordId, cameraId, changedInSpaces);
            spaceChangeRecordArrayList.add(spaceChangeRecord);
        }

        parkingGarage.setSpaceChangeRecordArrayList(spaceChangeRecordArrayList);
        parkingGarage.printSpaceChangeRecords();

//        // ********* UPDATE column "isNew" to zero ********** //
//
//        // Prepare the query using id as a parameter
//        query = ("UPDATE camera_log SET isNew = 0 WHERE id = ?");
//
//        // Prepare the statement
//        PreparedStatement preparedStatement = connection.prepareStatement(query);
//
//        int rowChanged;
//        for (SpaceChangeRecord spaceChangeRecord : spaceChangeRecordArrayList) {
//
//            // Set Parameters (1 means the 1st ? in the query)
//            preparedStatement.setInt(1, spaceChangeRecord.getRecordId());
//
//            // Execute SQL query
//            rowChanged = preparedStatement.executeUpdate();
//            if (rowChanged == 0) {
//                System.err.println("ERROR: Record ID: " + spaceChangeRecord.getRecordId() + " could not be updated.");
//                System.err.println("The camera_log table will be inaccurate.");
//            }
//        }

        resultSet.close();
        statement.close();

        // TODO: return the cameraRecordArrayList to main to then process the list
        // TODO: process the list:
        //  1) Get the section id of the camera
        //  2) Get the IP address
        //  3) Compute the new number of spaces available in the section
        //  4) Get the LED Display in the same section
        //  5) Send data to the LED display(s)
        //  6) Log the data sent to the displays into the display log
        //  7) Update the new number of spaces available in the section, level, garage tables
        //  8) Update the MongoDB database
        //  9) Repeat.
    }
}













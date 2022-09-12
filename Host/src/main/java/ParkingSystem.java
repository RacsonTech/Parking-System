import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;

public class ParkingSystem {
//    public static final int ID_NOT_FOUND = 9999;

    public static void main(String[] args) throws SQLException {

        // Create a connection to the local MySQL DB
        MySqlConnection mySqlConnection = new MySqlConnection();
        Connection connectionToDB = mySqlConnection.getConnection();

        ParkingGarage parkingGarage = new ParkingGarage();

        // Loads data from the database
        iniParkingSystem(parkingGarage, connectionToDB);

        // Reads new records from the database created by the python script
        getNewCameraLogRecords(parkingGarage, connectionToDB);

        // ******************* Process new records ******************** //
        int cameraId;
        int sectionId;
        int levelId;
        int changedSpaces;
        int sectionAvailableSpaces;

        ArrayList<Record> recordArrayList = parkingGarage.getRecordArrayList();

        // For every record in the list...
        for (Record record : recordArrayList) {

            cameraId = record.getCameraId();
            Section section = parkingGarage.getSection(cameraId);
            sectionId = section.getId();
            levelId = section.getLevelId();
            changedSpaces = record.getChangedSpaces();

            // Update DB Section Table
            updateSectionsTable(sectionId, changedSpaces, connectionToDB);

            // Update local Section variable
            parkingGarage.updateSectionAvailableSpaces(sectionId, changedSpaces);
            System.out.println("\nSection: " + sectionId + " | New available spaces: " + parkingGarage.getSectionAvailableSpaces(levelId));
            // TODO: Error when processing the 6th record from the new records. It seems to be
            //  updating Section 2, when both cameras (id 1 and 2) belong to section 1, so
            //  it should not be updating section 2.

            // Get new number of available spaces from the database
            sectionAvailableSpaces = queryAvailableSpaces(sectionId, connectionToDB);
//            System.out.println("New Available Spaces in Section " + sectionId + " is " + sectionAvailableSpaces);

            // Get the displays ID for the section being processed.
            ArrayList<Display> displayList = parkingGarage.getDisplayList(sectionId);

            // Send the number of available spaces to all the LED signs in this section
            updateDisplaySigns(displayList, sectionAvailableSpaces);
            //TODO: when a display is offline, the display log is updated as if the display was online.
            // A status variable can be added to the display class (and the same with the camera class)
            // so, the code can mark it as offline. A heart beat packet can be implemented (probably on
            // a separate thread. The code should not update the display log table if the display does
            // not respond. The section, level, and garage available spaces DB tables should be updated
            // regardless.

            // Update the DB Display Log Table
            System.out.println("Updating DB Display_log Table");
            updateDisplayLogTable(displayList, sectionAvailableSpaces, connectionToDB);

            // Update DB Level Table
            updateLevelsTable(levelId, changedSpaces, connectionToDB);

            // Update local level variable
            parkingGarage.updateLevelAvailableSpaces(levelId, changedSpaces);
            System.out.println("Level: " + levelId + " | New available spaces: " + parkingGarage.getLevelAvailableSpaces(levelId));

            // TODO:
            // 1) Get the section id of the camera (Done)
            // 2) Get the IP address (Done in the Display List)
            // 3) Compute the new number of spaces available in the section (Done)
            // 4) Get the LED Display in the same section (Done)
            // 5) Send data to the LED display(s) (Partially done, sending data to the displays will be done once the
            //   LED Display PCB is done)
            //  6) Log the data sent to the displays into the display log (Done)
            // TODO:
            //  7) Update the new number of spaces available in:
            //     Section Table and Local Variable (done)
            //     Level Table and Local Variable
            //     Garage tables and Local Variable
            //  8) Update the MongoDB database
            //  9) Repeat.

            // TODO: Fix bug when a section (level or garage) available spaces reaches zero, and the code
            //  tries to update with -1 the number of free spaces, an error is thrown (can't get less
            //  that 0 free spaces.

            //            updateDisplay()
            // for every display in the list...
//            for(Display display : displayList) {
//
//            }
        }

        // Close the connection
        (mySqlConnection.getConnection()).close();
//        EthernetClient.close();
//      try {
//        Thread.sleep(5000);
//      } catch (InterruptedException e) {
//        throw new RuntimeException(e);
//      }
//    } // While Loop
    }

    static void updateLevelsTable(int id, int changedSpaces, Connection connection) throws SQLException {
        // Prepare the query using id as a parameter
        String query = ("UPDATE levels SET available_spaces = available_spaces + ? WHERE id = ?");

        // Prepare the statement
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        // Set Parameters (1 means the 1st "?" in the query, 2 means the 2nd "?" and so on)
        preparedStatement.setInt(1, changedSpaces);
        preparedStatement.setInt(2, id);

        // Execute SQL query
        int rowChanged = preparedStatement.executeUpdate();

        if (rowChanged == 0) {
            System.err.println("ERROR updating the section Table. See updateSectionTable function.");
        }
        preparedStatement.close();
    }

    static void updateDisplayLogTable(ArrayList<Display> displayList, int freeSpaces, Connection connectionToDB) throws SQLException {
        PreparedStatement preparedStatement = null;
        int rowChanged;
        String query;

        // System.out.println("\nProcessing changes: Updating DB Display Log Table");

        // Prepare the query using id as a parameter
        query = ("INSERT INTO display_log (display_id, available_spaces) VALUES (?, ?)");

        // For each display in the list, do the following
        for (Display display : displayList) {

            // Prepare the statement
            preparedStatement = connectionToDB.prepareStatement(query);

            // Set Parameters (1 means the 1st "?" in the query, 2 means the 2nd "?" and so on)
            preparedStatement.setInt(1, display.getId());
            preparedStatement.setInt(2, freeSpaces);

            // Execute SQL query
            rowChanged = preparedStatement.executeUpdate();

            if (rowChanged == 0) {
                System.err.println("ERROR updating the section Table. See updateSectionTable function.");
            }
        }
        assert preparedStatement != null;
        preparedStatement.close();
    }

    static void updateDisplaySigns(ArrayList<Display> displayList, int freeSpaces) {
        String response;
        EthernetClient ethernetClient;


        for (Display display : displayList) {
            System.out.println("Sending: " + freeSpaces + " to display with IP address: " + display.getIpAddress());

//            ethernetClient = new EthernetClient(display.getIpAddress());
//            ethernetClient = new EthernetClient("192.168.1.138");
            ethernetClient = new EthernetClient("localhost");
            try {
                ethernetClient.connect();
                response = EthernetClient.sendData(freeSpaces);

                System.out.println("Response from Server: \"" + response + "\"");

                EthernetClient.close();

            } catch (IOException e) {
                System.out.println("Connection to display timed out");
//                throw new RuntimeException(e);
            }
        }
    }

    static int queryAvailableSpaces(int sectionId, Connection connection) throws SQLException {

        int availableSpaces;

        // Prepare a new query
        String query = ("SELECT available_spaces FROM sections WHERE id = ?");

        // Prepare the statement
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        // Set Parameters (1 means the 1st "?" in the query, 2 means the 2nd "?" and so on)
        preparedStatement.setInt(1, sectionId);

        // Execute SQL query
        ResultSet resultSet = preparedStatement.executeQuery();

        // Retrieve the value
        resultSet.next();
        availableSpaces = resultSet.getInt(1); // Alternative to resultSet.getInt("available_spaces");

        resultSet.close();
        preparedStatement.close();

        return availableSpaces;
    }

    static void updateSectionsTable(int sectionId, int changedSpaces, Connection connection) throws SQLException {

        // System.out.println("\nProcessing changes: Updating DB Section Table");

        // Prepare the query using id as a parameter
        String query = ("UPDATE sections SET available_spaces = available_spaces + ? WHERE id = ?");

        // Prepare the statement
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        // Set Parameters (1 means the 1st "?" in the query, 2 means the 2nd "?" and so on)
        preparedStatement.setInt(1, changedSpaces);
        preparedStatement.setInt(2, sectionId);

        // Execute SQL query
        int rowChanged = preparedStatement.executeUpdate();

        if (rowChanged == 0) {
            System.err.println("ERROR updating the section Table. See updateSectionTable function.");
        }
        preparedStatement.close();
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
        ArrayList<Record> recordArrayList = new ArrayList<>();
        int cameraId;
        int changedInSpaces;
        int recordId;

        while (resultSet.next()) {
            recordId = resultSet.getInt("id");
            cameraId = resultSet.getInt("camera_id");
            changedInSpaces = resultSet.getInt("changed_spaces");
            Record record = new Record(recordId, cameraId, changedInSpaces);
            recordArrayList.add(record);
        }

        parkingGarage.setSpaceChangeRecordArrayList(recordArrayList);
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
    }
}













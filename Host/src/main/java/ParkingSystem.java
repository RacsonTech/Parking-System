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

        // *********** Get Parking Garage Basic Information **********//

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

        // Print the result
        parkingGarage.printGarageInfo();

        resultSet.close();


        // ****************** Get Camera List  ***********************//
        // Prepare a new query
        query = ("select id, section, ipaddress from cameras");

        // Execute the query
        resultSet = statement.executeQuery(query);

        ArrayList<Camera> cameraArrayList = new ArrayList<>();
        int cameraId;
        int sectionId;
        String ipAddress;

        // Store the result
        while (resultSet.next()) {
            cameraId = resultSet.getInt("id");
            sectionId = resultSet.getInt("section");
            ipAddress = resultSet.getString("ipaddress");

            Camera camera = new Camera(cameraId, sectionId, ipAddress);
            cameraArrayList.add(camera);
        }

        parkingGarage.setCameraArrayList(cameraArrayList);

        // Print the result
        System.out.println("\n------ Camera List ------");
        parkingGarage.printCameraList();


        // ****************** Get Display List  ***********************//
        // Prepare a new query
        query = ("SELECT id, section, number, ipAddress from displays");

        // Execute the query
        resultSet = statement.executeQuery(query);

        ArrayList<Display> displayArrayList = new ArrayList<>();
        int displayId;
        int displayNumber;

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

        // Print the result
        System.out.println("\n------ Display List ------");
        parkingGarage.printDisplayList();

        resultSet.close();
        statement.close();
    }


    static void getNewCameraLogRecords(ParkingGarage parkingGarage, Connection connection) throws SQLException {
        System.out.println("\n------ New Camera Log Records ------");

        Statement statement = connection.createStatement();

        // Prepare the query
        String query = ("select * from new_camera_log_record");

        // Execute the query statement
        ResultSet resultSet = statement.executeQuery(query);

        // Store the result
        ArrayList<CameraRecord> cameraRecordArrayList = new ArrayList<>();
        int cameraId;
        int changedInSpaces;
        int recordId;

        while (resultSet.next()) {
            recordId = resultSet.getInt("id");
            cameraId = resultSet.getInt("camera_id");
            changedInSpaces = resultSet.getInt("changed_spaces");
            CameraRecord cameraRecord = new CameraRecord(recordId, cameraId, changedInSpaces);
            cameraRecordArrayList.add(cameraRecord);
        }

        // Print the result
        System.out.format("%2s %12s\n", "Camera ID", "ChangedSpaces");
        for (CameraRecord record : cameraRecordArrayList) {
            System.out.format("%4s %13s\n", record.getCameraId(), record.getChangedSpaces());
        }

        // ********* UPDATE column "isNew" to zero ********** //

        // Prepare the query using id as a parameter
        query = ("UPDATE camera_log SET isNew = 0 WHERE id = ?");

        // Prepare the statement
        PreparedStatement preparedStatement = connection.prepareStatement(query);

        int rowChanged;
        for (CameraRecord cameraRecord : cameraRecordArrayList) {

            // Set Parameters (1 means the 1st ? in the query)
            preparedStatement.setInt(1, cameraRecord.getRecordId());

            // Execute SQL query
            rowChanged = preparedStatement.executeUpdate();
            if (rowChanged == 0) {
                System.err.println("ERROR: Record ID: " + cameraRecord.getRecordId() + " could not be updated.");
                System.err.println("The camera_log table will be inaccurate.");
            }
        }

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













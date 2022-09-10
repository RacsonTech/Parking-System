import java.sql.*;
import java.util.ArrayList;


public class ParkingSystem {


  public static void main(String[] args) throws SQLException {
    // Create a connection to the local MySQL DB
    MySqlConnection mySqlConnection = new MySqlConnection();

    ParkingGarage parkingGarage = new ParkingGarage();

    initializeParkingSystem(parkingGarage, mySqlConnection.getConnection());

    getNewCameraLogRecords(parkingGarage, mySqlConnection.getConnection());

//    List<CameraRecord> cameraRecordList = new ArrayList<CameraRecord>();
//
//
//    //noinspection InfiniteLoopStatement
////    while (true) {
//
//    // Create a statement
//    // Statement statement = connection.createStatement();   // Simple statement
//    Statement statement = connection.createStatement(       // Allows to move up and down the rows and update them
//        ResultSet.TYPE_SCROLL_SENSITIVE,
//        ResultSet.CONCUR_UPDATABLE);
//
//    // TODO: Turn this statement into a Prepared Statement to be able to pass parameters to the query (i.e.,
//    //  passing camera_id 1, 2, 3, etc.). This will probably force us to make separate query to update the column
//    //  "isNew" to zero.
//
//    // Execute a statement
//    ResultSet resultSet = statement.executeQuery("select * from parkingsystem.camera_log where camera_id = 1 AND " +
//        "isNew = 1");
//
//    // Iterate through the result set and print the returned results
//    System.out.println("Results of the Query:\n");
//    System.out.println("Time Stamp\t\t\t\tCamera ID\t\tChanged in Spaces\t\tIsNew");
//
//    int id;
//    int spaces;
//    String time;
//    int isNew;
//
//    while (resultSet.next()) {
//      id = resultSet.getInt("camera_id");
//      spaces = resultSet.getInt("changed_spaces");
//      time = resultSet.getString("time_stamp");
//      isNew = resultSet.getInt("isNew");
//
//      System.out.println(time + "\t\t\t" + id + "\t\t\t\t\t" + spaces + "\t\t\t\t" + isNew);
//
//      cameraRecordList.add(new CameraRecord(id, spaces));
//    }
//
//    // Update the isNew column in the DB to zero
//    System.out.println("Updating \"isNew\" to zero.");
//    resultSet.beforeFirst();
//    while (resultSet.next()) {
//      resultSet.updateInt("isNew", 0);
//      resultSet.updateRow();
//    }
//    resultSet.close();
//
//    System.out.println();
//    System.out.println();
//
//    //*********************************************************************************
//    //     Update Spaces available in the DB
//    //*********************************************************************************
//    // TODO: instead of querying the database every time to find the section where a
//    //  camera is located at (A camera's location would not change after being set),
//    //  the program could run several queries as part of if initialization process to
//    //  find all the information needed about the parking system and keep this data in
//    //  a class object. These queries should be trigger everytime there is an update
//    //  in the parking system like a new camera is added or a new LED display is added.
//    // PART I: Find the section where this camera is located
//
//    // Query that needs the id parameter
//    String query = ("select section from parkingsystem.cameras where id = ?");
//
//    // Prepare the statement
//    PreparedStatement preparedStatement = connection.prepareStatement(query);
//
//    // Get the id to search for from the cameras table (From the first element in the list)
//    CameraRecord singleRecord = cameraRecordList.get(0);
//    id = singleRecord.getCameraId();
//
//    // Set Parameters (1 means the first "?" in the query, 2 = the 2nd "?", and so on)
//    preparedStatement.setInt(1, id);
//
//    // Execute SQL query
//    resultSet = preparedStatement.executeQuery();
//
//    // Display the result
//    resultSet.next();
//    System.out.println("Camera ID " + id +  "\t Section: " + resultSet.getInt("section") );
//
//

    // Close the connection
    (mySqlConnection.getConnection()).close();

//      try {
//        Thread.sleep(5000);
//      } catch (InterruptedException e) {
//        throw new RuntimeException(e);
//      }
//    } // While Loop
  }

  static void initializeParkingSystem(ParkingGarage parkingGarage, Connection connection) throws SQLException {

    System.out.println("Parking Garage Program Initializing");

//    Statement statement = connection.createStatement();
    Statement statement = connection.createStatement(       // Allows to move up and down the rows and update them
        ResultSet.TYPE_SCROLL_SENSITIVE,
        ResultSet.CONCUR_UPDATABLE);

    // Prepare the query
    String query = ("select name, total_spaces, available_spaces, levels from parkingsystem.garage");

    // Execute the query statement
    System.out.println("Obtaining garage information");
    ResultSet resultSet = statement.executeQuery(query);

    // Store the result
    resultSet.next();
    parkingGarage.setGarageName(resultSet.getString("name"));
    parkingGarage.setTotalSpaces(resultSet.getInt("total_spaces"));
    parkingGarage.setAvailableSpaces(resultSet.getInt("available_spaces"));
    parkingGarage.setNumLevels(resultSet.getInt("levels"));

    // Print the result
    parkingGarage.printGarageInfo();

    resultSet.close();

    // Prepare a new query
    query = ("select id, section, ipaddress from parkingsystem.cameras");

    // Execute the query
    resultSet = statement.executeQuery(query);

    ArrayList<Camera> cameraArrayList = new ArrayList<Camera>();
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

    parkingGarage.setCameraList(cameraArrayList);

    // Print the result
    System.out.println("\n------ Camera List ------");
    parkingGarage.printCameraList();

    resultSet.close();
    statement.close();
  }

  static void getNewCameraLogRecords(ParkingGarage parkingGarage, Connection connection) throws SQLException {
    System.out.println("\n------ New Camera Log Records ------");

    Statement statement = connection.createStatement();

    // Prepare the query
    String query = ("select * from parkingsystem.new_camera_log_record");

    // Execute the query statement
    System.out.println("Obtaining new records");
    ResultSet resultSet = statement.executeQuery(query);

    // Store the result
    ArrayList<CameraRecord> cameraRecordArrayList = new ArrayList<>();
    int cameraId;
    int changedInSpaces;

    while (resultSet.next()) {
      cameraId = resultSet.getInt("camera_id");
      changedInSpaces = resultSet.getInt("changed_spaces");
      CameraRecord cameraRecord = new CameraRecord(cameraId, changedInSpaces);
      cameraRecordArrayList.add(cameraRecord);
    }

    // Print the result
    System.out.format("%2s %12s\n", "Camera ID", "ChangedSpaces");
    for (CameraRecord record : cameraRecordArrayList) {
      System.out.format("%4s %13s\n", record.getCameraId(), record.getChangedSpaces());
    }

    resultSet.close();
    statement.close();

    // TODO: update the camera log isNew to zero.
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













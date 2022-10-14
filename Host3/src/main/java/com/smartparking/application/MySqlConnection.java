package com.smartparking.application;

import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;


public class MySqlConnection {
    private String mySqlUrl;
    private String mySqlUser;
    private String mySqlPassword;
    private MysqlDataSource dataSource = null;
    private Connection connection;

    // Constructor
    public MySqlConnection() {

        // Reading the MySQL credentials from the dp.properties file
        System.out.println("Reading MySQL Credentials");
        readMySqlCredentials();

        // Prepare connection to the local MySQL DB
        System.out.println("Preparing Connection to MySQL");
        prepareConnection();

        // Establish a connection to the database
        System.out.print("Connecting...");
        assert dataSource != null;
        connection = null;

        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println(" Failed");
        }

        if (connection != null) {
            System.out.println(" Connected.\n");
        } else {
            System.err.println("Error connecting to MySQL.");
            System.err.println("Program terminated.");
            System.exit(1);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    private void readMySqlCredentials() {

        // Read MySQL credentials from the properties file
        Properties properties = new Properties();
        FileInputStream inputFile;

        try {
            inputFile = new FileInputStream("src\\main\\resources\\db.properties");
            properties.load(inputFile);

            mySqlUrl = properties.getProperty("JAVA_MYSQL_DB_URL");
            mySqlUser = properties.getProperty("JAVA_MYSQL_DB_USERNAME");
            mySqlPassword = properties.getProperty("JAVA_MYSQL_DB_PASSWORD");

            inputFile.close();
            properties.clear();

        } catch (FileNotFoundException e) {
            System.err.println("ERROR: db.properties file not found");
            System.out.println("ERROR: db.properties file not found");
            System.out.println("Program Terminated");
            System.exit(1);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void prepareConnection() {
        dataSource = new MysqlDataSource();
        dataSource.setURL(mySqlUrl);
        dataSource.setUser(mySqlUser);
        dataSource.setPassword(mySqlPassword);
    }
}

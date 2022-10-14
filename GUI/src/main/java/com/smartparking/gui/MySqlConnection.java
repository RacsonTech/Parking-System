package com.smartparking.gui;

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
    private Connection connection = null;

    // Constructor
    public MySqlConnection() {
    }

    public Connection getConnection() {
        return connection;
    }

    public String readMySqlCredentials() {

        // Custom Exception
        class PropertiesException extends Exception {
            public PropertiesException(String str) {
                super(str);
            }
        }

        // Read MySQL credentials from the properties file
        Properties properties = new Properties();
        FileInputStream inputFile;

        try {
            inputFile = new FileInputStream("src\\main\\resources\\db.properties");
            properties.load(inputFile);

            if(properties.size() < 3) {
                throw new PropertiesException("Error reading credentials from db.properties");
            }
            mySqlUrl = properties.getProperty("JAVA_MYSQL_DB_URL");
            mySqlUser = properties.getProperty("JAVA_MYSQL_DB_USERNAME");
            mySqlPassword = properties.getProperty("JAVA_MYSQL_DB_PASSWORD");

            inputFile.close();
            properties.clear();

        } catch (PropertiesException | IOException e) {
            return e.getMessage();
        }

        prepareConnection();

        return null;
    }

    private void prepareConnection() {
        dataSource = new MysqlDataSource();
        dataSource.setURL(mySqlUrl);
        dataSource.setUser(mySqlUser);
        dataSource.setPassword(mySqlPassword);
    }

    public String connect () {

        // Establish a connection to the database
        System.out.print("Connecting...");
        assert dataSource != null;
        connection = null;

        try {
            connection = dataSource.getConnection();
            System.out.println(" Connected.\n");
        }
        catch (SQLException e) {
            return e.getMessage();
        }
        return null;
    }

    public String disconnect() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            return e.getMessage();
        }
        return null;
    }

    public String getServerAddress() {
        return mySqlUrl;
    }
}

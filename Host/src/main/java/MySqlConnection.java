import com.mysql.cj.jdbc.MysqlDataSource;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;
import java.sql.SQLException;


public class MySqlConnection {
    private String mySqlUrl;
    private String mySqlUser;
    private String mySqlPassword;
    private MysqlDataSource dataSource = null;
    private Connection connection;

    public MySqlConnection() throws SQLException {

        // Reading the MySQL credentials from the dp.properties file
        System.out.println("Reading MySQL Credentials");
        readMySqlCredentials();

        // Prepare connection to the local MySQL DB
        System.out.println("Preparing Connection to MySQL");
        prepareConnection();

        // Establish a connection to the database
        System.out.print("Connecting...");
        assert dataSource != null;
        connection = dataSource.getConnection();
        System.out.println("Connected.\n");
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

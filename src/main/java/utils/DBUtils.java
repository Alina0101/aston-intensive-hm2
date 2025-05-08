package utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class DBUtils {

    private static final String PROPERTIES_FILE = "db.properties";

    public static Connection getConnection() throws Exception {
        Properties props = new Properties();
        try (InputStream input = DBUtils.class.getClassLoader().getResourceAsStream(PROPERTIES_FILE)) {
            props.load(input);
        }

        String url = props.getProperty("db.url");
        String user = props.getProperty("db.username");
        String password = props.getProperty("db.password");

        Connection connection = DriverManager.getConnection(url, user, password);
        return connection;
    }
}

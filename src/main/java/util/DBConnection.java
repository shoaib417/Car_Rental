package util;

import java.sql.*;

public class DBConnection {
	
	static Connection connection = null;
	
    public static Connection getConnection() {
    	String propstr=DBPropertyUtil.getPropertyString("db.properties");
		connection=DBConnUtil.getConnection(propstr);
		return connection;
    }
}

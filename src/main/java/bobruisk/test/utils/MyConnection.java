package bobruisk.test.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MyConnection {

	public static final String URL = "jdbc:postgresql://127.0.0.1:5432/test";
	private final static String LOGIN = "postgres";
	private final static String PASS = "1111";
	static Connection connection = null;
	
	static {
		try {
			Class.forName("org.postgresql.Driver");
			System.out.println("--- Драйвер успешно загружен! ---\n");
			connection = DriverManager.getConnection(URL, LOGIN, PASS);
		} catch (ClassNotFoundException e) {
			System.err.println("Драйвер не найден!");
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConnection() throws SQLException {
		return (Connection) DriverManager.getConnection(URL, LOGIN, PASS);
	}
}

package me.xwang1024.sifResExplorer.data.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SqlLiteConnection {
	private Map<String, Connection> map = Collections
			.synchronizedMap(new HashMap<String, Connection>());
	private static SqlLiteConnection instance;

	private SqlLiteConnection() {
	}

	public static SqlLiteConnection getInstance() {
		if (instance == null) {
			instance = new SqlLiteConnection();
		}
		return instance;
	}

	public Connection getConnection(String dbPath) throws SQLException, ClassNotFoundException {
		if (map.containsKey(dbPath)) {
			return map.get(dbPath);
		}
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
		map.put(dbPath, conn);
		return conn;
	}
}

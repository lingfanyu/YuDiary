package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.jolbox.bonecp.BoneCPDataSource;

public class DBFactory {
	public static Connection getConnection() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
		Connection conn = DriverManager.getConnection("jdbc:sqlite:jnote.db");
		return conn;
	}

	public static DataSource getDataSource() {
		BoneCPDataSource dds = null;
		try {
			Class.forName("org.sqlite.JDBC");
			dds = new BoneCPDataSource();
			dds.setJdbcUrl("jdbc:sqlite:" + DbFile.getDbfilepath());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return dds;
	}

}

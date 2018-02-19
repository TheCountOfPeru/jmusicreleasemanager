package jmusicreleasemanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
/**
 * My custom Driver for JDBC
 * @author Jonathan
 *
 */
public class sqlfunctions {
	private Connection myConn;
	private Statement myStmt;
	private ResultSet myRs;
	
	public sqlfunctions() {
		try {
			String URL = "jdbc:mysql://localhost:3306/mydb?autoReconnect=true&useSSL=false";
			myConn = DriverManager.getConnection(URL, "root", "?t5$Yk^Op;gXtY~1[qs%]5?eb12U>NFL7F.KkshmFkmcRMpy8eGGf2Z&n|VE2C[$");
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("SELECT * FROM mydb.releases;");
		}catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Connected to the database....");
		
	}
	public void insertData(sqlfunctions sqlfunctionsDriver, String artist, String releasename, String releasetype, DATE date, 
			String url, String edition, String label, String catalog, String musicbrainz, String discogs) {
		String query = " insert into releases (artist, releasename, releasetype, date, url, edition, label, catalog, musicbrainz, discogs)"
				+" values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	}

}

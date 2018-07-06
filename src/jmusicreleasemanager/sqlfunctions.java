package jmusicreleasemanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * My custom Driver for JDBC
 * @author Jonathan
 *
 */
public class sqlfunctions {
	private Connection myConn;
	private Statement myStmt;
	private ResultSet myRs;
	/**
	 * Constructor for the JDBC driver. Connects to a localhost mysql database.
	 */
	public sqlfunctions() {
		try {
			String URL = "jdbc:mysql://localhost:3306/jmusicrelease?autoReconnect=true&useSSL=false";
			myConn = DriverManager.getConnection(URL, "root", "rootpass");
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("SELECT * FROM jmusicrelease.artist;");
		}catch (SQLException e) {
			System.out.println("Error. Unable to create the Driver for JDBC.");
			e.printStackTrace();
		}
		System.out.println("Connected to the database....");
		
	}
	public void insertData(sqlfunctions sqlfunctionsDriver, String ARTIST, String ReleaseName, String ReleaseType, java.sql.Date ReleaseDate, 
			String URL, String Edition, String Label, String Catalog, String musicbrainz, String discogs) {
		String query = " insert into releases (ARTIST, ReleaseName, ReleaseType, ReleaseDate, URL, Edition, Label, Catalog, musicbrainz, discogs)"
				+" values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = sqlfunctionsDriver.myConn.prepareStatement(query);
			preparedStmt.setString (1, ARTIST);
			preparedStmt.setString (2, ReleaseName);
			preparedStmt.setString (3, ReleaseType);
			preparedStmt.setDate(4, ReleaseDate);
	      	preparedStmt.setString (5, URL);
	      	preparedStmt.setString (6, Edition);
	      	preparedStmt.setString (7, Label);
	      	preparedStmt.setString (8, Catalog);
	      	preparedStmt.setString (9, musicbrainz);
	      	preparedStmt.setString (10, discogs);
	      	preparedStmt.execute();
		} catch (SQLException e) {
			System.out.println("Error. Unable to insert into the database.");
			e.printStackTrace();
		}
	}
	public void updateData(sqlfunctions sqlfunctionsDriver, String ARTIST, String ReleaseName, String ReleaseType, java.sql.Date ReleaseDate, 
			String URL, String Edition, String Label, String Catalog, String musicbrainz, String discogs) {
		//java.sql.Date sqlDate = new java.sql.Date(ReleaseDate.getTime());
		String query = " update releases set ARTIST = ?, ReleaseName = ?, ReleaseType = ?, ReleaseDate = ?, "
				+ "URL = ?, Edition = ?, Label = ?, Catalog = ?, musicbrainz = ?, discogs = ? where "
				+ "ReleaseName = ? and ReleaseDate = ? and Edition = ?;";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = sqlfunctionsDriver.myConn.prepareStatement(query);
			preparedStmt.setString (1, ARTIST);
			preparedStmt.setString (2, ReleaseName);
			preparedStmt.setString (3, ReleaseType);
			preparedStmt.setDate(4, ReleaseDate);
	      	preparedStmt.setString (5, URL);
	      	preparedStmt.setString (6, Edition);
	      	preparedStmt.setString (7, Label);
	      	preparedStmt.setString (8, Catalog);
	      	preparedStmt.setString (9, musicbrainz);
	      	preparedStmt.setString (10, discogs);
	      	preparedStmt.setString (11, ReleaseName);
	      	preparedStmt.setDate(12, ReleaseDate);
	      	preparedStmt.setString (13, Edition);
	      	preparedStmt.execute();
		} catch (SQLException e) {
			System.out.println("Error. Unable to update the database.");
			e.printStackTrace();
		}
	}
	public boolean check_dup(sqlfunctions sqlfunctionsDriver, String ARTIST, String ReleaseName, String ReleaseType, String Edition, java.sql.Date ReleaseDate) {
		String query = "SELECT * FROM releases WHERE ARTIST= ? And ReleaseName= ? And ReleaseType= ? And Edition= ? And ReleaseDate= ?";
		PreparedStatement preparedStmt;
		boolean check = false;
		try {
			preparedStmt = sqlfunctionsDriver.myConn.prepareStatement(query);
			preparedStmt.setString (1, ARTIST);
			preparedStmt.setString (2, ReleaseName);
			preparedStmt.setString (3, ReleaseType);
			preparedStmt.setString (4, Edition);
			preparedStmt.setDate (5, ReleaseDate);
			ResultSet resultSet = preparedStmt.executeQuery(query);
			if(resultSet.next()){
	            check =  true;
	        }else{
	            check = false;
	        }
		} catch (SQLException e) {
			System.out.println("Error. Unable to check for duplicates in the database.");
			e.printStackTrace();
		}
		return check;
		
	}
}

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
			String URL = "jdbc:mysql://localhost:3306/mydb?autoReconnect=true&useSSL=false";
			myConn = DriverManager.getConnection(URL, "root", "?t5$Yk^Op;gXtY~1[qs%]5?eb12U>NFL7F.KkshmFkmcRMpy8eGGf2Z&n|VE2C[$");
			myStmt = myConn.createStatement();
			myRs = myStmt.executeQuery("SELECT * FROM mydb.releases;");
		}catch (SQLException e) {
			System.out.println("Error. Unable to create the Driver for JDBC.");
			e.printStackTrace();
		}
		System.out.println("Connected to the database....");
		
	}
	/**
	 * The main function for inserting into a database
	 * @param sqlfunctionsDriver
	 * @param ARTIST
	 * @param ReleaseName
	 * @param ReleaseType
	 * @param ReleaseDate
	 * @param URL
	 * @param Edition
	 * @param Label
	 * @param Catalog
	 * @param musicbrainz
	 * @param discogs
	 */
	public void insertData(sqlfunctions sqlfunctionsDriver, String ARTIST, String ReleaseName, String ReleaseType, Date ReleaseDate, 
			String URL, String Edition, String Label, String Catalog, String musicbrainz, String discogs) {
		String query = " insert into releases (ARTIST, ReleaseName, ReleaseType, ReleaseDate, URL, Edition, Label, Catalog, musicbrainz, discogs)"
				+" values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	}
	/**
	 * The main function for updating entries in a database
	 * @param sqlfunctionsDriver
	 * @param ARTIST
	 * @param ReleaseName
	 * @param ReleaseType
	 * @param ReleaseDate
	 * @param URL
	 * @param Edition
	 * @param Label
	 * @param Catalog
	 * @param musicbrainz
	 * @param discogs
	 */
	public void updateDate(sqlfunctions sqlfunctionsDriver, String ARTIST, String ReleaseName, String ReleaseType, Date ReleaseDate, 
			String URL, String Edition, String Label, String Catalog, String musicbrainz, String discogs) {
		java.sql.Date sqlDate = new java.sql.Date(ReleaseDate.getTime());
		String query = " update releases set ARTIST = ?, ReleaseName = ?, ReleaseType = ?, ReleaseDate = ?, "
				+ "URL = ?, Edition = ?, Label = ?, Catalog = ?, musicbrainz = ?, discogs = ? where "
				+ "ReleaseName = ? and ReleaseDate = ? and Edition = ?;";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = sqlfunctionsDriver.myConn.prepareStatement(query);
			preparedStmt.setString (1, ARTIST);
			preparedStmt.setString (2, ReleaseName);
			preparedStmt.setString (3, ReleaseType);
			preparedStmt.setDate(4, sqlDate);
	      	preparedStmt.setString (5, URL);
	      	preparedStmt.setString (6, Edition);
	      	preparedStmt.setString (7, Label);
	      	preparedStmt.setString (8, Catalog);
	      	preparedStmt.setString (9, musicbrainz);
	      	preparedStmt.setString (10, discogs);
	      	preparedStmt.setString (11, ReleaseName);
	      	preparedStmt.setDate(12, sqlDate);
	      	preparedStmt.setString (13, Edition);
	      	preparedStmt.execute();
		} catch (SQLException e) {
			System.out.println("Error. Unable to update the database.");
			e.printStackTrace();
		}
	}
}

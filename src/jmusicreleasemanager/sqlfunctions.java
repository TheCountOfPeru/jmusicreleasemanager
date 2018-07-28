package jmusicreleasemanager;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;
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
	/**
	 * Retrieves and artist's id number from the db given the artist's name
	 * An existing artist will have a non zero and non negative number
	 * @param sqlfunctionsDriver
	 * @param ARTIST
	 * @return
	 */
	public int retrieveArtistId(sqlfunctions sqlfunctionsDriver, String ARTIST) {
		String query = "SELECT id FROM artist WHERE name=?;";
		PreparedStatement preparedStmt;
		ResultSet resultSet;
		try {
			preparedStmt = sqlfunctionsDriver.myConn.prepareStatement(query);
			preparedStmt.setString (1, ARTIST);
			resultSet = preparedStmt.executeQuery();
			if(resultSet.next()) {
				return resultSet.getInt("id");
			}
		}catch (SQLException e) {
			System.out.println("Error. Unable to retrieve an artist from the database.");
			e.printStackTrace();
		}
		return 0;
		
	}
	/**
	 * Inserts a new row in the release table
	 * @param sqlfunctionsDriver
	 * @param ARTIST
	 * @param ReleaseName
	 * @param ReleaseType
	 * @param ReleaseDate
	 * @param URL
	 * @return
	 */
	public int insertData(sqlfunctions sqlfunctionsDriver, String ARTIST, String ReleaseName, String ReleaseType, java.sql.Date ReleaseDate, 
			String URL) {
		//get the artist id
		int artistId = retrieveArtistId(sqlfunctionsDriver, ARTIST);
		if(artistId == 0) {//when no artist exists need to create a new artist entry
			addArtist(sqlfunctionsDriver, ARTIST, "");//Kanji is ignored here
			//get the artist id
			artistId = retrieveArtistId(sqlfunctionsDriver, ARTIST);
		}
		boolean exists = check_dup(sqlfunctionsDriver, artistId, ReleaseName, ReleaseType, ReleaseDate);
			if(!exists) {
				String query = "INSERT INTO jmusicrelease.release (name, date, type, artistId, url)"
						+" values (?, ?, ?, ?, ?)";
				
				PreparedStatement preparedStmt;
				try {
					preparedStmt = sqlfunctionsDriver.myConn.prepareStatement(query);
					preparedStmt.setString (1, ReleaseName);
					preparedStmt.setDate(2, ReleaseDate);
					preparedStmt.setString (3, ReleaseType);
					preparedStmt.setInt(4, artistId);
			      	preparedStmt.setString (5, URL);
			      	preparedStmt.execute();
				} catch (SQLException e) {
					System.out.println("Error. Unable to insert a new release into the database.");
					e.printStackTrace();
				}
			}
			else {
				return 0; //record already exists
			}
	return 1; //No need to cancel any insertions
	}
	
	/**
	 * Adds a new artist to the artist table
	 * @param artist
	 */
	public void addArtist(sqlfunctions sqlfunctionsDriver, String name, String kanji) {
		String query = " INSERT INTO jmusicrelease.artist (name, kanji)"
				+" values (?, ?)";
		PreparedStatement preparedStmt;
		try {
			preparedStmt = sqlfunctionsDriver.myConn.prepareStatement(query);
			preparedStmt.setString (1, name);
			preparedStmt.setString(2, kanji);
	      	preparedStmt.execute();
		} catch (SQLException e) {
			System.out.println("Error. Unable to insert a new artist into the database.");
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Checks if a row exists according to given conditions
	 * @param sqlfunctionsDriver
	 * @param artistId
	 * @param ReleaseName
	 * @param ReleaseType
	 * @param ReleaseDate
	 * @return
	 */
	public boolean check_dup(sqlfunctions sqlfunctionsDriver, int artistId, String ReleaseName, String ReleaseType, java.sql.Date ReleaseDate) {
		String query = "SELECT * FROM jmusicrelease.release WHERE artistId = ? And name = ? And type = ? And date = ?";
		PreparedStatement preparedStmt;
		boolean check = false;
		try {
			preparedStmt = sqlfunctionsDriver.myConn.prepareStatement(query);
			preparedStmt.setInt (1, artistId);
			preparedStmt.setString (2, ReleaseName);
			preparedStmt.setString (3, ReleaseType);
			preparedStmt.setDate (4, ReleaseDate);
			ResultSet resultSet = preparedStmt.executeQuery();
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
	public ResultSet retrieveReleases(sqlfunctions sqlfunctionsDriver, java.sql.Date afterD, java.sql.Date beforeD){
		PreparedStatement preparedStmt;
		ResultSet rs = null;
		String query = "SELECT release.name, artist.name AS artist, release.date, release.type\r\n" + 
				"FROM jmusicrelease.release, jmusicrelease.artist\r\n" + 
				"WHERE release.artistId=artist.id AND release.date >= '"+afterD.toString()+"'";
		if(beforeD!=null)
		{
			query+="AND release.date <= '"+beforeD.toString()+"'";
		}
				 
		query+="\nORDER BY release.date;";
		try {
			preparedStmt = sqlfunctionsDriver.myConn.prepareStatement(query);
			rs = preparedStmt.executeQuery();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return rs;
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
}

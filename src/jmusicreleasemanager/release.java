package jmusicreleasemanager;

import java.util.Date;

public class release {
	private String artist;
	private String rname;
	private String rtype;
	private Date date;

	private String url;

	
	
	public release(String artist, String rname, String rtype, Date date, String url) {
		this.artist = artist;
		this.rname = rname;
		this.rtype = rtype;
		this.date = date;
		this.url = url;

	}
	public release(String artist, String rname, String rtype, Date date) {
		this(artist, rname, rtype, date, "");
	}
}

package jmusicreleasemanager;

public class release {
	private String artist;
	private String rname;
	private String rtype;
	private DATE date;
	private String edition;
	private String url;
	private String label;
	private String catalog;
	private String musicbrainz;
	private String discogs;
	
	
	public release(String artist, String rname, String rtype, DATE date, String edition, String url, String label, String catalog,
			String musicbrainz, String discogs) {
		this.artist = artist;
		this.rname = rname;
		this.rtype = rtype;
		this.date = date;
		this.edition = edition;
		this.url = url;
		this.edition = label;
		this.catalog = catalog;
		this.musicbrainz = musicbrainz;
		this.discogs = discogs;
	}
	public release(String artist, String rname, String rtype, DATE date) {
		this(artist, rname, rtype, date, "", "", "", "", "", "");
	}
}

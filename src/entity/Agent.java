package entity;
import java.time.LocalDate;
import java.util.ArrayList;

public class Agent 
{
	private int id;
	private String name;
	private String mail;
	private ArrayList<Artist> artistsList;
	
	
	
	//Contractor
	public Agent(int id, String name, String mail) 
	{
		super();
		this.id = id;
		this.name = name;
		this.mail = mail;
		this.artistsList = new ArrayList<Artist>();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public ArrayList<Artist> getArtistsList() {
		return artistsList;
	}

	public void setArtistsList(ArrayList<Artist> artistsList) {
		this.artistsList = artistsList;
	}
	
	

	@Override
	public String toString() {
		return getName();
	}

	public void getReportByDate(LocalDate start, LocalDate end) {
		throw new UnsupportedOperationException();
	}

	public void addSinger() {
		throw new UnsupportedOperationException();
	}

	public void addBand() {
		throw new UnsupportedOperationException();
	}
}

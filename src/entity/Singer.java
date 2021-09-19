package entity;
import util.gender;

public class Singer extends Artist 
{

	public Singer(int id, String name, String myJaner, int idOfAgent) {
		super(id, name, myJaner, idOfAgent);
		// TODO Auto-generated constructor stub
	}

	private gender gender;

	public Singer(int id, String name, String myJaner, int idOfAgent, util.gender gender) {
		super(id, name, myJaner, idOfAgent);
		this.gender = gender;
	}

	public gender getGender() {
		return gender;
	}

	public void setGender(gender gender) {
		this.gender = gender;
	}

	@Override
	public String toString() {
		return "Singer [gender=" + gender + "]";
	}
	
	
}

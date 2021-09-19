package entity;

import java.util.ArrayList;

public class Capsule {

	private int id;
	private int numberOfSeats;
	
	public Capsule(int id, int numberOfSeats) {
		super();
		this.id = id;
		this.numberOfSeats = numberOfSeats;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getNumberOfSeats() {
		return numberOfSeats;
	}
	public void setNumberOfSeats(int numberOfSeats) {
		this.numberOfSeats = numberOfSeats;
	}
	
	
	
}

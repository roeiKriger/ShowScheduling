package entity;

import control.ControlShow;

public class Theater 
{
	private int theaterID;
	private String theaterName;
	private int maxCapacity;
	private Manager manager;
	private City city;
	

	public Theater(int theaterID, String theaterName, int maxCapacity, int managerID, int cityID) 
	{
		this.theaterID =theaterID;
		this.theaterName = theaterName;
		this.maxCapacity = maxCapacity;
		this.manager = ControlShow.getInstance().getManagerByID(managerID);
		this.city = ControlShow.getInstance().getCityByID(cityID);
		
	}


	public int getTheaterID() {
		return theaterID;
	}


	public String getTheaterName() {
		return theaterName;
	}


	public void setTheaterName(String theaterName) {
		this.theaterName = theaterName;
	}


	public int getMaxCapacity() {
		return maxCapacity;
	}


	public void setMaxCapacity(int maxCapacity) {
		this.maxCapacity = maxCapacity;
	}


	public Manager getManager() {
		return manager;
	}


	public void setManager(Manager manager) {
		this.manager = manager;
	}


	public City getCity() {
		return city;
	}


	public void setCity(City city) {
		this.city = city;
	}


	public void setCapsuleSize() {
		throw new UnsupportedOperationException();
	}


	@Override
	public String toString() {
		return "Theater " + getTheaterName() + ", In City " +getCity().getCityName() +", Capacity: " + getMaxCapacity() ;
	}
	
	
}

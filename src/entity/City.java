package entity;

import control.ControlShow;
import util.Color;

public class City 
{
	private int cityId;
	private String cityName;
	private Color cityColor;
	
	
	public City(int id ,String cityName, int colorID) 
	{
		super();
		this.cityId = id;
		this.cityName = cityName;
		this.cityColor = ControlShow.getInstance().getColorByID(colorID);
	}
	
	
	
	public City(String cityName, String colorName) 
	{
		super();
		this.cityName = cityName;
		colorName = colorName.toLowerCase();
		switch (colorName)
		{
		case "red":
			this.cityColor = ControlShow.getInstance().getColorByID(1);
			break;
		case "yellow":
			this.cityColor = ControlShow.getInstance().getColorByID(2);
			break;
		default:
			this.cityColor = ControlShow.getInstance().getColorByID(3);
		}
	}



	public int getCityId() {
		return cityId;
	}

	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public Color getCityColor() {
		return cityColor;
	}
	public void setCityColor(Color cityColor) {
		this.cityColor = cityColor;
	}
	@Override
	public String toString() {
		return "City [cityName=" + cityName + ", cityColor=" + cityColor.getName() + "]";
	}
	
	
}

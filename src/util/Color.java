package util;

public class Color 
{
	private int colorID;
	private flashLightColor name;
	private int maxInCapsule;
	private int maxInShow;
	
	public Color(int colorID, flashLightColor name, int maxInCapsule, int maxInShow) {
		super();
		this.colorID = colorID;
		this.name = name;
		this.maxInCapsule = maxInCapsule;
		this.maxInShow = maxInShow;
	}
	
	public Color(int colorID, String name, int maxInCapsule, int maxInShow) {
		super();
		this.colorID = colorID;
		this.name = this.nameToEnum(name);
		this.maxInCapsule = maxInCapsule;
		this.maxInShow = maxInShow;
	}
	public Color(String maxInTheater, String maxInCapsuleStr) 
	{
		maxInShow = Integer.parseInt(maxInTheater);
		maxInCapsule = Integer.parseInt(maxInCapsuleStr);
	}

	public flashLightColor getName() {
		return name;
	}
	public void setName(flashLightColor name) {
		this.name = name;
	}
	
	public int getId()
	{
		return this.colorID;
	}
	
	public void setName(String name) {
		this.name = this.nameToEnum(name);
	}
	public int getMaxInCapsule() {
		return maxInCapsule;
	}
	public void setMaxInCapsule(int maxInCapsule) {
		this.maxInCapsule = maxInCapsule;
	}
	public int getMaxInShow() {
		return maxInShow;
	}
	public void setMaxInShow(int maxInShow) {
		this.maxInShow = maxInShow;
	}
	
	//return flashlight colors by typing String
	private flashLightColor nameToEnum(String name)
	{
		if(name.equals("Red"))
		{
			return flashLightColor.Red;
		}
		else if(name.equals("Yellow"))
		{
			return flashLightColor.Yellow;
		}
		else
		{
			return flashLightColor.Green;
		}
		
	}

	@Override
	public String toString() {
		return   name.toString() ;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + maxInCapsule;
		result = prime * result + maxInShow;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Color other = (Color) obj;
		if (maxInCapsule != other.maxInCapsule)
			return false;
		if (maxInShow != other.maxInShow)
			return false;
		if (name != other.name)
			return false;
		return true;
	}
	
	
	
	
}

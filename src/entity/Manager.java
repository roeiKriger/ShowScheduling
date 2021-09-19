package entity;
public class Manager 
{
	private int managerId;
	private String name;
	
	public Manager(int managerId, String name) {
		super();
		this.managerId = managerId;
		this.name = name;
	}

	public int getManagerId() {
		return managerId;
	}

	public void setManagerId(int managerId) {
		this.managerId = managerId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "Manager is " + name;
	}
	
	
	
}

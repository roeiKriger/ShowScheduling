package entity;
import util.janer;

public class Artist {

	private int id;
	private String name;
	private janer janer;
	private int idOfAgent;
	
	
	public Artist(int id, String name, String myJaner, int idOfAgent) 
	{
		super();
		this.id = id;
		this.name = name;		
		this.janer = setTheRealJaner(myJaner);
		this.idOfAgent = idOfAgent;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getIdOfAgent() {
		return idOfAgent;
	}
	public void setIdOfAgent(int idOfAgent) {
		this.idOfAgent = idOfAgent;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public janer getJaner() {
		return janer;
	}
	public void setJaner(janer janer) {
		this.janer = janer;
	}
	
	public janer setTheRealJaner(String myJaner) 
	{
		if(myJaner.equals("Metal"))
			return util.janer.Metal;
		if(myJaner.equals("Pop"))
			return util.janer.Pop;
		if(myJaner.equals("Queen"))
			return util.janer.Queen;
		if(myJaner.equals("Rock"))
			return util.janer.Rock;
		if(myJaner.equals("Jaz"))
			return util.janer.Jaz;
		if(myJaner.equals("Acustic"))
			return util.janer.Acustic;
		if(myJaner.equals("Diva"))
			return util.janer.Diva;
		if(myJaner.equals("Rap"))
			return util.janer.Rap;
		if(myJaner.equals("Kpop"))
			return util.janer.Kpop;		
		return util.janer.Pop;
	}
	@Override
	public String toString() {
		return "id: "+ id+", Artist name: " + name + ", janer " + janer;
	}
	
}

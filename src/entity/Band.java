package entity;

public class Band extends Artist {

	public Band(int id, String name, String myJaner, int idOfAgent) {
		super(id, name, myJaner, idOfAgent);
		// TODO Auto-generated constructor stub
	}

	private int amount;

	public Band(int id, String name, String myJaner, int idOfAgent, int amount) {
		super(id, name, myJaner, idOfAgent);
		this.amount = amount;
	}

	public int getAmount() {
		return amount;
	}

	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public String toString() {
		return "Band [amount=" + amount + "]";
	}
	
	
}

package control;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import entity.Agent;
import entity.Manager;
import entity.Show;
import entity.ShowInTheater;
import entity.Theater;
import entity.User;
import util.Consts;

public class LoginControl 
{
	
	
	public static LoginControl instance;
	public static LoginControl getInstance() 
	{
		if (instance == null)
			instance = new LoginControl();
		return instance;
	}
	
	private User loginCustumer;
	
	
	public User getLoginUser() 
	{
		return loginCustumer;
	}


	public void setLoginUser(User loginCustumer) 
	{
		this.loginCustumer = loginCustumer;
	}


	//This method get all custumers from DB
	public ArrayList<User> getAllUsers()
	{
		ArrayList<User> myCutstumers = new ArrayList<>();
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(util.Consts.SQL_SEL_ALL_USERS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					myCutstumers.add(new User(rs.getString(i++), rs.getString(i++), rs.getString(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return myCutstumers;	
		
	}
	
	//This method return true if the user is agent
	public boolean checkAgent(User u)
	{
		ArrayList<Agent> agents = ReportControl.getInstance().getAgents();
		for(Agent a: agents)
		{
			if(a.getName().equals(u.getUsername()))
			{
				return true;
			}
		}
		return false;
	}
	
	//This method get user and return the agent
	public Agent getAgent(User u)
	{
		ArrayList<Agent> agents = ReportControl.getInstance().getAgents();
		for(Agent a: agents)
		{
			if(a.getName().equals(u.getUsername()))
			{
				return a;
			}
		}
		return null;
	}
	

}

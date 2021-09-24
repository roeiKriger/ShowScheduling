package control;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JFrame;

import entity.*;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.swing.JRViewer;
import util.Consts;


public class ReportControl 
{
	public static ReportControl instance;
	
	public static ReportControl getInstance() {
		if (instance == null)
			instance = new ReportControl();
		return instance;
	}
	
	
	/**
	 * Gets all the shows from the database
	 * @return ArrayList of Shows
	 */
	
	public ArrayList<Agent> getAgents() 
	{
		ArrayList<Agent> agentList = new ArrayList<Agent>();
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(util.Consts.SQL_SEL_ALL_AGENTS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					agentList.add(new Agent(rs.getInt(i++),rs.getString(i++),rs.getString(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return agentList;	
	}
	
	
	public JFrame produceReport(Agent agentNum, Date start, Date end) 
	{
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR))
			{
				HashMap<String, Object> params = new HashMap<>();

				params.put("agent_id", (Integer) agentNum.getId());
				params.put("start",new java.sql.Date(start.getTime()));
				params.put("end",new java.sql.Date(end.getTime()));
				JasperPrint print = JasperFillManager.fillReport(
						getClass().getResourceAsStream("/boundary/AgentReport.jasper"),
						params, conn);
				JFrame frame = new JFrame("Show Report for Agent " + agentNum.getName());
				frame.getContentPane().add(new JRViewer(print));
				frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
				frame.pack();
				return frame;
			}
			catch (Exception e) {
				e.printStackTrace();
			}

		}
		catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	
	//This method calculates (sums up) all artist in show
	private int calcArtistInShow(int show_id)
	{
		int counter=0;
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_SEL_ALL_ARTIST_IN_SHOW)){
				stmt.setInt(1,show_id);
				ResultSet rs = stmt.executeQuery();{
					while (rs.next()) 
					{
						counter++;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return counter;
	}
	
	
	//this method calculate (sums up) all artist in show by agent
	public int calcArtistOfAgentInShow(int show_id, int agent_id)
	{
		int counter=0;
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_SEL_ALL_ARTIST_IN_SHOW_BY_AGENT)){
				stmt.setInt(1,show_id);
				stmt.setInt(2,agent_id);
				ResultSet rs = stmt.executeQuery();{
					while (rs.next()) 
					{
						counter++;
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return counter;
	}
	
	//this method gets Agent and Show and return the percent of artist by agent in show
	public double calcArtistPrecent(int show_ID , int agent_ID) 
	{
		int byAgent = calcArtistOfAgentInShow(show_ID,agent_ID);
		int allArtist = calcArtistInShow(show_ID);
		return (double) byAgent * 100/allArtist;	
	}

	
}

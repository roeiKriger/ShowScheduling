package control;
import java.awt.TextField;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.chrono.ChronoLocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

import entity.*;
import exceptions.NegativeNumberException;
import groovy.ui.SystemOutputInterceptor;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import util.Color;
import util.Consts;

public class ControlShow 
{
	public static ControlShow instance;
	private Show show;
	private Date showDate;
	
	public static ControlShow getInstance() {
		if (instance == null)
			instance = new ControlShow();
		return instance;
	}
	
	
	//this method get show and return array of dates that show is showing
	public ArrayList<Date> getDatesOfShow(Show sh) 
	{
		ArrayList<Date> datesList = new ArrayList<Date>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement callst = conn.prepareCall(Consts.SQL_SEL_DATES_BY_SHOW))
					{
					int k=1;
					callst.setInt(k++, sh.getId());
				
					ResultSet rs = callst.executeQuery();
				while (rs.next()) {
					int i = 1;
					datesList.add(rs.getDate(i++));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		for(Date d : datesList)
		{
			java.lang.System.out.println(d);
		}
		return datesList;	
	}
	

	/**
	 * Gets all the shows from the database
	 * @return ArrayList of Shows
	 */
	
	public ArrayList<Show> getShows() {
		ArrayList<Show> showList = new ArrayList<Show>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(util.Consts.SQL_SEL_SHOWS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					showList.add(new Show(rs.getInt(i++),rs.getString(i++),rs.getInt(i++),rs.getBoolean(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return showList;	
	}
	
	
	/**
	 * Gets all the Colors from the database
	 * @return ArrayList of Colors
	 */
	
	public ArrayList<Color> getColors() {
		ArrayList<Color> colorList = new ArrayList<Color>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(util.Consts.SQL_SEL_COLORS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					colorList.add(new Color(rs.getInt(i++), rs.getString(i++), rs.getInt(i++), rs.getInt(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return colorList;	
	}
	
	/**
	 * Gets all the Colors from the database
	 * @return ArrayList of Colors
	 */
	
	public ArrayList<City> getCity() {
		ArrayList<City> cityList = new ArrayList<City>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(util.Consts.SQL_SEL_CITIES);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					cityList.add(new City(rs.getInt(i++), rs.getString(i++), rs.getInt(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return cityList;	
	}
	
	
	//this method get id and return the color
	public Color getColorByID(int id)
	{
		ArrayList<Color> myColors = new ArrayList<Color>();
		myColors.addAll(getColors());
		for(Color c : myColors)
		{
			if(c.getId() == id)
			{
				return c;
			}
		}
		return null;
	}
	
	/**
	 * Gets all the Manager from the database
	 * @return ArrayList of Manager
	 */
	
	public ArrayList<Manager> getManager() {
		ArrayList<Manager> managerList = new ArrayList<Manager>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(util.Consts.SQL_SEL_Managers);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					managerList.add(new Manager(rs.getInt(i++), rs.getString(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return managerList;	
	}
	
	/**
	 * Gets all the Theaters from the database
	 * @return ArrayList of Theaters
	 */
	
	public ArrayList<Theater> getTheaters() {
		ArrayList<Theater> theaterList = new ArrayList<Theater>();

		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(util.Consts.SQL_SEL_THEATERS);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					theaterList.add(new Theater(rs.getInt(i++), rs.getString(i++), rs.getInt(i++),rs.getInt(i++), rs.getInt(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return theaterList;	
	}
	
	//this method get id and return the city
	public City getCityByID(int id)
	{
		ArrayList<City> myCities = new ArrayList<City>();
		myCities.addAll(getCity());
		for(City c : myCities)
		{
			if(c.getCityId() == id)
			{
				return c;
			}
		}
		return null;
	}
	
	//this method get id and return the Manager
	public Manager getManagerByID(int id)
	{
		ArrayList<Manager> myManager = new ArrayList<Manager>();
		myManager.addAll(getManager());
		for(Manager ma : myManager)
		{
			if(ma.getManagerId() == id)
			{
				return ma;
			}
		}
		return null;
	}
	
	
	/**
	 * Gets all the Theaters from the database
	 * @return ArrayList of Theaters
	 */
	
	public ArrayList<Date> getNotValidDatesByShow(int showID) 
	{
		ArrayList<Date> datesList = new ArrayList<Date>();
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_SEL_DATES_BY_SHOW)){
					stmt.setInt(1, showID);
					ResultSet rs = stmt.executeQuery();{
						while (rs.next()) {
							int i = 1;
							datesList.add(rs.getDate(i++));
						}
					}				

			}catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return datesList;	
	}
	

	public ArrayList<Artist> getArtist() {
		ArrayList<Artist> ArtistList = new ArrayList<Artist>();
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(util.Consts.SQL_SEL_ARTIST);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					ArtistList.add(new Artist(rs.getInt(i++),rs.getString(i++),rs.getString(i++),rs.getInt(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return ArtistList;	
	}
	
	public ArrayList<Integer> getArtistId() {
		ArrayList<Integer> ArtistListId = new ArrayList<Integer>();
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(util.Consts.SQL_SEL_ARTIST_ID);
					ResultSet rs = stmt.executeQuery()) {
				while (rs.next()) {
					int i = 1;
					ArtistListId.add((rs.getInt(i++)));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return ArtistListId;	
	}

	

	
	//getting selected
	public Show getShowSelected()
	{
		return show;
	}
	
	public Boolean setShowDate(LocalDate dateOfShow)
	{
		showDate = java.sql.Date.valueOf(dateOfShow);
		return true;
		
	}
	
	public Boolean setShow(Show myShow)
	{
		show = myShow;
		return true;
		
	}
	
	public Date getDateSelected()
	{
		return showDate;
	}
	
	
	public boolean addShow(Show show) {	
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(util.Consts.SQL_INS_SHOW)) {
				int i = 1;

				stmt.setString(i++, show.getShowName());
				stmt.setInt(i++, show.getShowLeangth());
				stmt.setBoolean(i++, show.isHasBreak());
				
				stmt.executeUpdate();
				return true;
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public boolean assignAritstToShow(int showId, int artistId) {
	try {
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
				CallableStatement stmt = conn.prepareCall(util.Consts.SQL_INS_ARTIST_TO_SHOW)) {
			int i = 1;

			stmt.setInt(i++, showId);
			stmt.setInt(i++, artistId);
			
			stmt.executeUpdate();
			return true;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	} catch (ClassNotFoundException e) {
		e.printStackTrace();
	}
	return false;
}
	
	/*  SQL_ARTIST_BY_SHOW_ID  */

	public int getArtistToShowCounter(int showId) {
		//ArrayList<Integer> ArtistListId = new ArrayList<Integer>();
		int num = 0;
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					PreparedStatement stmt =  conn.prepareStatement(util.Consts.SQL_COUNT_ARTIST_IN_SHOW)){
					stmt.setInt(1, showId);
					ResultSet rs = stmt.executeQuery();{
						while (rs.next()) {
							int i = 1;
							num = (rs.getInt(i++));						
						}
					}
					
			}catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
			return num;
	}
	

	public int checkCount(int showId)
	{
		try {
			int amount = 0;
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_COUNT_ARTIST_IN_SHOW)){
					stmt.setInt(1, showId);
					ResultSet rs = stmt.executeQuery();{
						while (rs.next()) {
							int i = 1;
							amount = (rs.getInt(i++));
						}
						return amount;
					}
					
			}catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	
	
	public Boolean CheckIfPilot(int showID) 
	{
		int flag = -2;
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_IS_PILOT)){
					stmt.setInt(1, showID);
					ResultSet rs = stmt.executeQuery();{
						while (rs.next()) {
							int i = 1;
							flag = (rs.getInt(i++));
						}
					}				

			}catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		if(flag>0)
			return false;
		return true;
	}
	
	
	public ArrayList<String> getShowsByStatus(String status) {
		ArrayList<String> showList = new ArrayList<String>();
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_SHOWS_BY_STATUS)){
				stmt.setString(1, status);
				ResultSet rs = stmt.executeQuery(); {		
					while (rs.next()) {
						int i = 1;
						showList.add((rs.getString(i++)));				
					}
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return showList;	
	}
	

	
	public ArrayList<String> getShowsAndStatus() {
		ArrayList<String> showList = new ArrayList<String>();
		String showName;
		String stat="";
		String add;
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					PreparedStatement stmt = conn.prepareStatement(util.Consts.SQL_SHOWS_AND_STATUS);
				ResultSet rs = stmt.executeQuery()) {		
					while (rs.next()) {
						int i = 1;
						showName=((rs.getString(i++)));
						stat = ((rs.getString(i++)));					
						add = showName + ",  status is: " + stat;
						showList.add(add);
					}
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return showList;	
	}


	
	public Show getShowById(int showId) {
		Show sh = null;
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_SEL_SHOW_BY_ID)){
				stmt.setInt(1, showId);	
				ResultSet rs = stmt.executeQuery(); {
				while (rs.next()) {
					int i = 1;
					 sh = new Show(rs.getInt(i++),rs.getString(i++),rs.getInt(i++),rs.getBoolean(i++));
				}
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return sh;	
	}
	
	public Theater getTheaterById(int theaterId) {
		Theater sh = null;
		try {
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_SEL_THEATER_BY_ID)){
					stmt.setInt(1, theaterId);
					ResultSet rs = stmt.executeQuery(); {
				while (rs.next()) {
					int i = 1;
					 sh =new Theater(rs.getInt(i++),rs.getString(i++),rs.getInt(i++),rs.getInt(i++),rs.getInt(i++));
				}
					}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return sh;	
	}
	

	public ArrayList<ShowInTheater> getAllShowInTheaters() 
	{
		ArrayList<ShowInTheater> arr = new ArrayList<ShowInTheater>();
		Time startTime;
		int myShowID;
		int myTheaterID;
		Date showDate;
		String myStatus;
		double myPrice;
		int idid;
		
			try {
				Class.forName(Consts.JDBC_STR);
			} catch (ClassNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				Class.forName(Consts.JDBC_STR);
				try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
						PreparedStatement stmt = conn.prepareStatement(util.Consts.SQL_SEL_ALL_SHOW_IN_THEATER);
					ResultSet rs = stmt.executeQuery()) {		
						while (rs.next()) {
							int i = 1;
							idid = rs.getInt(i++);
							myShowID= rs.getInt(i++);
							myTheaterID = rs.getInt(i++);				
							showDate = rs.getDate(i++);
							startTime = rs.getTime(i++);
							myPrice = rs.getDouble(i++);
							myStatus = rs.getString(i++);
							int price = (int)(myPrice);
							Show sh = getShowById(myShowID);
							Theater th = getTheaterById(myTheaterID);
							java.sql.Date dtNow = java.sql.Date.valueOf(LocalDate.now());
							Date d = dtNow;
							ShowInTheater myShTh = new ShowInTheater(th, sh, showDate, startTime, price, d, myStatus);
							arr.add(myShTh);
						}
					
				} catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			return arr;
		}	
	

	public static boolean updateShowInTheaterStatus(int showId, int theaterId, String status) throws ClassNotFoundException, SQLException
	{
		Class.forName(Consts.JDBC_STR);
		try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
				CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_UPD_STATUS_BY_SHOW_ID_THEATER_ID)){
			int i = 1;
			stmt.setString(i++, status);
			stmt.setInt(i++, showId);		
			stmt.setInt(i++, theaterId);
			stmt.executeUpdate();		
		}
		return true;	
	}
	
	//this method get ShowID and AgentID and return if the agent has artist in the show. 
	public static boolean isArtistCanBeThere(int show_id, int agent_id)
	{
		int numOfAgentArtistInShow = ReportControl.getInstance().calcArtistOfAgentInShow(show_id, agent_id);
		if(numOfAgentArtistInShow == 0)
		{
			return false;
		}
		return true;
	}

}	

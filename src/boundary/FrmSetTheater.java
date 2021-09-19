package boundary;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;


import control.ControlShow;
import entity.Show;
import entity.Theater;
import exceptions.NegativeNumberException;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.Consts;

public class FrmSetTheater {

	@FXML
	private ListView theaterlist;
	@FXML
	private javafx.scene.control.TextField showPrice;
	@FXML 
	private ComboBox hour;
	@FXML 
	private ComboBox minute;
	@FXML
	private Button home;
	
	private Theater t;
	
	
	@FXML
	public void initialize() 
	{
		theaterlist.setItems(FXCollections.observableArrayList(canTheaterWithThisDate()));
		ArrayList<Integer> hoursList  = new ArrayList<>();
		ArrayList<Integer> minuteList  = new ArrayList<>();
		for(int i=0;i<22;i++) 
		{
			hoursList.add(i);
		}
		hour.setItems(FXCollections.observableArrayList(hoursList));	
		for(int i=0;i<60;i++) 
		{
			minuteList.add(i);
		}
		minute.setItems(FXCollections.observableArrayList(minuteList));
		
		FileInputStream input;
		try {
			input = new FileInputStream("./photos/newlogo.png");
			Image img = new Image(input);
			ImageView view = new ImageView(img);
			view.setFitHeight(30);
			view.setPreserveRatio(true);
			home.setGraphic(view);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	
	//this method return the theaters can be the show
	public ArrayList<Theater> canTheaterWithThisDate()
	{
		Show show = ControlShow.getInstance().getShowSelected();
		HashSet<Integer> allTheatersNotValid = new HashSet<Integer>();
		allTheatersNotValid.addAll(alltheaterNotFreeInThisDate());
		allTheatersNotValid.addAll(allTheatersWithThisShow());
		ArrayList<Theater> allTheaters = new ArrayList<>();
		allTheaters.addAll(ControlShow.getInstance().getTheaters());
		
		for(int thID : allTheatersNotValid) //because all theater is by Index
		{
			//for(Theater t : allTheaters )
			for(int j=0;j<allTheaters.size();j++)
			{
				Theater t = allTheaters.get(j);
				//java.lang.System.out.println(t);
				if(t.getTheaterID() == thID)
				{
					//java.lang.System.out.println(t.getTheaterID());
					//allTheaters.remove(t);
					allTheaters.remove(j);
				}
			}
		}
		return allTheaters;
		
	}
	
	
	
	//this method return id's list of theater not can in this date
	private ArrayList <Integer> alltheaterNotFreeInThisDate()
	{
		Show show = ControlShow.getInstance().getShowSelected();
		Date inDate = ControlShow.getInstance().getDateSelected();
		ArrayList<Integer> theaterNotFreeList = new ArrayList<Integer>();

			try {
				Class.forName(Consts.JDBC_STR);
				try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
						CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_SEL_DATES_BY_THEATER)){
						stmt.setDate(1,inDate);
						ResultSet rs = stmt.executeQuery();{
							while (rs.next()) {
								int i = 1;
								theaterNotFreeList.add(rs.getInt(i++));
							}
						}
						

				}catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return theaterNotFreeList;	
	}
	
	//this method return all theaterID that show was 
	private ArrayList <Integer> allTheatersWithThisShow()
	{
		Show show = ControlShow.getInstance().getShowSelected();
		ArrayList<Integer> theaterNotFreeList = new ArrayList<Integer>();

			try {
				Class.forName(Consts.JDBC_STR);
				try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
						CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_SEL_THEATER_WITH_THIS_SHOW)){
						stmt.setInt(1,show.getId());
						ResultSet rs = stmt.executeQuery();{
							while (rs.next()) {
								int i = 1;
								theaterNotFreeList.add(rs.getInt(i++));
							}
						}
						

				}catch (SQLException e) {
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return theaterNotFreeList;	
	}
	
	//when we press the button this method will do sceduling the show
	@FXML
	public void schedulingThisShow(MouseEvent event) throws Exception
	{
		Boolean isSucceed = doingSchedulingThisShow();
		if(isSucceed)
		{
			Alert alert = new Alert(AlertType.INFORMATION, "Secsessful Scheduling!\nWe Back to Home Scrren");
			alert.setHeaderText("Secsessful Scheduling!");
			alert.setTitle("Secsessful Scheduling!");
			alert.showAndWait();
			moveNext("HomeScreen");
		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR, "Error");
			alert.setHeaderText("Value not excepted");
			alert.setTitle("Failed scheduling");
			alert.showAndWait();
		}
	}
	
	
	
	private boolean doingSchedulingThisShow() 
	{
		try 
		{
			Theater t = (Theater)theaterlist.getSelectionModel().getSelectedItem();
			
			double myPrice = Double.parseDouble(showPrice.getText());

			if(myPrice<1.0)
			{
				throw new NegativeNumberException();
			}
			int hours = (int) hour.getSelectionModel().getSelectedItem();
			int minites =  (int) minute.getSelectionModel().getSelectedItem();
			Time startTime = new Time(hours, minites, 0);
			int myShowID = ControlShow.getInstance().getShowSelected().getId();
			
			Date showDate = ControlShow.getInstance().getDateSelected();
			
			String myStatus;
			boolean flag = false;
			flag = ControlShow.getInstance().CheckIfPilot(myShowID);
			if(flag)
			{
				myStatus = "pilot";
			}
			else
			{
				myStatus = "available";
			}
					
			Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_INS_SCHEDULING_SHOW)){
				int i = 1;
				stmt.setInt(i++, myShowID);
				
				stmt.setInt(i++, t.getTheaterID());
				stmt.setDate(i++, new java.sql.Date(showDate.getTime()));
				stmt.setTime(i++, startTime);
				stmt.setDouble(i++, myPrice);
				stmt.setString(i++, myStatus);
				stmt.executeUpdate();
				return true;
			}
			
			
			
		}
		
		//java.lang.System.out.println(myPrice);
		catch(NullPointerException e)
		{
			Alert alert = new Alert(AlertType.ERROR, "All Values Needs To Be Field");
			alert.setHeaderText("All Values Needs To Be Field");
			alert.setTitle("Failed scheduling");
			alert.showAndWait();
		}
		catch(NumberFormatException e)
		{
			Alert alert = new Alert(AlertType.ERROR, "Price Should be number!");
			alert.setHeaderText("Value not excepted");
			alert.setTitle("Failed scheduling");
			alert.showAndWait();
		} catch (NegativeNumberException e) {
			Alert alert = new Alert(AlertType.ERROR, "Price Should Be More than 1.0!");
			alert.setHeaderText("Value not excepted");
			alert.setTitle("Failed scheduling");
			alert.showAndWait();
		}
		catch(SQLException e)
		{
			Alert alert = new Alert(AlertType.ERROR, e.getMessage());
			alert.setHeaderText("Something Wrong");
			alert.setTitle("Failed scheduling");
			alert.showAndWait();
			e.printStackTrace();
		}
		catch(ClassNotFoundException e)
		{
			Alert alert = new Alert(AlertType.ERROR, e.getMessage());
			alert.setHeaderText("Something Wrong");
			alert.setTitle("Failed scheduling");
			alert.showAndWait();
			e.printStackTrace();
		}
		return false;
		
		
	}
	
	//for moving windows//
	@FXML
	public void moveHomeScreen(MouseEvent event) throws Exception
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundary/HomeScreen.fxml"));
		Stage primaryStage = (Stage) hour.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	
	//for moving windows//
	public void moveNext(String page) throws Exception
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundary/"+page+".fxml"));
		Stage primaryStage = (Stage) hour.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}

}
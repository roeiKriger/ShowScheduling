package boundary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashSet;

import control.ControlShow;
import entity.Show;
import entity.Theater;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import util.Consts;

public class FrmSetDateToShow 
{
	
	@FXML
	private DatePicker dp;
	
	/**
	 * here show only valid teaters
	 */
	private LocalDate datesForShow;
	
	private Show show = ControlShow.getInstance().getShowSelected();
	@FXML
	private Button home;
	
	@FXML
	public void initialize() 
	{
		//couldent be in the past, and can be from tomorrow only
		dp.setDayCellFactory(picker -> new DateCell() {
			public void updateItem(LocalDate date, boolean empty) {
				super.updateItem(date, empty);
				LocalDate today = LocalDate.now();
				LocalDate tomorrow = today.plus(1,ChronoUnit.DAYS);
	            LocalDate maxDate = LocalDate.of(2030, 1, 1);
	            setDisable(date.compareTo(maxDate)>0 || date.compareTo(tomorrow) < 0 );
			}
		});
		dp.setEditable(false);
		
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

	@FXML
	//after click checkDate do this method that check the valid of the date
	public void btnCheckDate(MouseEvent event) throws Exception
	{
		Boolean f = checkDateOfShow();
		
		if(f == true)
		{
			Alert alert = new Alert(AlertType.INFORMATION,"This is valid date");
			alert.setHeaderText("Valid date!");
			alert.setTitle("Valid date!");
			alert.showAndWait();
			datesForShow = dp.getValue();
			ControlShow.getInstance().setShowDate(datesForShow);
			moveNext("FrmSetTheater");
			
			//java.lang.System.out.println(canTheaterWithThisDate());
			

		}
		else 
		{
			Alert alert = new Alert(AlertType.ERROR,"This is not valid date");
			alert.setHeaderText("Not Valid Date!");
			alert.setTitle("Not Valid Date!");
			alert.showAndWait();
		}
	}

	
	
	//this method check if date is valid
	private Boolean checkDateOfShow()
	{
		LocalDate choseDate = dp.getValue();
		ArrayList <Date> datesList = new ArrayList<Date>();
		datesList.addAll(getNotValidDatesByShow(show.getId()));
		
		for(Date d : datesList)
		{
			if(d.toLocalDate().equals(choseDate))
			{
				return false;
			}
		}
		
		return true;
		
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
	
	

	
	//for moving windows//
	@FXML
	public void moveHomeScreen(MouseEvent event) throws Exception
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundary/HomeScreen.fxml"));
		Stage primaryStage = (Stage) dp.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	
	//for moving windows//
	public void moveNext(String page) throws Exception
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundary/"+page+".fxml"));
		Stage primaryStage = (Stage) dp.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}

	public void btnFindTheater() {
		// TODO - implement FrmSetDateToShow.btnFindTheater
		throw new UnsupportedOperationException();
	}

}
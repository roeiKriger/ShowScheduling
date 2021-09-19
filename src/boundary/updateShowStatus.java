package boundary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import control.ControlShow;
import entity.Show;
import entity.ShowInTheater;
import entity.Theater;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class updateShowStatus 
{
	@FXML
	private ListView showsList;
	
	@FXML
	private ComboBox comboStatus;
	
	@FXML
	private ComboBox combShow;
	
	@FXML
	private Button btnUpdate;
	@FXML
	private Button home;
	
	ArrayList<String> statusAllArr  = new ArrayList<>();
	
	@FXML
	public void moveHomeScreen(MouseEvent event) throws Exception
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundary/HomeScreen.fxml"));
		Stage primaryStage = (Stage) combShow.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	
	@FXML
	public void initialize() 
	{	
		statusAllArr.add("available"); statusAllArr.add("pilot"); statusAllArr.add("suspended"); statusAllArr.add("canceled");
		comboStatus.setItems(FXCollections.observableArrayList(statusAllArr));
		comboStatus.getSelectionModel().selectFirst();
		combShow.setItems(FXCollections.observableArrayList(ControlShow.getInstance().getShows()));
		combShow.getSelectionModel().selectFirst();
		showsList.setItems(FXCollections.observableArrayList(ControlShow.getInstance().getShowsAndStatus()));
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
	public void btnUpdateStat(MouseEvent event) throws Exception 
	{
		ArrayList<ShowInTheater> arr = ControlShow.getInstance().getAllShowInTheaters();
		
		ArrayList<ShowInTheater> upd = new ArrayList<ShowInTheater>();
		for(ShowInTheater s : arr)
		{
			if(s.getShow().getShowName().equals(combShow.getValue().toString()))
			{
				upd.add(s);
			}
		}
		for(ShowInTheater s : upd)
		{
			s.setStatus(comboStatus.getValue().toString());
		}
		for(ShowInTheater s : upd)
		{
			ControlShow.getInstance().updateShowInTheaterStatus(s.getShow().getId(), s.getTheater().getTheaterID(), s.getStatus());
		}
		
		initialize();
		
	}
	
	
}

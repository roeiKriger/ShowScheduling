package boundary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import control.ControlShow;
import entity.Show;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
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

public class Catalog 
{
	
	@FXML
	private ListView showsList;
	
	@FXML
	private ComboBox comboStatus;
	@FXML
	private ImageView catIMG;
	@FXML
	private Button home;
	
	ArrayList<String> statusAllArr  = new ArrayList<>();
	ArrayList<String> statusPilotArr  = new ArrayList<>();
	ArrayList<String> statusSuspendedArr  = new ArrayList<>();
	ArrayList<String> statusCanceldArr  = new ArrayList<>();
	ArrayList<String> statusAvailldArr = new ArrayList<>();
	ArrayList<Show> allShows = new ArrayList<>();
	@FXML
	public void initialize() 
	{	
		statusAllArr.add("all"); statusAllArr.add("available"); statusAllArr.add("pilot"); statusAllArr.add("suspended"); statusAllArr.add("canceled");
		comboStatus.setItems(FXCollections.observableArrayList(statusAllArr));
		comboStatus.getSelectionModel().selectFirst();
		statusPilotArr = ControlShow.getInstance().getShowsByStatus("pilot");
		statusSuspendedArr = ControlShow.getInstance().getShowsByStatus("suspended");
		statusCanceldArr = ControlShow.getInstance().getShowsByStatus("canceled");
		statusAvailldArr = ControlShow.getInstance().getShowsByStatus("available");	
		allShows = ControlShow.getInstance().getShows();
		showsList.setItems(FXCollections.observableArrayList(allShows));
		FileInputStream input;
		try {
			input = new FileInputStream("./photos/catalog.jpg");
			Image image = new Image(input);
			catIMG.setImage(image);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
	public void chooseCatalog(ActionEvent event) 
	{
		if(comboStatus.getValue().equals("pilot"))
			showsList.setItems(FXCollections.observableArrayList(statusPilotArr));
		else if(comboStatus.getValue().equals("suspended"))
			showsList.setItems(FXCollections.observableArrayList(statusSuspendedArr));
		else if(comboStatus.getValue().equals("canceled"))
			showsList.setItems(FXCollections.observableArrayList(statusCanceldArr));
		else if(comboStatus.getValue().equals("available"))
			showsList.setItems(FXCollections.observableArrayList(statusAvailldArr));
		else
			showsList.setItems(FXCollections.observableArrayList(allShows));
		
	}
	
	@FXML
	public void moveHomeScreen(MouseEvent event) throws Exception
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundary/HomeScreen.fxml"));
		Stage primaryStage = (Stage) showsList.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	
}

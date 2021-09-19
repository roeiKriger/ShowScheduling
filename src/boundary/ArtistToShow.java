package boundary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import control.ControlShow;
import entity.Artist;
import entity.Show;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ArtistToShow {
	
	@FXML
	private ListView artistList;
	
	@FXML
	private ComboBox comboShow;
	
	@FXML
	private ComboBox comboArtist;
	@FXML
	private ImageView assImg;
	@FXML
	private Button home;
	
	
	
	@FXML
	public void initialize() 
	{
		//inserting to shows combox
		artistList.setItems(FXCollections.observableArrayList(ControlShow.getInstance().getArtist()));
		artistList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		
		comboShow.setItems(FXCollections.observableArrayList(ControlShow.getInstance().getShows()));
		comboArtist.setItems(FXCollections.observableArrayList(ControlShow.getInstance().getArtistId()));
		FileInputStream input;
		try {
			input = new FileInputStream("./photos/assign.jpg");
			Image image = new Image(input);
			assImg.setImage(image);
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
	public void moveHomeScreen(MouseEvent event) throws Exception
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundary/HomeScreen.fxml"));
		Stage primaryStage = (Stage) comboShow.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	
	
	public void btnChooseShow(MouseEvent event) throws Exception 
	{
		int showId = 0;
		int artistId = 0;
		int num = 0;
		
		Boolean flag = false;
		if(comboShow.getValue() == null || comboArtist.getValue()== null)
		{
			Alert alert = new Alert(AlertType.ERROR, "Empty Field");
			alert.setHeaderText("YOU NEED TO FILL ALL THE FIELDS");
			alert.setTitle("Failed Creating New Show");
			alert.showAndWait();
		}
		
		else
		{
			 Show sh= (Show) comboShow.getValue();
			 showId = sh.getId();
			 artistId = (int) comboArtist.getValue();
			 num = ControlShow.getInstance().getArtistToShowCounter(showId);
				 
			 if(num >= 5)
			 {
				 flag = false;
				 Alert alert = new Alert(AlertType.ERROR, "Only 5 artists can be assigned to a show");
				 alert.setHeaderText("Try Another Show");
				 alert.setTitle("Error");
				 alert.showAndWait();
			 }
			 else if(ControlShow.getInstance().isArtistCanBeThere(showId, getAgentIdByArtistId(artistId))) //the agent has artist in this show
			 {
				 flag = false;
				 Alert alert = new Alert(AlertType.ERROR, "This agent can only one Artist");
				 alert.setHeaderText("Try Another Artist");
				 alert.setTitle("Error");
				 alert.showAndWait();
			 }
			 else
			 {
				 flag = true;
				
			 }
			 if(flag) 
			 {
				 if(ControlShow.getInstance().assignAritstToShow(showId, artistId))
				 {
					 Alert alert = new Alert(AlertType.INFORMATION, "Artist was added to Show");
					 alert.setHeaderText("Success");
					 alert.setTitle("New Artist Added");
					 alert.showAndWait();
				 }
				 else
				 {
					 Alert alert = new Alert(AlertType.ERROR, "Error adding artist");
					 alert.setHeaderText("Try Again Later");
					 alert.setTitle("Error");
					 alert.showAndWait();
				 }
			 
			 }
		}

	}
	
	//This method gets id of artist and return the agent id
	private int getAgentIdByArtistId(int id)
	{
		ArrayList<Artist> artists = ControlShow.getInstance().getArtist();
		for(Artist a : artists)
		{
			if(a.getId() == id)
				return a.getIdOfAgent();
		}
		
		return -1;
	}
}

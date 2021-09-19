package boundary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import control.ControlShow;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class SuspendShow {
	
	@FXML
	private ListView showList;
	
	@FXML
	private ComboBox comboShow;
	
	@FXML
	private Button home;
	
	@FXML
	private ImageView productImg;

	@FXML
	public void initialize() 
	{
		//inserting to shows combox
		showList.setItems(FXCollections.observableArrayList(ControlShow.getInstance().getArtist()));
		
		comboShow.setItems(FXCollections.observableArrayList(ControlShow.getInstance().getShows()));
		
		FileInputStream input2;
		try {
			input2 = new FileInputStream("./photos/data.jpg");
			Image image2 = new Image(input2);
			productImg.setImage(image2);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void btnChooseShow(MouseEvent event) throws Exception 
	{
		
		
	}
	
	@FXML
	public void moveHomeScreen(MouseEvent event) throws Exception
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundary/HomeScreen.fxml"));
		Stage primaryStage = (Stage) comboShow.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	
}

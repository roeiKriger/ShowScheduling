package boundary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import control.ControlXml;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class importXml 
{

	@FXML
	private Button btnImport;
	@FXML
	private ImageView flashIMG;
	@FXML
	private Button home;
	
	
	@FXML
	public void initialize() 
	{
		FileInputStream input;
		try {
			input = new FileInputStream("./photos/flashLightImg.png");
			Image image = new Image(input);
			flashIMG.setImage(image);
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
		Stage primaryStage = (Stage) btnImport.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	
	
	@FXML
	public void btnImportXml(MouseEvent event) throws Exception
	{
		ControlXml.getInstance().importColorsFromXML("resources/city.xml");
	}
	
}

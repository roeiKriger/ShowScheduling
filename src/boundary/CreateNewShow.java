package boundary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import control.ControlShow;
import entity.Show;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class CreateNewShow 
{
		
	@FXML
	private TextField txtName;
	
	@FXML
	private TextField txtLength;
	
	@FXML
	private CheckBox checkboxBreak;
	
	
	
	@FXML
	private Button addButton;
	
	@FXML
	private ImageView showIm;
	@FXML
	private Button home;
	
	@FXML
	public void initialize() 
	{
		FileInputStream input;
		try {
			input = new FileInputStream("./photos/hall1.jpg");
			Image image = new Image(input);
			showIm.setImage(image);
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
		Stage primaryStage = (Stage) addButton.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	
	public void btnChooseShow(MouseEvent event) throws Exception 
	{
		Show show = null;
		Boolean flag =false, added = false;
		int len = 0;
		if(txtLength != null)
			len =Integer.parseInt(txtLength.getText());
		if(txtName!= null && txtLength!= null && len>0)
		{
			show = new Show(txtName.getText(), len, checkboxBreak.isSelected());
			flag = true;
		}	 
				
		if(txtName.getText()==null || txtLength.getText() == null )
		{
			Alert alert = new Alert(AlertType.ERROR, "Empty Field");
			alert.setHeaderText("YOU NEED TO FILL ALL THE FIELDS");
			alert.setTitle("Failed Creating New Show");
			alert.showAndWait();
		}
		
		if(flag)
		{
			if(added = ControlShow.getInstance().addShow(show))
			{
				Alert alert = new Alert(AlertType.INFORMATION, "Show Created Succesfully");
				alert.setHeaderText("Success");
				alert.setTitle("New Show Added");
				alert.showAndWait();
			}
			else
			{
				Alert alert = new Alert(AlertType.ERROR, "Error adding show");
				alert.setHeaderText("Try Again Later");
				alert.setTitle("Error");
				alert.showAndWait();
			}
		}
		
		
	}
	

}

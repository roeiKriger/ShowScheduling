

package boundary;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import com.sun.xml.internal.bind.v2.schemagen.xmlschema.List;

import control.ControlShow;
import entity.Show;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class FrmSchedulingShow 
{

	private Show show;
	@FXML
	private ComboBox showCombo;


	@FXML
	private Button btnSetShow;
	@FXML
	private Button home;

	@FXML
	public void initialize() 
	{
		//inserting to shows combox
		showCombo.setItems(FXCollections.observableArrayList(ControlShow.getInstance().getShows()));
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
	



	public Show[] getshows() {
		// TODO - implement FrmSchedulingShow.getshows
		throw new UnsupportedOperationException();
	}

	@FXML
	public void btnChooseShow(MouseEvent event) throws Exception 
	{
		show  = (Show) showCombo.getSelectionModel().getSelectedItem();
		ControlShow.getInstance().setShow(show);
		if(ControlShow.getInstance().getShowSelected()==null)
		{
			Alert alert = new Alert(AlertType.ERROR, "YOU NEED TO CHOOSE ONE SHOW");
			alert.setHeaderText("YOU NEED TO CHOOSE ONE SHOW");
			alert.setTitle("Failed scheduling");
			alert.showAndWait();
		}
		else
		{
			Alert alert = new Alert(AlertType.INFORMATION, "SUCSSESFUL CHOSING WE MOVE TO THE NEXT STEP");
			alert.setTitle("COUNTINUE SCEDULING");
			alert.showAndWait();
			moveNext("FrmSetDateToShow");
		}

	}

	public void btnSendDetails() {
		// TODO - implement FrmSchedulingShow.btnSendDetails
		throw new UnsupportedOperationException();
	}
	
	//for moving windows//
	@FXML
	public void moveHomeScreen(MouseEvent event) throws Exception
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundary/HomeScreen.fxml"));
		Stage primaryStage = (Stage) showCombo.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	
	//for moving windows//
	public void moveNext(String page) throws Exception
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundary/"+page+".fxml"));
		Stage primaryStage = (Stage) showCombo.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}

}
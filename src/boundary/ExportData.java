package boundary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.time.LocalDate;

import control.ExportControl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class ExportData 
{
	@FXML
	private Button btnImport;
	@FXML
	private ImageView flashIMG;
	@FXML
	private Button home;
	@FXML
	private DatePicker dp;
	
	
	@FXML
	public void initialize() 
	{
		FileInputStream input;
		try {
			input = new FileInputStream("./photos/data.jpg");
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
		
		dp.setDayCellFactory(param -> new DateCell() {
	        @Override
	        public void updateItem(LocalDate date, boolean empty) {
	            super.updateItem(date, empty);
	            LocalDate current = LocalDate.now();
	            setDisable(empty || date.compareTo(current)>0);
	        }
	    });

	}
	@FXML
	public void doingExport(MouseEvent event)
	{
		java.sql.Date today = java.sql.Date.valueOf(dp.getValue());
		ExportControl.getInstance().exportToJSON(today);
	}
	
	@FXML
	public void moveHomeScreen(MouseEvent event) throws Exception
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundary/HomeScreen.fxml"));
		Stage primaryStage = (Stage) btnImport.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}

}

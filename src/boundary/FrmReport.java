package boundary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.Date;
import java.time.LocalDate;

import javax.swing.JFrame;

import control.ControlShow;
import control.LoginControl;
import control.ReportControl;
import entity.Agent;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class FrmReport {

	private LocalDate dateStart;
	private LocalDate dateEnd;
	
	//for back Home 
	@FXML
	private Button home;
	@FXML
	private ComboBox comboAgent;
	@FXML
	private DatePicker dpFrom;
	@FXML
	private DatePicker dpTo;
	@FXML
	private Label forAgent;
	@FXML
	private Label laAgent;
	@FXML
	private Label lbAgentName;
	@FXML
	private ImageView reportImg;
	
	
	
	@FXML
	public void initialize() 
	{		
		Tooltip.install(home, new Tooltip("Back To Home Screen"));
		if(LoginControl.getInstance().getLoginUser().getTypeOfUser().equals("agent")) //if agent
		{
			Agent agent = LoginControl.getInstance().getAgent(LoginControl.getInstance().getLoginUser());
			comboAgent.setItems(FXCollections.observableArrayList(agent));
			//comboAgent.selectionModelProperty().setValue(3);
			comboAgent.getSelectionModel().select(3);
			comboAgent.setDisable(true);
			lbAgentName.setText("Hello " + agent.getName().toString()+"!");
			String message = "Please choose Dates for your report:"; 
			forAgent.setText(message);
			forAgent.setVisible(true);
			lbAgentName.setVisible(true);
			comboAgent.setVisible(false);
			laAgent.setVisible(false);
		}
		else
		{//admin
			comboAgent.setItems(FXCollections.observableArrayList(ReportControl.getInstance().getAgents()));
			forAgent.setVisible(false);
			forAgent.setVisible(false);
			lbAgentName.setVisible(false);
		}
		
		FileInputStream input;
		try {
			input = new FileInputStream("./photos/report.jpg");
			Image image = new Image(input);
			reportImg.setImage(image);
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
	private void btnGenerateRep(MouseEvent event) 
	{
		try 
		{
			Agent agentNum = ((Agent)comboAgent.getValue());
			Date from =  java.sql.Date.valueOf(dpFrom.getValue());
			Date to =  java.sql.Date.valueOf(dpTo.getValue());
			//System.out.println(from +" " + to);
			if(agentNum == null )
			{
				agentNum = LoginControl.getInstance().getAgent(LoginControl.getInstance().getLoginUser());
			}
			if (agentNum == null || from == null || to==null ) 
			{
				Alert alert = new Alert(AlertType.ERROR,"Fields are missing");
				alert.setTitle("Fields are missing");
				alert.setHeaderText("Please select Agent, date from and date to");
				alert.showAndWait();
			}
			else {
				JFrame reportFrame = ReportControl.getInstance().produceReport(agentNum, from, to);
				//if(reportFrame!=null)
			reportFrame.setVisible(true);
		}
	}
	catch(NullPointerException e)
	{			
		Alert alert = new Alert(AlertType.ERROR,"Fields are missing");
		alert.setTitle("Fields are missing");
		alert.setHeaderText("Please select Agent, date from and date to");
		alert.showAndWait();
	}
}
	
	
	//for moving windows//
	@FXML
	public void moveHomeScreen(MouseEvent event) throws Exception
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundary/HomeScreen.fxml"));
		Stage primaryStage = (Stage) home.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}

}
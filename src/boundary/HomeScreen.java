package boundary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import control.LoginControl;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class HomeScreen 
{
	@FXML
	private Button btnSch; // this button will move you to scheduling show
	
	@FXML 
	private Button btnReport; // this button will move you to set Report
	
	@FXML
	private Button btnNewShow;
	
	@FXML
	private Button btnAssign;
	
	@FXML
	private Button btnCatalog;
	
	@FXML
	private Button btnUpdateStatus;
	
	@FXML
	private Button btnImport;
	@FXML
	private ImageView logo;
	@FXML
	private Button btnExport;
	
	@FXML
	public void initialize() 
	{
		boolean admin = false;
		if(LoginControl.getInstance().getLoginUser().getTypeOfUser().equals("admin"))
			admin = true;
		if(!admin)
		{
			btnNewShow.setVisible(false);
			btnImport.setVisible(false);
			btnUpdateStatus.setVisible(false);
			btnAssign.setVisible(false);
			btnSch.setVisible(false);
			btnExport.setVisible(false);
		}
		FileInputStream input;
		try {
			input = new FileInputStream("./photos/newlogo.png");
			Image image = new Image(input);
			logo.setImage(image);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	//for moving window//
	public void movePage(String toMove) throws Exception
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundary/"+toMove+".fxml"));
		Stage primaryStage = (Stage) btnSch.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}
	
	@FXML
	public void btnSchMove(MouseEvent event) throws Exception
	{
		movePage("FrmSchedulingShow");
	}
	
	@FXML
	public void btnReportMove(MouseEvent event) throws Exception
	{
		movePage("FrmReport");
	}
	@FXML
	public void btnShowMove(MouseEvent event) throws Exception
	{
		movePage("CreateNewShow");
	}
	@FXML
	public void btnAssignMove(MouseEvent event) throws Exception
	{
		movePage("ArtistToShow");
	}
	
	@FXML
	public void btnCatalogMove(MouseEvent event) throws Exception
	{
		movePage("Catalog");
	}
	
	@FXML
	public void btnUpdateMove(MouseEvent event) throws Exception
	{
		movePage("updateShowStatus");
	}
	
	@FXML
	public void btnImportMove(MouseEvent event) throws Exception
	{
		movePage("importXml");
	}
	@FXML
	public void btnExportMove(MouseEvent event) throws Exception
	{
		movePage("ExportData");
	}
	

}

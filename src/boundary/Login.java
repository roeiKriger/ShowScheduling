package boundary;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import control.LoginControl;
import entity.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class Login 
{
	
	@FXML 
	private TextField txtUser;
	
	@FXML 
	private PasswordField txtPass;
	
	@FXML 
	private Button btnLogin;
	@FXML
	private ImageView logoCompany;
	@FXML
	private ImageView productImg;
	@FXML
	private ImageView systemIMG;
	
	@FXML
	public void initialize() 
	{
		FileInputStream input;
		try {
			input = new FileInputStream("./photos/newlogo.png");
			Image image = new Image(input);
			logoCompany.setImage(image);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		
		FileInputStream input2;
		try {
			input2 = new FileInputStream("./photos/loginImg.jpg");
			Image image2 = new Image(input2);
			productImg.setImage(image2);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		FileInputStream input3;
		try {
			input3 = new FileInputStream("./photos/sysLog.png");
			Image image3 = new Image(input3);
			systemIMG.setImage(image3);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	String type="";
	
	//for moving window//
	public void movePage(String toMove) throws Exception
	{
		Parent newRoot = FXMLLoader.load(getClass().getResource("/boundary/"+toMove+".fxml"));
		Stage primaryStage = (Stage) btnLogin.getScene().getWindow();
		primaryStage.getScene().setRoot(newRoot);
		primaryStage.show();
	}

	@FXML
	public void btnLogMove(MouseEvent event) throws Exception 
	{
		if(isCanLogin())
		{
			movePage("HomeScreen");

		}
		else
		{
			Alert alert = new Alert(AlertType.ERROR,"One or More Fields are incoorect");
			alert.setHeaderText("Login Error");
			alert.setTitle("Login Error");
			alert.showAndWait();
		}
		
	}
	
	public boolean isCanLogin()
	{
		
		User c= new User(txtUser.getText(), txtPass.getText());
		ArrayList<User> allCustumers = LoginControl.getInstance().getAllUsers();
		for(User cus: allCustumers)
		{
			if(cus.equals(c)) //if userName and password exist
			{
				LoginControl.getInstance().setLoginUser(cus); //save the login member
				return true;
			}
		}
			return false;
	}
	
}

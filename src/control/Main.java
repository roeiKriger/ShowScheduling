package control;

import javafx.application.Application;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;

import org.apache.log4j.BasicConfigurator;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

/*
 * Create by: Matan Mayerowicz & Roei Kriger
 */
public class Main extends Application {

	public static void main(String[] args) 
	{
		//ExportControl.exportCustomersToJSON();
		BasicConfigurator.configure();
		launch(args);
	}


	public void start(Stage primaryStage) throws Exception 
	{
		//which page we want to load next
		Parent root = FXMLLoader.load(getClass().getResource("/boundary/Login.fxml"));
		Scene scene = new Scene(root);  //size of window
		primaryStage.setScene(scene);
		primaryStage.setResizable(true);  //not resizeable
		primaryStage.initStyle(StageStyle.DECORATED);
		primaryStage.setTitle("Show Scheduling");
		FileInputStream input;
		try {
			input = new FileInputStream("./photos/systemLogo.JPG");
			Image img = new Image(input);
			primaryStage.getIcons().add(img);  //icon
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		primaryStage.show();
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			@Override
			public void handle(WindowEvent event) 
			{
				Platform.exit();
				System.exit(0);
			}
		});

	}



}


package control;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.simple.JsonArray;
import org.json.simple.JsonObject;
import org.json.simple.Jsoner;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import util.Consts;

public class ExportControl 
{
	private static ExportControl instance;

	public static ExportControl getInstance() {
		if (instance == null)
			instance = new ExportControl();
		return instance;
	}


	//This method gets date and do export to JSON for ShowManaging System
	public static void exportToJSON(java.sql.Date today) 
	{
		try {
			Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");
			try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
					CallableStatement stmt = conn.prepareCall(
							Consts.SQL_EXPORT_DATA_TEST)){
					stmt.setDate(1, today);
					ResultSet rs = stmt.executeQuery(); {
				JsonArray updatedShows = new JsonArray();
				while (rs.next()) {
					JsonObject updatedShow = new JsonObject();

					for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++)
						updatedShow.put(rs.getMetaData().getColumnName(i), rs.getString(i));


					updatedShows.add(updatedShow);
				}

				JsonObject doc = new JsonObject();
				doc.put("shows", updatedShows);

				File file = new File("json/shows.json");
				file.getParentFile().mkdir();

				try (FileWriter writer = new FileWriter(file)) {
					writer.write(Jsoner.prettyPrint(doc.toJson()));
					writer.flush();
					 Alert alert = new Alert(AlertType.INFORMATION, "Shows data exported successfully!");
					 alert.setHeaderText("Success");
					 alert.setTitle("Success Data Export");
					 alert.showAndWait();
				} catch (IOException e) {
					e.printStackTrace();
				}
					}
			} catch (SQLException | NullPointerException e) {
				e.printStackTrace();
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}	
	}
	
	
	
	
	
	
	
}
	


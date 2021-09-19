package control;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Node;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.simple.DeserializationException;
import org.json.simple.JsonArray;
import org.json.simple.JsonObject;
import org.json.simple.Jsoner;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import entity.City;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import util.Color;
import util.Consts;
import util.Consts.Manipulation;



public class ControlXml {
	private static ControlXml instance;
	
	public static ControlXml getInstance() {
		if (instance == null)
			instance = new ControlXml();
		return instance;
	}
	
	
	    /**
	     * imports customers from xml to db.
	     * @param path xml filepath.
	     */
	    public void importColorsFromXML(String path) {
	    	try {
				Document doc = DocumentBuilderFactory.newInstance()
									.newDocumentBuilder().parse(new File(path));
				doc.getDocumentElement().normalize();
				NodeList nl = doc.getElementsByTagName("city");
				int errors = 0;
				for (int i = 0; i < nl.getLength(); i++) {
					if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
						Element el = (Element) nl.item(i);
						City c = new City(el.getElementsByTagName("cityName").item(0).getTextContent(),
								el.getElementsByTagName("colorName").item(0).getTextContent());
							Color cl = new Color(el.getElementsByTagName("maxInTheater").item(0).getTextContent(),
								el.getElementsByTagName("maxInCapsule").item(0).getTextContent());
							if(isUpdateColor(cl))
							{
								updateColor(c.getCityColor().toString(), cl); // doing update
								//Needs to update events
								int maxC = cl.getMaxInShow();
								updateEventsByCity(c.getCityName(),maxC);
								
								
							}
							
						
							
						if (!manipulateCustomer(c, Manipulation.INSERT) && 	!manipulateCustomer(c, Manipulation.UPDATE))
							errors++;
					}
				}
				
				Alert alert = new Alert(AlertType.INFORMATION, "Data imported to data bases");
				alert.setHeaderText("Success");
				alert.setTitle("imported");
				alert.showAndWait();
				
			} catch (SAXException | IOException | ParserConfigurationException e) {
				e.printStackTrace();
			}
	    }
	    /**
	     * performs data manipulation in db on given customer.
	     * @param c customer to be manipulated.
	     * @param manipulation manipulation type.
	     * @return success or failure.
	     */
	  
	    public boolean updateColor(String colorName, Color c) {
	    	try {
				Class.forName(Consts.JDBC_STR);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement stmnt =  conn.prepareCall(util.Consts.UPDATE_COLOR)){
				int i = 1;	
				stmnt.setInt(i++, c.getMaxInShow());		
				stmnt.setInt(i++, c.getMaxInCapsule());
				stmnt.setString(i++, colorName);
				stmnt.executeUpdate();		
			
	    	return true;
	    } catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			return false;    
	    
	   }
	    
	    
	    public boolean manipulateCustomer(City c, Manipulation manipulation) {
	    	try {
	    		Class.forName(Consts.JDBC_STR);
	    		try (Connection conn = DriverManager.getConnection(Consts.CONN_STR);
	    				CallableStatement stmt = conn.prepareCall(
	    						(manipulation.equals(Manipulation.UPDATE)) ? 
	    								Consts.UPDATE_CITY : Consts.INSERT_CITY)) {
	    			allocateCustomerParams(stmt, c, manipulation);
	    			stmt.executeUpdate();   			
	    			return true;
	    		} catch (SQLException e) {
	    		}
	    	} catch (ClassNotFoundException e) {
	    	}    	
	    	return false;
	    }    
	    /**
	     * fills statement's placeholders with customer's field values.
	     * @param stmt statement object.
	     * @param c customer.
	     * @param m manipulation type.
	     * @throws SQLException
	     * @throws ClassNotFoundException 
	     */
	    private void allocateCustomerParams(CallableStatement stmt, City c, Manipulation m) throws SQLException, ClassNotFoundException
	    {	    	
	    	String str = c.getCityColor().toString();
	    	str = str.toLowerCase(); 
	    	int colorId=0; 	   

	    	
	    	if(str.equals("green"))
	    	{
	    		colorId = 3;
	    	}
	    	if(str.equals("yellow"))
	    	{
	    		colorId =2;
	    	}
	    	if(str.equals("red"))
	    	{
	    		colorId = 1;
	    	}   	
	    	
	    	Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement stmnt =  conn.prepareCall(util.Consts.UPDATE_CITY)){
				int i = 1;	
				stmnt.setInt(i++, colorId);		
				stmnt.setString(i++, c.getCityName());
				stmnt.executeUpdate();		
			}
	    	
	    }
	    
	    //This method get Color and return if need to update
	    public boolean isUpdateColor(Color c)
	    {
	    	ArrayList <Color> allColors = ControlShow.getInstance().getColors();
	    	for(Color i : allColors)
	    	{
	    		if(i == c) // if the colors are the same
	    		{
	    			return false;
	    		}
	    	}
	    	return true;

	    }
	    
	    public void updateEventsByCity(String cityName, int maxC)
	    {
	    	int showId;
	    	int theaterId;
    		try {
    			Class.forName(Consts.JDBC_STR);
    			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
    					CallableStatement stmt =  conn.prepareCall(util.Consts.SQL_GET_ALL_EVENTS_FROM_TODAY_BY_CITY)){
    				stmt.setString(1, cityName);	
    				ResultSet rs = stmt.executeQuery(); {
    				while (rs.next()) {
    					int i = 1;
    					showId = rs.getInt(i++);
    					theaterId = rs.getInt(i++);
    					updateTheStatusDate(showId, theaterId);
    					updateMaxCapcity(theaterId, maxC);
    				}
    					}
    			} catch (SQLException e) {
    				e.printStackTrace();
    			}
    		} catch (ClassNotFoundException e) {
    			e.printStackTrace();
    		}
	    	
	    	
	    }
	
	    
	    
	    public void updateTheStatusDate(int showId, int theaterId) throws SQLException, ClassNotFoundException
	    {    	    	
	    	Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement stmnt =  conn.prepareCall(util.Consts.SQL_UPD_UPDATEsTATUS_BY_SHOWID_THEATERID)){
				int i = 1;	
				stmnt.setDate(i++, java.sql.Date.valueOf(LocalDate.now()));
				stmnt.setInt(i++, showId);		
				stmnt.setInt(i++, theaterId);
				stmnt.executeUpdate();		
	    	
			}
			
	    }
	    
	    
	    public void updateMaxCapcity(int theaterID, int maxCap) throws SQLException, ClassNotFoundException
	    {
	    	Class.forName(Consts.JDBC_STR);
			try (Connection conn = DriverManager.getConnection(util.Consts.CONN_STR);
					CallableStatement stmnt =  conn.prepareCall(util.Consts.SQL_UPD_MAXCAPCITY)){
				int i = 1;	
				stmnt.setInt(i++, maxCap);
				stmnt.setInt(i++, theaterID);
				stmnt.executeUpdate();
	    	
			}
	    }
}

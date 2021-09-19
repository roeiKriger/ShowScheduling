package util;

import java.net.URLDecoder;


public class Consts {

	protected static final String DB_FILEPATH = getDBPath();
	public static final String CONN_STR =
			//"jdbc:ucanaccess://src/entity/Transfer_Database.accdb;COLUMNORDER=DISPLAY";
			"jdbc:ucanaccess://" + DB_FILEPATH;
	public static final String JDBC_STR = "net.ucanaccess.jdbc.UcanaccessDriver";

	
	
	 public enum Manipulation {
	    	UPDATE, INSERT, DELETE;
	    }
	
	 public static final String UPDATE_CITY = "{ call quer_upd_city(?,?); }"; 
	 public static final String INSERT_CITY = "{ call qryInsertCustomer(?,?); }";
	 public static final String UPDATE_COLOR = "{ call upd_color(?,?,?); }"; 
	
	/* ------------------------------ Show QUERIES ------------------------------ */
	
	public static final String SQL_SEL_SHOWS = "SELECT tbl_show.* FROM tbl_show";
	public static final String SQL_SEL_COLORS = "SELECT tbl_color.* FROM tbl_color";
	public static final String SQL_SEL_CITIES ="SELECT tbl_city.* FROM tbl_city";
	public static final String SQL_SEL_Managers="SELECT tbl_manager.* FROM tbl_manager";
	public static final String SQL_SEL_SPECIFIC_THEATER_OF_SHOW="SELECT tbl_theater.theaterId, tbl_theater.theaterName, tbl_theater.maxCapacity, tbl_theater.managerId, tbl_theater.cityId\r\n"
	+ "FROM tbl_theater LEFT JOIN tbl_show_theater ON tbl_theater.[theaterId] = tbl_show_theater.[theaterID]\r\n"
	+ "GROUP BY tbl_theater.theaterId, tbl_theater.theaterName, tbl_theater.maxCapacity, tbl_theater.managerId, tbl_theater.cityId, tbl_show_theater.showID\r\n"
	+ "HAVING (((tbl_show_theater.showID)=[Type the showID]));\r\n";
	public static final String SQL_SEL_THEATERS="SELECT tbl_theater.* FROM tbl_theater";
	//public static final String SQL_SEL_DATES_BY_SHOW="{ call quer_dates_by_show(?) }";
	public static final String SQL_SEL_DATES_BY_SHOW="{ call quer_date_show(?) }";
	public static final String SQL_SEL_DATES_BY_THEATER = "{ call quer_dates_in_theater(?) }"; // this return the id of theater that not valid
	public static final String SQL_SEL_THEATER_WITH_THIS_SHOW= "{ call quer_theater_with_this_show(?) }";
	
	public static final String SQL_SEL_ARTIST="SELECT tb_artist.artistId, tb_artist.artistName, tb_artist.janer, tb_artist.agentId FROM tb_artist";
	public static final String SQL_SEL_ARTIST_ID="SELECT tb_artist.artistId FROM tb_artist";
	public static final String SQL_COUNT_ARTIST_IN_SHOW="{ call count_artist_in_show(?) }";
	public static final String SQL_ARTIST_BY_SHOW_ID ="{ call quer_artist_by_show_id(?) }";
	
	
	public static final String SQL_INS_SCHEDULING_SHOW = "{ call quer_insert_scheduling(?,?,?,?,?,?) }";
	public static final String SQL_INS_SHOW ="{ call quer_insert_show(?,?,?)";
	public static final String SQL_INS_ARTIST_TO_SHOW ="{ call quer_insert_artist_to_show(?,?)";
	
	

	public static final String SQL_SEL_ALL_AGENTS = "SELECT tbl_agent.* FROM tbl_agent";

	public static final String SQL_SEL_ALL_ARTIST_IN_SHOW = "{ call quer_artist_in_show_by_show(?) }";
	public static final String SQL_SEL_ALL_ARTIST_IN_SHOW_BY_AGENT = "{ call quer_artist_in_show_by_agent(?,?) }";
	public static final String SQL_IS_PILOT = "{ call quer_is_pilot(?) }";
	public static final String SQL_SHOWS_BY_STATUS = "{ call quer_shows_by_status(?) }";
	//public static final String SQL_SHOWS_AND_STATUS = "{ call quer_get_show_and_status }";
	public static final String SQL_SHOWS_AND_STATUS = "SELECT tbl_show.showName, tbl_show_theater.status FROM tbl_show INNER JOIN tbl_show_theater ON (tbl_show.showId = tbl_show_theater.showID) AND (tbl_show.showId = tbl_show_theater.showID)GROUP BY tbl_show.showName, tbl_show_theater.status";

	
	
	
	////////////for export stuff////////
	public static final String SQL_SEL_ALL_SHOWS = "SELECT tbl_show.* FROM tbl_show";
	public static final String SQL_SEL_ALL_SHOWS_FOR_JSON ="SELECT tbl_show.showName, tbl_show.showLength, tbl_show.hasBreak, tbl_theater.theaterName, tbl_theater.maxCapacity, tbl_show_theater.dateOfShow, tbl_show_theater.startTime, tbl_show_theater.price, tbl_manager.managerName, tbl_city.cityName, tbl_show_theater.status, tbl_capsule.maxSeats, tbl_show.showId, tbl_theater.theaterId, tbl_show_theater.updateStatus\r\n"
			+ "FROM ((tbl_manager INNER JOIN (tbl_city INNER JOIN tbl_theater ON tbl_city.cityId = tbl_theater.cityId) ON tbl_manager.managerId = tbl_theater.managerId) INNER JOIN (tbl_show INNER JOIN tbl_show_theater ON tbl_show.showId = tbl_show_theater.showID) ON tbl_theater.theaterId = tbl_show_theater.theaterID) INNER JOIN tbl_capsule ON tbl_theater.theaterId = tbl_capsule.theaterId;\r\n"
			+ "";
	
	public static final String SQL_SEL_SHOW_BY_ID ="{call quer_get_show_by_id(?) }";
	
	public static final String SQL_SEL_THEATER_BY_ID ="{call quer_sel_theater_by_id(?) }";
	
	public static final String SQL_SEL_ALL_SHOW_IN_THEATER ="SELECT tbl_show_theater.*\r\n"
			+ "FROM tbl_show_theater;\r\n"
			+ "";
	
	
	public static final String SQL_SEL_ALL_USERS ="SELECT tbl_login.* FROM tbl_login;";
	
	public static final String SQL_UPD_STATUS_BY_SHOW_ID_THEATER_ID	 ="{ call quer_upd_status_by_show_id_theater_id(?,?,?) }";
	public static final String SQL_EXPORT_DATA	 ="{ call quer_Export_Data(?) }";
	
	public static final String SQL_EXPORT_DATA_TEST	 ="SELECT tbl_show_theater.dateOfShow, tbl_city.cityName, tbl_show_theater.price, tbl_show_theater.showID, tbl_show.showLength, tbl_color.maxInCapsule, tbl_theater.maxCapacity, tbl_manager.managerName, tbl_theater.theaterId, tbl_show.hasBreak, tbl_show_theater.status, tbl_show_theater.startTime, tbl_show_theater.updateStatus, tbl_show.showName, tbl_theater.theaterName\r\n"
			+ "FROM tbl_color INNER JOIN ((tbl_manager INNER JOIN (tbl_city INNER JOIN tbl_theater ON tbl_city.cityId = tbl_theater.cityId) ON tbl_manager.managerId = tbl_theater.managerId) INNER JOIN (tbl_show INNER JOIN tbl_show_theater ON tbl_show.showId = tbl_show_theater.showID) ON tbl_theater.theaterId = tbl_show_theater.theaterID) ON tbl_color.colorId = tbl_city.colorId\r\n"
			+ "WHERE (((tbl_show_theater.updateStatus)=?));\r\n";
	
	
	public static final String SQL_GET_ALL_EVENTS_FROM_TODAY_BY_CITY ="SELECT tbl_show_theater.showID, tbl_show_theater.theaterID\r\n"
			+ "FROM ((tbl_color INNER JOIN tbl_city ON tbl_color.colorId = tbl_city.colorId) INNER JOIN tbl_theater ON tbl_city.cityId = tbl_theater.cityId) INNER JOIN tbl_show_theater ON tbl_theater.theaterId = tbl_show_theater.theaterID\r\n"
			+ "WHERE (((tbl_city.cityName)=?) AND ((tbl_show_theater.dateOfShow)>Now()));\r\n"
			+ "";
	
	public static final String SQL_UPD_UPDATEsTATUS_BY_SHOWID_THEATERID = "{ call upd_updateStatus_by_showId_theaterId(?,?,?) }";
	public static final String SQL_UPD_MAXCAPCITY = "{ call upd_maxTheaterCapcity(?,?) }";

	
	
	/**
	 * find the correct path of the DB file
	 * @return the path of the DB file (from eclipse or with runnable file)
	 */
	
	private static String getDBPath() {
		try {
			String path = Consts.class.getProtectionDomain().getCodeSource().getLocation().getPath();
			String decoded = URLDecoder.decode(path, "UTF-8");
			if (decoded.contains(".jar")) {
				decoded = decoded.substring(0, decoded.lastIndexOf('/'));
				return decoded + "/src/entity/db.accdb";
			} else {
				decoded = decoded.substring(0, decoded.lastIndexOf("bin/"));
				return decoded + "src/entity/db.accdb";
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	
}

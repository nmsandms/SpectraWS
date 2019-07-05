package gr.wind.spectra.business;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;

import gr.wind.spectra.business.Help_Func;

// Notice, do not import com.mysql.cj.jdbc.*
// or you will have problems!

public class DB_Connection 
{
    // init database constants
    private static final String DATABASE_DRIVER = "com.mysql.cj.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://172.16.142.124:3306/SmartOutageDB?";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "vQaSx4iVipDPLKfmdVDc";
    private static final String MAX_POOL = "250";
    private static final String FileLogPath = "C:\\Users\\AP.KAPETANIOS\\eclipse_enterprise\\SpectraWS\\logs\\DB_Operations.txt";
    java.sql.Connection conn = null;
    
    FileLogger LogFile = new FileLogger(FileLogPath);
    
    
    public Connection Connect()
    {
    	LogFile.Log(Help_Func.GetTimeStamp() + "Starting Connection with database...");
    	try {
    	    conn =
    	       DriverManager.getConnection(DATABASE_URL +
    	                                   "user=" + USERNAME + "&" +
    	                                   "password=" + PASSWORD + "&" +
    	                                   "useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC");

    	    LogFile.Log(Help_Func.GetTimeStamp() + "Connection established!");
    	    // Do something with the Connection
    	    
    	} catch (SQLException ex) {
    	    // handle any errors
    	    System.out.println("SQLException: " + ex.getMessage());
    	    System.out.println("SQLState: " + ex.getSQLState());
    	    System.out.println("VendorError: " + ex.getErrorCode());
    	    conn = null;
    	    
    	    LogFile.Log(Help_Func.GetTimeStamp() + "Could not open connection with database!");
    	}
		return conn;      	
    	
    }
    
    public boolean IsActive() throws SQLException
    {
    	if (conn.isValid(0))
    	{
    		return true;
    	}
    	else
    	{
    		return false;
    	}
    }
    
    
    
    public void closeDBConnection() throws SQLException
    {
    	conn.close();
    	LogFile.Log(Help_Func.GetTimeStamp() + "Closing DB Connection");
    }

    public static void main(String[] args) throws SQLException 
    {

    	Statement stmt = null;
    	ResultSet rs = null;
    	
    	List<Integer> idList = new ArrayList<Integer>();
    	List<String> userList = new ArrayList<String>();
    	
    	DB_Connection conObj = new DB_Connection();
    	Connection conn = conObj.Connect();
    	DB_Operations dbs = new DB_Operations(conn);
    	
    	// O P E R A T I O N S
    	//------------------------------------------------------
    	//boolean result = dbs.checkIfStringExistsInSpecificColumn("TestTable", "Name", "Apostolis Kapetanios");
    	//------------------------------------------------------
    	//boolean result = dbs.InsertValuesInTable("TestTable", new String[] {"ID", "Name"}, new String[] {"3", "Nikos Zorzos"});
    	//------------------------------------------------------
//    	List<String> myList = new ArrayList<String>();
//    	myList = new ArrayList<String>();
//    	myList = dbs.GetOneColumnResultSet("TestTable", "ID", "Name = 'Nikos Zorzos'");
//    	for (String item : myList)
//    	{
//    		System.out.println(item);
//    	}
//    	conObj.closeDBConnection();

    	//------------------------------------------------------
    	//int rowsAffected = dbs.UpdateValuesForOneColumn("TestTable", "Surname", "Vernikos", "Name = 'Apostolis' or Name = 'Manos'");
    	//System.out.println("Rows affected: " + rowsAffected);
    	
    }
    
}

  	    //System.out.println(sqlString);
	    //rs = stmt.executeQuery("sqlString");
	    /*if (rs.next())
	    {
	    	found = true;
	    }
	    */
	    // or alternatively, if you don't know ahead of time that
	    // the query will be a SELECT...
	
	    // if (stmt.execute("SELECT " + field + " FROM " + table + " WHERE " + field + "= " + searchValue)) 
	    
  	    //if (stmt.execute("SELECT Name from TestTable where Name = \"Apostolis Kapetanios\""))
  	     //if (stmt.execute(sqlString))
	    // {
        //    rs = stmt.getResultSet();
	    // }
	    // if (rs.next())
	    // {
	    // 	found = true;
	    // }
        	
    	    // Now do something with the ResultSet ....
    	   /* while (rs.next()) 
    	    {
               /* String coffeeName = rs.getString("COF_NAME");
                int supplierID = rs.getInt("SUP_ID");
                float price = rs.getFloat("PRICE");
                int sales = rs.getInt("SALES");
                int total = rs.getInt("TOTAL");
                
                ## Time 
                java.sql.Time dbSqlTime = rs.getTime(1);
        		java.sql.Date dbSqlDate = rs.getDate(2);
        		java.sql.Timestamp dbSqlTimestamp = rs.getTimestamp();
    	    	java.sql.Date dbSqlDate = rs.getDate("CurrentDate");
  	    	
    	    	int    myID     = rs.getInt("ID");
    	    	String myString = rs.getString("Name");

    	    	
    	    	String CurrentValue = rs.getString("Name");
    	    	
    	    	
    	    	if (CurrentValue.equals(anObject) )
    	    	
    	    	String 
    	    }
    	    */

	
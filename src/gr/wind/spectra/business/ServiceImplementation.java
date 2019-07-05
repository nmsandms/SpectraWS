package gr.wind.spectra.business;

import java.sql.Connection;
import java.sql.SQLException;

import gr.wind.spectra.business.DB_Connection;
import gr.wind.spectra.business.DB_Operations;

public class ServiceImplementation {

	DB_Connection conObj;
	Connection conn;
	DB_Operations dbs;
	
	public ServiceImplementation()
	{
		conObj = new DB_Connection();
		conn = conObj.Connect();
		dbs = new DB_Operations(conn);
	}
	
	
	public int submitOutage()
	{
		int rowsAffected = 0;
		
    	try {
			rowsAffected = dbs.UpdateValuesForOneColumn("TestTable", "Surname", "Kapetanios", "Name = 'Apostolis' or Name = 'Manos'");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
  	
    	
		return rowsAffected;
		
	}
	
	
	public static void main(String args[])
	{
		ServiceImplementation s1 = new ServiceImplementation();
		System.out.println("Rows affected: " + s1.submitOutage());;
	}
}

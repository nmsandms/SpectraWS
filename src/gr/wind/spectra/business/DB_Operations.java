package gr.wind.spectra.business;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import gr.wind.spectra.web.InvalidInputException;

//Import log4j classes.
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DB_Operations extends Thread
{
	// Logger instance
	private static final Logger logger = LogManager.getLogger(gr.wind.spectra.business.DB_Operations.class.getName());
	
	Connection conn;
	Statement stmt = null;
	ResultSet rs = null;

	public DB_Operations(Connection conn)
	{
		this.conn = conn;
	}

	@Override
	public void run()
	{
		System.out.println("New Thread is created here!");
		Charset utf8 = StandardCharsets.UTF_8;
		List<String> lines = Arrays.asList("1st line", "2nd line");
		try
		{
			Files.write(Paths.get("MyExportFile.txt"), lines, utf8, StandardOpenOption.CREATE,
					StandardOpenOption.APPEND);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public boolean checkIfStringExistsInSpecificColumn(String table, String columnName, String searchValue)
			throws SQLException
	{
		boolean found = false;

		String sqlString = "SELECT `" + columnName + "` FROM `" + table + "` WHERE `" + columnName + "` = ?";
		PreparedStatement pst = conn.prepareStatement(sqlString);
		pst.setString(1, searchValue);
		pst.execute();

		ResultSet rs = pst.executeQuery();

		while (rs.next())
		{
			String current = rs.getString(columnName);

			if (current.contentEquals(searchValue))
			{
				found = true;
			}
		}
		return found;
	}

	public boolean insertValuesInTable(String table, String[] columnNames, String[] columnValues, String[] types)
			throws SQLException, ParseException
	{
		boolean statusOfOperation = false;
		String sqlString = "INSERT INTO " + table + Help_Func.columnsToInsertStatement(columnNames)
				+ Help_Func.valuesToInsertStatement(columnValues);
		System.out.println(sqlString);
		PreparedStatement pst = conn.prepareStatement(sqlString, Statement.RETURN_GENERATED_KEYS);

		for (int i = 0; i < columnNames.length; i++)
		{
			if (columnValues[i].equals(""))
			{
				pst.setNull(i + 1, Types.NULL);
			} else
			{
				if (types[i].equals("String"))
				{
					pst.setString(i + 1, columnValues[i]);
				} else if (types[i].equals("DateTime"))
				{
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					LocalDateTime dateTime = LocalDateTime.parse(columnValues[i], formatter);
					pst.setObject(i + 1, dateTime);
				} else if (types[i].equals("Integer"))
				{
					pst.setInt(i + 1, Integer.parseInt(columnValues[i]));
				}

			}
		}

		try
		{
			pst.executeUpdate();
			statusOfOperation = true;
			// Code to get Generated Key
			// ResultSet tableKeys = pst.getGeneratedKeys();
			// tableKeys.next();
			// autoGeneratedID = Integer.toString(tableKeys.getInt(1));
		} catch (SQLException e)
		{
			statusOfOperation = false;
			e.printStackTrace();

		}

		return statusOfOperation;
	}

	public int getMaxIntegerValue(String table, String columnName) throws SQLException
	{
		int returnValue = 0;
		String sqlString = "SELECT MAX(" + columnName + ") FROM " + table;
		PreparedStatement pst = conn.prepareStatement(sqlString);
		pst.execute();
		ResultSet rs = pst.executeQuery();

		while (rs.next())
		{
			returnValue = rs.getInt(1);
		}

		return returnValue;
	}

	public boolean checkIfCriteriaExists(String table, String[] predicateKeys, String[] predicateValues,
			String[] predicateTypes) throws SQLException
	{
		boolean criteriaIfExists = false;

		String sqlQuery = "SELECT COUNT(*) as Result FROM " + table + " WHERE "
				+ Help_Func.generateANDPredicateQuestionMarks(predicateKeys);
		System.out.println(sqlQuery);
		PreparedStatement pst = conn.prepareStatement(sqlQuery);
		ResultSet rs = null;
		for (int i = 0; i < predicateKeys.length; i++)
		{
			if (predicateTypes[i].equals("String"))
			{
				pst.setString(i + 1, predicateValues[i]);
			} else if (predicateTypes[i].equals("Integer"))
			{
				pst.setInt(i + 1, Integer.parseInt(predicateValues[i]));
			}
		}

		rs = pst.executeQuery();
		rs.next();
		String numOfRowsFound = rs.getString("Result");

		if (!numOfRowsFound.equals("0"))
		{
			criteriaIfExists = true;
		}

		return criteriaIfExists;
	}

	public String getOneValue(String table, String columnName, String[] predicateKeys, String[] predicateValues,
			String[] predicateTypes) throws SQLException
	{
		String sqlQuery = "SELECT " + columnName + " FROM " + table + " WHERE "
				+ Help_Func.generateANDPredicateQuestionMarks(predicateKeys);
		System.out.println(sqlQuery);
		PreparedStatement pst = conn.prepareStatement(sqlQuery);

		for (int i = 0; i < predicateKeys.length; i++)
		{
			if (predicateTypes[i].equals("String"))
			{
				pst.setString(i + 1, predicateValues[i]);
			} else if (predicateTypes[i].equals("Integer"))
			{
				pst.setInt(i + 1, Integer.parseInt(predicateValues[i]));
			}
		}
		pst.execute();
		ResultSet rs = pst.executeQuery();
		rs.next();
		return rs.getString(columnName);
	}

	public List<String> getOneColumnUniqueResultSet(String table, String columnName, String[] predicateKeys,
			String[] predicateValues, String[] predicateTypes) throws SQLException
	{
		// Example: select DISTINCT ID from table where a = 2 and b = 3

		List<String> myList = new ArrayList<String>();

		String sqlString = "SELECT DISTINCT `" + columnName + "` FROM `" + table + "` WHERE "
				+ Help_Func.generateANDPredicateQuestionMarks(predicateKeys);
		System.out.println(sqlString);
//		System.out.println("We are HERE");
//		System.out.println("columnName " + columnName);
//		System.out.println("predicateKeys " + Arrays.toString(predicateKeys));
//		System.out.println("predicateValues " + Arrays.toString(predicateValues));
//		System.out.println("predicateTypes " + Arrays.toString(predicateTypes));

		PreparedStatement pst = conn.prepareStatement(sqlString);

		for (int i = 0; i < predicateKeys.length; i++)
		{
			if (predicateTypes[0].equals("String"))
			{
				pst.setString(i + 1, predicateValues[i]);
			} else if (predicateTypes[1].equals("Integer"))
			{
				pst.setString(i + 1, predicateValues[i]);
			}
		}

		pst.execute();
		ResultSet rs = null;
		rs = pst.executeQuery();

		try
		{
			while (rs.next())
			{
				String current = rs.getString(columnName);
				if (!(current == null || current.isEmpty()))
				{
					myList.add(current);
				}
			}
		} catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Sort list alphabetically
		java.util.Collections.sort(myList);

		return myList;
	}

	public int updateValuesForOneColumn(String table, String setColumnName, String newValue, String[] predicateKeys,
			String[] predicateValues, String[] predicateTypes) throws SQLException
	{
		// Example: update TestTable set `Name` = 100 where Surname = "Kapetanios";

		String sqlString = "update `" + table + "` set `" + setColumnName + "` = '" + newValue + "' WHERE "
				+ Help_Func.generateANDPredicateQuestionMarks(predicateKeys);
		System.out.println(sqlString);
		PreparedStatement pst = conn.prepareStatement(sqlString);

		for (int i = 0; i < predicateKeys.length; i++)
		{
			if (predicateTypes[i].equals("String"))
			{
				pst.setString(i + 1, predicateValues[i]);
			} else if (predicateTypes[i].equals("Integer"))
			{
				pst.setInt(i + 1, Integer.parseInt(predicateValues[i]));
			}
		}

		int rowsAffected = pst.executeUpdate();

		return rowsAffected;

	}

	public String numberOfRowsFound(String table, String[] predicateKeys, String[] predicateValues,
			String[] predicateTypes) throws SQLException
	{
		int numOfRows = 0;
		String sqlQuery = "SELECT *" + " FROM " + table + " WHERE "
				+ Help_Func.generateANDPredicateQuestionMarks(predicateKeys);
		System.out.println(sqlQuery);
		PreparedStatement pst = conn.prepareStatement(sqlQuery);

		for (int i = 0; i < predicateKeys.length; i++)
		{
			if (predicateTypes[i].equals("String"))
			{
				pst.setString(i + 1, predicateValues[i]);
			} else if (predicateTypes[i].equals("Integer"))
			{
				pst.setInt(i + 1, Integer.parseInt(predicateValues[i]));
			}
		}

		pst.execute();
		ResultSet rs = pst.executeQuery();

		while (rs.next())
		{
			numOfRows++;
		}

		return Integer.toString(numOfRows);
	}

	public String countDistinctRowsForSpecificColumn(String table, String column, String[] predicateKeys,
			String[] predicateValues, String[] predicateTypes) throws SQLException
	{
		String numOfRows = "";
		String sqlQuery = "SELECT COUNT(DISTINCT(" + column + ")) as " + column + " FROM " + table + " WHERE "
				+ Help_Func.generateANDPredicateQuestionMarks(predicateKeys);
		System.out.println(sqlQuery);
		PreparedStatement pst = conn.prepareStatement(sqlQuery);

		for (int i = 0; i < predicateKeys.length; i++)
		{
			if (predicateTypes[i].equals("String"))
			{
				pst.setString(i + 1, predicateValues[i]);
			} else if (predicateTypes[i].equals("String"))
			{
				pst.setString(i + 1, predicateValues[i]);
			}
		}

		pst.execute();
		ResultSet rs = pst.executeQuery();

		while (rs.next())
		{
			numOfRows = rs.getString(column);
			// numOfRows++;
		}

		return numOfRows;
	}

	/*
	 * SELECT COUNT(*) FROM ( SELECT DISTINCT ActiveElement,Subrack,Slot,Port,PON
	 * FROM Voice_Resource_Path WHERE SiteName = 'ACHARNAI' AND ActiveElement =
	 * 'ATHOACHRNAGW01' AND Subrack = '2' AND Slot = '04' ) as AK;
	 *
	 */

	public String countDistinctRowsForSpecificColumns(String table, String[] columns, String[] predicateKeys,
			String[] predicateValues, String[] predicateTypes) throws SQLException
	{
		String numOfRows = "";
		String sqlQuery = "SELECT COUNT(*) AS Result FROM(SELECT DISTINCT ";

		for (int i = 0; i < columns.length; i++)
		{
			if (i < columns.length - 1)
			{
				sqlQuery += columns[i] + ",";
			} else
			{
				sqlQuery += columns[i];
			}
		}

		sqlQuery += " FROM " + table + " WHERE " + Help_Func.generateANDPredicateQuestionMarks(predicateKeys)
				+ ") as AK ";

		System.out.println(sqlQuery);
		PreparedStatement pst = conn.prepareStatement(sqlQuery);

		for (int i = 0; i < predicateKeys.length; i++)
		{
			if (predicateTypes[i].equals("String"))
			{
				pst.setString(i + 1, predicateValues[i]);
			} else if (predicateTypes[i].equals("Integer"))
			{
				pst.setInt(i + 1, Integer.parseInt(predicateValues[i]));
			}
		}

		pst.execute();
		ResultSet rs = pst.executeQuery();

		while (rs.next())
		{
			numOfRows = rs.getString("Result");
		}
		return numOfRows;
	}

	public ResultSet getRows(String table, String[] columnNames, String[] predicateKeys, String[] predicateValues,
			String[] predicateTypes) throws SQLException
	{
		String sqlQuery = "SELECT " + Help_Func.columnsWithCommas(columnNames) + " FROM " + table + " WHERE "
				+ Help_Func.generateANDPredicateQuestionMarks(predicateKeys);

		PreparedStatement pst = conn.prepareStatement(sqlQuery);
		for (int i = 0; i < predicateKeys.length; i++)
		{
			if (predicateTypes[i].equals("String"))
			{
				pst.setString(i + 1, predicateValues[i]);
			} else if (predicateTypes[i].equals("Integer"))
			{
				pst.setInt(i + 1, Integer.parseInt(predicateValues[i]));
			}
		}

		System.out.println(sqlQuery);

		pst.execute();
		ResultSet rs = pst.executeQuery();
		return rs;
	}

	public boolean authenticateRequest(String userName, String password) throws SQLException
	{
		boolean found = false;
		String table = "WSAccounts";
		String sqlString = "SELECT * FROM `" + table + "` WHERE `UserName` = ?";
		PreparedStatement pst = conn.prepareStatement(sqlString);
		pst.setString(1, userName);
		// pst.setString(2, password);
		pst.execute();

		ResultSet rs = pst.executeQuery();

		while (rs.next())
		{
			rs.getString("UserName");
			String r_password = rs.getString("Password");

			boolean passwordIsCorrect = false;
			try
			{
				/**
				 * Complete Implementation of Password hashing
				 * https://stackoverflow.com/questions/2860943/how-can-i-hash-a-password-in-java
				 * We are storing 'salt$iterated_hash(password, salt)'. The salt are 32 random
				 * bytes and it's purpose is that if two different people choose the same
				 * password, the stored passwords will still look different.
				 */
				passwordIsCorrect = Password.check(password, r_password);

//					System.out.println("password " + password);
//					System.out.println("r_password " + r_password);
//					System.out.println("passwordIsCorrect " + passwordIsCorrect);
			} catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			if (passwordIsCorrect)
			{
				found = true;
			} else
			{
				found = false;
			}
		}
		return found;
	}

	public String maxNumberOfCustomersAffected(String table, String SumOfColumn, String[] predicateColumns,
			String[] predicateValues) throws SQLException
	{
		String numOfRows = "";
		String sqlQuery = "SELECT MAX(" + SumOfColumn + ") as Result FROM " + table + " WHERE "
				+ Help_Func.generateANDPredicateQuestionMarks(predicateColumns);
		System.out.println(sqlQuery);
		PreparedStatement pst = conn.prepareStatement(sqlQuery);

		if (predicateColumns.length == predicateValues.length)
		{
			for (int i = 0; i < predicateColumns.length; i++)
			{
				pst.setString(i + 1, predicateValues[i]);
			}
		}

		ResultSet rs = pst.executeQuery();
		rs.next();
		numOfRows = rs.getString("Result");

		return numOfRows;
	}

	public int updateColumnOnSpecificCriteria(String tableName, String[] columnNamesForUpdate,
			String[] columnValuesForUpdate, String[] setColumnDataTypes, String[] predicateColumns,
			String[] predicateValues, String[] predicateColumnsDataTypes) throws SQLException, InvalidInputException
	{
		int numOfRowsUpdated = 0;
		// update SmartOutageDB.SubmittedIncidents set Duration = '2' where OutageID =
		// '5' and IncidentID = 'INC1';
		String sqlQuery = "UPDATE " + tableName + " SET "
				+ Help_Func.generateCommaPredicateQuestionMarks(columnNamesForUpdate) + " WHERE "
				+ Help_Func.generateANDPredicateQuestionMarks(predicateColumns);
		System.out.println(sqlQuery);

		PreparedStatement pst = conn.prepareStatement(sqlQuery);

		// If no predicates are provided then SQL update command will update all
		// (something VERY undesirable)
		if (columnNamesForUpdate.length == 0)
		{
			throw new InvalidInputException("No predicated provided for SQL update - Aborting Operation", "Error 702");
		} else
		{
			// Set Values for the Updated Columns (SET Part of SQL Expresion)
			for (int i = 0; i < columnNamesForUpdate.length; i++)
			{
				if (setColumnDataTypes[i].equals("String"))
				{
					pst.setString(i + 1, columnValuesForUpdate[i]);
				} else if (setColumnDataTypes[i].equals("Integer"))
				{
					pst.setInt(i + 1, Integer.parseInt(columnValuesForUpdate[i]));
				} else if (setColumnDataTypes[i].equals("Date"))
				{
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
					LocalDateTime dateTime = LocalDateTime.parse(columnValuesForUpdate[i], formatter);

					pst.setObject(i + 1, dateTime);
				}

			}

			// Set Values for the predicate Columns (WHERE Part of SQL Expresion)
			if (predicateColumns.length == predicateValues.length)
			{
				for (int i = 0; i < predicateColumns.length; i++)
				{
					if (predicateColumnsDataTypes[i].equals("String"))
					{
						pst.setString(i + columnNamesForUpdate.length + 1, predicateValues[i]);
					} else if (predicateColumnsDataTypes[i].equals("Integer"))
					{
						pst.setInt(i + columnNamesForUpdate.length + 1, Integer.parseInt(predicateValues[i]));
					} else if (predicateColumnsDataTypes[i].equals("Date"))
					{
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
						LocalDateTime dateTime = LocalDateTime.parse(predicateValues[i], formatter);

						pst.setObject(i + columnNamesForUpdate.length + 1, dateTime);
					}
				}
			}
		}

		numOfRowsUpdated = pst.executeUpdate();
		return numOfRowsUpdated;
	}

}

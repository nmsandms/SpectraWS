package gr.wind.spectra.business;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Update_CallerDataTable extends Thread
{
	DB_Operations dbs;
	s_DB_Operations s_dbs;
	String CLIProvided;
	String IncidentID;
	String allAffectedServices;
	String foundScheduled;

	public Update_CallerDataTable(DB_Operations dbs, s_DB_Operations s_dbs, String CLIProvided, String IncidentID,
			String allAffectedServices, String foundScheduled)
	{
		this.dbs = dbs;
		this.s_dbs = s_dbs;
		this.CLIProvided = CLIProvided;
		this.IncidentID = IncidentID;
		this.allAffectedServices = allAffectedServices;
		this.foundScheduled = foundScheduled;
	}

	@Override
	public void run()
	{
		// System.out.println("Running thread for Update_CallerDataTable...");
		Help_Func hf = new Help_Func();

		try
		{
			// Check if cli value exists in Prov_Internet_Resource_Path CliValue column
			//boolean cliValueExistsInResourceTable = dbs.checkIfStringExistsInSpecificColumn("Prov_Internet_Resource_Path",
			//		"CliValue", CLIProvided);

			// Get number of rows
			String numOfRowsFound = dbs.numberOfRowsFound("Prov_Internet_Resource_Path", new String[] { "CliValue" },
					new String[] { CLIProvided }, new String[] { "String" });

			if (Integer.parseInt(numOfRowsFound) > 0) // CLI was found in Internet_Resource_Path
			{
				ResultSet rs = null;

				// Get Lines from Internet Resource Path
				rs = dbs.getRows("Prov_Internet_Resource_Path",
						new String[] { "NGA_TYPE", "GeneralArea", "SiteName", "Concentrator", "AccessService",
								"PoP_Name", "PoP_Code", "OltElementName", "OltRackNo", "OltSubRackNo", "OltSlot",
								"OltPort", "Onu", "KvCode", "CabinetCode", "ActiveElement", "Rack", "Subrack", "Slot",
								"Port", "PORT_LOCATION", "PORT_CABLE_CODE", "PORT_ID", "CLID", "Username",
								"PASPORT_COID", "LOOP_NUMBER", "CLI_TYPE", "Domain", "ServiceType", "BRASNAME" },
						new String[] { "CliValue" }, new String[] { CLIProvided }, new String[] { "String" });

				while (rs.next())
				{

					String NGA_TYPE = rs.getString("NGA_TYPE");
					String GeneralArea = rs.getString("GeneralArea");
					String SiteName = rs.getString("SiteName");
					String Concentrator = rs.getString("Concentrator");
					String AccessService = rs.getString("AccessService");
					String PoP_Name = rs.getString("PoP_Name");
					String PoP_Code = rs.getString("PoP_Code");
					String OltElementName = rs.getString("OltElementName");
					String OltRackNo = rs.getString("OltRackNo");
					String OltSubRackNo = rs.getString("OltSubRackNo");
					String OltSlot = rs.getString("OltSlot");
					String OltPort = rs.getString("OltPort");
					String Onu = rs.getString("Onu");
					String KvCode = rs.getString("KvCode");
					String CabinetCode = rs.getString("CabinetCode");
					String ActiveElement = rs.getString("ActiveElement");
					String Rack = rs.getString("Rack");
					String Subrack = rs.getString("Subrack");
					String Slot = rs.getString("Slot");
					String Port = rs.getString("Port");
					String PORT_LOCATION = rs.getString("PORT_LOCATION");
					String PORT_CABLE_CODE = rs.getString("PORT_CABLE_CODE");
					String PORT_ID = rs.getString("PORT_ID");
					String CLID = rs.getString("CLID");
					String Username = rs.getString("Username");
					String PASPORT_COID = rs.getString("PASPORT_COID");
					String LOOP_NUMBER = rs.getString("LOOP_NUMBER");
					String CLI_TYPE = rs.getString("CLI_TYPE");
					String Domain = rs.getString("Domain");
					String ServiceType = rs.getString("ServiceType");
					String BRASNAME = rs.getString("BRASNAME");

					if (NGA_TYPE == null)
					{
						NGA_TYPE = "";
					}
					if (GeneralArea == null)
					{
						GeneralArea = "";
					}
					if (SiteName == null)
					{
						SiteName = "";
					}
					if (Concentrator == null)
					{
						Concentrator = "";
					}
					if (AccessService == null)
					{
						AccessService = "";
					}
					if (PoP_Name == null)
					{
						PoP_Name = "";
					}
					if (PoP_Code == null)
					{
						PoP_Code = "";
					}
					if (OltElementName == null)
					{
						OltElementName = "";
					}
					if (OltRackNo == null)
					{
						OltRackNo = "";
					}
					if (OltSubRackNo == null)
					{
						OltSubRackNo = "";
					}
					if (OltSlot == null)
					{
						OltSlot = "";
					}
					if (OltPort == null)
					{
						OltPort = "";
					}
					if (Onu == null)
					{
						Onu = "";
					}
					if (KvCode == null)
					{
						KvCode = "";
					}
					if (CabinetCode == null)
					{
						CabinetCode = "";
					}
					if (ActiveElement == null)
					{
						ActiveElement = "";
					}
					if (Rack == null)
					{
						Rack = "";
					}
					if (Subrack == null)
					{
						Subrack = "";
					}
					if (Slot == null)
					{
						Slot = "";
					}
					if (Port == null)
					{
						Port = "";
					}
					if (PORT_LOCATION == null)
					{
						PORT_LOCATION = "";
					}
					if (PORT_CABLE_CODE == null)
					{
						PORT_CABLE_CODE = "";
					}
					if (PORT_ID == null)
					{
						PORT_ID = "";
					}
					if (CLID == null)
					{
						CLID = "";
					}
					if (Username == null)
					{
						Username = "";
					}
					if (PASPORT_COID == null)
					{
						PASPORT_COID = "";
					}
					if (LOOP_NUMBER == null)
					{
						LOOP_NUMBER = "";
					}
					if (CLI_TYPE == null)
					{
						CLI_TYPE = "";
					}
					if (Domain == null)
					{
						Domain = "";
					}
					if (ServiceType == null)
					{
						ServiceType = "";
					}
					if (BRASNAME == null)
					{
						BRASNAME = "";
					}

					//					System.out.println("CLIProvided          " + CLIProvided);
					//					System.out.println("hf.now()             " + hf.now());
					//					System.out.println("IncidentID           " + IncidentID);
					//					System.out.println("allAffectedServices  " + allAffectedServices);
					//					System.out.println("foundScheduled       " + foundScheduled);
					//					System.out.println("NGA_TYPE             " + NGA_TYPE);
					//					System.out.println("GeneralArea          " + GeneralArea);
					//					System.out.println("SiteName             " + SiteName);
					//					System.out.println("Concentrator         " + Concentrator);
					//					System.out.println("AccessService        " + AccessService);
					//					System.out.println("PoP_Name             " + PoP_Name);
					//					System.out.println("PoP_Code             " + PoP_Code);
					//					System.out.println("OltElementName       " + OltElementName);
					//					System.out.println("OltRackNo            " + OltRackNo);
					//					System.out.println("OltSubRackNo         " + OltSubRackNo);
					//					System.out.println("OltSlot              " + OltSlot);
					//					System.out.println("OltPort              " + OltPort);
					//					System.out.println("Onu                  " + Onu);
					//					System.out.println("KvCode               " + KvCode);
					//					System.out.println("CabinetCode          " + CabinetCode);
					//					System.out.println("ActiveElement        " + ActiveElement);
					//					System.out.println("Rack                 " + Rack);
					//					System.out.println("Subrack              " + Subrack);
					//					System.out.println("Slot                 " + Slot);
					//					System.out.println("Port                 " + Port);
					//					System.out.println("PORT_LOCATION        " + PORT_LOCATION);
					//					System.out.println("PORT_CABLE_CODE      " + PORT_CABLE_CODE);
					//					System.out.println("PORT_ID              " + PORT_ID);
					//					System.out.println("CLID                 " + CLID);
					//					System.out.println("Username             " + Username);
					//					System.out.println("PASPORT_COID         " + PASPORT_COID);
					//					System.out.println("LOOP_NUMBER          " + LOOP_NUMBER);
					//					System.out.println("CLI_TYPE             " + CLI_TYPE);
					//					System.out.println("Domain               " + Domain);
					//					System.out.println("ServiceType          " + ServiceType);
					//					System.out.println("BRASNAME             " + BRASNAME);

					s_dbs.insertValuesInTable("Caller_Data",
							new String[] { "CliValue", "DateTimeCalled", "Affected_by_IncidentID", "AffectedServices",
									"Scheduled", "NGA_TYPE", "GeneralArea", "SiteName", "Concentrator", "AccessService",
									"PoP_Name", "PoP_Code", "OltElementName", "OltRackNo", "OltSubRackNo", "OltSlot",
									"OltPort", "Onu", "KvCode", "CabinetCode", "ActiveElement", "Rack", "Subrack",
									"Slot", "Port", "PORT_LOCATION", "PORT_CABLE_CODE", "PORT_ID", "CLID", "Username",
									"PASPORT_COID", "LOOP_NUMBER", "CLI_TYPE", "Domain", "ServiceType", "BRASNAME" },
							new String[] { CLIProvided, hf.now(), IncidentID, allAffectedServices, foundScheduled,
									NGA_TYPE, GeneralArea, SiteName, Concentrator, AccessService, PoP_Name, PoP_Code,
									OltElementName, OltRackNo, OltSubRackNo, OltSlot, OltPort, Onu, KvCode, CabinetCode,
									ActiveElement, Rack, Subrack, Slot, Port, PORT_LOCATION, PORT_CABLE_CODE, PORT_ID,
									CLID, Username, PASPORT_COID, LOOP_NUMBER, CLI_TYPE, Domain, ServiceType,
									BRASNAME },
							new String[] { "String", "DateTime", "String", "String", "String", "String", "String",
									"String", "String", "String", "String", "String", "String", "String", "String",
									"String", "String", "String", "String", "String", "String", "String", "String",
									"String", "String", "String", "String", "String", "String", "String", "String",
									"String", "String", "String", "String", "String" });

				}

			} else // CLI was not found in Internet_Resource_Path
			{
				s_dbs.insertValuesInTable("Caller_Data",
						new String[] { "CliValue", "DateTimeCalled", "Affected_by_IncidentID", "AffectedServices",
								"Scheduled", "NGA_TYPE", "GeneralArea", "SiteName", "Concentrator", "AccessService",
								"PoP_Name", "PoP_Code", "OltElementName", "OltRackNo", "OltSubRackNo", "OltSlot",
								"OltPort", "Onu", "KvCode", "CabinetCode", "ActiveElement", "Rack", "Subrack", "Slot",
								"Port", "PORT_LOCATION", "PORT_CABLE_CODE", "PORT_ID", "CLID", "Username",
								"PASPORT_COID", "LOOP_NUMBER", "CLI_TYPE", "Domain", "ServiceType", "BRASNAME" },
						new String[] { CLIProvided, hf.now(), "", "", "", "", "", "", "", "", "", "", "", "", "", "",
								"", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "", "" },
						new String[] { "String", "DateTime", "String", "String", "String", "String", "String", "String",
								"String", "String", "String", "String", "String", "String", "String", "String",
								"String", "String", "String", "String", "String", "String", "String", "String",
								"String", "String", "String", "String", "String", "String", "String", "String",
								"String", "String", "String", "String" });
			}

		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

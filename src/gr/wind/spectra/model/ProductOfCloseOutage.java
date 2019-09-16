package gr.wind.spectra.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "Element")
@XmlType(name = "basicStruct6", propOrder = { "requestID", "incidentID", "outageID", "statusCode", "description" })
public class ProductOfCloseOutage
{
	private String requestID;
	private String incidentID;
	private String outageID;
	private String statusCode;
	private String description;

	public ProductOfCloseOutage()
	{
		// TODO Auto-generated constructor stub
	}

	public ProductOfCloseOutage(String requestID, String incidentID, String outageID, String statusCode,
			String description)
	{
		this.requestID = requestID;
		this.incidentID = incidentID;
		this.outageID = outageID;
		this.statusCode = statusCode;
		this.description = description;
	}

	@XmlElement(name = "requestID")
	public String getrequestID()
	{
		return requestID;
	}

	public void setrequestID(String requestID)
	{
		this.requestID = requestID;
	}

	@XmlElement(name = "incidentID")
	public String getincidentID()
	{
		return incidentID;
	}

	public void setincidentID(String incidentID)
	{
		this.incidentID = incidentID;
	}

	@XmlElement(name = "outageID")
	public String getoutageID()
	{
		return outageID;
	}

	public void setoutageID(String outageID)
	{
		this.outageID = outageID;
	}

	@XmlElement(name = "statusCode")
	public String getstatusCode()
	{
		return statusCode;
	}

	public void setstatusCode(String statusCode)
	{
		this.statusCode = statusCode;
	}

	@XmlElement(name = "description")
	public String getdescription()
	{
		return description;
	}

	public void setdescription(String description)
	{
		this.description = description;
	}

}

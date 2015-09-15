package model;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.Scanner;

/**
 * @author S.Trémouille
 * A database model fetching all informations needed to establish a connection
 *
 */
public class Database {
	private LINK_TYPE connexionType;
	private String odbcLinkName;
	private String login;
	private String password;
	private String serverURL;
	private String databaseName;
	private String instanceName;
	private String alias;
	private boolean activated;
	
	/**
	 * Constructor
	 * @param alias
	 * @param connexionType
	 * @param odbcLinkName
	 * @param login
	 * @param password
	 * @param serverName
	 * @param databaseName
	 * @param instanceName
	 */
	
	public Database(String alias,LINK_TYPE connexionType, String odbcLinkName, String login,
		String password, String serverName,
		String databaseName, String instanceName, boolean activated) {
	super();
	this.alias=alias;
	this.connexionType = connexionType;
	this.odbcLinkName = odbcLinkName;
	this.login = login;
	this.password = password;
	this.serverURL = serverName;
	this.databaseName = databaseName;
	this.instanceName = instanceName;
	this.activated=activated;
}
	
	
	public Database() {
		this("",LINK_TYPE.ODBC,"","","","","","",true);
	}


	/**
	 * @return LINK_TYPE
	 */
	public LINK_TYPE getConnexionType() {
		return connexionType;
	}
	/**
	 * @param connexionType
	 */
	public void setConnexionType(LINK_TYPE connexionType) {
		this.connexionType = connexionType;
	}
	/**
	 * @return ODBC Link Name
	 */
	public String getOdbcLinkName() {
		return odbcLinkName;
	}
	/**
	 * @param odbcLinkName
	 */
	public void setOdbcLinkName(String odbcLinkName) {
		this.odbcLinkName = odbcLinkName;
	}
	/**
	 * @return login
	 */
	public String getLogin() {
		return login;
	}
	/**
	 * @param login
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	/**
	 * @return password (no encryption)
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password (no encryption)
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return Server URL
	 */
	public String getServerURL() {
		return serverURL;
	}
	/**
	 * @param serverURL
	 */
	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}
	/**
	 * @return DB Name
	 */
	public String getDatabaseName() {
		return databaseName;
	}
	/**
	 * @param databaseName
	 */
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	/**
	 * @return instance of the database
	 */
	public String getInstanceName() {
		return instanceName;
	}
	/**
	 * @param instanceName
	 */
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	/**
	 * @return Alias of the database
	 */
	public String getAlias() {
		return alias;
	}
	/**
	 * @param alias
	 */
	public void setAlias(String alias) {
		this.alias=alias;
	}
	/**
	 * @return last Update Date of the db
	 */

	public boolean isActivated() {
	    return activated;
	}

	public void setActivated(boolean activated) {
	    this.activated = activated;
	}
	
	public void copy(Database db){
	    this.alias=db.getAlias();
	    this.connexionType=db.getConnexionType();
	    this.odbcLinkName=db.getOdbcLinkName();
	    this.login=db.getLogin();
	    this.password=db.getPassword();
	    this.serverURL=db.getServerURL();
	    this.databaseName=db.getDatabaseName();
	    this.instanceName=db.getInstanceName();
	    this.activated=db.isActivated();
	}
	
	public String toString(){
	    return alias+" - "+activated;
	}
	
	public void loadConnectionData(String fileName){
		File f = new File(fileName);
		try {
			Scanner s = new Scanner(f);
			while(s.hasNext()){
				Scanner sIn = new Scanner(s.next()).useDelimiter(":");
				String prop = sIn.next();
				String data;
				if(sIn.hasNext()){
					data = sIn.next();
				} else {
					data ="";
				}
				if(prop.equals("databaseAlias")){
					this.setAlias(data);
				} else if(prop.equals("link_type")){
					if(data.equals("ODBC")){
						this.setConnexionType(LINK_TYPE.ODBC);
					} else if(data.equals("TCPIP")){
						this.setConnexionType(LINK_TYPE.TCPIP);
					} else if(data.equals("WINAUTH")){
						this.setConnexionType(LINK_TYPE.WINDOWS_AUTH);
					}
					
				} else if(prop.equals("ODBCLinkName")){
					this.setOdbcLinkName(data);
				} else if(prop.equals("login")){
					this.setLogin(data);
				} else if(prop.equals("password")){
					this.setPassword(data);
				} else if(prop.equals("serverURL")){
					this.setServerURL(data);
				} else if(prop.equals("databaseName")){
					this.setDatabaseName(data);
				} else if(prop.equals("SQLInstace")){
					this.setInstanceName(data);
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}


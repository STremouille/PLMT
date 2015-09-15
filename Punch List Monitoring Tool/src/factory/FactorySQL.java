package factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Properties;
import java.util.Scanner;

import javax.swing.JOptionPane;

import model.Database;
import model.PunchItem;

public class FactorySQL extends Factory{

	private Database db;
	private Statement stmt;
	private ResultSet rs;
	
	private Factory f;
	
	private ArrayList<PunchItem> cache;
	private ArrayList<String> cachePLCategories,cachePLDiscipline,cachePLsubsystems;
	
	public FactorySQL(Database db) {
		this.db=db;
		this.f=this;
	}
	
	public boolean connect(){
		if(db.isActivated()){
			//System.out.println("Icaps linked");
			Properties props = new Properties();
			props.setProperty("user",db.getLogin());
			props.setProperty("password",db.getPassword());
			String URL;
			Connection conn = null;
			String ODBCDRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
			
			try {
				  props.setProperty("autoReconnect", "true");
				if(db.getConnexionType()==model.LINK_TYPE.ODBC){
					try {
						Class.forName(ODBCDRIVER).newInstance();
						URL = "jdbc:odbc:"+db.getOdbcLinkName();
						conn = DriverManager.getConnection(URL,props);
					} catch (InstantiationException e) {
						//System.out.println("Error 1");
						JOptionPane.showMessageDialog(null, e.getMessage());
						e.printStackTrace(System.out);
					} catch (IllegalAccessException e) {
						//System.out.println("Error 2");
						JOptionPane.showMessageDialog(null, e.getMessage());
						e.printStackTrace(System.out);
					} catch (ClassNotFoundException e) {
						//System.out.println("Error 3");
						JOptionPane.showMessageDialog(null, e.getMessage());
						e.printStackTrace(System.out);
					}
					} else if(db.getConnexionType()==model.LINK_TYPE.TCPIP){
					    	URL="jdbc:sqlserver://"+db.getServerURL()+"\\"+db.getInstanceName();
					    	conn = DriverManager.getConnection(URL+";databaseName="+db.getDatabaseName()+";",props);
					} else if(db.getConnexionType()==model.LINK_TYPE.WINDOWS_AUTH){
						System.out.println("Windows Auth connection");
						URL="jdbc:sqlserver://"+db.getServerURL()+"\\"+db.getInstanceName();
				    	conn = DriverManager.getConnection(URL+";databaseName="+db.getDatabaseName()+";integratedSecurity=true;",props);
						
					}
				stmt = conn.createStatement();
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
				return false;
			}
		} else {
		    //JOptionPane.showMessageDialog(null, db.getAlias()+" database isn't linked in Settings");
		    return false;
		}
		return true;
	}
	
	@Override
	public ArrayList<PunchItem> getPunchLists() {
		if(cache==null){
			connect();
			ArrayList<PunchItem> result = new ArrayList<PunchItem>();
			String delimiter=";";
			Scanner scanner = null;
			scanner = new Scanner(new InputStreamReader(getClass().getClassLoader().getResourceAsStream("rq.sql"))).useDelimiter(delimiter);
			String request=scanner.next()+delimiter;
			try {
				if(stmt!=null){
					this.rs= stmt.executeQuery(request);
					while(rs.next()){
						result.add(new PunchItem(	rs.getString("PL"), 
													rs.getString("SYS"),
													rs.getString("Equipment_No") , 
													rs.getString("DI"), 
													rs.getString("MODULE"), 
													rs.getString("Issued By"), 
													rs.getString("Act_By"), 
													rs.getString("Vendor_Name"), 
													rs.getString("Description"),
													rs.getString("Engineering_req") ,
													rs.getString("Req_Material"), 
													rs.getString("Approved By"), 
													rs.getString("Cleared By"), 
													rs.getString("Checked By"), 
													rs.getString("CorrectiveAction"),
													rs.getString("TA"), 
													rs.getString("Drawing_No"),
													rs.getString("TAL"), 
													rs.getString("ExtraData1"), 
													rs.getString("ExtraData2"), 
													rs.getString("ExtraData3"),
													rs.getString("OTStatus"),
													rs.getString("TA Description"),
													rs.getString("PLP"),
													rs.getString("PLAY"),
													rs.getDate("Issue_Date"), 
													rs.getDate("Third_Date"), 
													rs.getDate("Clearance_Date"), 
													rs.getDate("Approve_Date"),
													rs.getDouble("PC"),
													rs.getDouble("C"),
													Boolean.valueOf(rs.getString("ONLY OT REMAINING")),
													f));
					}
				}
			} catch (SQLException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace(System.out);
			}
			this.cache = new ArrayList<PunchItem>(result);
		}
		return cache;
	}

	public void refreshCache(){
		getPunchLists();
	}
	
	@Override
	public ArrayList<PunchItem> getLastPunchIssued(Date date) {
		if(cache == null){
			refreshCache();
		} 
		
		ArrayList<PunchItem> result = new ArrayList<PunchItem>();
		Iterator<PunchItem> it = cache.iterator();
		while(it.hasNext()){
			PunchItem pi = it.next();
			if(pi.getOriginatedDate().getTime() >= date.getTime()){
				result.add(pi);
			}
		}
		return result;
	}

	@Override
	public ArrayList<PunchItem> getLastPunchClosed(Date date) {
		if(cache == null){
			refreshCache();
		} 
		
		ArrayList<PunchItem> result = new ArrayList<PunchItem>();
		Iterator<PunchItem> it = cache.iterator();
		while(it.hasNext()){
			PunchItem pi = it.next();
			if(pi.getApprovedDate()!=null && pi.getApprovedDate().getTime() >= date.getTime()){
				result.add(pi);
			}
		}
		return result;
	}

	@Override
	public ArrayList<String> getPunchListCategories() {
		if(cachePLCategories==null){
			cachePLCategories = new ArrayList<String>();
			if(cache == null){
				refreshCache();
			} 
			
			Iterator<PunchItem> it = cache.iterator();
			while(it.hasNext()){
				String plCat = it.next().getCategory();
				if(!cachePLCategories.contains(plCat))
					cachePLCategories.add(plCat);
			}
		}
		return cachePLCategories;
	}

	@Override
	public ArrayList<String> getPunchListDiscipline() {
		if(cachePLDiscipline==null){
			cachePLDiscipline = new ArrayList<String>();
			if(cache == null){
				refreshCache();
			} 
			
			Iterator<PunchItem> it = cache.iterator();
			while(it.hasNext()){
				String plDi = it.next().getDiscipline();
				if(!cachePLDiscipline.contains(plDi))
					cachePLDiscipline.add(plDi);
			}
		}
		return cachePLDiscipline;
	}

	@Override
	public ArrayList<String> getPunchListSubsystems() {
		if(cachePLsubsystems==null){
			cachePLsubsystems = new ArrayList<String>();
			if(cache == null){
				refreshCache();
			} 
			
			Iterator<PunchItem> it = cache.iterator();
			while(it.hasNext()){
				String plSubsystem = it.next().getSubsystem();
				if(!cachePLsubsystems.contains(plSubsystem))
					cachePLsubsystems.add(plSubsystem);
			}
		}
		return cachePLsubsystems;
	}
	
	

}

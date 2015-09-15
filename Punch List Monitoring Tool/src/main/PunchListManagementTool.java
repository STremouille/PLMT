package main;

import java.sql.Date;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import settings.PunchListTableSettings;
import view.LoadingFrame;
import view.View;
import controller.Controller;
import factory.FactorySQL;
import model.Database;
import model.PunchItem;
import model.PunchList;

public class PunchListManagementTool {

	public static void main(String[] args) throws ParseException {
		//Database db = new Database("DB", LINK_TYPE.ODBC, "CLOV", "probe", "", "FREP-L212440", "CLOV", "SQLEXPRESS", true);
		 try {
			UIManager.setLookAndFeel(
			            UIManager.getSystemLookAndFeelClassName());
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedLookAndFeelException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		LoadingFrame lf = new LoadingFrame();
		PunchListTableSettings plts = new PunchListTableSettings();
		Database db = new Database();
		db.loadConnectionData("connection.data");
		FactorySQL fsql = new FactorySQL(db);
		init(fsql);
		
		//SimpleDateFormat f = new SimpleDateFormat("yyyy.MM.dd");
		//ArrayList<PunchItem> pl = fsql.getLastPunchClosed(new Date(f.parse("2012.04.26").getTime()));
		
		View v = new View(fsql,plts);
		Controller c = new Controller(fsql, v);
		lf.dispose();
		
		/*PunchList pl = new PunchList(fsql.getPunchLists());
		PunchList pl1 = new PunchList(new ArrayList<PunchItem>());
		pl1.unserialize(pl.serialize());
		
		//System.out.println(pl1.equals(pl));
		
		pl1.getList().get(0).setActionBy("MODIFIED");
		pl1.getList().get(0).setApprovedBy("1");
		pl1.getList().get(0).setEngineering("2");
		pl1.getList().get(10).setTaskNumber("New Task Number");
		System.out.println(pl.reportDiff(pl1));

		/*PunchItem pi = pl.getList().get(1);
		PunchItem pi2 = new PunchItem();
		pi2.unserialize(pi.serialize());
		System.out.println(pi.equals(pi2));
		System.out.println(pi.getComment1());
		System.out.println(pi2.getComment1());*/
	}

	
	public static void init(FactorySQL fsql){
		System.out.println("Start Init");
		//With this method all cache are filled at launch
		fsql.getPunchListCategories();
		System.out.println("End init");
	}
}

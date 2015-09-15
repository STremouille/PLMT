package factory;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.Database;
import model.PunchItem;

public abstract class Factory {
	
	public abstract ArrayList<PunchItem> getPunchLists();
	public abstract ArrayList<PunchItem> getLastPunchIssued(Date date);
	public abstract ArrayList<PunchItem> getLastPunchClosed(Date date);
	public abstract ArrayList<String> getPunchListCategories();
	public abstract ArrayList<String> getPunchListDiscipline();
	public abstract ArrayList<String> getPunchListSubsystems();
	public SimpleDateFormat getDateFormatter() {
		return new SimpleDateFormat("dd/MM/yyyy");
	}
}

package view;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.TreeMap;

import javax.swing.JLabel;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;

import settings.PunchListTableSettings;
import model.PunchList;
import factory.Factory;

public class PunchListTableModel extends AbstractTableModel{

	private PunchList pl;
	private JLabel listSize;
	private PunchListTableSettings plts;
	private TreeMap<Integer, PunchListFields> cache;
	
	public PunchListTableModel(PunchList pl, JLabel listSize, PunchListTableSettings plts) {
		this.pl=pl;
		this.listSize=listSize;
		this.plts=plts;
		
		updateCache();
	}
	
	@Override
	public int getColumnCount() {
		return cache.size();
	}

	@Override
	public int getRowCount() {
		
		return pl.getList().size();
	}

	
	
	
	@Override
	public Class<?> getColumnClass(int c) {
		switch (cache.get(c)) {
		case IssueDate:
			return Date.class;
		case CarriedOutDate:
			return Date.class;
		case CheckedDate:
			return Date.class;
		case ApprovedDate:
			return Date.class;
		default:
			return super.getClass();
		}
	}

	@Override
	public String getColumnName(int c) {
		return getPunchListFieldProperName(cache.get(c));
		/*switch (c) {
		case 0:
			return "Number";
		case 1:
			return "Priority";
		case 2:
			return "Sub-system";
		case 3:
			return "PC %";
		case 4:
			return "C %";
		case 5:
			return "Only OTP Remaining";
		case 6:
			return "OTP Status";
		case 7:
			return "Task";
		case 8:
			return "Equipment";
		case 9:
			return "Defect";
		case 10:
			return "Issued By";
		case 11:
			return "Issue Date";
		case 12:
			return "Material";
		case 13:
			return "Engineering";
		case 14:
			return "Discipline";
		case 15:
			return "Module";
		case 16:
			return "Action By";
		case 17:
			return "Vendor";
		case 18:
			return "Drawing";
		case 19:
			return "Carried Out By";
		case 20:
			return "Carried Out Date";
		case 21:
			return "Checked By";
		case 22:
			return "Checked Date";
		case 23:
			return "Approved By";
		case 24:
			return "Approved Date";
		case 25:
			return "Corrective Action";
		case 26:
			return "Comment 1";
		case 27:
			return "Comment 2";
		case 28:
			return "Comment 2";
		default:
			return "null";
		}*/
		
	}

	@Override
	public Object getValueAt(int r, int c) {
		
		switch (cache.get(c)) {
		case Number:
			return pl.getList().get(r).getNumber();
		case Priority:
			return pl.getList().get(r).getCategory();
		case Subsystem:
			return pl.getList().get(r).getSubsystem();
		case activityCode:
			return pl.getList().get(r).getPlActivityCode();
		case PC:
			return Math.round(pl.getList().get(r).getPrecommProgress()*10)/10.0;
		case C:
			return Math.round(pl.getList().get(r).getCommProgress()*10)/10.0;
		case OnlyOTPRemaining:
			return pl.getList().get(r).isOnlyOTRemaining();
		case OTPStatus:
			return pl.getList().get(r).getOtStatus();
		case Task:
			return pl.getList().get(r).getTaskNumber() == null ? "" : (pl.getList().get(r).getTaskNumber() +" ") + pl.getList().get(r).getTaskDescription() == null ? "": pl.getList().get(r).getTaskDescription();
		case Equipment:
			return pl.getList().get(r).getEquipment();
		case Defect:
			return pl.getList().get(r).getDefectDescription();
		case IssuedBy:
			return pl.getList().get(r).getOriginatedBy();
		case IssueDate:
			return pl.getList().get(r).getOriginatedDate();
		case Material:
			return pl.getList().get(r).getMaterial();
		case Engineering:
			return pl.getList().get(r).getEngineering();
		case Discipline:
			return pl.getList().get(r).getDiscipline();
		case Module:
			return pl.getList().get(r).getModule();
		case ActionBy:
			return pl.getList().get(r).getActionBy();
		case Vendor:
			return pl.getList().get(r).getVendor();
		case Drawing:
			return pl.getList().get(r).getDrawing();
		case CarriedOutBy:
			return pl.getList().get(r).getCarriedOutBy();
		case CarriedOutDate:
			return pl.getList().get(r).getCarriedOutDate();
		case CheckedBy:
			return pl.getList().get(r).getCheckedBy();
		case CheckedDate:
			return pl.getList().get(r).getCheckedDate();
		case ApprovedBy:
			return pl.getList().get(r).getApprovedBy();
		case ApprovedDate:
			return pl.getList().get(r).getApprovedDate();
		case CorrectiveAction:
			return pl.getList().get(r).getCorrectiveAction();
		case Location:
			return pl.getList().get(r).getLocation();
		case Comment1:
			return pl.getList().get(r).getComment1();
		case Comment2:
			return pl.getList().get(r).getComment2();
		case Comment3:
			return pl.getList().get(r).getComment3();
		default:
			return "null";
		}
		
	}

//	public void resetFilter() {
//		System.out.println("Reset");
//		pl = new PunchList(f.getPunchLists());
//		fireTableDataChanged();
//	}

	public void setPunchList(PunchList pl) {
		this.pl = new PunchList(pl.getList());
		listSize
		.setText(this.pl.getList().size()+" punch items in the list");
		fireTableDataChanged();
	}
	
	public void updateCache(){
		int acc = 0;
		cache = new TreeMap<Integer, PunchListTableModel.PunchListFields>();
		
		for(PunchListFields plf : PunchListFields.values()){
			if(plts.getVisibleFields().get(plf)){
				cache.put(acc, plf);
				acc++;
			}
		}
		fireTableStructureChanged();
	}
	
	public String getPunchListFieldProperName(PunchListFields plf){
		switch (plf) {
		case Number:
			return "Number";
		case Priority:
			return "Priority";
		case Subsystem:
			return "Sub-system";
		case activityCode:
			return "Activity Code";
		case PC:
			return "PC %";
		case C:
			return "C %";
		case OnlyOTPRemaining:
			return "Only OT remaining ?";
		case OTPStatus:
			return "OT Status";
		case Task:
			return "Task";
		case Equipment:
			return "Equipment";
		case Defect:
			return "Defect Description";
		case IssuedBy:
			return "Originated By";
		case IssueDate:
			return "Originated Date";
		case Material:
			return "Material";
		case Engineering:
			return "Engineering";
		case Discipline:
			return "Discipline";
		case Module:
			return "Module";
		case ActionBy:
			return "Action By";
		case Vendor:
			return "Vendor";
		case Drawing:
			return "Drawing";
		case CarriedOutBy:
			return "Carry Out By";
		case CarriedOutDate:
			return "Carry Out Date";
		case CheckedBy:
			return "Checked By";
		case CheckedDate:
			return "Checked Date";
		case ApprovedBy:
			return "Approved By";
		case ApprovedDate:
			return "Approved Date";
		case CorrectiveAction:
			return "Corrective Action";
		case Location:
			return "Location";
		case Comment1:
			return "Comment 1";
		case Comment2:
			return "Comment 2";
		case Comment3:
			return "Comment 3";
		default:
			return "null";
		}
		
	}
	
	public enum PunchListFields{
		Number,
		Priority,
		Subsystem,
		activityCode,
		PC,
		C,
		OnlyOTPRemaining,
		OTPStatus,
		Task,
		Equipment,
		Defect,
		IssuedBy,
		IssueDate,
		Material,
		Engineering,
		Discipline,
		Module,
		ActionBy,
		Vendor,
		Drawing,
		CarriedOutBy,
		CarriedOutDate,
		CheckedBy,
		CheckedDate,
		ApprovedBy,
		ApprovedDate,
		CorrectiveAction, 
		Location,
		Comment1,
		Comment2,
		Comment3;
	}
}

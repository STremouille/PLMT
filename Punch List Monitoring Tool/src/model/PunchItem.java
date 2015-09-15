package model;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.Scanner;

import factory.Factory;
import view.PunchListTableModel.PunchListFields;

public class PunchItem {
	
	private String number, subsystem, equipment, discipline, module, originatedBy, actionBy, vendor, defectDescription, engineering, material, carriedOutBy, checkedBy, approvedBy, correctiveAction, taskNumber, drawing, location, comment1, comment2, comment3,otStatus,taskDescription,category;
	private String plActivityCode;
	private String separatorChar = "separatorCharForTimeMachine";
	private String assigmentChar = "assignmentCharForTimeMachine";
	
	private Factory factory;
	
	public String getOtStatus() {
		return otStatus;
	}
	public void setOtStatus(String otStatus) {
		this.otStatus = otStatus;
	}
	public String getTaskDescription() {
		return taskDescription;
	}
	public void setTaskDescription(String taskDescription) {
		this.taskDescription = taskDescription;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public boolean isOnlyOTRemaining() {
		return onlyOTRemaining;
	}
	public void setOnlyOTRemaining(boolean onlyOTRemaining) {
		this.onlyOTRemaining = onlyOTRemaining;
	}
	private Date originatedDate, carriedOutDate, checkedDate, approvedDate;
	private double precommProgress,commProgress;
	private boolean onlyOTRemaining;
	
	
	public PunchItem(String number, String subsystem, String equipment, String discipline, String module,
			String originatedBy, String actionBy, String vendor, String defectDescription, String engineering,
			String material, String carriedOutBy, String checkedBy, String approvedBy, String correctiveAction,
			String taskNumber, String drawing, String location, String comment1, String comment2, String comment3,
			String otStatus, String taskDescription, String category, String plActivityCode,Date originatedDate, Date carriedOutDate,
			Date checkedDate, Date approvedDate, double precommProgress, double commProgress, boolean onlyOTRemaining, Factory fac) {
		super();
		this.factory = fac;
		this.plActivityCode=plActivityCode;
		this.number = number;
		this.subsystem = subsystem;
		this.equipment = equipment;
		this.discipline = discipline;
		this.module = module;
		this.originatedBy = originatedBy;
		this.actionBy = actionBy;
		this.vendor = vendor;
		this.defectDescription = defectDescription;
		this.engineering = engineering;                                    
		this.material = material;
		this.carriedOutBy = carriedOutBy;
		this.checkedBy = checkedBy;
		this.approvedBy = approvedBy;
		this.correctiveAction = correctiveAction;
		this.taskNumber = taskNumber;
		this.drawing = drawing;
		this.location = location;
		this.comment1 = comment1;
		this.comment2 = comment2;
		this.comment3 = comment3;
		this.otStatus = otStatus;
		this.taskDescription = taskDescription;
		this.category = category;
		this.originatedDate = originatedDate;
		this.carriedOutDate = carriedOutDate;
		this.checkedDate = checkedDate;
		this.approvedDate = approvedDate;
		this.precommProgress = precommProgress;
		this.commProgress = commProgress;
		this.onlyOTRemaining = onlyOTRemaining;
	}
	public PunchItem() {
	}
	public String getSubsystem() {
		return subsystem;
	}
	public void setSubsystem(String subsystem) {
		this.subsystem = subsystem;
	}
	public String getEquipment() {
		return equipment+"";
	}
	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}
	public String getDiscipline() {
		return discipline;
	}
	public void setDiscipline(String discipline) {
		this.discipline = discipline;
	}
	public String getModule() {
		return module+"";
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getOriginatedBy() {
		return originatedBy;
	}
	public void setOriginatedBy(String originatedBy) {
		this.originatedBy = originatedBy;
	}
	public String getActionBy() {
		return actionBy;
	}
	public void setActionBy(String actionBy) {
		this.actionBy = actionBy;
	}
	public String getVendor() {
		return vendor+"";
	}
	public void setVendor(String vendor) {
		this.vendor = vendor;
	}
	public String getDefectDescription() {
		return defectDescription;
	}
	public void setDefectDescription(String defectDescription) {
		this.defectDescription = defectDescription;
	}
	public String getEngineering() {
		return engineering+"";
	}
	public void setEngineering(String engineering) {
		this.engineering = engineering;
	}
	public String getMaterial() {
		return material+"";
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	public String getCarriedOutBy() {
		return carriedOutBy+"";
	}
	public void setCarriedOutBy(String carriedOutBy) {
		this.carriedOutBy = carriedOutBy;
	}
	public String getCheckedBy() {
		return checkedBy+"";
	}
	public void setCheckedBy(String checkedBy) {
		this.checkedBy = checkedBy;
	}
	public String getApprovedBy() {
		return approvedBy+"";
	}
	public void setApprovedBy(String approvedBy) {
		this.approvedBy = approvedBy;
	}
	public String getCorrectiveAction() {
		return correctiveAction+"";
	}
	public void setCorrectiveAction(String correctiveAction) {
		this.correctiveAction = correctiveAction;
	}
	public String getTaskNumber() {
		return taskNumber+"";
	}
	public void setTaskNumber(String taskNumber) {
		this.taskNumber = taskNumber;
	}
	public String getDrawing() {
		return drawing+"";
	}
	public void setDrawing(String drawing) {
		this.drawing = drawing;
	}
	public String getLocation() {
		return location+"";
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getComment1() {
		return comment1+"";
	}
	public void setComment1(String comment1) {
		this.comment1 = comment1;
	}
	public String getComment2() {
		return comment2+"";
	}
	public void setComment2(String comment2) {
		this.comment2 = comment2;
	}
	public String getComment3() {
		return comment3+"";
	}
	public void setComment3(String comment3) {
		this.comment3 = comment3;
	}
	public Date getOriginatedDate() {
		return originatedDate;
	}
	public void setOriginatedDate(Date originatedDate) {
		this.originatedDate = originatedDate;
	}
	public Date getCarriedOutDate() {
		return carriedOutDate;
	}
	public void setCarriedOutDate(Date carriedOutDate) {
		this.carriedOutDate = carriedOutDate;
	}
	public Date getCheckedDate() {
		return checkedDate;
	}
	public void setCheckedDate(Date checkedDate) {
		this.checkedDate = checkedDate;
	}
	public Date getApprovedDate() {
		return approvedDate;
	}
	public void setApprovedDate(Date approvedDate) {
		this.approvedDate = approvedDate;
	}
	
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	@Override
	public String toString() {
		return "PunchItem [number=" + number + ", subsystem=" + subsystem + ", equipment=" + equipment
				+ ", discipline=" + discipline + ", module=" + module + ", originatedBy=" + originatedBy
				+ ", actionBy=" + actionBy + ", vendor=" + vendor + ", defectDescription=" + defectDescription
				+ ", engineering=" + engineering + ", material=" + material + ", carriedOutBy=" + carriedOutBy
				+ ", checkedBy=" + checkedBy + ", approvedBy=" + approvedBy + ", correctiveAction=" + correctiveAction
				+ ", taskNumber=" + taskNumber + ", drawing=" + drawing + ", location=" + location + ", comment1="
				+ comment1 + ", comment2=" + comment2 + ", comment3=" + comment3 + ", otStatus=" + otStatus
				+ ", taskDescription=" + taskDescription + ", category=" + category + ", originatedDate="
				+ originatedDate + ", carriedOutDate=" + carriedOutDate + ", checkedDate=" + checkedDate
				+ ", approvedDate=" + approvedDate + ", precommProgress=" + precommProgress + ", commProgress="
				+ commProgress + ", onlyOTRemaining=" + onlyOTRemaining + "]";
	}
	
	public String serialize() {
		return 	PunchListFields.Number+assigmentChar + number + separatorChar+
				PunchListFields.Subsystem+assigmentChar + subsystem + separatorChar+
				PunchListFields.Equipment+assigmentChar + equipment	+ separatorChar+
				PunchListFields.Discipline+assigmentChar + discipline + separatorChar+
				PunchListFields.Module+assigmentChar + module + separatorChar+
				PunchListFields.IssuedBy+assigmentChar + originatedBy+ separatorChar+
				PunchListFields.ActionBy+assigmentChar + actionBy + separatorChar+
				PunchListFields.Vendor+assigmentChar + vendor + separatorChar+
				PunchListFields.Defect+assigmentChar + defectDescription + separatorChar+
				PunchListFields.Engineering+assigmentChar + engineering + separatorChar+
				PunchListFields.Material+assigmentChar + material + separatorChar+
				PunchListFields.CarriedOutBy+assigmentChar + carriedOutBy+ separatorChar+
				PunchListFields.CheckedBy+assigmentChar + checkedBy + separatorChar+
				PunchListFields.ApprovedBy+assigmentChar + approvedBy + separatorChar+
				PunchListFields.CorrectiveAction+assigmentChar + correctiveAction+ separatorChar+
				PunchListFields.Task+assigmentChar + taskNumber + separatorChar+
				PunchListFields.Drawing+assigmentChar + drawing + separatorChar+
				PunchListFields.Location+assigmentChar + location + separatorChar+
				PunchListFields.Comment1+assigmentChar+ comment1 + separatorChar+
				PunchListFields.Comment2+assigmentChar + comment2 + separatorChar+
				PunchListFields.Comment3+assigmentChar + comment3 + separatorChar+
				PunchListFields.OTPStatus+assigmentChar + otStatus	+ separatorChar+
				PunchListFields.Priority+assigmentChar + category + separatorChar+
				PunchListFields.IssueDate+assigmentChar	+ originatedDate.getTime() + separatorChar+
				PunchListFields.CarriedOutDate+assigmentChar + (carriedOutDate==null?0:carriedOutDate.getTime()) + separatorChar+
				PunchListFields.CheckedDate+assigmentChar + (checkedDate==null?0:checkedDate.getTime())	+ separatorChar+
				PunchListFields.ApprovedDate+assigmentChar + (approvedDate==null?0:approvedDate.getTime()) + separatorChar+
				PunchListFields.PC+assigmentChar + precommProgress + separatorChar+
				PunchListFields.C+assigmentChar	+ commProgress + separatorChar+
				PunchListFields.OnlyOTPRemaining+assigmentChar + onlyOTRemaining+ separatorChar+
				PunchListFields.activityCode+assigmentChar + plActivityCode;
	}
	
	public void unserialize(String serializePunchItem) {
		Scanner s = new Scanner(serializePunchItem).useDelimiter(separatorChar);
		while(s.hasNext()){
			Scanner sc = new Scanner(s.next()).useDelimiter(assigmentChar);
			PunchListFields plf = PunchListFields.valueOf(sc.next());
			String fieldValue = sc.next();
			if(fieldValue.equals("null")){
				fieldValue = null;
			}
			switch (plf) {
			case ActionBy:
				actionBy = fieldValue;
				break;
			case ApprovedBy:
				approvedBy = fieldValue;
				break;
			case C:
				commProgress= Double.valueOf(fieldValue);
				break;
			case CarriedOutBy:
				carriedOutBy= fieldValue;
				break;
			case CheckedBy:
				checkedBy= fieldValue;
				break;
			case Comment1:
				comment1= fieldValue;
				break;
			case Comment2:
				comment2= fieldValue;
				break;
			case Comment3:
				comment3= fieldValue;
				break;
			case CorrectiveAction:
				correctiveAction= fieldValue;
				break;
			case Defect:
				defectDescription= fieldValue;
				break;
			case Discipline:
				discipline= fieldValue;
				break;
			case Drawing:
				drawing= fieldValue;
				break;
			case Engineering:
				engineering= fieldValue;
				break;
			case Equipment:
				equipment= fieldValue;
				break;
			case IssuedBy:
				originatedBy= fieldValue;
				break;
			case Location:
				location= fieldValue;
				break;
			case Material:
				material= fieldValue;
				break;
			case Module:
				module= fieldValue;
				break;
			case Number:
				number= fieldValue;
				break;
			case OnlyOTPRemaining:
				onlyOTRemaining= Boolean.valueOf(fieldValue);
				break;
			case OTPStatus:
				otStatus= fieldValue;
				break;
			case PC:
				precommProgress = Double.valueOf(fieldValue);
				break;
			case Priority:
				category= fieldValue;
				break;
			case Subsystem:
				subsystem= fieldValue;
				break;
			case Task:
				taskNumber= fieldValue;
				break;
			case Vendor:
				vendor= fieldValue;
				break;
			case ApprovedDate:
				if(Long.valueOf(fieldValue)==0){
					approvedDate = null;
				} else {
					approvedDate = new Date(Long.valueOf(fieldValue));
				}
				break;
			case CarriedOutDate:
				if(Long.valueOf(fieldValue)==0){
					carriedOutDate =null;
				} else {
					carriedOutDate = new Date(Long.valueOf(fieldValue));
				}
				break;
			case CheckedDate:
				if(Long.valueOf(fieldValue)==0){
					checkedDate =null;
				} else {
					checkedDate = new Date(Long.valueOf(fieldValue));
				}
				break;
			case IssueDate:
				originatedDate = new Date(Long.valueOf(fieldValue));
				break;
			case activityCode:
				plActivityCode = fieldValue;
				break;
			default:
				break;
			}
		}
	}
	
	public double getCommProgress() {
		return commProgress;
	}
	public void setCommProgress(double commProgress) {
		this.commProgress = commProgress;
	}
	public double getPrecommProgress() {
		return precommProgress;
	}
	public void setPrecommProgress(double precommProgress) {
		this.precommProgress = precommProgress;
	}

	public String getField(PunchListFields plf) {
		switch (plf) {
		case ActionBy:
			return getActionBy();
		case ApprovedBy:
			return getApprovedBy();
		case ApprovedDate:
			return factory.getDateFormatter().format(getApprovedDate());
		case C:
			return String.valueOf(getCommProgress());
		case CarriedOutBy:
			return getCarriedOutBy();
		case CheckedBy:
			return getCheckedBy();
		case Comment1:
			return getComment1();
		case Comment2:
			return getComment2();
		case Comment3:
			return getComment3();
		case CorrectiveAction:
			return getCorrectiveAction();
		case Defect:
			return getDefectDescription();
		case Discipline:
			return getDiscipline();
		case Drawing:
			return getDrawing();
		case Engineering:
			return getEngineering();
		case Equipment:
			return getEquipment();
		case IssuedBy:
			return getOriginatedBy();
		case Material:
			return getMaterial();
		case Module:
			return getModule();
		case Number:
			return getNumber();
		case OnlyOTPRemaining:
			return isOnlyOTRemaining()+"";
		case OTPStatus:
			return getOtStatus();
		case PC:
			return String.valueOf(getPrecommProgress());
		case Priority:
			return getCategory();
		case Subsystem:
			return getSubsystem();
		case Task:
			return getTaskNumber();
		case Vendor:
			return getVendor();
		case CarriedOutDate:
			return factory.getDateFormatter().format(getCarriedOutDate());
		case CheckedDate:
			return factory.getDateFormatter().format(getCheckedDate());
		case IssueDate:
			return factory.getDateFormatter().format(getOriginatedDate());
		case activityCode:
			return plActivityCode;
		default:
			return "";
		}
	}
	@Override
	public boolean equals(Object obj) {
		PunchItem other = (PunchItem) obj;
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		
		if (actionBy == null) {
			if (other.actionBy != null)
				return false;
		} else if (!actionBy.equals(other.actionBy))
			return false;
		if (approvedBy == null) {
			if (other.approvedBy != null)
				return false;
		} else if (!approvedBy.equals(other.approvedBy))
			return false;
		if(approvedDate == null){
			if(other.approvedDate != null)
				return false;
		} else if (!approvedDate.equals(other.approvedDate))
			return false;
		if (carriedOutBy == null) {
			if (other.carriedOutBy != null)
				return false;
		} else if (!carriedOutBy.equals(other.carriedOutBy))
			return false;
		if(carriedOutDate == null){
			if(other.carriedOutDate != null)
				return false;
		} else if (!carriedOutDate.equals(other.carriedOutDate))
			return false;
		if (category == null) {
			if (other.category != null)
				return false;
		} else if (!category.equals(other.category))
			return false;
		if (checkedBy == null) {
			if (other.checkedBy != null)
				return false;
		} else if (!checkedBy.equals(other.checkedBy))
			return false;
		if(checkedDate == null){
			if(other.checkedDate != null)
				return false;
		} else if (!checkedDate.equals(other.checkedDate))
			return false;
		//
		if (Double.doubleToLongBits(commProgress) != Double
				.doubleToLongBits(other.commProgress))
			return false;
		if (comment1 == null) {
			if (other.comment1 != null)
				return false;
		} else if (!comment1.equals(other.comment1))
			return false;
		if (comment2 == null) {
			if (other.comment2 != null)
				return false;
		} else if (!comment2.equals(other.comment2))
			return false;
		if (comment3 == null) {
			if (other.comment3 != null)
				return false;
		} else if (!comment3.equals(other.comment3))
			return false;
		if (correctiveAction == null) {
			if (other.correctiveAction != null)
				return false;
		} else if (!correctiveAction.equals(other.correctiveAction))
			return false;
		if (defectDescription == null) {
			if (other.defectDescription != null)
				return false;
		} else if (!defectDescription.equals(other.defectDescription))
			return false;
		if (discipline == null) {
			if (other.discipline != null)
				return false;
		} else if (!discipline.equals(other.discipline))
			return false;
		
		if (drawing == null) {
			if (other.drawing != null)
				return false;
		} else if (!drawing.equals(other.drawing))
			return false;
		if (engineering == null) {
			if (other.engineering != null)
				return false;
		} else if (!engineering.equals(other.engineering))
			return false;
		if (equipment == null) {
			if (other.equipment != null)
				return false;
		} else if (!equipment.equals(other.equipment))
			return false;
		if (location == null) {
			if (other.location != null)
				return false;
		} else if (!location.equals(other.location))
			return false;
		if (material == null) {
			if (other.material != null)
				return false;
		} else if (!material.equals(other.material))
			return false;
		if (module == null) {
			if (other.module != null)
				return false;
		} else if (!module.equals(other.module))
			return false;
		if (number == null) {
			if (other.number != null)
				return false;
		} else if (!number.equals(other.number))
			return false;
		if (onlyOTRemaining != other.onlyOTRemaining)
			return false;
		
		if (originatedBy == null) {
			if (other.originatedBy != null)
				return false;
		} else if (!originatedBy.equals(other.originatedBy))
			return false;
		if (originatedDate == null) {
			if (other.originatedDate != null)
				return false;
		} else if (!originatedDate.equals(other.originatedDate))
			return false;
		if (otStatus == null) {
			if (other.otStatus != null)
				return false;
		} else if (!otStatus.equals(other.otStatus))
			return false;
		if (Double.doubleToLongBits(precommProgress) != Double
				.doubleToLongBits(other.precommProgress))
			return false;
		if (subsystem == null) {
			if (other.subsystem != null)
				return false;
		} else if (!subsystem.equals(other.subsystem))
			return false;
		if (taskNumber == null) {
			if (other.taskNumber != null)
				return false;
		} else if (!taskNumber.equals(other.taskNumber))
			return false;
		if (vendor == null) {
			if (other.vendor != null)
				return false;
		} else if (!vendor.equals(other.vendor))
			return false;
		if (plActivityCode == null) {
			if (other.plActivityCode != null)
				return false;
		} else if (!plActivityCode.equals(other.plActivityCode))
			return false;
		return true;
	}
	public String reportDiffWith(PunchItem other) {
		StringBuilder sb = new StringBuilder();
		if (!actionBy.equals(other.actionBy))
			sb.append("Action by :"+this.actionBy+" -> "+other.getActionBy() +"\n");
		if (!this.getApprovedBy().equals(other.getApprovedBy()))
			sb.append("Approved by :"+this.approvedBy+" -> "+other.getApprovedBy()+"\n");		
		if (!(getApprovedDate()==null&&other.getApprovedDate()==null)){
			if(getApprovedDate()==null) {
				sb.append("Approved date :"+this.getApprovedDate()+" -> "+other.getApprovedDate()+"\n");
			} else if(!getApprovedDate().equals(other.getApprovedDate())){
				sb.append("Approved date :"+this.getApprovedDate()+" -> "+other.getApprovedDate()+"\n");
			}
		}
		if (!getCarriedOutBy().equals(other.getCarriedOutBy()))
			sb.append("Carry out by :"+this.getCarriedOutBy()+" -> "+other.getCarriedOutBy()+"\n");
		if (!(getCarriedOutDate()==null&&other.getCarriedOutDate()==null)){
			if(getCarriedOutDate()==null){
				sb.append("Carry out date :"+this.getCarriedOutDate()+" -> "+other.getCarriedOutDate()+"\n");
			} else if(!getCarriedOutDate().equals(other.getCarriedOutDate())){
				sb.append("Carry out date :"+this.getCarriedOutDate()+" -> "+other.getCarriedOutDate()+"\n");
			}
		}
		if (!getCategory().equals(other.getCategory()))
			sb.append("Category :"+this.getCategory()+" -> "+other.getCategory()+"\n");
		if (!getCheckedBy().equals(other.getCheckedBy()))
			sb.append("Checked by :"+this.getCheckedBy()+" -> "+other.getCheckedBy()+"\n");
		if (!(getCheckedDate()==null&&other.getCheckedDate()==null)){
			if(getCheckedDate()==null){
				sb.append("Checked date :"+this.getCheckedDate()+" -> "+other.getCheckedDate()+"\n");
			} else if(!getCheckedDate().equals(other.getCheckedDate())){
				sb.append("Checked date :"+this.getCheckedDate()+" -> "+other.getCheckedDate()+"\n");
			}
		}
		if (!getComment1().equals(other.getComment1()))
			sb.append("Comment 1 :"+this.getComment1()+" -> "+other.getComment1()+"\n");
		if (!getComment2().equals(other.getComment2()))
			sb.append("Comment 2 :"+this.getComment2()+" -> "+other.getComment2()+"\n");
		if (!getComment3().equals(other.getComment3()))
			sb.append("Comment 3 :"+this.getComment3()+" -> "+other.getComment3()+"\n");
		if (!getCorrectiveAction().equals(other.getCorrectiveAction()))
			sb.append("Corrective action :"+this.getCorrectiveAction()+" -> "+other.getCorrectiveAction()+"\n");
		if (!getDefectDescription().equals(other.getDefectDescription()))
			sb.append("Defect description :"+this.getDefectDescription()+" -> "+other.getDefectDescription()+"\n");
		if (!getDiscipline().equals(other.getDiscipline()))
			sb.append("Discpline :"+this.getDiscipline()+" -> "+other.getDiscipline()+"\n");
		if (!getDrawing().equals(other.getDrawing()))
			sb.append("Drawing :"+this.getDrawing()+" -> "+other.getDrawing()+"\n");
		if (!getEngineering().equals(other.getEngineering()))
			sb.append("Engineering :"+this.getEngineering()+" -> "+other.getEngineering()+"\n");
		if (!getEquipment().equals(other.getEquipment()))
			sb.append("Equipment :"+this.getEquipment()+" -> "+other.getEquipment()+"\n");
		if (!getLocation().equals(other.getLocation()))
			sb.append("Location :"+this.getLocation()+" -> "+other.getLocation()+"\n");
		if (!getMaterial().equals(other.getMaterial()))
			sb.append("Material :"+this.getMaterial()+" -> "+other.getMaterial()+"\n");
		if (!getModule().equals(other.getModule()))
			sb.append("Module :"+this.getModule()+" -> "+other.getModule()+"\n");
		if (!getOriginatedBy().equals(other.getOriginatedBy()))
			sb.append("Originated by :"+this.getOriginatedBy()+" -> "+other.getOriginatedBy()+"\n");
		if(!(getOriginatedDate().equals(other.getOriginatedDate()))){
				System.out.println("bug :"+this.getOriginatedDate().getTime()+"!="+other.getOriginatedDate().getTime());
				sb.append("Originated Date :"+this.getOriginatedDate()+" -> "+other.getOriginatedDate()+"\n");
			}
		if (!getSubsystem().equals(other.getSubsystem()))
			sb.append("Subsystem :"+this.getSubsystem()+" -> "+other.getSubsystem()+"\n");
		if (!getTaskNumber().equals(other.getTaskNumber()))
			sb.append("Task :"+this.getTaskNumber()+" -> "+other.getTaskNumber()+"\n");
		if (!getVendor().equals(other.getVendor()))
			sb.append("Vendor :"+this.getVendor()+" -> "+other.getVendor()+"\n");
		if (!getPlActivityCode().equals(other.getPlActivityCode()))
			sb.append("Activity Code :"+this.getPlActivityCode()+" -> "+other.getPlActivityCode()+"\n");
		return sb.toString();
	}
	public String getPlActivityCode() {
		return plActivityCode+"";
	}
	public void setPlActivityCode(String plActivityCode) {
		this.plActivityCode = plActivityCode;
	}
	
	
	
	
	
}

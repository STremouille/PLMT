package settings;

import java.util.ArrayList;
import java.util.TreeMap;

import view.PunchListTableModel.PunchListFields;

public class PunchListTableSettings {

	public TreeMap<PunchListFields, Boolean> visible;
	
	private TreeMap<Integer, PunchListFields> position;
	
		
	public PunchListTableSettings(){
		visible = new TreeMap<PunchListFields, Boolean>();
		position = new TreeMap<Integer, PunchListFields>();
		for (PunchListFields plf : PunchListFields.values()) {
			visible.put(plf, true);
		}
		
		int acc=0;
		for(PunchListFields plf : PunchListFields.values()){
			position.put(acc, plf);
			acc++;
		}
	}

	public void setPosition(PunchListFields p , Integer pos){
		PunchListFields tmp = null;
		for (int acc = 0; acc< PunchListFields.values().length;acc++) {
			if(pos > acc){}
			else if(pos==acc){
				tmp = position.get(pos);
				position.put(pos, p);
			} else if(pos < acc){
				PunchListFields toCPYAfter = position.get(acc);
				position.put(acc, tmp);
				tmp = toCPYAfter;
			}
		}
	}
	
	public TreeMap<PunchListFields, Boolean> getVisibleFields() {
		return visible;
	}
	
	public TreeMap<Integer, PunchListFields> getPositionFields() {
		return position;
	}

}
	

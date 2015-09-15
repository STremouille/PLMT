package view;

import javax.swing.JComboBox;
import javax.swing.JTextField;

import view.PunchListTableModel.PunchListFields;

public class FilterCouple {
	public JComboBox filterOn;
	public JTextField filter;
	
	public FilterCouple(){
		filterOn	=	new JComboBox(PunchListFields.values());
		filter 		= 	new JTextField();
	}
	
	public FilterCouple(JComboBox fOn,JTextField f){
		filterOn	=	fOn;
		filter 		= 	f;
	}
}
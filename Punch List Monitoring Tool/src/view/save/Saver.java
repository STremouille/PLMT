package view.save;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;
import javax.swing.text.View;

import view.FilterCouple;
import view.PunchListTableModel.PunchListFields;

public class Saver {

	private ArrayList<FilterCouple> textualFilters;
	private Date issueDateMin, issueDateMax, approvedDateMin, approvedDateMax;
	private JCheckBox onlyOpenedPunchItems;
	private ArrayList<JCheckBox> categoryCb;
	
	String lineSeparator = "//";
	String sentenceSeparator = "::";
	
	public Saver(ArrayList<FilterCouple> textualFilters,Date issueDateMin, Date issueDateMax, Date approvedDateMin, Date approvedDateMax, JCheckBox onlyOpenedPunchItems, ArrayList<JCheckBox> categoryCb){
		this.textualFilters=textualFilters;
		this.issueDateMin=issueDateMin;
		this.issueDateMax=issueDateMax;
		this.approvedDateMin=approvedDateMin;
		this.approvedDateMax=approvedDateMax;
		this.categoryCb=categoryCb;
		this.onlyOpenedPunchItems=onlyOpenedPunchItems;
	}
	
	public void load(File f, view.View v) throws IOException{
		if(f.exists()){
			textualFilters.clear();
			v.cleanTextualFiltersBeforeLoading();
			Scanner lineScanner = new Scanner(f).useDelimiter(lineSeparator);
			while (lineScanner.hasNext()) {
				String line = lineScanner.next();
				Scanner sc = new Scanner(line).useDelimiter(sentenceSeparator);
				while(sc.hasNext()){
					String pointer = sc.next();
					if(pointer.equals("cat")){
//						if(categoryCb.contains(o))
						//TODO CAT LOADING
					} else if(pointer.equals("oopi")){
						this.onlyOpenedPunchItems.setSelected(Boolean.valueOf(sc.next()));
					}else if(pointer.equals("idmi")){
						String date = sc.next();
						if(!date.equals(" "))
							this.issueDateMin = new Date(Long.valueOf(date));
						else
							this.issueDateMin=null;
					}else if(pointer.equals("idma")){
						String date = sc.next();
						if(!date.equals(" "))
							this.issueDateMax = new Date(Long.valueOf(date));
						else
							this.issueDateMax=null;
					}else if(pointer.equals("admi")){
						String date = sc.next();
						if(!date.equals(" "))
							this.approvedDateMin = new Date(Long.valueOf(date));
						else
							this.approvedDateMin=null;
					}else if(pointer.equals("adma")){
						String date = sc.next();
						if(!date.equals(" "))
							this.approvedDateMax = new Date(Long.valueOf(date));
						else
							this.approvedDateMax=null;
					}else if(pointer.equals("tf")){
						JComboBox filterOn = new JComboBox(PunchListFields.values());
						String tmp = sc.next();
						filterOn.setSelectedItem(PunchListFields.valueOf(tmp));
						textualFilters.add(new FilterCouple(filterOn, new JTextField(sc.next())));
					}
				}
				sc.close();
			}
			lineScanner.close();
			v.updatingUIAfterFilterLoad(issueDateMin == null ? 0 : issueDateMin.getTime(),issueDateMax==null?0:issueDateMax.getTime(),approvedDateMin==null ? 0 : approvedDateMin.getTime(),approvedDateMax==null ? 0 :approvedDateMax.getTime());
			v.getFiltersPanel().updateUI();
		} else {
			JOptionPane.showMessageDialog(v.getFrame(), "File choosed is not existing.");
		}
	}
	
	public void save(File f) throws IOException{
		if(!f.exists()){
			f.createNewFile();
		}
		if(f.exists()){
			StringBuffer sb = new StringBuffer();
			Iterator<JCheckBox> it = this.categoryCb.iterator();
			while(it.hasNext()){
				JCheckBox cb = it.next();
				sb.append("cat"+sentenceSeparator+cb.getText()+sentenceSeparator+cb.isSelected()+lineSeparator);
			}
			sb.append("oopi"+sentenceSeparator+this.onlyOpenedPunchItems.isSelected()+lineSeparator);
			if(this.issueDateMin==null){
				sb.append("idmi"+sentenceSeparator+" "+lineSeparator);
			} else {
				sb.append("idmi"+sentenceSeparator+this.issueDateMin.getTime()+lineSeparator);
			}
			if(this.issueDateMax==null){
				sb.append("idma"+sentenceSeparator+" "+lineSeparator);
			} else {
				sb.append("idma"+sentenceSeparator+this.issueDateMax.getTime()+lineSeparator);
			}
			if(this.approvedDateMin==null){
				sb.append("admi"+sentenceSeparator+" "+lineSeparator);
			} else {
				sb.append("admi"+sentenceSeparator+this.approvedDateMin.getTime()+lineSeparator);
			}
			if(this.approvedDateMax==null){
				sb.append("adma"+sentenceSeparator+" "+lineSeparator);
			} else {
				sb.append("adma"+sentenceSeparator+this.approvedDateMax.getTime()+lineSeparator);
			}
			
			Iterator<FilterCouple> itt = this.textualFilters.iterator();
			while(itt.hasNext()){
				FilterCouple fc = itt.next();
				sb.append("tf"+sentenceSeparator+fc.filterOn.getSelectedItem().toString()+sentenceSeparator+ (fc.filter.getText().equals("") ? " ":fc.filter.getText()) +lineSeparator);
			}
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(f));
			bos.write(sb.toString().getBytes());
			bos.flush();
			bos.close();
			
		}
	}
	
}

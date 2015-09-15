package controller;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.print.PrinterException;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
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
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import java.util.TreeMap;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.OrientationRequested;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import model.PunchItem;
import model.PunchList;
import view.DiffFrame;
import view.FilterCouple;
// test
import view.ManageColumnView;
import view.PunchListTableModel.PunchListFields;
import view.View;
import view.save.Saver;
import factory.Factory;

public class Controller {
	
	private Factory factory;
	private View view;
	
	
	private TreeMap<PunchListFields,ArrayList<String>> textualFilter;
	
	public Controller(Factory model,View view){
		this.factory=model;
		this.view=view;
		
		this.textualFilter = new TreeMap<PunchListFields, ArrayList<String>>();
		
		Iterator<String> itCategories = model.getPunchListCategories().iterator();
		while(itCategories.hasNext()){
			view.addActionListenerCategoryButton(itCategories.next(), new FilterButtonListener());
		}
		
		view.addShowFilterListerner(new ShowFilterActionListener());
		view.addIssueDateMaxListener(new FilterButtonListener());
		view.addIssueDateMinListener(new FilterButtonListener());
		view.addClosedDateMaxListener(new FilterButtonListener());
		view.addClosedDateMinListener(new FilterButtonListener());
		view.addOpenedPunchListener(new FilterButtonListener());
		view.addOpenedPunchListener(new OpenPunchListener());
		view.addReportDiffBetween2TMActionListener(new ReportDiffBetween2TMActionListener());
		//view.addSubsystemFilterListener(new TextualFilterDocumentListener(view.getSubsystemFilterTextField()));
		
		Iterator<view.FilterCouple> itTextualFilters = view.getTextualFilters().iterator();
		while(itTextualFilters.hasNext()){
			view.FilterCouple fc = itTextualFilters.next();
			fc.filter.getDocument().addDocumentListener(new TextualFilterDocumentListener(fc.filter,fc.filterOn));
		}
		
		view.addPrintListener(new PrintListener());
		view.addQuitListener(new QuitListener());
		view.addColumnButtonListener(new ColumnManagerListener());
		
		view.addButtonTextualFilterListener(new AddTextualFilter());
		view.addSaveFilterActionListener(new SaveFilterListener());
		view.addLoadFilterActionListener(new LoadFilterListener());
		
		view.addPicturePunchListActionListener(new PicturePunchListListener());
		view.addReportDifferenceActionListener(new ReportDifferenceActionListener());
	}
	
	
	class FilterButtonListener implements ActionListener{

		
		@Override
		public void actionPerformed(ActionEvent e) {
			applyFilter();
		}
		
	}
	
	class ShowFilterActionListener implements MouseListener{


		@Override
		public void mouseClicked(MouseEvent arg0) {
			
		}

		@Override
		public void mouseEntered(MouseEvent e) {
		}

		@Override
		public void mouseExited(MouseEvent e) {
			
		}

		@Override
		public void mousePressed(MouseEvent e) {
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			view.getFiltersPanel().setVisible(!view.getFiltersPanel().isVisible());
			view.swithIcon();
		}
		
	}
	
	class ColumnManagerListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			ManageColumnView mcv = new ManageColumnView(view.getPunchListColumnSettings(),view);
			mcv.pack();
			mcv.setVisible(true);
		}
		
	}
	
	class PrintListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			MessageFormat header = null;
			MessageFormat footer = new MessageFormat("Page {0,number,integer}");
			PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
			pras.add(OrientationRequested.LANDSCAPE);
			try {
				view.getPlTable().print(JTable.PrintMode.FIT_WIDTH, header, footer, true, pras, true);
			} catch (HeadlessException e1) {
				JOptionPane.showMessageDialog(view.getFrame(), (Object)e1.getMessage());
				e1.printStackTrace();
			} catch (PrinterException e1) {
				JOptionPane.showMessageDialog(view.getFrame(), (Object)e1.getMessage());
				e1.printStackTrace();
			}
		}
		
	}
	
	class TextualFilterDocumentListener implements DocumentListener{

		private JTextField textField;
		private JComboBox plf;
		
		public TextualFilterDocumentListener(JTextField tf,JComboBox comboBox) {
			this.textField=tf;
			this.plf=comboBox;
		}
		
		@Override
		public void changedUpdate(DocumentEvent e) {
			notificated();
		}

		@Override
		public void insertUpdate(DocumentEvent e) {
			notificated();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			notificated();
		}
		
		public void notificated(){
			if(textField.getText().trim().endsWith(";")){
				ArrayList<String> tFilter = new ArrayList<String>();
				Scanner scanner = new Scanner(textField.getText().trim()).useDelimiter(";");
				while(scanner.hasNext()){
					String filterCriteria = scanner.next();
					filterCriteria = filterCriteria.trim();
					tFilter.add(filterCriteria);
				}
				textualFilter.put((PunchListFields) plf.getSelectedItem(), tFilter);
				applyFilter();
			} else if(textField.getText().equals("")){
				textualFilter.remove(plf.getSelectedItem());
				applyFilter();
			}
		}
		
	}
	
	class QuitListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
		
	}
	
	class OpenPunchListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			view.disableApproveDateFilter();
		}
		
	}
	
	public void applyFilter() {
		
		ArrayList<JCheckBox> categoryCheckBox = view.getCategoryCheckBox();
		Date issueDateMin = view.getIssueDateMin();
		Date issueDateMax = view.getIssueDateMax();
		Date closedDateMin = view.getClosedDateMin();
		Date closedDateMax = view.getClosedDateMax();
		boolean openedPunch = view.isClosedPunchSelected();
		
		
		// Punch Cat
		ArrayList<String> cats = new ArrayList<String>();
		Iterator<JCheckBox> it1 = categoryCheckBox.iterator();
		while (it1.hasNext()) {
			JCheckBox temp = it1.next();
			if (temp.isSelected()) {
				cats.add(temp.getText());
			}
		}

		PunchList pl = new PunchList(new ArrayList<PunchItem>());

		Iterator<PunchItem> it = factory.getPunchLists().iterator();

		// APPLY FILTERS
		while (it.hasNext()) {
			PunchItem pi = it.next();
			if (cats.contains(pi.getCategory())) {
				if (issueDateMin != null) {
					if (issueDateMax != null) {
						// ISSUE DATE MIN && ISSUE DATE MAX
						if (issueDateMin.getTime() <= pi.getOriginatedDate().getTime()
								&& issueDateMax.getTime() >= pi.getOriginatedDate().getTime()) {
							pl.getList().add(pi);
						}
					} else {
						// ONLY ISSUE DATE MIN
						if (issueDateMin.getTime() <= pi.getOriginatedDate().getTime()) {
							pl.getList().add(pi);
						}
					}
				} else if (issueDateMax != null) {
					// ONLY ISSUE DATE MAX
					if (issueDateMax.getTime() >= pi.getOriginatedDate().getTime()) {
						pl.getList().add(pi);
					}
				} else {
					pl.getList().add(pi);
				}
			}
		}

		if(openedPunch){
			// ONLY OPENED
			ArrayList<PunchItem> temp = new ArrayList<PunchItem>();
			it = pl.getList().iterator();
			while (it.hasNext()) {
				PunchItem pi = it.next();
				if (pi.getApprovedDate() == null) {
					temp.add(pi);
				} 
			}
	
			pl = new PunchList(temp);
		} else {
			// CLOSED PL FILTER
			ArrayList<PunchItem> temp = new ArrayList<PunchItem>();
			java.util.Calendar c = java.util.Calendar.getInstance();
			if (closedDateMin != null) {
				c.setTime(closedDateMin);
				c.add(java.util.Calendar.DAY_OF_YEAR, -1);
				closedDateMin = new Date(c.getTimeInMillis());
			}
			it = pl.getList().iterator();
			while (it.hasNext()) {
				PunchItem pi = it.next();
				if (pi.getApprovedDate() != null) {
					if (closedDateMin != null) {
						if (closedDateMax != null) {
							if (pi.getApprovedDate().getTime() >= closedDateMin.getTime()
									&& pi.getApprovedDate().getTime() <= closedDateMax.getTime()) {
								System.out.println(pi.getApprovedDate());
								System.out.println(closedDateMin);
								temp.add(pi);
							}
						} else {
							if (pi.getApprovedDate().getTime() >= closedDateMin.getTime()) {
								temp.add(pi);
							}
						}
					} else if (closedDateMax != null) {
						if (pi.getApprovedDate().getTime() <= closedDateMax.getTime()) {
							temp.add(pi);
						}
					} else {
						temp.add(pi);
					}
				} else if (closedDateMax == null) {
					temp.add(pi);
				}
			}
	
			pl = new PunchList(temp);
		}
		
		//TEXTUAL FILTER
		if(textualFilter!=null && textualFilter.size()>0){
			for(Map.Entry<PunchListFields, ArrayList<String>> entry : textualFilter.entrySet()){
				ArrayList<PunchItem> temp = new ArrayList<PunchItem>();
				it = pl.getList().iterator();
				while (it.hasNext()) {
					PunchItem pi = it.next();
					Iterator<String> subsystemFilterIt = entry.getValue().iterator();
					while(subsystemFilterIt.hasNext()){
						if(pi.getField(entry.getKey()).matches(subsystemFilterIt.next())&&!temp.contains(pi)){
							temp.add(pi);
						}
					}
				}
				pl = new PunchList(temp);
			}
		}
		view.getPlTableModel().setPunchList(pl);
	}
	
	class AddTextualFilter implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			
			JComboBox comboBox = new JComboBox(PunchListFields.values());
			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			gbc_comboBox.insets = new Insets(0, 0, 5, 5);
			gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox.gridx = 0; 
			gbc_comboBox.gridy = view.getIndentForTextualFilter();
			view.getFiltersPanel().add(comboBox, gbc_comboBox);
			
			JTextField textualFilter = new JTextField();
			textualFilter.setToolTipText("Separate filter criteria by ';' (use of regexp is allowed)");
			GridBagConstraints gbc_textField = new GridBagConstraints();
			gbc_textField.insets = new Insets(0, 0, 5, 0);
			gbc_textField.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField.gridx = 1;
			gbc_textField.gridy = view.getIndentForTextualFilter();
			view.getFiltersPanel().add(textualFilter, gbc_textField);
			textualFilter.setColumns(10);
			
			view.getTextualFilters().add(new view.FilterCouple(comboBox, textualFilter));
			textualFilter.getDocument().addDocumentListener(new TextualFilterDocumentListener(textualFilter,comboBox));
			//DOWNSCALE BUTTON
			GridBagLayout layout = (GridBagLayout) view.getFiltersPanel().getLayout();
			Component[] comps = view.getFiltersPanel().getComponents();
			for (int i = 0; i < comps.length; ++i) {
			    Component comp = comps[i];
			    GridBagConstraints gbc = layout.getConstraints(comp);
			    if(comp.equals(view.getAddTextualFilterButton())){
			    	System.out.println(view.getIndentForTextualFilter() +1);
			    	gbc.gridy = view.getIndentForTextualFilter() +1 ;
			    	view.getFiltersPanel().remove(comp);
			    	view.getFiltersPanel().add(comp, gbc);
			    }
			}
			view.getFiltersPanel().updateUI();
			
		}
		
	}
	
	class SaveFilterListener implements ActionListener{
		
		private String saveFileExtension = "filter";

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter('.'+saveFileExtension, saveFileExtension);
			jfc.setFileFilter(filter);
			int returnVal = jfc.showSaveDialog(view.getFrame());
			
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File save = jfc.getSelectedFile();
				String name = save.getName();
				name = name.replaceAll("\\s", "-");
				int ext = name.lastIndexOf('.');
				if (ext != -1) {
					String extension = name.substring(ext, name.length());
					if (extension != saveFileExtension) {
						name = name.substring(0, ext) + '.' + saveFileExtension;
					}
				} else {
					name = name + '.' + saveFileExtension;
				}
				//set the new filename
				String folder = save.getPath().substring(0, save.getPath().lastIndexOf(File.separatorChar));
				save = new File(folder + File.separatorChar + name);
				Saver s = new Saver(view.getTextualFilters(), view.getIssueDateMin(), view.getIssueDateMax(), view.getClosedDateMin(), view.getClosedDateMax(), view.getClosedPunchCheckBox(), view.getCategoryCheckBox());
				try {
					s.save(save);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(view.getFrame(), e1.getMessage());
					e1.printStackTrace();
				}
			}
		}
		
	}
	
	class LoadFilterListener implements ActionListener{
		
		private String saveFileExtension = "filter";

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter('.'+saveFileExtension, saveFileExtension);
			jfc.setFileFilter(filter);
			int returnVal = jfc.showOpenDialog(view.getFrame());
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File save = jfc.getSelectedFile();
				String fileName = save.getName();
				int ext = fileName.lastIndexOf('.');
				if (ext != -1) {
					String extension = fileName.substring(ext, fileName.length());
					if (extension != saveFileExtension) {
						fileName = fileName.substring(0, ext) + '.' + saveFileExtension;
					}
				} else {
					fileName = fileName + '.' + saveFileExtension;
				}
				Saver s = new Saver(view.getTextualFilters(), view.getIssueDateMin(), view.getIssueDateMax(), view.getClosedDateMin(), view.getClosedDateMax(), view.getClosedPunchCheckBox(), view.getCategoryCheckBox());
				try {
					s.load(save,view);
				} catch (IOException e1) {
					JOptionPane.showMessageDialog(view.getFrame(), e1.getMessage());
					e1.printStackTrace();
				}
			}
		}
		
	}
	
	class PicturePunchListListener implements ActionListener{

		private String saveFileExtension = "time";

		@Override
		public void actionPerformed(ActionEvent e) {
			try {
				JFileChooser jfc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter('.'+saveFileExtension, saveFileExtension);
				jfc.setFileFilter(filter);
				int returnVal = jfc.showSaveDialog(view.getFrame());
				
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File save = jfc.getSelectedFile();
					String name = save.getName();
					name = name.replaceAll("\\s", "-");
					int ext = name.lastIndexOf('.');
					if (ext != -1) {
						String extension = name.substring(ext, name.length());
						if (extension != saveFileExtension) {
							name = name.substring(0, ext) + '.' + saveFileExtension;
						}
					} else {
						name = name + '.' + saveFileExtension;
					}
					//set the new filename
					String folder = save.getPath().substring(0, save.getPath().lastIndexOf(File.separatorChar));
					save = new File(folder + File.separatorChar + name);
					if(!save.exists()){
						save.createNewFile();
					}
					if(save.exists()){
						BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(save));
						PunchList pl = new PunchList(factory.getPunchLists());
						
							bos.write(pl.serialize().getBytes());
							bos.flush();
							bos.close();
							JOptionPane.showMessageDialog(view.getFrame(), "Picture of Punch Items' list is done properly");
					}
				}
		
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(view.getFrame(), e1.getMessage());
				e1.printStackTrace();
			}
		
		}
	}
	
	class ReportDifferenceActionListener implements ActionListener{

		private String saveFileExtension = "time";
		private JFrame progress;
		private JLabel p;

		@Override
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter('.'+saveFileExtension, saveFileExtension);
			jfc.setFileFilter(filter);
			int returnVal = jfc.showOpenDialog(view.getFrame());
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File save = jfc.getSelectedFile();
				String fileName = save.getName();
				int ext = fileName.lastIndexOf('.');
				if (ext != -1) {
					String extension = fileName.substring(ext, fileName.length());
					if (extension != saveFileExtension) {
						fileName = fileName.substring(0, ext) + '.' + saveFileExtension;
					}
				} else {
					fileName = fileName + '.' + saveFileExtension;
				}
				if(save.exists()){
					progress = new JFrame();
					progress.setResizable(false);
					progress.setLocation(view.getFrame().getLocation());
					progress.setTitle("T. M.");
					p = new JLabel("Start Time Machine");
					progress.add(p,BorderLayout.NORTH);
					JProgressBar jpb = new JProgressBar();
					jpb.setIndeterminate(true);
					progress.add(jpb,BorderLayout.CENTER);
					progress.pack();
					progress.setLocationRelativeTo(view.getFrame());
					progress.setVisible(true);
					ReportDiffSwingWorker rdsw = new ReportDiffSwingWorker(save,factory.getPunchLists(),view,progress,p);
					try {
						rdsw.execute();
					} catch (Exception e1) {
						JOptionPane.showMessageDialog(view.getFrame(), e1.getMessage());
						e1.printStackTrace();
					}
					
				} else {
					JOptionPane.showMessageDialog(view.getFrame(), "File choosed can't be found.");
				}
			}
		}
	}
	
	class ReportDiffBetween2TMActionListener implements ActionListener{
		private String saveFileExtension = "time";
		private JFrame progress;
		private JLabel p;
		
		
		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(view.getFrame(), "Please pick the oldest one first.");
			JFileChooser jfc = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter('.'+saveFileExtension, saveFileExtension);
			jfc.setFileFilter(filter);
			int returnVal = jfc.showOpenDialog(view.getFrame());
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File save = jfc.getSelectedFile();
				String fileName = save.getName();
				int ext = fileName.lastIndexOf('.');
				if (ext != -1) {
					String extension = fileName.substring(ext, fileName.length());
					if (extension != saveFileExtension) {
						fileName = fileName.substring(0, ext) + '.' + saveFileExtension;
					}
				} else {
					fileName = fileName + '.' + saveFileExtension;
				}
				if(save.exists()){
					JOptionPane.showMessageDialog(view.getFrame(), "Now the second one. First is :"+fileName);
					JFileChooser jfc2 = new JFileChooser();
					FileNameExtensionFilter filter2 = new FileNameExtensionFilter('.'+saveFileExtension, saveFileExtension);
					jfc2.setFileFilter(filter2);
					int returnVal2 = jfc.showOpenDialog(view.getFrame());
					if (returnVal2 == JFileChooser.APPROVE_OPTION) {
						File save2 = jfc.getSelectedFile();
						String fileName2 = save.getName();
						int ext2 = fileName.lastIndexOf('.');
						if (ext2 != -1) {
							String extension = fileName.substring(ext, fileName.length());
							if (extension != saveFileExtension) {
								fileName2 = fileName2.substring(0, ext2) + '.' + saveFileExtension;
							}
						} else {
							fileName2 = fileName2 + '.' + saveFileExtension;
						}
						if(save2.exists()){
							progress = new JFrame();
							progress.setResizable(false);
							progress.setLocation(view.getFrame().getLocation());
							progress.setTitle("T. M.");
							p = new JLabel("Start Time Machine");
							progress.add(p,BorderLayout.NORTH);
							JProgressBar jpb = new JProgressBar();
							jpb.setIndeterminate(true);
							progress.add(jpb,BorderLayout.CENTER);
							progress.pack();
							progress.setLocationRelativeTo(view.getFrame());
							progress.setVisible(true);
							ReportDiffSwingWorkerBetween2TM rdsw = new ReportDiffSwingWorkerBetween2TM(save,save2,view,progress,p);
							try {
								rdsw.execute();
							} catch (Exception e1) {
								JOptionPane.showMessageDialog(view.getFrame(), e1.getMessage());
								e1.printStackTrace();
							}
							
						} else {
							JOptionPane.showMessageDialog(view.getFrame(), "Second file choosed can't be found.");
						}
					}
					
				} else {
					JOptionPane.showMessageDialog(view.getFrame(), "First file choosed can't be found.");
				}
			}
		}
		
	}
		
}

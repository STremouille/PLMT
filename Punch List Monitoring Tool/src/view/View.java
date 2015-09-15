package view;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.Properties;

import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import factory.Factory;
import model.PunchList;

import java.awt.Container;
import java.awt.GridBagLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JCheckBox;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import org.jdatepicker.DateModel;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import settings.PunchListTableSettings;
import view.PunchListTableModel.PunchListFields;

import javax.swing.SwingConstants;

import java.awt.FlowLayout;

import javax.swing.JMenuBar;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.ImageIcon;

public class View {

	private JFrame frmPunchListMonitoring;
	private PunchListTableSettings plts;
	private Factory model;
	private JTable plTable;
	private ArrayList<JCheckBox> categoryCheckBoxs;
	private ArrayList<FilterCouple> textualFilterList;
	private PunchListTableModel pltm;
	private JDatePickerImpl datePickerMin,datePickerMax,datePickerClosedMax,datePickerClosedMin;
	private JLabel showFiltersButton;
	private JPanel filtersPanel;
	private JMenuItem mntmPrintTable, mntmQuit,mntmColumns,mntmSaveFilter,mntmLoadFilter,mntmPicturePunchList,mntmReportDifference,mntmReportDifferenceBetween;
	private JCheckBox openedPunch;
	private JLabel closedLabelMin, closedLabelMax, listSize;
	private JTextField textualFilter;
	private JButton btnAddFilter;
	/**
	 * Create the application.
	 */
	public View(Factory f, PunchListTableSettings plts) {
		this.model=f;
		this.plts=plts;
		initialize();
		frmPunchListMonitoring.pack();
		frmPunchListMonitoring.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmPunchListMonitoring = new JFrame();
		frmPunchListMonitoring.setTitle("Punch List Monitoring Tool");
		frmPunchListMonitoring.setBounds(100, 100, 450, 300);
		frmPunchListMonitoring.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		filtersPanel = new JPanel();
		filtersPanel.setVisible(false);
		frmPunchListMonitoring.getContentPane().add(filtersPanel, BorderLayout.WEST);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{0, 0 ,0};
		gbl_panel.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,0,0,0,0,0,0,0, 0, 0, 0, 0, 0, 0,0};
		gbl_panel.columnWeights = new double[]{1.0,1.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0,0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		filtersPanel.setLayout(gbl_panel);
		
		//MISC FOR LAYOUT
		JLabel lblNewLabel = new JLabel("");
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 0;
		gbc_lblNewLabel.gridy = 0;
		filtersPanel.add(lblNewLabel, gbc_lblNewLabel);
		
		JLabel label = new JLabel("  ");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 0;
		gbc_label.gridy = 1;
		filtersPanel.add(label, gbc_label);
		
		//ONE BUTTON FOR EACH CAT
		Iterator<String> it = model.getPunchListCategories().iterator();
		categoryCheckBoxs = new ArrayList<JCheckBox>();
		
		while(it.hasNext()){
			String cat = it.next();
			JCheckBox categoryCheckBox = new JCheckBox(cat);
			categoryCheckBox.setSelected(true);
			GridBagConstraints gbc_categoryButton = new GridBagConstraints();
			gbc_categoryButton.insets = new Insets(0, 0, 5, 0);
			gbc_categoryButton.gridx = 0;
			gbc_categoryButton.gridy = 2+categoryCheckBoxs.size()+1/*+1 for the reset button*/;
			filtersPanel.add(categoryCheckBox, gbc_categoryButton);
			categoryCheckBoxs.add(categoryCheckBox);
		}
		
		JLabel lblPriority = new JLabel("Priority :");
		GridBagConstraints gbc_lblPriority = new GridBagConstraints();
		gbc_lblPriority.insets = new Insets(0, 0, 5, 5);
		gbc_lblPriority.gridx = 0;
		gbc_lblPriority.gridy = 2;
		filtersPanel.add(lblPriority, gbc_lblPriority);
		
		
		//ISSUE DATE
		//MIN
		JLabel issueLabel = new JLabel("Issue Date min:");
		GridBagConstraints gbc_issueLabel = new GridBagConstraints();
		gbc_issueLabel.insets = new Insets(0, 0, 5, 5);
		gbc_issueLabel.gridx = 0;
		gbc_issueLabel.gridy = 2+categoryCheckBoxs.size()+2/*+1 for the reset button*/;
		filtersPanel.add(issueLabel, gbc_issueLabel);
		
		
		UtilDateModel DPmodel = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(DPmodel,p );
		datePickerMin = new JDatePickerImpl(datePanel,new DateLabelFormatter() );
		GridBagConstraints gbc_issueDate = new GridBagConstraints();
		gbc_issueDate.insets = new Insets(0, 0, 5, 5);
		gbc_issueDate.gridx = 0;
		gbc_issueDate.gridy = 2+categoryCheckBoxs.size()+3/*+1 for the reset button*/;
		filtersPanel.add(datePickerMin, gbc_issueDate);
		
		//MAX
		JLabel issueLabelMax = new JLabel("Issue Date max:");
		GridBagConstraints gbc_issueLabelMax = new GridBagConstraints();
		gbc_issueLabelMax.insets = new Insets(0, 0, 5, 0);
		gbc_issueLabelMax.gridx = 1;
		gbc_issueLabelMax.gridy = 2+categoryCheckBoxs.size()+2/*+1 for the reset button*/;
		filtersPanel.add(issueLabelMax, gbc_issueLabelMax);
		
		
		UtilDateModel DPmodelMax = new UtilDateModel();
		Properties pMax = new Properties();
		pMax.put("text.today", "Today");
		pMax.put("text.month", "Month");
		pMax.put("text.year", "Year");
		JDatePanelImpl datePanelMax = new JDatePanelImpl(DPmodelMax,p );
		datePickerMax = new JDatePickerImpl(datePanelMax,new DateLabelFormatter());
		GridBagConstraints gbc_issueDateMax = new GridBagConstraints();
		gbc_issueDateMax.insets = new Insets(0, 0, 5, 0);
		gbc_issueDateMax.gridx = 1;
		gbc_issueDateMax.gridy = 2+categoryCheckBoxs.size()+3/*+1 for the reset button*/;
		filtersPanel.add(datePickerMax, gbc_issueDateMax);
		
		
		//CLOSED DATE
		//CLOSED Y/N
		openedPunch = new JCheckBox("Only opened punch item");
		GridBagConstraints gbc_closedPunch = new GridBagConstraints();
		gbc_closedPunch.insets = new Insets(0, 0, 5, 5);
		gbc_closedPunch.gridx = 0;
		gbc_closedPunch.gridy = 2+categoryCheckBoxs.size()+4/*+1 for the reset button*/;
		filtersPanel.add(openedPunch, gbc_closedPunch);
		
		//MIN
		closedLabelMin = new JLabel("Approved Date min:");
		GridBagConstraints gbc_closedLabelMin = new GridBagConstraints();
		gbc_closedLabelMin.insets = new Insets(0, 0, 5, 5);
		gbc_closedLabelMin.gridx = 0;
		gbc_closedLabelMin.gridy = 2+categoryCheckBoxs.size()+5/*+1 for the reset button*/;
		filtersPanel.add(closedLabelMin, gbc_closedLabelMin);
		
		UtilDateModel DPmodelClosedMin = new UtilDateModel();
		Properties pClosedMin = new Properties();
		pClosedMin.put("text.today", "Today");
		pClosedMin.put("text.month", "Month");
		pClosedMin.put("text.year", "Year");
		JDatePanelImpl datePanelClosedMin = new JDatePanelImpl(DPmodelClosedMin,pClosedMin);
		datePickerClosedMin = new JDatePickerImpl(datePanelClosedMin,new DateLabelFormatter() );
		GridBagConstraints gbc_issueDateClosedMin = new GridBagConstraints();
		gbc_issueDateClosedMin.insets = new Insets(0, 0, 5, 5);
		gbc_issueDateClosedMin.gridx = 0;
		gbc_issueDateClosedMin.gridy = 2+categoryCheckBoxs.size()+6/*+1 for the reset button*/;
		filtersPanel.add(datePickerClosedMin, gbc_issueDateClosedMin);
		
		//MAX
		closedLabelMax = new JLabel("Approved Date max:");
		GridBagConstraints gbc_closedLabelMax = new GridBagConstraints();
		gbc_closedLabelMax.insets = new Insets(0, 0, 5, 0);
		gbc_closedLabelMax.gridx = 1;
		gbc_closedLabelMax.gridy = 2+categoryCheckBoxs.size()+5/*+1 for the reset button*/;
		filtersPanel.add(closedLabelMax, gbc_closedLabelMax);
		
		UtilDateModel DPmodelClosedMax = new UtilDateModel();
		Properties pClosedMax = new Properties();
		pClosedMax.put("text.today", "Today");
		pClosedMax.put("text.month", "Month");
		pClosedMax.put("text.year", "Year");
		JDatePanelImpl datePanelClosedMax = new JDatePanelImpl(DPmodelClosedMax,pClosedMax );
		datePickerClosedMax = new JDatePickerImpl(datePanelClosedMax,new DateLabelFormatter() );
		GridBagConstraints gbc_issueDateClosedMax = new GridBagConstraints();
		gbc_issueDateClosedMax.insets = new Insets(0, 0, 5, 0);
		gbc_issueDateClosedMax.gridx = 1;
		gbc_issueDateClosedMax.gridy = 2+categoryCheckBoxs.size()+6/*+1 for the reset button*/;
		filtersPanel.add(datePickerClosedMax, gbc_issueDateClosedMax);
		
		//TEXTUAL FILTER
		textualFilterList = new ArrayList<FilterCouple>();
		Iterator<FilterCouple> itTextualFilter = textualFilterList.iterator();
		int acc=0;
		while(itTextualFilter.hasNext()){
			FilterCouple fc = itTextualFilter.next();
			JComboBox comboBox = new JComboBox(PunchListFields.values());
			comboBox.setSelectedItem(fc.filterOn);
			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			gbc_comboBox.insets = new Insets(0, 0, 5, 5);
			gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox.gridx = 0; 
			gbc_comboBox.gridy = categoryCheckBoxs.size()+ 9 + acc;
			filtersPanel.add(comboBox, gbc_comboBox);
			
			textualFilter = fc.filter;
			textualFilter.setToolTipText("Separate filter criteria by ';' (use of regexp is allowed)");
			GridBagConstraints gbc_textField = new GridBagConstraints();
			gbc_textField.insets = new Insets(0, 0, 5, 0);
			gbc_textField.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField.gridx = 1;
			gbc_textField.gridy =categoryCheckBoxs.size()+ 9 +acc;
			filtersPanel.add(textualFilter, gbc_textField);
			textualFilter.setColumns(10);
			acc++;
		}
		
		btnAddFilter = new JButton("Add textual filter");
		GridBagConstraints gbc_btnAddFilter = new GridBagConstraints();
		gbc_btnAddFilter.insets = new Insets(0, 0, 5, 0);
		gbc_btnAddFilter.gridx = 1;
		gbc_btnAddFilter.gridy = categoryCheckBoxs.size()+ 9 + textualFilterList.size();
		filtersPanel.add(btnAddFilter, gbc_btnAddFilter);
		
		JPanel panel_1 = new JPanel();
		frmPunchListMonitoring.getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		listSize = new JLabel(model.getPunchLists().size()+" punch items in the list");
		panel_1.add(listSize);
		
		if(model!=null && model.getPunchLists()!=null){
			PunchList pl = new PunchList(model.getPunchLists());
			pltm = new PunchListTableModel(pl,listSize,plts);
			plTable = new JTable(pltm);
			plTable.setAutoCreateRowSorter(true);
			plTable.setAutoResizeMode(JTable.AUTO_RESIZE_LAST_COLUMN);
			plTable.setPreferredScrollableViewportSize(new Dimension(100, 2000));
			plTable.setDefaultRenderer(Date.class, new DateRenderer(model.getDateFormatter()));
			
		}
		
		JScrollPane scrollPane = new JScrollPane(plTable);
		frmPunchListMonitoring.getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		
		
		JPanel panel_2 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_2.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		frmPunchListMonitoring.getContentPane().add(panel_2, BorderLayout.NORTH);
		
		this.showFiltersButton = new JLabel(/*"[+]"*/);
		showFiltersButton.setIcon(new ImageIcon(View.class.getResource("/com/sun/java/swing/plaf/motif/icons/ScrollRightArrowActive.gif")));
		//showFiltersButton.setIcon(new ImageIcon(View.class.getResource("/org/eclipse/jface/text/source/projection/images/collapsed.png")));
		this.showFiltersButton.setHorizontalAlignment(SwingConstants.LEFT);
		panel_2.add(this.showFiltersButton);
		
		JMenuBar menuBar = new JMenuBar();
		frmPunchListMonitoring.setJMenuBar(menuBar);
		
		JMenu mnFile = new JMenu("   File   ");
		menuBar.add(mnFile);
		
		mntmPrintTable = new JMenuItem("   Print   ");
		mnFile.add(mntmPrintTable);
		
		mntmSaveFilter = new JMenuItem("   Save Filter   ");
		mnFile.add(mntmSaveFilter);
		
		mntmLoadFilter = new JMenuItem("   Load Filter   ");
		mnFile.add(mntmLoadFilter);
		
		mntmQuit = new JMenuItem("   Quit   ");
		mnFile.add(mntmQuit);
		
		JMenu mnSettings = new JMenu("   View   ");
		menuBar.add(mnSettings);
		
		mntmColumns = new JMenuItem("   Columns    ");
		mnSettings.add(mntmColumns);
		
		JMenu mnTimeMachine = new JMenu("   Time Machine   ");
		menuBar.add(mnTimeMachine);
		
		mntmPicturePunchList = new JMenuItem("   Picture Punch List   ");
		mnTimeMachine.add(mntmPicturePunchList);
		mntmReportDifference = new JMenuItem("   Report Difference   ");
		mnTimeMachine.add(mntmReportDifference);
		
		mntmReportDifferenceBetween = new JMenuItem("   Report Difference Between Two Time Machine   ");
		mnTimeMachine.add(mntmReportDifferenceBetween);
		
		processTable(plTable);
		
		frmPunchListMonitoring.pack();
		frmPunchListMonitoring.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
	}
	
	
	
	public void addSaveFilterActionListener(ActionListener al){
		this.mntmSaveFilter.addActionListener(al);
	}
	
	public void addActionListenerCategoryButton(String button,ActionListener al){
		Iterator<JCheckBox> it = categoryCheckBoxs.iterator();
		while(it.hasNext()){
			JCheckBox b = it.next();
			if(b.getText().equals(button)){
				b.addActionListener(al);
			}
		}
	}
	
	public void addReportDiffBetween2TMActionListener(ActionListener al){
		this.mntmReportDifferenceBetween.addActionListener(al);
	}
	
	public void addButtonTextualFilterListener(ActionListener al){
		this.btnAddFilter.addActionListener(al);
	}
	
	public void addColumnButtonListener(ActionListener al){
		this.mntmColumns.addActionListener(al);
	}
	
	public void processTable(JTable table){
		table.setAutoResizeMode( JTable.AUTO_RESIZE_OFF );
		 
		//TableColumnAdjuster tca = new TableColumnAdjuster(table);
		//tca.adjustColumns();
		
		for (int column = 0; column < table.getColumnCount(); column++)
		{
		    TableColumn tableColumn = table.getColumnModel().getColumn(column);
		    int preferredWidth = tableColumn.getPreferredWidth();
		    int maxWidth = tableColumn.getMaxWidth();
		 
		    for (int row = 0; row < table.getRowCount(); row++)
		    {
		        TableCellRenderer cellRenderer = table.getCellRenderer(row, column);
		        Component c = table.prepareRenderer(cellRenderer, row, column);
		        int width = c.getPreferredSize().width + table.getIntercellSpacing().width;
		        preferredWidth = Math.max(preferredWidth, width);
		 
		        //  We've exceeded the maximum width, no need to check other rows
		 
		        if (preferredWidth >= maxWidth)
		        {
		            preferredWidth = maxWidth;
		            break;
		        }
		    }
		    tableColumn.setPreferredWidth( preferredWidth );
		}
	}
	

	public PunchListTableModel getPlTableModel() {
		return pltm;
	}
	
	public ArrayList<JCheckBox> getCategoryCheckBox(){
		return this.categoryCheckBoxs;
	}
	
	public void addIssueDateMinListener(ActionListener al){
		this.datePickerMin.addActionListener(al);
	}
	
	public Date getIssueDateMin(){
		if(this.datePickerMin.getJDateInstantPanel().getModel().getValue()!=null){
			return new Date(((java.util.Date) this.datePickerMin.getJDateInstantPanel().getModel().getValue()).getTime());
		} else {
			return null;
		}
		
	}
	
	public void addIssueDateMaxListener(ActionListener al){
		this.datePickerMax.addActionListener(al);
	}
	
	public void addOpenedPunchListener(ActionListener al){
		this.openedPunch.addActionListener(al);
	}
	
	public void addClosedDateMinListener(ActionListener al){
		this.datePickerClosedMin.addActionListener(al);
	}
	
	public void addClosedDateMaxListener(ActionListener al){
		this.datePickerClosedMax.addActionListener(al);
	}
	
	public Date getIssueDateMax(){
		if(this.datePickerMax.getJDateInstantPanel().getModel().getValue()!=null){
			return new Date(((java.util.Date) this.datePickerMax.getJDateInstantPanel().getModel().getValue()).getTime());
		} else {
			return null;
		}
	}
	
	
	public void addShowFilterListerner(MouseListener ml){
		this.showFiltersButton.addMouseListener(ml);
	}
	

	class DateLabelFormatter extends AbstractFormatter {

	    /**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private String datePattern = "yyyy-MM-dd";
	    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

	    @Override
	    public Object stringToValue(String text) throws ParseException {
	        return dateFormatter.parseObject(text);
	    }

	    @Override
	    public String valueToString(Object value) throws ParseException {
	        if (value != null) {
	            Calendar cal = (Calendar) value;
	            return dateFormatter.format(cal.getTime());
	        }

	        return "";
	    }

	}

	public void addPicturePunchListActionListener(ActionListener al){
		this.mntmPicturePunchList.addActionListener(al);
	}
	
	public void addReportDifferenceActionListener(ActionListener al){
		this.mntmReportDifference.addActionListener(al);
	}
	
	public JPanel getFiltersPanel() {
		return filtersPanel;
	}
	
	public boolean isClosedPunchSelected(){
		return this.openedPunch.isSelected();
	}


	public Date getClosedDateMax() {
		if(this.datePickerClosedMax.getJDateInstantPanel().getModel().getValue()!=null){
			return new Date(((java.util.Date) this.datePickerClosedMax.getJDateInstantPanel().getModel().getValue()).getTime());
		} else {
			return null;
		}
	}
	
	public Date getClosedDateMin() {
		if(this.datePickerClosedMin.getJDateInstantPanel().getModel().getValue()!=null){
			return new Date(((java.util.Date) this.datePickerClosedMin.getJDateInstantPanel().getModel().getValue()).getTime());
		} else {
			return null;
		}
	}

	public void addPrintListener(ActionListener printListener) {
		this.mntmPrintTable.addActionListener(printListener);		
	}
	
	public void addQuitListener(ActionListener quitListener){
		this.mntmQuit.addActionListener(quitListener);
	}

	public void disableApproveDateFilter() {
		if(openedPunch.isSelected()){
			closedLabelMax.setVisible(false);
			closedLabelMin.setVisible(false);
			datePickerClosedMin.setVisible(false);
			datePickerClosedMax.setVisible(false);
		} else {
			closedLabelMax.setVisible(true);
			closedLabelMin.setVisible(true);
			datePickerClosedMin.setVisible(true);
			datePickerClosedMax.setVisible(true);
		}
	}

	public JTable getPlTable() {
		return plTable;
	}

	public Component getFrame() {
		return frmPunchListMonitoring;
	}

	public JTextField getSubsystemFilterTextField() {
		return textualFilter;
	}

	public void swithIcon() {
		if(this.getFiltersPanel().isVisible()){
			//showFiltersButton.setText("[-]");
			showFiltersButton.setIcon(new ImageIcon(View.class.getResource("/com/sun/java/swing/plaf/motif/icons/ScrollLeftArrowActive.gif")));
			//showFiltersButton.setIcon(new ImageIcon(View.class.getResource("/org/eclipse/jface/text/source/projection/images/expanded.png")));
		} else {
			//showFiltersButton.setText("[+]");
			showFiltersButton.setIcon(new ImageIcon(View.class.getResource("/com/sun/java/swing/plaf/motif/icons/ScrollRightArrowActive.gif")));
			//showFiltersButton.setIcon(new ImageIcon(View.class.getResource("/org/eclipse/jface/text/source/projection/images/collapsed.png")));
		}
	}
	
	public void cleanTextualFiltersBeforeLoading(){
		GridBagLayout layout = (GridBagLayout) this.getFiltersPanel().getLayout();
		Component[] comps = this.getFiltersPanel().getComponents();
		for (int i = 0; i < comps.length; ++i) {
		    Component comp = comps[i];
		    GridBagConstraints gbc = layout.getConstraints(comp);
		    if(comp.getClass().equals(new JComboBox().getClass()) || comp.getClass().equals(new JTextField().getClass())){
		    	this.getFiltersPanel().remove(comp);
		    }
		}
	}
	
	public void updatingUIAfterFilterLoad(long idmi,long idma,long admi, long adma){
		//TODO DATE + only punch item openned remaining
		this.openedPunch.setSelected(true);
		Calendar cal = Calendar.getInstance();
		if(idmi!=0){
			cal.setTime(new Date(idmi));
			this.datePickerMin.getModel().setDate(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_WEEK));
			this.datePickerMin.getModel().setSelected(true);
			System.out.println(this.datePickerMin.getModel().getYear());
		} else {
			this.datePickerMin.getModel().setValue(null);
		}
		if(idma!=0){
			cal.setTime(new Date(idma));
			this.datePickerMax.getModel().setDate(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_WEEK));
			this.datePickerMax.getModel().setSelected(true);
		} else {
			this.datePickerMax.getModel().setValue(null);
		}
		if(admi!=0){
			cal.setTime(new Date(admi));
			this.datePickerClosedMin.getModel().setDate(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_WEEK));
			this.datePickerClosedMin.getModel().setSelected(true);
		} else {
			this.datePickerClosedMin.getModel().setValue(null);
		}
		if(adma!=0){
			cal.setTime(new Date(adma));
			this.datePickerClosedMax.getModel().setDate(cal.get(Calendar.YEAR),cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_WEEK));
			this.datePickerClosedMax.getModel().setSelected(true);
		} else {
			this.datePickerClosedMax.getModel().setValue(null);
		}
		
		Iterator<FilterCouple> itTextualFilter = textualFilterList.iterator();
		int acc=0;
		while(itTextualFilter.hasNext()){
			FilterCouple fc = itTextualFilter.next();
			JComboBox comboBox = new JComboBox(PunchListFields.values());
			comboBox.setSelectedItem(fc.filterOn.getSelectedItem());
			GridBagConstraints gbc_comboBox = new GridBagConstraints();
			gbc_comboBox.insets = new Insets(0, 0, 5, 5);
			gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
			gbc_comboBox.gridx = 0; 
			gbc_comboBox.gridy = categoryCheckBoxs.size()+ 9 + acc;
			filtersPanel.add(comboBox, gbc_comboBox);
			
			textualFilter = fc.filter;
			textualFilter.setToolTipText("Separate filter criteria by ';' (use of regexp is allowed)");
			GridBagConstraints gbc_textField = new GridBagConstraints();
			gbc_textField.insets = new Insets(0, 0, 5, 0);
			gbc_textField.fill = GridBagConstraints.HORIZONTAL;
			gbc_textField.gridx = 1;
			gbc_textField.gridy =categoryCheckBoxs.size()+ 9 +acc;
			filtersPanel.add(textualFilter, gbc_textField);
			textualFilter.setColumns(10);
			acc++;
		}
		
		GridBagLayout layout = (GridBagLayout) this.getFiltersPanel().getLayout();
		Component[] comps = this.getFiltersPanel().getComponents();
		for (int i = 0; i < comps.length; ++i) {
		    Component comp = comps[i];
		    GridBagConstraints gbc = layout.getConstraints(comp);
		    if(comp.equals(this.getAddTextualFilterButton())){
		    	System.out.println(this.getIndentForTextualFilter() +1);
		    	gbc.gridy = this.getIndentForTextualFilter() +1 ;
		    	this.getFiltersPanel().remove(comp);
		    	this.getFiltersPanel().add(comp, gbc);
		    }
		}
		
		System.out.println(datePickerMin.getModel().getValue());
	}

	public PunchListTableSettings getPunchListColumnSettings() {
		return plts;
	}

	public void updateTableCache() {
		pltm.updateCache();
	}
	
	public ArrayList<FilterCouple> getTextualFilters() {
		return this.textualFilterList;
	}

	public int getIndentForTextualFilter() {
		 return categoryCheckBoxs.size()+ 9 + textualFilterList.size();
	}

	public JButton getAddTextualFilterButton() {
		return btnAddFilter;
	}
	
	public void addLoadFilterActionListener(ActionListener al){
		this.mntmLoadFilter.addActionListener(al);
	}

	class DateRenderer extends DefaultTableCellRenderer{
		
		SimpleDateFormat sdf;
		
		public DateRenderer(SimpleDateFormat sdf) {
			this.sdf = sdf; 
		}
		
		@Override
		public void setValue(Object value) {
	        setText((value == null) ? "" : sdf.format(value));
	    }
		
	}

	public JCheckBox getClosedPunchCheckBox() {
		return openedPunch;
	}
}


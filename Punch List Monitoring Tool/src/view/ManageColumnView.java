package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import java.awt.GridBagLayout;

import javax.swing.JLabel;

import java.awt.GridBagConstraints;

import javax.swing.JCheckBox;

import settings.PunchListTableSettings;
import view.PunchListTableModel.PunchListFields;

import java.awt.Insets;
import java.util.Map;
import java.util.TreeMap;

import javax.swing.JButton;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageColumnView extends JFrame {

	private JPanel centerPanel,southPanel, contentPanel;
	private PunchListTableSettings plts;
	private TreeMap<PunchListFields, JCheckBox> checkboxs;
	private JButton btnOk;
	private JButton btnCancel;
	private JButton btnNewButton;


	/**
	 * Create the frame.
	 */
	public ManageColumnView(final PunchListTableSettings plts, final View v) {
		setTitle("Column");
		this.plts=plts;
		checkboxs = new TreeMap<PunchListTableModel.PunchListFields, JCheckBox>();
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPanel = new JPanel();
		southPanel = new JPanel();
		FlowLayout flowLayout = (FlowLayout) southPanel.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPanel.setLayout(new BorderLayout());
		centerPanel = new JPanel();
		contentPanel.add(centerPanel,BorderLayout.CENTER);
		contentPanel.add(southPanel,BorderLayout.SOUTH);
		
		btnOk = new JButton("OK");
		southPanel.add(btnOk);
		
		btnCancel = new JButton("Cancel");
		southPanel.add(btnCancel);
		centerPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPanel);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0 , 0, 0, 0, 0  ,0, 0, 0, 0  ,0, 0, 0, 0 ,0 };
		gbl_contentPane.columnWeights = new double[]{0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0 ,0.0, 0.0, 0.0,0.0, 0.0, 0.0 ,0.0, 0.0, 0.0,0.0, Double.MIN_VALUE};
		centerPanel.setLayout(gbl_contentPane);
		
		
		int acc = 0;
		final JCheckBox cbSelectAll = new JCheckBox("Select All");
		cbSelectAll.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(Map.Entry<PunchListFields, JCheckBox> entry : checkboxs.entrySet()){
					entry.getValue().setSelected(cbSelectAll.isSelected());
				}
				for(Map.Entry<PunchListFields, JCheckBox> entry : checkboxs.entrySet()){
					plts.getVisibleFields().put(entry.getKey(), entry.getValue().isSelected());
				}
				v.updateTableCache();
			}
		});
		GridBagConstraints gbc_chckbxNewCheckBoxSelectAll = new GridBagConstraints();
		gbc_chckbxNewCheckBoxSelectAll.anchor = GridBagConstraints.WEST;
		gbc_chckbxNewCheckBoxSelectAll.insets = new Insets(0, 0, 5, 0);
		gbc_chckbxNewCheckBoxSelectAll.gridx = 0;
		gbc_chckbxNewCheckBoxSelectAll.gridy = acc;
		centerPanel.add(cbSelectAll, gbc_chckbxNewCheckBoxSelectAll);
		acc++;
		
		for (PunchListFields plf : PunchListFields.values()) {
			JCheckBox cb = new JCheckBox(plf.toString());
			cb.setSelected(plts.getVisibleFields().get(plf));
			cb.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					for(Map.Entry<PunchListFields, JCheckBox> entry : checkboxs.entrySet()){
						plts.getVisibleFields().put(entry.getKey(), entry.getValue().isSelected());
					}
					v.updateTableCache();
				}
			});
			GridBagConstraints gbc_chckbxNewCheckBox = new GridBagConstraints();
			gbc_chckbxNewCheckBox.anchor = GridBagConstraints.WEST;
			gbc_chckbxNewCheckBox.insets = new Insets(0, 0, 5, 0);
			gbc_chckbxNewCheckBox.gridx = 0;
			gbc_chckbxNewCheckBox.gridy = acc;
			centerPanel.add(cb, gbc_chckbxNewCheckBox);
			checkboxs.put(plf, cb);
			acc++;
		}
		
		int allSelected = 0;
		for(Map.Entry<PunchListFields, JCheckBox> entry : checkboxs.entrySet()){
			if(entry.getValue().isSelected()){
				allSelected++;
			}
		}
		
		if(allSelected==0){
			cbSelectAll.setSelected(false);
		} else if(allSelected == PunchListFields.values().length){
			cbSelectAll.setSelected(true);
		}
		
		btnOk.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				for(Map.Entry<PunchListFields, JCheckBox> entry : checkboxs.entrySet()){
					plts.getVisibleFields().put(entry.getKey(), entry.getValue().isSelected());
				}
				v.updateTableCache();
				dispose();
			}
		});
		
		btnCancel.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		
	}

}

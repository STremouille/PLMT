package view;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Highlighter;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JTextField;
import javax.swing.JButton;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.GridLayout;
import java.awt.FlowLayout;

import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import model.PunchList;
import controller.ReportDiffSwingWorker;
import javax.swing.JMenu;

public class DiffFrame extends JFrame {

	private JPanel contentPane,rightPanel;
	private JTextField searchField;
	private JTextArea textArea;
	private ArrayList<Integer> matchPositionAfterSearch;
	private JPanel panel;
	private JButton prevMatch;
	private JButton nextMatch;
	private JPanel panel_1;
	private JLabel lblNumberOfMatchs;
	private JMenuBar menuBar;
	private JMenuItem mntmExportAsText;
	private JFrame frame;
	private JMenu mnFile;

	/**
	 * Create the frame.
	 */
	public DiffFrame(String diffResult) {
		this.frame=this;
		matchPositionAfterSearch = new ArrayList<Integer>();
		setForeground(Color.BLACK);
		setTitle("Diff Report");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 900, 600);
		
		menuBar = new JMenuBar();
		setJMenuBar(menuBar);
		
		mnFile = new JMenu("File");
		menuBar.add(mnFile);
		
		mntmExportAsText = new JMenuItem("Export as text file");
		mnFile.add(mntmExportAsText);
		mntmExportAsText.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String saveFileExtension = "txt";
				JFileChooser jfc = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter('.'+saveFileExtension, saveFileExtension);
				jfc.setFileFilter(filter);
				int returnVal = jfc.showOpenDialog(frame);
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
					String folder = save.getPath().substring(0, save.getPath().lastIndexOf(File.separatorChar));
					save = new File(folder + File.separatorChar + fileName);
					if(!save.exists()){
						try {
							save.createNewFile();
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(frame, e1.getMessage());
							e1.printStackTrace();
						}
					}
					if(save.exists()){
						BufferedOutputStream bos;
						try {
							bos = new BufferedOutputStream(new FileOutputStream(save));
							bos.write(textArea.getText().getBytes());
							bos.flush();
							bos.close();
							JOptionPane.showMessageDialog(frame, "Export successful");
						} catch (FileNotFoundException e1) {
							JOptionPane.showMessageDialog(frame, e1.getMessage());
							e1.printStackTrace();
						} catch (IOException e1) {
							JOptionPane.showMessageDialog(frame, e1.getMessage());
							e1.printStackTrace();
						}
						
							
					}
					
				}
			}
		});
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		textArea = new JTextArea(diffResult);
		textArea.setEditable(false);
		textArea.setFont(new Font("Consolas", Font.PLAIN, 14));
		textArea.setForeground(Color.BLACK);
		
		JScrollPane scrollPane = new JScrollPane(textArea);
		contentPane.add(scrollPane, BorderLayout.CENTER);
		
		rightPanel = new JPanel();
		rightPanel.setVisible(false);
		contentPane.add(rightPanel, BorderLayout.EAST);
		rightPanel.setLayout(new BorderLayout(0, 0));
		
		searchField = new JTextField();
		rightPanel.add(searchField, BorderLayout.NORTH);
		searchField.setColumns(10);
		
		panel = new JPanel();
		rightPanel.add(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		prevMatch = new JButton("<-");
		panel.add(prevMatch);
		
		nextMatch = new JButton("->");
		panel.add(nextMatch);
		
		panel_1 = new JPanel();
		rightPanel.add(panel_1, BorderLayout.SOUTH);
		
		lblNumberOfMatchs = new JLabel("No match");
		lblNumberOfMatchs.setFont(new Font("Tahoma", Font.BOLD, 11));
		panel_1.add(lblNumberOfMatchs);
		
		searchField.getDocument().addDocumentListener(new DocumentListener() {
			
			@Override
			public void removeUpdate(DocumentEvent e) {
				woot();
			}
			
			@Override
			public void insertUpdate(DocumentEvent e) {
				woot();
			}
			
			@Override
			public void changedUpdate(DocumentEvent e) {
				woot();
			}
			
			private void woot(){
				textArea.getHighlighter().removeAllHighlights();
				matchPositionAfterSearch.clear();
				
				
				if(rightPanel.isVisible() && !searchField.getText().equals("")){
					Highlighter.HighlightPainter painter = 
						    new DefaultHighlighter.DefaultHighlightPainter( Color.magenta );
	
						int offset = textArea.getText().indexOf(searchField.getText());
						int length = searchField.getText().length();
						if(offset!=-1)
							matchPositionAfterSearch.add(offset);
	
						while ( offset != -1)
						{
						    try
						    {
						        textArea.getHighlighter().addHighlight(offset, offset + length, painter);
						        offset = textArea.getText().indexOf(searchField.getText(), offset+1);
						        if(offset!=-1)
									matchPositionAfterSearch.add(offset);
						    }
						    catch(BadLocationException ble) { System.out.println(ble); }
						}
						if(matchPositionAfterSearch.size()>0){
							textArea.setCaretPosition(matchPositionAfterSearch.get(0));
							lblNumberOfMatchs.setText("Number of matchs : "+matchPositionAfterSearch.size());
						}
						else
							lblNumberOfMatchs.setText("No match");
				} 
			}
		});
		
		textArea.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				 if ((e.getKeyCode() == KeyEvent.VK_F) && ((e.getModifiers() & KeyEvent.CTRL_MASK) != 0)) {
					 	rightPanel.setVisible(!rightPanel.isVisible());
	                }
			}
		});
		
		prevMatch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int prev = -1;
				if (matchPositionAfterSearch.size() > 0) {
					if (matchPositionAfterSearch.size() == 1) {
						textArea.setCaretPosition(matchPositionAfterSearch.get(0));
					} else if (textArea.getCaretPosition() == matchPositionAfterSearch.get(0).intValue()) {
						textArea.setCaretPosition(matchPositionAfterSearch.get(matchPositionAfterSearch.size() - 1));
					} else {
						Iterator<Integer> it = matchPositionAfterSearch.iterator();
						while (it.hasNext()) {
							int current = it.next();
							if (textArea.getCaretPosition() == current) {
								textArea.setCaretPosition(prev);
							} else {
								prev = current;
							}
						}
					}
				}

			}
		});
		
		nextMatch.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				if (matchPositionAfterSearch.size() > 0) {
					if (matchPositionAfterSearch.size() == 1) {
						textArea.setCaretPosition(matchPositionAfterSearch.get(0));
					} else if (textArea.getCaretPosition() == matchPositionAfterSearch.get(matchPositionAfterSearch.size() - 1).intValue()) {
						textArea.setCaretPosition(matchPositionAfterSearch.get(0));
					} else {
						Iterator<Integer> it = matchPositionAfterSearch.iterator();
						while (it.hasNext()) {
							if (textArea.getCaretPosition() == it.next()
									.intValue()) {
								textArea.setCaretPosition(it.next().intValue());
							} else {
							}
						}
					}
				}

			}
		});
	}
	
	/*class FinderClass implements ActionListener{
	   
		@Override
		public void actionPerformed(ActionEvent e) {

			String myWord = searchField.getText();
			Highlighter h = textArea.getHighlighter();

			if (e.getSource() == bFind) {
				pattern = Pattern.compile("\\b" + myWord + "\\b");
				Matcher matcher = pattern.matcher(myWord);
				boolean matchFound = matcher.matches(); // false
				if (!matchFound) {
					while (matcher.find()) {
						int start = matcher.start();
						int end = matcher.end();

						try {
							Font font = new Font("Verdana", Font.BOLD, 40);

							h.addHighlight(start, end,
									DefaultHighlighter.DefaultPainter);
						} catch (BadLocationException e1) {

							e1.printStackTrace();
						}
					}

				}

				else {
					JOptionPane.showMessageDialog(null, "No Match Found");
				}

			}
		}
	}*/
}

package controller;

import java.awt.BorderLayout;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JProgressBar;
import javax.swing.SwingWorker;

import view.DiffFrame;
import view.View;
import model.PunchItem;
import model.PunchList;

public class ReportDiffSwingWorker extends SwingWorker<Boolean, Boolean>{

	private File save;
	private ArrayList<PunchItem> pl;
	private View view;
	private JLabel p;
	private JFrame f;
	
	public ReportDiffSwingWorker(File save,ArrayList<PunchItem> pl, View view, JFrame progress, JLabel p) {
		this.save=save;
		this.pl=pl;
		this.view=view;
		this.p=p;
		this.f=progress;
	}
	@Override
	protected Boolean doInBackground() throws Exception {
		BufferedInputStream bis = new BufferedInputStream(new FileInputStream(save));
		f.setLayout(new BorderLayout());
		p.setText("Loading data ...");
		firePropertyChange("status", "test", "Loading...");
		int size = bis.available();
        byte[] b = new byte[size];
        int i=0;
        while (i < size) {
        	b[i] = (byte) bis.read();
        	//System.out.println(plToCompare.length()*2+" bytes");
           i++;
        }
        bis.close();
        String plToCompare = new String(b);
        //System.out.println("End loading file");
        
        PunchList pl = new PunchList(this.pl);
        PunchList pl2 = new PunchList(new ArrayList<PunchItem>());
        p.setText("Unserializing data ...");
        firePropertyChange("status", "", "Unserializing...");
        //System.out.println("Start Unserializing file");
        pl2.unserialize(plToCompare);
        //System.out.println("End Unserializing file");
        p.setText("Computing Diff ...");
        firePropertyChange("status", "", "Computing Diff...");
        //System.out.println("Start Diff");
        DiffFrame df = new DiffFrame(pl2.reportDiff(pl));
        df.setLocationRelativeTo(view.getFrame());
        f.dispose();
        firePropertyChange("gameover", "", "Loading...");
        df.setVisible(true);
        return true;
	}

}

package controller;

import java.awt.BorderLayout;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingWorker;

import model.PunchItem;
import model.PunchList;
import view.DiffFrame;
import view.View;

public class ReportDiffSwingWorkerBetween2TM extends SwingWorker<Boolean, Boolean>{
	
	private File save,save2;
	private View view;
	private JLabel p;
	private JFrame f;

	public ReportDiffSwingWorkerBetween2TM(File save, File save2, View view, JFrame progress, JLabel p) {
		this.save=save;
		this.save2=save2;
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
        
        BufferedInputStream bis2 = new BufferedInputStream(new FileInputStream(save2));
		int size2 = bis2.available();
        byte[] b2 = new byte[size2];
        int i2=0;
        while (i2 < size2) {
        	b2[i2] = (byte) bis2.read();
        	//System.out.println(plToCompare.length()*2+" bytes");
           i2++;
        }
        bis2.close();
        String plToCompare2 = new String(b2);
        //System.out.println("End loading file");
        
        PunchList pl = new PunchList(new ArrayList<PunchItem>());
        PunchList pl2 = new PunchList(new ArrayList<PunchItem>());
        p.setText("Unserializing data ...");
        firePropertyChange("status", "", "Unserializing...");
        //System.out.println("Start Unserializing file");
        pl2.unserialize(plToCompare);
        pl.unserialize(plToCompare2);
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

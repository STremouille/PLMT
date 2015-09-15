package model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class PunchList {

	private ArrayList<PunchItem> list;
	private String separatorChar = "!PISEPARATOR!";

	public PunchList(ArrayList<PunchItem> list) {
		super();
		this.list = list;
	}

	public ArrayList<PunchItem> getList() {
		return list;
	}

	public void setList(ArrayList<PunchItem> list) {
		this.list = list;
	}
	
	public String toString(){
		Iterator<PunchItem> it = list.iterator();
		StringBuffer sb = new StringBuffer();
		sb.append("Punch List, size : "+list.size()+'\n');
		while(it.hasNext()){
			sb.append(it.next().toString()+'\n');
		}
		return sb.toString();
	}
	
	public String serialize(){
		StringBuffer sb = new StringBuffer();
		Iterator<PunchItem> it = list.iterator();
		while(it.hasNext()){
			sb.append(it.next().serialize());
			sb.append(separatorChar);
		}
		return sb.toString();
	}
	
	public void unserialize(String s){
		Scanner scanner = new Scanner(s).useDelimiter(separatorChar);
		while(scanner.hasNext()){
			PunchItem pi = new PunchItem();
			pi.unserialize(scanner.next());
			list.add(pi);
		}
	}
	
	public boolean listContainsPlNumber(ArrayList<PunchItem> list,String plNumber){
		Iterator<PunchItem> iterator = list.iterator();
		while(iterator.hasNext()){
			if(iterator.next().getNumber().equals(plNumber))
				return true;
		}
		return false;
	}
	
	public boolean otherlistContainsPlNumber(PunchList otherList,String plNumber){
		Iterator<PunchItem> iterator = otherList.getList().iterator();
		while(iterator.hasNext()){
			if(iterator.next().getNumber().equals(plNumber))
				return true;
		}
		return false;
	}
	
	public String reportDiff(PunchList pl){
		if(this.equals(pl)){
			return "no diff";
		} else {
			StringBuilder sb = new StringBuilder();
			Iterator<PunchItem> it = pl.getList().iterator();
			while(it.hasNext()){
				PunchItem pi = it.next();
				System.out.println(pi.getNumber());
				if(this.listContainsPlNumber(list,pi.getNumber())){
					if(!this.getPl(pi.getNumber()).reportDiffWith(pi).equals("")){
						sb.append("MODIFICATION :\t"+this.getPl(pi.getNumber()).getNumber()+"\n");
						//sb.append(pi+"\n");
						sb.append(this.getPl(pi.getNumber()).reportDiffWith(pi)+"\n");
						sb.append("******************************************************************************\n");
					}
				} else {
					// PL ADDED
					sb.append("NEW PL :\t" + pi + "\n");
					sb.append("******************************************************************************\n");
				}
			}
			
			it = list.iterator();
			while(it.hasNext()){
				PunchItem pi = it.next();
				if(!this.listContainsPlNumber(pl.getList(),pi.getNumber())){
					sb.append("DELETED PL :\t" + pi + "\n");
					sb.append("******************************************************************************\n");
				}
			}
			return sb.toString();
		}
	}

	private PunchItem getPl(String number) {
		Iterator<PunchItem> iterator = this.list.iterator();
		while(iterator.hasNext()){
			PunchItem pi = iterator.next();
			if(pi.getNumber().equals(number))
				return pi;
		}
		return null;
	}

	@Override
	public boolean equals(Object o) {
		if(o.getClass().equals(this.getClass())){
			PunchList oPL = (PunchList) o;
			if(oPL.getList().size()==this.list.size()){
				Iterator<PunchItem> it = list.iterator();
				while(it.hasNext()){
					PunchItem pi = it.next();
					if(oPL.getList().contains(pi)){
					} else {
						return false;
					}
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}
	
	
}

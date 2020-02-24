import java.awt.*;
import java.awt.print.*;
import java.util.*;

public class State {
	private ArrayList<Change> modRep;
	
	public static void main(String[] args){
		ArrayList<Change> curry = new ArrayList<Change>();
		State a = new State(curry);
		a.print();
	}
	
	public State(ArrayList<Change> report) {
		modRep = report;
	}

	public ArrayList<Change> getModificationReport() {
		return this.modRep;
	}

	public void save() {

	}

	public void print() {
		PrinterJob pj = PrinterJob.getPrinterJob();
		if (pj.printDialog()) {
			try {
				pj.print();
			} catch (PrinterException exc) {
				System.out.println(exc);
			}
		}
	}
}

import java.awt.*;
import java.awt.print.*;
import java.util.*;

public class State {
	private ArrayList<Change> modRep;
	private String path;
	
	public State(ArrayList<Change> report, String path) {
		modRep = report;
		this.path = path;
	}

	public ArrayList<Change> getModificationReport() {
		return modRep;
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
	
	public String getPath() {
		return path;
	}
}
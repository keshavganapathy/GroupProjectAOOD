import java.awt.*;
import java.awt.print.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class State {
	private ArrayList<Change> modificationReport;
	private String path;
	private int id; //each state has a unique id, that will stay the same for its entire lifetime ((name of state folder))

	public State(String path, int id) {
		this.path = path;
		this.id = id;
	}

	public ArrayList<Change> createModificationReport() {
		modificationReport = new ArrayList<Change>();
		//assuming that the state before this one exists
		File currentState = new File(path);
		File metadataFolder = new File(currentState.getParent()); //parent of the state path is metadata folder
		File previousState = new File(metadataFolder + "/state_" + (id - 1));
		
		//String[] currentFiles = currentState.list();
		File[] currentFiles = currentState.listFiles();

		for (File f: currentFiles) {
			System.out.println(f.getAbsolutePath());
		}
		
		//TODO: somehow get all the files in the subdirectories as well. recursion? idk
		
		for (Change change: modificationReport) {
			System.out.println(change.getPath() + " - " + change.getType());
		}
		return modificationReport;
	}
	/*public ArrayList<Change> getModificationReport() {
		return modificationReport;
	}*/

	public void save(String filename) {
		String word = "";
		try {
			FileWriter writer = new FileWriter(filename, true);
			writer.write(word);
			writer.write("\r\n"); // write new line
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void print() {
		PrinterJob pj = PrinterJob.getPrinterJob();
		if (pj.printDialog()) {
			try {
				pj.print();
			} catch (PrinterException ex) {
				ex.printStackTrace();
			}
		}
	}
	
	public String getPath() {
		return path;
	}

	public int getID() {
		return id;
	}
}
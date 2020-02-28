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
		ArrayList<File> currentFiles = getAllFiles(currentState);

		for (File f: currentFiles) {
			System.out.println(f.getAbsolutePath());
		}
		
		//TODO: somehow get all the files in the subdirectories as well. recursion? idk
		
		for (Change change: modificationReport) {
			System.out.println(change.getPath() + " - " + change.type());
		}
		return modificationReport;
	}
	/*public ArrayList<Change> getModificationReport() {
		return modificationReport;
	}*/
	private ArrayList<File> getAllFiles(File currentState) {
		ArrayList<File> returnArrayList = new ArrayList<File>();
		File[] currentFiles = currentState.listFiles();
		returnArrayList = ArrayToArrayList(currentFiles);
		File currentFile;
		for (int i = 0; i < returnArrayList.size(); i++) {
			currentFile = new File(returnArrayList.get(i).getAbsolutePath());
			if (currentFile.isDirectory()) {
				returnArrayList.addAll(getAllFiles(currentFile));
			}
		}
	
		/*for (File file: returnArrayList) {
			currentFile = new File(file.getAbsolutePath());
			if (currentFile.isDirectory()) {
				returnArrayList.addAll(getAllFiles(currentFile));
			}
		}*/
		return returnArrayList;
		/*for (int i = 0; i < returnArrayList.size(); i++) {
			currentFile = new File(returnArrayList.size())
		}*/
	}
	
	private ArrayList<File> ArrayToArrayList(File[] array) {
		ArrayList<File> returnArray = new ArrayList<File>();
		for (int i = 0; i < array.length; i++) {
			returnArray.add(array[i]);
		}
		return returnArray;
	}

	public void save() {
		String word = "INDIA";
		try {
			if(modificationReport.size() > 0){
				
			}else {
				for(Change change: modificationReport){
					FileWriter writer = new FileWriter(new File(change.getPath(), change.getName() + Long.toString(change.getTime()) + change.getDate() + change.type.toString()));
					writer.write(word);
					writer.write("\r\n"); // write new line
					writer.close();
				}
			}
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
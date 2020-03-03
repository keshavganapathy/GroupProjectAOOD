import java.awt.*;
import java.awt.print.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class State {
	private ArrayList<Change> modificationReport;
	private String statePath;
	private int id; //each state has a unique id, that will stay the same for its entire lifetime ((name of state folder))
	private String dataPath;
	
	public State(String statePath, int id, String dataPath) {
		this.statePath = statePath;
		this.id = id;
		this.dataPath = dataPath;
	}

	public ArrayList<Change> createModificationReport() {
		modificationReport = new ArrayList<Change>();
		//assuming that the state before this one exists
		File currentState = new File(statePath);
		File metadataFolder = new File(currentState.getParent()); //parent of the state path is metadata folder
		String previousStatePath = metadataFolder + "\\state_" + (id - 1);
		File previousState = new File(previousStatePath);
		
		//String[] currentFiles = currentState.list();
		ArrayList<File> currentFiles = getAllFiles(currentState);
		
		String[] currentStateFilePaths = getOnlyFilePaths(currentFiles, statePath);
		
		ArrayList<File> previousFiles = getAllFiles(previousState);
		String[] previousStateFilePaths = getOnlyFilePaths(previousFiles, previousStatePath);
		
		/*System.out.println("current: ");
		for (String str: currentStateFilePaths) {
			System.out.println(str);
		}
		
		System.out.println("previous: ");
		for (String str: previousStateFilePaths) {
			System.out.println(str);
		}*/
		
		
		//new files
		for (int i = 0; i < currentStateFilePaths.length; i++) {
			String filePath = currentStateFilePaths[i];
			if (!arrayContains(previousStateFilePaths, filePath)) {
				modificationReport.add(new Change(statePath + "\\" + filePath, Change.Type.NEW));
			}
		}
		
		//edited files
		for (int i = 0; i < currentStateFilePaths.length; i++) {
			String filePath = currentStateFilePaths[i];
			if (arrayContains(previousStateFilePaths, filePath)) {
				File currentFile = new File(statePath + "\\" + filePath);
				File previousFile = new File(previousStatePath + "\\" + filePath);
				/*System.out.println("currentmodified: " + currentFile.lastModified());
				System.out.println("previousmodified: " + previousFile.lastModified());*/
				if (currentFile.lastModified() != previousFile.lastModified()) {
					modificationReport.add(new Change(statePath + "\\" + filePath, Change.Type.EDIT));
				}
			}
		}
		//TODO: new and edits
		/*System.out.println("\n\n report:");
		for (Change change: modificationReport) {
			System.out.println(change.getPath() + " - " + change.type());
		}*/
		return modificationReport;
	}
	/*public ArrayList<Change> getModificationReport() {
		return modificationReport;
	}*/
	
	private static boolean arrayContains(String[] arr, String str) {
		boolean returnBoolean = false;
		for (int i = 0; i < arr.length; i++) {
			if (arr[i].equals(str)) {
				returnBoolean = true;
			}
		}
		return returnBoolean;
	}
	private ArrayList<File> getAllFiles(File currentState) {
		ArrayList<File> returnArrayList = new ArrayList<File>();
		File[] currentFiles = currentState.listFiles();
		returnArrayList = ArrayToArrayList(currentFiles);
		
		ArrayList<File> iterateFiles = new ArrayList<File>();
		for (File file: returnArrayList) {
			iterateFiles.add(file);
		}
		
		File currentFile;
		for (int i = 0; i < iterateFiles.size(); i++) {
			currentFile = new File(iterateFiles.get(i).getAbsolutePath());
			if (currentFile.isDirectory()) {
				returnArrayList.addAll(getAllFiles(currentFile));
			}
		}

		return returnArrayList;

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
		return statePath;
	}

	public int getID() {
		return id;
	}
	
	public static String[] getOnlyFilePaths(ArrayList<File> files, String removePath) {
		String[] returnArray = new String[files.size()];
		for (int i = 0; i < files.size(); i++) {
			returnArray[i] = files.get(i).getAbsolutePath().replace(removePath + "\\", "");
		}
		
		return returnArray;
	}
}
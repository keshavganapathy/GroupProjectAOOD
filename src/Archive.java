import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

//http://commons.apache.org/proper/commons-io/
//library for copying files
public class Archive {
	private String archivePath;
	private String dataPath;
	
	ArrayList<State> states; //states ordered from oldest to newest
	
	private boolean manualTrim;
	private int numberStatesKept; 
	private int loadingBarCounter;
	
	public Archive(String archivePath, String dataPath, boolean newArchive) {
		this.archivePath = archivePath;
		this.dataPath = dataPath;
		if (newArchive) {
			//create XML file
		}
	}
	
	public String browseFileExplorer() {
		return null; //returns path of selected directory
	}

	public void trimState(int selectedIndex) {
		//
		//TODO: change modification report for the more recent state
		//TODO: modify file system, deleting the folder for each state before
		//selected index
		File currentDirectory;
		for (int i = selectedIndex; i >= 0; i--) { //counting down is important
			currentDirectory = new File(states.get(i).getPath());
			currentDirectory.delete();
			states.remove(i);
		}
		//change the modification report of state of index 0.
	}
	
	public void restore(int selectedIndex) { //if restoring to the data location
		//TODO: prompt user with a pop up asking "are you sure"
		// ONLY FOR THIS METHOD, not the general destinationpath one
		restore(selectedIndex, dataPath);
	}
	
	public void restore(int selectedIndex, String destinationPath) {
		File source = new File(states.get(selectedIndex).getPath());
		File destination = new File(destinationPath);
		
		try {
			FileUtils.copyDirectory(source, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void backUp() {
		//creating a new backup here
		
	}
	public void changeDataLocation(String newPath) {
		dataPath = newPath;
	}
	
}

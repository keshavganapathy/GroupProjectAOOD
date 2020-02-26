import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

//http://commons.apache.org/proper/commons-io/
//library for copying files
public class Archive {
	private String archivePath;
	private String dataPath;
	private String metadataPath;
	private String statesFolderPath;

	ArrayList<State> states; //states ordered from oldest to newest
	
	private boolean manualTrim;
	private int numberStatesKept;  //TODO: remember to change this in the other class this will be changed in
	private int loadingBarCounter;

	private int stateNumber = 3; //TODO: temporary. this is the number of the latest state.

	public Archive(String archivePath, String dataPath, boolean newArchive) {
		this.archivePath = archivePath;
		this.dataPath = dataPath;
		metadataPath = archivePath + "/metadata";
		statesFolderPath = metadataPath + "/states";
		
		states = new ArrayList<State>();
		
		File statesFolder = new File(statesFolderPath);

		if (newArchive) {
			File metadataFolder = new File(metadataPath);
			metadataFolder.mkdir();
			
			statesFolder.mkdir();
			//create XML file
		} else {
			//creating the list of states
			String[] statePaths = statesFolder.list();
			for (int i = 0; i < statePaths.length; i++) {
				statePaths[i] = statesFolderPath + "/" + statePaths[i];
				String[] splitName = statePaths[i].split("_");
				// the id will be the second of the two indices
				int id = Integer.parseInt(splitName[1]); 
				states.add(new State(statePaths[i], id));
			}
			
			
			for (State state: states) {
				System.out.println(state.getPath());
				System.out.println(state.getID());
			}
		}
	}
	
	public String browseFileExplorer() {
		return null; //returns path of selected directory
	}

	public void trimState(int selectedIndex) { //TODO: test trimState
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
		//TODO: progress bar
		//first, creating a new directory
		//path of the new state file will be in the metadata folder

		String newStatePath = statesFolderPath + "/state_" + stateNumber;
		File newStateDirectory = new File(newStatePath);
		if (!newStateDirectory.exists()) {
			newStateDirectory.mkdir();
		}

		//creating the signal file in the metadata folder
		String signalFilePath = metadataPath + "SIGNAL_FILE_206214"; //this name gotta stay constant
		File signalFile = new File(signalFilePath);
		try {
			signalFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		//copying data from datalocation into state folder
		File data = new File(dataPath);
		try {
			FileUtils.copyDirectory(data, newStateDirectory);
		} catch (IOException e) {
			e.printStackTrace();
		}
		//TODO: change the XML file. (state info, and increment the latest state number by 1)
		signalFile.delete();

		states.add(new State(newStatePath, stateNumber));
		stateNumber++;
	}
	public void changeDataLocation(String newPath) {
		dataPath = newPath;
	}

	public String getDataPath() {
		return dataPath;
	}

	public String getArchivePath() {
		return archivePath;
	}

	public void setNumberStatesKept(int num) {
		numberStatesKept = num;
	}

	
}
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

//http://commons.apache.org/proper/commons-io/
//library for copying files
public class Archive {
	private String archivePath;
	private String archiveDataPath;
	private String dataPath;
	private String metadataPath;
	private String statesFolderPath;
	private String name;

	ArrayList<State> states; // states ordered from oldest to newest

	private boolean manualTrim;
	private int numberStatesKept; // TODO: remember to change this in the other class this will be changed in
	private int loadingBarCounter;

	private int stateNumber; // TODO: temporary. this is the number of the latest state.

	public Archive(String archivePath, String dataPath, String name, boolean newArchive) {
		this.name = name;
		this.archivePath = archivePath;
		this.dataPath = dataPath;
		File archive = new File(archivePath);
		archiveDataPath = archivePath + "\\" + archive.getName();
		metadataPath = archivePath + "\\metadata";
		statesFolderPath = metadataPath + "\\states";

		states = new ArrayList<State>();

		File statesFolder = new File(statesFolderPath);

		if (newArchive) {
			archive.mkdir();
			File metadataFolder = new File(metadataPath);
			metadataFolder.mkdir();
			File archiveDataPathFolder = new File(archiveDataPath);
			archiveDataPathFolder.mkdir();

			statesFolder.mkdir();

			File propertiesFile = new File(metadataPath + "\\Properties.xml");
			try {
				propertiesFile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
			stateNumber = 0;
			numberStatesKept = 1;

			updateXML();
			// create XML file
		} else {
			// creating the list of states
			String[] statePaths = statesFolder.list();
			readXML();
			for (int i = 0; i < statePaths.length; i++) {
				statePaths[i] = statesFolderPath + "\\" + statePaths[i];
				String[] splitName = statePaths[i].split("_");
				// the id will be the second of the two indices
				int id = Integer.parseInt(splitName[1]);
				states.add(new State(statePaths[i], id, dataPath));
			}
			File archiveData = new File(archiveDataPath);
			String[] latestState = archiveData.list();
			if (latestState.length > 0) {
				latestState[0] = archiveDataPath + "\\" + latestState[0];
				String[] splitName = latestState[0].split("_");
				// the id will be the second of the two indices
				int id = Integer.parseInt(splitName[1]);
				states.add(new State(latestState[0], id, dataPath));
			}

			readXML();
			/*
			 * for (State state: states) { System.out.println(state.getPath());
			 * System.out.println(state.getID()); }
			 */
		}
		/*
		 * System.out.println("stateNumber: " + stateNumber);
		 * System.out.println("numberStatesKept: " + numberStatesKept);
		 */
	}

	public String browseFileExplorer() {
		return null; // returns path of selected directory
	}

	public void trimState(int selectedIndex) { // TODO: test trimState
		//
		// TODO: change modification report for the more recent state
		// TODO: modify file system, deleting the folder for each state before
		// selected index
		for (State state : states) {
			// System.out.println(state.getPath());
			// System.out.println(state.getID());
		}

		File currentDirectory;
		for (int i = selectedIndex; i >= 0; i--) { // counting down is important
			currentDirectory = new File(states.get(i).getPath());
			try {
				FileUtils.deleteDirectory(currentDirectory);
			} catch (IOException e) {
				e.printStackTrace();
			}
			states.remove(i);
		}
		updateXML();
		// change the modification report of state of index 0.
	}

	public void restore(int selectedIndex) { // if restoring to the data location
		// TODO: prompt user with a pop up asking "are you sure"
		//// ONLY FOR THIS METHOD, not the general destinationpath one
		// System.out.println(" " + selectedIndex);
		restore(selectedIndex, dataPath);
	}

	public void restore(int selectedIndex, String destinationPath) {
		File source = new File(states.get(selectedIndex).getPath());
		File destination = new File(destinationPath);

		try {
			FileUtils.cleanDirectory(destination); // deleting all files in the target directory
			FileUtils.copyDirectory(source, destination); // replacing the target directory w/ source directory
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void backUpOld() { // TODO: backup needs to be more efficient apparently
		stateNumber++;
		updateXML();

		// creating a new backup here
		// TODO: progress bar
		// first, creating a new directory
		// path of the new state file will be in the metadata folder

		String newStatePath = statesFolderPath + "\\state_" + stateNumber;
		File newStateDirectory = new File(newStatePath);
		if (!newStateDirectory.exists()) {
			newStateDirectory.mkdir();
		}

		// creating the signal file in the metadata folder
		String signalFilePath = metadataPath + "SIGNAL_FILE_206214.txt"; // this name gotta stay constant
		File signalFile = new File(signalFilePath);
		try {
			signalFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// copying data from datalocation into state folder
		File data = new File(dataPath);
		try {
			FileUtils.copyDirectory(data, newStateDirectory);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// TODO: change the XML file. (state info, and increment the latest state number
		// by 1)
		signalFile.delete();

		states.add(new State(newStatePath, stateNumber, dataPath));
		if (states.size() > numberStatesKept) {
			int amountOfStatesTrimmed = states.size() - numberStatesKept;
			for (int i = 0; i < amountOfStatesTrimmed; i++) {
				trimState(0);
			}
		}

	}

	public void backUp() { // TODO: backup needs to be more efficient apparently
		stateNumber++;
		updateXML();

		// creating the signal file in the metadata folder
		String signalFilePath = metadataPath + "SIGNAL_FILE_206214.txt"; // this name gotta stay constant
		File signalFile = new File(signalFilePath);
		try {
			signalFile.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}

		// creating a new backup here
		// TODO: progress bar
		// first, creating a new directory
		// path of the new state file will be in the metadata folder
		// System.out.print(archiveDataPath);
		File archiveData = new File(archiveDataPath);
		File statesFolder = new File(statesFolderPath);
		try {
			FileUtils.copyDirectory(archiveData, statesFolder);
			FileUtils.cleanDirectory(archiveData);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (states.size() > 0)
			states.get(states.size() - 1).setPath(statesFolderPath + "\\state_" + (stateNumber - 1));

		String newStatePath = archiveDataPath + "\\state_" + stateNumber;
		File newStateDirectory = new File(newStatePath);
		if (!newStateDirectory.exists()) {
			newStateDirectory.mkdir();
		}

		// copying data from datalocation into state folder
		File data = new File(dataPath);
		try {
			FileUtils.copyDirectory(data, newStateDirectory);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// TODO: change the XML file. (state info, and increment the latest state number
		// by 1)
		signalFile.delete();

		states.add(new State(newStatePath, stateNumber, dataPath));
		if (states.size() > numberStatesKept) {
			int amountOfStatesTrimmed = states.size() - numberStatesKept;
			for (int i = 0; i < amountOfStatesTrimmed; i++) {
				trimState(0);
			}
		}

	}

	public String potentialTrim() {
		String potentialLostState = "";
		if (states.size() + 1 > numberStatesKept) {
			int amountOfStatesTrimmed = states.size() + 1 - numberStatesKept;
			for (int i = 0; i < amountOfStatesTrimmed; i++) {
				potentialLostState += "State " + states.get(i).getID();
				if (i < amountOfStatesTrimmed - 1) {
					potentialLostState += ", ";
				}
			}
		}
		return potentialLostState;
	}

	public void cleanUp(boolean replace) {
		File currentDirectory = new File(states.get(stateNumber).getPath());
		try {
			FileUtils.deleteDirectory(currentDirectory);
		} catch (IOException e) {
			e.printStackTrace();
		}

		states.remove(stateNumber);
		File signalFile = new File(metadataPath + "\\metadata\\SIGNAL_FILE_206214.txt");
		signalFile.delete();
		// TODO: REMOVE THE DIRECTORY PROBABLY?
		if (replace) {
			backUp();
		}
	}

	public void check() {
		File signalFile = new File(metadataPath + "\\SIGNAL_FILE_206214.txt");
		if (signalFile.exists()) {
			cleanUp(false); // TODO: this should be a popup that asks the user what
			// they would like to do and calls cleanup(boolean b)
			// accordingly
		}
	}

	public void changeDataLocation(String newPath) {
		dataPath = newPath;
		updateXML();
	}

	public String getDataPath() {
		return dataPath;
	}

	public String getArchivePath() {
		return archivePath;
	}

	// public void setNumberStatesKept(int num) {
	// numberStatesKept = num;
	// if (states.size() > numberStatesKept) {
	// trimState(numberStatesKept - states.size() - 1);
	// }
	// updateXML();
	// }

	private void readXML() {
		try {
			File fXmlFile = new File(archivePath + "\\metadata\\Properties.xml");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			NodeList nodes = doc.getElementsByTagName("information");
			for (int i = 0; i < nodes.getLength(); i++) {
				Node info = nodes.item(i);
				if (info.getNodeType() == Node.ELEMENT_NODE) {
					Element information = (Element) info;
					dataPath = information.getElementsByTagName("dataPath").item(0).getTextContent();
					stateNumber = Integer
							.parseInt(information.getElementsByTagName("stateNumber").item(0).getTextContent());
					numberStatesKept = Integer
							.parseInt(information.getElementsByTagName("numberStatesKept").item(0).getTextContent());

				}
			}

		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updateXML() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document xml = builder.newDocument();

			Element rootElement = xml.createElement("rootElement");
			xml.appendChild(rootElement);

			Element information = xml.createElement("information");
			rootElement.appendChild(information);

			Element dataPath = xml.createElement("dataPath");
			dataPath.appendChild(xml.createTextNode(this.dataPath));
			information.appendChild(dataPath);

			Element stateNumber = xml.createElement("stateNumber");
			stateNumber.appendChild(xml.createTextNode("" + this.stateNumber));
			information.appendChild(stateNumber);

			Element numberOfStatesKept = xml.createElement("numberStatesKept");
			numberOfStatesKept.appendChild(xml.createTextNode("" + this.numberStatesKept));
			information.appendChild(numberOfStatesKept);

			TransformerFactory create = TransformerFactory.newInstance();
			Transformer transform = create.newTransformer();
			DOMSource source = new DOMSource(xml);
			StreamResult result = new StreamResult(new File(archivePath + "\\metadata\\Properties.xml"));
			transform.transform(source, result);
		} catch (ParserConfigurationException pce) {
			pce.printStackTrace();
		} catch (TransformerException tfe) {
			tfe.printStackTrace();
		}

	}

	public int numberOfStates() {
		return stateNumber;
	}

	public ArrayList<String> returnDisplay() {
		ArrayList<String> list = new ArrayList<String>();
		for (int i = 0; i < states.size(); i++) {

			File file = new File(states.get(i).getPath());
			String date = "ERROR";
			BasicFileAttributes attrs;
			try {
				attrs = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
				FileTime time = attrs.creationTime();

				String pattern = "MM/dd/yyyy - HH:mm";
				SimpleDateFormat format = new SimpleDateFormat(pattern);

				date = format.format(new Date(time.toMillis()));

			} catch (IOException e) {
				e.printStackTrace();
			}
			list.add("State " + states.get(i).getID() + " - " + date);
		}
		return list;
	}

	public ArrayList<String> modificationReportDisplay(int stateIndex) {
		ArrayList<String> display = new ArrayList<String>();
		if (stateIndex > 0) {
			ArrayList<Change> changes = states.get(stateIndex).createModificationReport(states.get(stateIndex - 1).getPath());
			for (int i = 0; i < changes.size(); i++) {
				display.add(changes.get(i).display());
			}
			
		}
		if(display.size() == 0) {
			display.add("No Changes from Previous Version");
		}
		return display;
	}
	public String print(int stateIndex) {
		return states.get(stateIndex).print();
	}
	public boolean saveModification(int stateIndex, String path) {
		File Path = new File(path);
		if(!Path.exists())
			return false;
		states.get(stateIndex).save(path);
		return true;
	}
	public String getName() {
		return name;
	}

	public void changeTrim(int number) {
		numberStatesKept = number;
		updateXML();
	}

	public String trimAmnt() {
		return "" + numberStatesKept;
	}
}
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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

	ArrayList<State> states; //states ordered from oldest to newest
	
	private boolean manualTrim;
	private int numberStatesKept;  //TODO: remember to change this in the other class this will be changed in
	private int loadingBarCounter;

	private int stateNumber; //TODO: temporary. this is the number of the latest state.

	public Archive(String archivePath, String dataPath, boolean newArchive) {
		this.archivePath = archivePath;
		this.dataPath = dataPath;
		File archive = new File(archivePath);
		archiveDataPath = archivePath + "\\" + archive.getName();
		
		metadataPath = archivePath + "\\metadata";
		statesFolderPath = metadataPath + "\\states";
		
		states = new ArrayList<State>();
		
		File statesFolder = new File(statesFolderPath);

		if (newArchive) {
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
			//create XML file
		} else {
			//creating the list of states
			String[] statePaths = statesFolder.list();
			for (int i = 0; i < statePaths.length; i++) {
				statePaths[i] = statesFolderPath + "\\" + statePaths[i];
				String[] splitName = statePaths[i].split("_");
				// the id will be the second of the two indices
				int id = Integer.parseInt(splitName[1]); 
				states.add(new State(statePaths[i], id, dataPath));
			}
			
			readXML();
			/*for (State state: states) {
				System.out.println(state.getPath());
				System.out.println(state.getID());
			}*/
		}
		/*System.out.println("stateNumber: " + stateNumber);
		System.out.println("numberStatesKept: " + numberStatesKept);*/
	}
	
	public String browseFileExplorer() {
		return null; //returns path of selected directory
	}

	public void trimState(int selectedIndex) { //TODO: test trimState
		//
		//TODO: change modification report for the more recent state
		//TODO: modify file system, deleting the folder for each state before
		//selected index
		for (State state: states) {
			System.out.println(state.getPath());
			System.out.println(state.getID());
		}
		
		
		File currentDirectory;
		for (int i = selectedIndex; i >= 0; i--) { //counting down is important
			currentDirectory = new File(states.get(i).getPath());
			try {
				FileUtils.deleteDirectory(currentDirectory);
			} catch (IOException e) {
				e.printStackTrace();
			}
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
			FileUtils.cleanDirectory(destination); //deleting all files in the target directory
			FileUtils.copyDirectory(source, destination); //replacing the target directory w/ source directory
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void backUp() {  //TODO: backup needs to be more efficient apparently
		stateNumber++;
		updateXML();
		
		//creating a new backup here
		//TODO: progress bar
		//first, creating a new directory
		//path of the new state file will be in the metadata folder

		String newStatePath = statesFolderPath + "\\state_" + stateNumber;
		File newStateDirectory = new File(newStatePath);
		if (!newStateDirectory.exists()) {
			newStateDirectory.mkdir();
		}

		//creating the signal file in the metadata folder
		String signalFilePath = metadataPath + "SIGNAL_FILE_206214.txt"; //this name gotta stay constant
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

		states.add(new State(newStatePath, stateNumber, dataPath));
		
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
		//TODO: REMOVE THE DIRECTORY PROBABLY?
		if (replace) {
			backUp();
		}
	}
	
	public void check() {
		File signalFile = new File(metadataPath + "\\SIGNAL_FILE_206214.txt");
		if(signalFile.exists()) {
			cleanUp(false); //TODO: this should be a popup that asks the user what 
			//they would like to do and calls cleanup(boolean b) 
			//accordingly	
		}
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
		updateXML();
	}
	
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

	
}
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class State {
	private ArrayList<Change> modificationReport;
	private String statePath;
	private int id; // each state has a unique id, that will stay the same for its entire lifetime
					// ((name of state folder))
	private String dataPath;

	public State(String statePath, int id, String dataPath) {
		this.statePath = statePath;
		this.id = id;
		this.dataPath = dataPath;
	}
	// How the state is displayed to the user, state and date created

	public ArrayList<Change> createModificationReport(String previousStatePath) {
		modificationReport = new ArrayList<Change>();
		// assuming that the state before this one exists
		File currentState = new File(statePath);
		File previousState = new File(previousStatePath);

		ArrayList<File> currentFiles = getAllFiles(currentState);

		String[] currentStateFilePaths = getOnlyFilePaths(currentFiles, statePath);
		ArrayList<File> previousFiles = getAllFiles(previousState);
		String[] previousStateFilePaths = getOnlyFilePaths(previousFiles, previousStatePath);

		// new files
		for (int i = 0; i < currentStateFilePaths.length; i++) {
			String filePath = currentStateFilePaths[i];
			if (!arrayContains(previousStateFilePaths, filePath)) {
				modificationReport.add(new Change(statePath + "\\" + filePath, Change.Type.NEW));
			}
		}

		// edited files
		for (int i = 0; i < currentStateFilePaths.length; i++) {
			String filePath = currentStateFilePaths[i];
			if (arrayContains(previousStateFilePaths, filePath)) {
				File currentFile = new File(statePath + "\\" + filePath);
				File previousFile = new File(previousStatePath + "\\" + filePath);

				if (currentFile.lastModified() != previousFile.lastModified() && (!currentFile.isDirectory())) {
					modificationReport.add(new Change(statePath + "\\" + filePath, Change.Type.EDIT));
				}
			}
		}

		return modificationReport;
	}

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
		for (File file : returnArrayList) {
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

	public void save(String path) {
		try {

			File modification = new File(path + "\\State_" + id + "_ModificationReport.txt");
			modification.createNewFile();
			FileWriter writer = new FileWriter(modification);
			for (Change change : modificationReport) {
				writer.write(change.display());
				writer.write("\r\n"); // write new line
			}
			writer.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public String print() {
		String report = "Modification Report State " + id + "\r\n";
		if (!modificationReport.isEmpty()) {
			for (Change change : modificationReport)
				report += (change.display() + "\r\n");
		}
		return report;

	}

	public void setPath(String path) {
		statePath = path;
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

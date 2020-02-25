import java.util.ArrayList;

public class Archive {
	private String archivePath;
	private String dataPath;
	
	ArrayList<State> states;
	
	private boolean manualTrim;
	private int statesKept; 
	private int counterLoading;
	
	public Archive(String archivePath, String dataPath, boolean newArchive) {
		this.archivePath = archivePath;
		this.dataPath = dataPath;
		if (newArchive) {
			//create XML file
		}
	}
	
	public void changeDataLocation(String newPath) {
		
	}
}

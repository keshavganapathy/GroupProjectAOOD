import java.util.ArrayList;

public class Mainframe {
ArrayList<Archive> archives;
	public Mainframe() {
		archives = new ArrayList<Archive>();
	}
	public void createArchive(String archivePath, String dataPath) {
		archives.add(new Archive(archivePath, dataPath, true));
	}
	public void  openArchive(String archivePath, String dataPath) {
		archives.add(new Archive(archivePath, dataPath, false));
		archives.get(archives.size() - 1).check();
	}
	public Archive getArchive(int i) {
		return archives.get(i);
	}
	
}


public class test {
	public static void main(String[] args) {
		String archivePath = "C:\\Users\\ylee9251\\Desktop\\testingarchive";

		//String archivePath = "C:\\Users\\ylee9251\\Desktop\\anotherArchive";
		String dataPath = "C:\\Users\\ylee9251\\Desktop\\some_data_folder";
		//String dataPath = "C:\\Users\\ylee9251\\Desktop\\another_data_path";
		Archive archive = new Archive(archivePath, dataPath, true);
		archive.states.get(2).createModificationReport();
	}
}

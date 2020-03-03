
public class test {
	public static void main(String[] args) {
		//String archivePath = "C:\\Users\\ylee9251\\Desktop\\testingarchive";
		String archivePath = "C:\\Users\\ylee9251\\Desktop\\subfoldertestingarchive";

		//String archivePath = "C:\\Users\\ylee9251\\Desktop\\anotherArchive";
		String dataPath = "C:\\Users\\ylee9251\\Desktop\\second_data_folder";
		//String dataPath = "C:\\Users\\ylee9251\\Desktop\\another_data_path";
		Archive archive = new Archive(archivePath, dataPath, false);
		//archive.backUp();
		archive.states.get(3).createModificationReport();
	}
}

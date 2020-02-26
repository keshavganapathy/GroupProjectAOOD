
public class test {
	public static void main(String[] args) {
		String archivePath = "C:\\Users\\ylee9251\\Desktop\\testingarchive";
		//String archivePath = "C:\\Users\\ylee9251\\Desktop\\testingarchive";
		String dataPath = "C:\\Users\\ylee9251\\Desktop\\some_data_folder";
		Archive archive = new Archive(archivePath, dataPath, false);
		//archive.trimState(2);
	}
}

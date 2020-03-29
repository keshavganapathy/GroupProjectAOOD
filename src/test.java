import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class test {
	public static void main(String[] args) {
		//String archivePath = "C:\\Users\\ylee9251\\Desktop\\testingarchive";
		//String archivePath = "C:\\Users\\ylee9251\\Desktop\\subfoldertestingarchive";
		//String archivePath = "C:\\Users\\ylee9251\\Desktop\\iwanttomakeanewarchive";

		//String archivePath = "C:\\Users\\ylee9251\\Desktop\\anotherArchive";
		//String dataPath = "C:\\Users\\ylee9251\\Desktop\\some_data_folder";
		//String dataPath = "C:\\Users\\ylee9251\\Desktop\\iwanttomakeanewdatapath";

		//String dataPath = "C:\\Users\\ylee9251\\Desktop\\second_data_folder";
		//String dataPath = "C:\\Users\\ylee9251\\Desktop\\another_data_path";
		//Archive archive = new Archive(archivePath, dataPath, false);
		//archive.backUp();
		//archive.states.get(3).createModificationReport();
		//archive.backUp();
		
//		String archivePath = "yahoo\\hhehe\\name";
//		String name = "";
//		int nameIndex = archivePath.lastIndexOf("\\");
//		System.out.print(archivePath.substring(nameIndex + 1));
		
//		String path = "C:\\Users\\Home\\Desktop\\test1";
//		String destination = "C:\\Users\\Home\\Desktop\\test2";
//		File original = new File(path);
//		File destiny = new File(destination);
//		try {
//			FileUtils.copyDirectory(destiny, original);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
		File printer = new File("C:\\Users\\Home\\Desktop\\ModificationReport.txt");
		PrinterJob pj = PrinterJob.getPrinterJob();
		if (pj.printDialog()) {
			try {
				pj.(printer);
				Printable
				pj.print();
			} catch (PrinterException ex) {
				ex.printStackTrace();
			}
		}
		
	}
}

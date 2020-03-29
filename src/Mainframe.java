import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Mainframe {
	ArrayList<Archive> archives;

	public Mainframe() {
		archives = new ArrayList<Archive>();
	}
	
	public void createArchive(String dataPath, String archivePath, String name) {
		archives.add(new Archive(archivePath, dataPath, name, true));
	}
	public boolean openArchive(String archivePath, String dataPath) {
		File fXmlFile = new File(archivePath + "\\metadata\\Properties.xml");
		boolean exists = fXmlFile.exists();
		if (!exists) {
			return false;
		}
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(fXmlFile);
			NodeList nodes = doc.getElementsByTagName("information");
			for (int i = 0; i < nodes.getLength(); i++) {
				Node info = nodes.item(i);
				if (info.getNodeType() == Node.ELEMENT_NODE) {
					Element information = (Element) info;
					dataPath = information.getElementsByTagName("dataPath").item(0).getTextContent();
					

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
		String name = "";
		int nameIndex = archivePath.lastIndexOf("\\");
		name = archivePath.substring(nameIndex + 1);
		archives.add(new Archive(archivePath, dataPath, name, false));
		archives.get(archives.size() - 1).check();
		System.out.print(archivePath);
		return true;
	}
	public Archive getArchive(int i) {
		return archives.get(i);
	}
//	public boolean browseFileExplorer() {
//		boolean run = false;
//		JFileChooser jfc = new JFileChooser();
//		jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//		jfc.showOpenDialog(null);
//		if(jfc.getSelectedFile() != null)
//			run = openArchive(jfc.getSelectedFile().toString(), "");
//		return run;
//	}
	public ArrayList<Archive> getArchives() {return archives;} //TODO: returns list of archives
//	public static void main(String[]args) {
//		Archive pineapple = new Archive("C:\\Users\\mbenal0768\\Desktop\\archive", "C:\\Users\\mbenal0768\\Desktop\\yuwhan", false);
//	}

}

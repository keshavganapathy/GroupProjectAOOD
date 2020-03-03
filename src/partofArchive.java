import org.w3c.dom.*;
import org.xml.sax.SAXException;

import javax.xml.parsers.*;
import java.io.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.parsers.ParserConfigurationException;

public class partofArchive {
	String archivePath = "C:\\Users\\mbenal0768\\Desktop\\archive";
	String dataPath = "C:\\fakeDataPath";
	int stateNumber = 4;
	int numberStatesKept = 6;

	public partofArchive() {

		readXML();
		System.out.println(dataPath);
		System.out.println(stateNumber);
		System.out.println(numberStatesKept);
		//updateXML();

	}

	public static void main(String[] args) {
		partofArchive test = new partofArchive();

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

			// State DATA Creation after this
			// Element state = xml.createElement("state");
			// Attr attribute = xml.createAttribute("id");
			// attribute.setValue("1");
			// state.setAttributeNode(attribute);
			// rootElement.appendChild(state);
			// Element
			//
			// Element state2 = xml.createElement("state");
			// Attr attribute2 = xml.createAttribute("id");
			// attribute.setValue("2");
			// state.setAttributeNode(attribute);
			// rootElement.appendChild(state2);

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



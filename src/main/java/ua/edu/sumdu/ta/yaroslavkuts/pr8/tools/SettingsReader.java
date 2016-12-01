package ua.edu.sumdu.ta.yaroslavkuts.pr8.tools;

import ua.edu.sumdu.ta.yaroslavkuts.pr8.db.*;
import org.apache.log4j.Logger;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

public class SettingsReader {
	
	private final static Logger LOG = Logger.getLogger(SettingsReader.class);

	public static DBInfo getDBInfo(String dbms) {
		
		DBInfo info = null;
		try {
			File settings = new File("settings.xml");
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			Document doc = builder.parse(settings);
			
			NodeList nodeList = doc.getElementsByTagName("config");
			
			if (nodeList != null) LOG.info("Node 'config' exists and it's length = " + nodeList.getLength());

			for (int temp = 0; temp < nodeList.getLength(); temp++) {

				Node node = nodeList.item(temp);

				if (node.getNodeType() == Node.ELEMENT_NODE) {

					Element e = (Element) node;
					String name = e.getAttribute("dbms");
					LOG.info("dbms: " + name);
					
					if (name.equals(dbms)) {
						info = new DBInfo();
						info.setDriver(e.getElementsByTagName("driver").item(0).getTextContent());
						info.setUrl(e.getElementsByTagName("url").item(0).getTextContent());
						info.setUser(e.getElementsByTagName("user").item(0).getTextContent());
						info.setPassword(e.getElementsByTagName("password").item(0).getTextContent());
					} 
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}

}
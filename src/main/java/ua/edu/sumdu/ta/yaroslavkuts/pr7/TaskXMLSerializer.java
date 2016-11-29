package ua.edu.sumdu.ta.yaroslavkuts.pr7;

import org.apache.log4j.Logger;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;
import org.jdom2.input.SAXBuilder;
import org.jdom2.JDOMException;
import org.jdom2.Namespace;

/**
 * Util for saving and loading from xml file list of tasks.
 *
 * @version 1.0 28 Nov 2016
 * @author Yaroslav Kuts
 */
public class TaskXMLSerializer {

    private final static Logger LOG = Logger.getLogger(TaskXMLSerializer.class);

	/**
	 * Save task list in xml file. 
	 * @param object list that will be saved
	 * @param file absolute or relation url for xml file
	 * @throws IllegalArgumentException if object or file is null, or file is empty
	 */
	public static void save(AbstractTaskList object, String file) throws IllegalArgumentException {
		if (object == null || file == null || file.equals("")) {
			throw new IllegalArgumentException("One or both params are null or file string empty");
		}
		
		try {
			Element tasksElement = new Element("tasks");
			tasksElement.setAttribute(new Attribute("xs", "http://www.w3.org/2001/XMLSchema-instance"));
			tasksElement.setAttribute(new Attribute("noNamespaceSchemaLocation", "schema.xsd"));
			Document doc = new Document(tasksElement);

			for (Task task : object) {
				Element taskElement = new Element("task").setText(task.getTitle());
				taskElement.setAttribute(new Attribute("active", Boolean.toString(task.isActive())));
				taskElement.setAttribute(new Attribute("time", "" + task.getTime()));
				taskElement.setAttribute(new Attribute("start", "" + task.getStartTime()));
				taskElement.setAttribute(new Attribute("end", "" + task.getEndTime()));
				taskElement.setAttribute(new Attribute("repeat", "" + task.getRepeatInterval()));
				taskElement.setAttribute(new Attribute("repeated", Boolean.toString(task.isRepeated())));
				tasksElement.addContent(taskElement);
			}
			XMLOutputter out = new XMLOutputter();
			out.setFormat(Format.getPrettyFormat());
			out.output(doc, new FileWriter(file));
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
	}

	/**
	 * Extract task list from xml file. 
	 * @param file or relation url for xml file
	 * @return list of extracted tasks
	 * @throws IllegalArgumentException if file string is null or empty
	 */
	public static ArrayTaskList load(String file) throws IllegalArgumentException {
		if (file == null || file.equals("")) {
			throw new IllegalArgumentException("File string is null or empty");
		}
		
		ArrayTaskList list = writeInList(file, new ArrayTaskList());
		return list;
	}
	
	/**
	 * Extract task list from xml file. 
	 * @param file absolute or relation url for xml file
	 * @param list
	 * @return concrete format of list that contains extracted tasks
	 * @throws IllegalArgumentException if file string is null or empty
	 */
	public static <T extends AbstractTaskList> T load(String file, T list) throws IllegalArgumentException {
		if (list == null || file == null || file.equals("")) {
			throw new IllegalArgumentException("One or both params are null or file string empty");
		}
		
		list = writeInList(file, list);
		return list;
	}
	
	/**
	 * Parse xml file to list of tasks. 
	 * @param file absolute or relation url for xml file
	 * @param taskList
	 * @return concrete format of list that contains extracted tasks
	 */
	private static <T extends AbstractTaskList> T writeInList(String file, T taskList) {
		try {
			File inputFile = new File(file);
			SAXBuilder saxBuilder = new SAXBuilder();
			Document doc = saxBuilder.build(inputFile);
			Element tasksElement = doc.getRootElement();
			List<Element> elementList = tasksElement.getChildren("task");
			
			for (Element taskElement : elementList) {
				Task task = null;
				String title = taskElement.getText();
				boolean active = taskElement.getAttribute("active").getBooleanValue();
				int time = taskElement.getAttribute("time").getIntValue();
				int start = taskElement.getAttribute("start").getIntValue();
				int end = taskElement.getAttribute("end").getIntValue();
				int repeat = taskElement.getAttribute("repeat").getIntValue();
				boolean repeated = taskElement.getAttribute("repeated").getBooleanValue();
				if (repeated) {
					task = new Task(title, start, end, repeat);
				} else {
					task = new Task(title, time);
				}
				taskList.add(task);
			}
		} catch (IOException | JDOMException e) {
			LOG.error(e.getMessage());
		}
		return taskList;
	}
}

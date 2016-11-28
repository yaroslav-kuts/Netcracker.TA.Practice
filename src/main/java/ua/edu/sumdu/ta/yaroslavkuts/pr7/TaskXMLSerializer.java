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

/**
 * Created by Ярослав on 28.11.2016.
 */
public class TaskXMLSerializer {

    private final static Logger LOG = Logger.getLogger(TaskXMLSerializer.class);

	public static void save(AbstractTaskList object, String file) {
		try {
			Element tasksElement = new Element("tasks");
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

    public static AbstractTaskList load(String file) {
		
		ArrayTaskList taskList = new ArrayTaskList();
		
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

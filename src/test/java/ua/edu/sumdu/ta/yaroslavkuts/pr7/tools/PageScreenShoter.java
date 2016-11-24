package ua.edu.sumdu.ta.yaroslavkuts.pr7.tests.tools;

import org.apache.log4j.Logger;
import java.io.*;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;

public class PageScreenShoter {

	private final static Logger LOG = Logger.getLogger(PageScreenShoter.class);

	public static void takeScreenshot(WebDriver driver) {
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			String title = driver.getTitle();
			if (PathChecker.isCorrect(title)) {
				FileUtils.copyFile(screenshot, new File(driver.getTitle() + ".png"));
			} else {
				FileUtils.copyFile(screenshot, new File("screenshot.png"));
			}	
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
	}
}
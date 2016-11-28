package ua.edu.sumdu.ta.yaroslavkuts.pr7.tests.tools;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;

public class PageScreenShoter {

	private final static Logger LOG = Logger.getLogger(PageScreenShoter.class);

	public static void takeScreenshot(WebDriver driver, ReportFormer report, String testStepName) {
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			String title = driver.getTitle();
			if (ua.edu.sumdu.ta.yaroslavkuts.pr7.tests.tools.PathChecker.isCorrect(title)) {
				String fileName = "reports/" + driver.getTitle() + report.getNumOfSteps() + ".png";
				FileUtils.copyFile(screenshot, new File(fileName));
				report.buildStep(testStepName, driver.getTitle() + report.getNumOfSteps() + ".png");
			} else {
				FileUtils.copyFile(screenshot, new File("reports/screenshot" + report.getNumOfSteps() + ".png"));
			}	
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
	}
}
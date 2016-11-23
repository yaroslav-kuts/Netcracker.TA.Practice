package ua.edu.sumdu.ta.yaroslavkuts.pr7.tests;

import ua.edu.sumdu.ta.yaroslavkuts.pr7.*;
import java.util.*;
import java.io.*;
import org.junit.*;
import static org.junit.Assert.*;
import org.apache.log4j.Logger;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WebSiteTest {
	
	private final static Logger LOG = Logger.getLogger(WebSiteTest.class);
	
	@BeforeClass
	public static void setGeckoDriverProperty() {
		System.setProperty("webdriver.gecko.driver", "C:/Program Files/geckodriver/geckodriver.exe");
	}
	
	@Test
	public void testNetcrackerVacancies() {
		
		WebDriver driver = new FirefoxDriver();

		driver.get("http://www.google.com");
		
		WebElement element = driver.findElement(By.name("q"));
		
		element.sendKeys("NetCracker Su");
		
		WebElement promt = driver.findElement(By.className("sbdd_a"));
		
		List<WebElement> promtItems = promt.findElements(By.className("sbsb_c"));
		
		String target = "NetCracker Sumy";
		boolean flag = false;
		
		for (WebElement e : promtItems) {
			if (e.getText().equals(target)) {
				e.click();
				flag = true;
			}
		}
		
		if (!flag) {
			element.sendKeys("my");
			element.submit();
		}
		
		try {
			WebDriverWait waitForLink = new WebDriverWait(driver, 10);
			//WebElement link = waitForLink.until(ExpectedConditions.elementToBeClickable(By.linkText("Netcracker - Open Positions")));
			WebElement link = waitForLink.until(ExpectedConditions.elementToBeClickable(By.linkText("NetCracker. :: Суми")));
			link.click();
		} catch (TimeoutException e) {
			driver.get("http://www.netcracker.com/ukr/vacancies");
		}
		
		WebDriverWait waitForLocationCheckBox = new WebDriverWait(driver, 60);
		WebElement location = waitForLocationCheckBox.until(ExpectedConditions.presenceOfElementLocated(By.id("location_1752")));
		location.click();
		
		File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
		try {
			FileUtils.copyFile(screenshot, new File(driver.getTitle() + ".png"));
		} catch (IOException e) {
			LOG.error(e.getMessage());
		}
		
		List<WebElement> jobItems = driver.findElements(By.className("result"));
		
		HashMap<String, String> vacancies = new HashMap<String, String>();
		
		for (WebElement job : jobItems) {
			vacancies.put(job.getAttribute("data-searchable"), job.getAttribute("data-category"));
		}
		
		driver.quit();
		
		assertEquals(18, vacancies.size());
		assertTrue(vacancies.containsKey("junior ta engineer\n\tsumy, ukraine\n\t"));
		assertTrue(vacancies.containsKey("senior technical solution support engineer\n\tsumy, ukraine\n\t"));
		assertTrue(vacancies.containsKey("technical support manager\n\tsumy, ukraine\n\t"));
		assertTrue(vacancies.containsKey("technical writer\n\tsumy, ukraine\n\t"));
		assertTrue(vacancies.containsKey("ta engineer\n\tsumy, ukraine\n\t"));
		assertTrue(vacancies.containsKey("customer support analyst (business trips to canada)\n\tsumy, ukraine\n\t"));
		assertTrue(vacancies.containsKey("assistant of trainings sector (with fluent english)\n\tsumy, ukraine\n\t"));
		assertTrue(vacancies.containsKey("qa engineer\n\tsumy, ukraine\n\t"));
		assertTrue(vacancies.containsKey("senior qa engineer\n\tsumy, ukraine\n\t"));
		assertTrue(vacancies.containsKey("senior ta engineer\n\tsumy, ukraine\n\t"));
		assertTrue(vacancies.containsKey("wiki support specialist\n\tsumy, ukraine\n\t"));
		assertTrue(vacancies.containsKey("junior qa engineer\n\tsumy, ukraine\n\t"));
		assertTrue(vacancies.containsKey("it help desk engineer\n\tsumy, ukraine\n\t"));
		assertTrue(vacancies.containsKey("control inspector\n\tsumy, ukraine\n\t"));
		assertTrue(vacancies.containsKey("release manager\n\tsumy, ukraine\n\t"));
		assertTrue(vacancies.containsKey("technical solution support engineer\n\tsumy, ukraine\n\t"));
		assertTrue(vacancies.containsKey("customer support analyst\n\tsumy, ukraine\n\t"));
		assertTrue(vacancies.containsKey("osg specialist (operations support group)\n\tsumy, ukraine\n\t"));
	}
}
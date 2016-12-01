package ua.edu.sumdu.ta.yaroslavkuts.pr8.tests;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import ua.edu.sumdu.ta.yaroslavkuts.pr8.tests.pages.GoogleSearchPage;
import ua.edu.sumdu.ta.yaroslavkuts.pr8.tests.pages.NetCrackerVacanciesPage;
import ua.edu.sumdu.ta.yaroslavkuts.pr8.tests.pages.Offices;
import ua.edu.sumdu.ta.yaroslavkuts.pr8.tests.tools.PageScreenShoter;
import ua.edu.sumdu.ta.yaroslavkuts.pr8.tests.tools.ReportFormer;

import java.util.HashMap;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class WebSiteTest {

	private static HashMap<String, String> vacancies;

	@BeforeClass
	public static void setGeckoDriverProperty() {
		System.setProperty("webdriver.gecko.driver", "C:/Program Files/geckodriver/geckodriver.exe");
	}
	
	@Test
	public void findAndExtractVacancies() {
		WebDriver driver = new FirefoxDriver();
        ReportFormer report = new ReportFormer("vacanciesTest");
		driver.get("http://www.google.com");
        PageScreenShoter.takeScreenshot(driver, report, "Step1: google.com");
		GoogleSearchPage googlePage = new GoogleSearchPage(driver);
		googlePage.typeSearchingString("NetCracker Su");
        PageScreenShoter.takeScreenshot(driver, report, "Step2: search 'NetCracker Su'");
		
		WebElement item = googlePage.getPromtItem("NetCracker Sumy");
		if (item != null) {
			item.click();
		} else {
			googlePage.typeSearchingString("my");
		}
		PageScreenShoter.takeScreenshot(driver, report, "Step3: search results");
		
		WebElement link = googlePage.getResultLink("NetCracker. :: Суми");
		if (link != null) {
			link.click();
		} else {
			driver.get("https://www.netcracker.com/careers/open-positions/?region=Russia+%2F+Ukraine+%2F+Belarus");
		}
		
		NetCrackerVacanciesPage netCrackerVacanciesPage = new NetCrackerVacanciesPage(driver);
		PageScreenShoter.takeScreenshot(driver, report, "Step4: netcracker vacancies");

		netCrackerVacanciesPage.selectOfficeCheckBox(Offices.SUMY);
		PageScreenShoter.takeScreenshot(driver, report, "Step5: netcracker sumy vacancies");
		vacancies = netCrackerVacanciesPage.getVacancies();

		report.close();
		netCrackerVacanciesPage.exit();
	}
	
	@Test
	public void testCorrectnessOfVacancies() {
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
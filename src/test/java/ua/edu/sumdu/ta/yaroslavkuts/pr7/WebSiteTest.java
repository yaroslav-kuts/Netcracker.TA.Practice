package ua.edu.sumdu.ta.yaroslavkuts.pr7.tests;

import ua.edu.sumdu.ta.yaroslavkuts.pr7.tests.pages.NetCrackerVacanciesPage;
import ua.edu.sumdu.ta.yaroslavkuts.pr7.tests.pages.GoogleSearchPage;
import ua.edu.sumdu.ta.yaroslavkuts.pr7.tests.pages.Offices;
import ua.edu.sumdu.ta.yaroslavkuts.pr7.tests.tools.PageScreenShoter;
import java.util.*;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.junit.runners.MethodSorters;
import ua.edu.sumdu.ta.yaroslavkuts.pr7.tests.tools.PropertiesReader;

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
		driver.get("http://www.google.com");
		GoogleSearchPage googlePage = new GoogleSearchPage(driver);
		googlePage.typeSearchingString("NetCracker Su");
		
		WebElement item = googlePage.getPromtItem("NetCracker Sumy");
		if (item != null) {
			item.click();
		} else {
			googlePage.typeSearchingString("my");
		}
		
		WebElement link = googlePage.getResultLink("NetCracker. :: Суми");
		if (link != null) {
			link.click();
		} else {
			driver.get("https://www.netcracker.com/careers/open-positions/?region=Russia+%2F+Ukraine+%2F+Belarus");
		}
		
		NetCrackerVacanciesPage netCrackerVacanciesPage = new NetCrackerVacanciesPage(driver);

		netCrackerVacanciesPage.selectOfficeCheckBox(Offices.SUMY);
		PageScreenShoter.takeScreenshot(driver);
		vacancies = netCrackerVacanciesPage.getVacancies();
		
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
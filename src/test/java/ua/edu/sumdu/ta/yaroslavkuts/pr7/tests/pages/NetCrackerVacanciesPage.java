package ua.edu.sumdu.ta.yaroslavkuts.pr7.tests.pages;

import java.util.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Created by Ярослав on 24.11.2016.
 */
public class NetCrackerVacanciesPage extends Page {

    private static By jobItemsLocator = By.className("result");

    public NetCrackerVacanciesPage(WebDriver driver) {
        super(driver);
        if (driver == null) {
            throw new NullPointerException("Driver cannot be null");
        }
		if (!driver.getCurrentUrl().contains("www.netcracker.com")) {
			throw new IllegalArgumentException("Its not a netcracker page");
		}
    }

    public void selectOfficeCheckBox(Offices office) throws NullPointerException {
        if (office != null) {
            WebDriverWait waitForLocationCheckBox = new WebDriverWait(driver, 60);
            WebElement location = waitForLocationCheckBox.until(ExpectedConditions.presenceOfElementLocated(office.getCheckBoxLocator()));
            location.click();
        } else {
            throw new NullPointerException("Office cannot be null");
        }
    }

    public HashMap<String, String> getVacancies() {
        List<WebElement> jobItems = driver.findElements(jobItemsLocator);
        HashMap<String, String> vacancies = new HashMap<>();

        for (WebElement job : jobItems) {
            vacancies.put(job.getAttribute("data-searchable"), job.getAttribute("data-category"));
        }

        return vacancies;
    }
}

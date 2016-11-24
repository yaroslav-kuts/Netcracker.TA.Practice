package ua.edu.sumdu.ta.yaroslavkuts.pr7.tests.pages;

import java.util.*;
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

public class GoogleSearchPage extends Page {
	
	private By searchFormLocator = By.name("q");
	private By promtLocator = By.className("sbsb_b");
	private By promtItemsLocator = By.className("sbqs_c");
	
	public GoogleSearchPage(WebDriver driver) {
		super(driver);
		if (driver == null && !driver.getCurrentUrl().contains("http://www.google.com")) {
			throw new IllegalArgumentException("Its not a google search page");
		} 
	}
	
	public GoogleSearchPage typeSearchingString(String str) throws NullPointerException {
		if (str != null) {
			WebElement searchForm = driver.findElement(searchFormLocator);
			searchForm.sendKeys(str);
			return this;
		} else {
			throw new NullPointerException("Searching string cannot be null");
		}
	}
	
	public WebElement getPromtItem(String str) throws IllegalArgumentException {
		if (str == null || str.equals("")) {
			throw new IllegalArgumentException("Promt string cannot be empty or null");
		}
		
		WebElement item = null;
		WebDriverWait waitPromtPopup = new WebDriverWait(driver, 10);
		WebElement promt = waitPromtPopup.until(ExpectedConditions.presenceOfElementLocated(promtLocator));
		List<WebElement> promtItems = promt.findElements(promtItemsLocator);
		
		for (WebElement e : promtItems) {
			String text = e.getText();
			if (text.equalsIgnoreCase(str)) {
				item = e;
				break;
			}
		}
		return item;
	}
	
	/*public GoogleSearchPage selectPromtItem(WebElement promtItem) {
		promtItem.click();
		return this;
	}*/
	
	public WebElement getResultLink(String linkText) throws IllegalArgumentException {
		if (linkText == null || linkText.equals("")) {
			throw new IllegalArgumentException("Link text cannot be empty or null");
		}
		
		WebElement link = null;
		try {
			WebDriverWait waitForLink = new WebDriverWait(driver, 10);
			link = waitForLink.until(ExpectedConditions.elementToBeClickable(By.linkText(linkText)));
		} catch (TimeoutException e) {
			return link;
		}
		return link;
	}
	
	/*public Page goByLink(WebElement link) {
		String url = link.getAttribute("href");
		driver.get(url);
		return new Page(driver);
	}*/
}

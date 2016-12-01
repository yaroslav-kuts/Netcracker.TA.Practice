package ua.edu.sumdu.ta.yaroslavkuts.pr8.tests.pages;

import org.openqa.selenium.WebDriver;

public class Page {

	final WebDriver driver;
	
	public Page(WebDriver driver) {
		this.driver = driver;
	}
	
	public void exit() {
		driver.quit();
	}
}
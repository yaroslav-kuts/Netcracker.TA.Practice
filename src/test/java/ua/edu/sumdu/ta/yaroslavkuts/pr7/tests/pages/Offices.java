package ua.edu.sumdu.ta.yaroslavkuts.pr7.tests.pages;

import org.openqa.selenium.By;
import ua.edu.sumdu.ta.yaroslavkuts.pr7.tests.tools.PropertiesReader;

/**
 * Created by Ярослав on 24.11.2016.
 */
public enum Offices {
    SUMY, ODESSA, KYIV;

    private By checkBoxLocator;

    Offices() {
		String id = PropertiesReader.getLocator(this);
        this.checkBoxLocator = (By.id(id));
    }

    public By getCheckBoxLocator() {
        return checkBoxLocator;
    }
}

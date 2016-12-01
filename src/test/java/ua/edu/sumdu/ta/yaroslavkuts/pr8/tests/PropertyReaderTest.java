package ua.edu.sumdu.ta.yaroslavkuts.pr8.tests;

import ua.edu.sumdu.ta.yaroslavkuts.pr8.tests.tools.PropertiesReader;
import ua.edu.sumdu.ta.yaroslavkuts.pr8.tests.pages.Offices;
import ua.edu.sumdu.ta.yaroslavkuts.pr8.*;
import org.junit.*;
import org.junit.rules.*;
import static org.junit.Assert.*;

public class PropertyReaderTest {
	
	@Test
	public void getLocatorTest() {
		String id = PropertiesReader.getLocator(Offices.SUMY);
		assertEquals(id, "location_1752");
	}
}
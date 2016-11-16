package ua.edu.sumdu.ta.yaroslavkuts.pr6.tests;

import ua.edu.sumdu.ta.yaroslavkuts.pr6.*;
import ua.edu.sumdu.ta.yaroslavkuts.pr6.db.*;
import ua.edu.sumdu.ta.yaroslavkuts.pr6.tools.*;
import org.junit.*;
import static org.junit.Assert.*;

public class SettingsReaderTest {

	@Test
	public void getDBInfoTest() {
		DBInfo info1 = SettingsReader.getDBInfo("oracle");
		DBInfo info2 = SettingsReader.getDBInfo("mysql");
		
		assertNotNull(info1);
		assertNotNull(info2);
	}
	
	@Test
	public void testEnumDataBases() {
		DBInfo info = DataBases.ORACLE.getDBInfo();
		
		assertEquals("oracle.jdbc.driver.OracleDriver", info.getDriver());
		assertEquals("jdbc:oracle:thin:@localhost:1521:xe", info.getUrl());
		assertEquals("system", info.getUser());
		assertEquals("2888", info.getPassword());
	}
}
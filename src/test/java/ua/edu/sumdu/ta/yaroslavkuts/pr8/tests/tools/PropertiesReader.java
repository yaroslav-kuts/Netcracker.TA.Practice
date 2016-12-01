package ua.edu.sumdu.ta.yaroslavkuts.pr8.tests.tools;

import ua.edu.sumdu.ta.yaroslavkuts.pr8.tests.pages.Offices;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class PropertiesReader {
	
	public static String getLocator(Offices office) {
		BufferedReader br = null;
		try {
			String line = null;
			br = new BufferedReader(new FileReader("src/test/resources/offices.properties"));

			while ((line = br.readLine()) != null) {
				String[] pair = line.split("=");
				if ((office.name()).equals(pair[0])) {
					return pair[1];
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
}
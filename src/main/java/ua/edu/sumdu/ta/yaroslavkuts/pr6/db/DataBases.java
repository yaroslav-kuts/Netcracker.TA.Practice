package ua.edu.sumdu.ta.yaroslavkuts.pr6.db;

import ua.edu.sumdu.ta.yaroslavkuts.pr6.tools.SettingsReader;

public enum DataBases {
	
	ORACLE("oracle"), MYSQL("mysql");
	
	private DBInfo info;
	
	DataBases(String dbms) {
			info = SettingsReader.getDBInfo(dbms);
	}
	
	public DBInfo getDBInfo() {
		return info;
	}
}
package ua.edu.sumdu.ta.yaroslavkuts.pr8.db;

import ua.edu.sumdu.ta.yaroslavkuts.pr8.tools.SettingsReader;

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
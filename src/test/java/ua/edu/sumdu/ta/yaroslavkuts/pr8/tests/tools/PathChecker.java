package ua.edu.sumdu.ta.yaroslavkuts.pr8.tests.tools;

import java.nio.file.Paths;
import java.nio.file.InvalidPathException;

public class PathChecker {
	
	public static boolean isCorrect(String path) {
		if (path == null && path.equals("")) throw new IllegalArgumentException("path cannot be null or empty");
		try {
			Paths.get(path);
        }catch (InvalidPathException e) {
			return false;
		}
		return true;
	}
}
package io.baardl.jwt.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


public class FileUtils {

	public String readFileAsString(String fileName) {
		String result = "";

		ClassLoader classLoader = getClass().getClassLoader();
		try {
			result = new String(Files.readAllBytes(Paths.get(fileName)));
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}
}

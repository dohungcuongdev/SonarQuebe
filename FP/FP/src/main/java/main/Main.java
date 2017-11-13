package main;

import java.io.File;
import java.io.FileReader;
import java.util.Properties;

public class Main {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		Properties properties = new Properties();
		try {
			properties.load(new FileReader(new File("api.properties")));
			String link = properties.getProperty("api.project");
			System.out.println(link);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}

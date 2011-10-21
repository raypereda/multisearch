package multisearch;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * This class provides a static method for reading a text file in one fell swoop.
 */
public class TextReader {

	/**
	 * @param filename the name of a file that is in UTF-8 format
	 * @return the content of the file as text
	 */
	public static String readWholeFile(String filename) {
		FileInputStream fis;
		try {
			fis = new FileInputStream(filename);
		} catch (FileNotFoundException e) {
			throw new RuntimeException("File not found: " + filename);
		}
		
		InputStreamReader in;
		try {
			in = new InputStreamReader(fis, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new RuntimeException("Unsupported file encoding for file: " + filename +
					" encoding error: " + e.getMessage());
		}
		String text = new Scanner(in).useDelimiter("\\Z").next();
		return text;
	}

	public static String[] readLines(String filename) {
		String text = readWholeFile(filename);
		String NL = System.getProperty("line.separator");
		String[] lines = text.split(NL);
		return lines;
	}
	
	
}

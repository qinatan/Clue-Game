package clueGame;

import java.io.FileWriter;
import java.io.IOException;


@SuppressWarnings("serial")
public class BadConfigFormatException extends Exception {
	public BadConfigFormatException (String errorMessage) {
		super(errorMessage);
		FileWriter myWriter;
		try {
			myWriter = new FileWriter("log.txt", true);
			myWriter.write(errorMessage + "\n");
			myWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}

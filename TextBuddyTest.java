import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Test;

public class TextBuddyTest {

	@Test
	public void testCheckIfFileExistsAndImportIfExists() throws FileNotFoundException, IOException {
		File myFile = new File("testfile.txt");
		TextBuddy.checkIfFileExistsAndImportIfExists(myFile);
		assertTrue(isFileExists(myFile));
	}

	private boolean isFileExists(File myFile) {
		if (myFile.exists()) {
			return true;
		}
		return false;
	}
}

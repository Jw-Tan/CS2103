import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TextBuddyTest {
	
	@Before
	public void setupFileName() {
		TextBuddy.setFileName("testfile.txt");
	}

	@Test
	public void testCheckIfFileExistsAndImportIfExists() throws FileNotFoundException, IOException {
		// checks whether the method creates the file
		File myFile = new File("testfile.txt");
		TextBuddy.checkIfFileExistsAndImportIfExists(myFile);
		assertTrue(myFile.exists());
	}

	@Test
	public void testTryToAdd() {
		// creates a dummy arraylist, a valid input command and an invalid input command
		ArrayList<String> testArrayList = new ArrayList<String>();
		String inputCommand1 = "add this is a test line";
		String inputCommand2 = "add ";
		
		
		// uses the add function and checks for correct feedback message, and that arraylist now has size of 1
		assertEquals("added to testfile.txt: \"this is a test line\"", TextBuddy.tryToAdd(testArrayList, inputCommand1));
		assertEquals(1, testArrayList.size());

		// uses add function with invalid input command and checks for correct feedback message and that no new line is added
		assertEquals("Please include desired text in same line with \"add\"", TextBuddy.tryToAdd(testArrayList, inputCommand2));
		assertNotEquals(2, testArrayList.size());
	}
}

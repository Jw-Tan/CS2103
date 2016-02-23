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
		String testFileName = "testfile.txt";
		TextBuddy.setFileName(testFileName);
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
		
		
		// uses add function and checks for correct feedback message, and that the line is correctly added
		assertEquals("added to testfile.txt: \"this is a test line\"", TextBuddy.tryToAdd(testArrayList, inputCommand1));
		assertEquals(1, testArrayList.size());
		assertEquals("this is a test line", testArrayList.get(0));

		// uses add function with invalid input command and checks for correct feedback message and that no new line is added
		assertEquals("Please include desired text in same line with \"add\"", TextBuddy.tryToAdd(testArrayList, inputCommand2));
		assertNotEquals(2, testArrayList.size());
	}

	@Test
	public void testTryToDelete() {
		// creates a dummy arraylist and adds a line to it
		ArrayList<String> testArrayList = new ArrayList<String>();
		testArrayList.add("this is a test line");
		
		// uses delete function with invalid input (no number) and checks for correct feedback and that no line is deleted
		String inputCommand1 = "delete ";
		assertEquals("Please include desired line number in same line with \"delete\"", TextBuddy.tryToDelete(testArrayList, inputCommand1));
		assertFalse(testArrayList.isEmpty());
		
		// uses delete function with invalid input (non-numeric) and checks for correct feedback and that no line is deleted
		String inputCommand2 = "delete abc";
		assertEquals("Invalid line number entered. Please try again", TextBuddy.tryToDelete(testArrayList, inputCommand2));
		assertFalse(testArrayList.isEmpty());
		
		// uses delete function with valid input and checks for correct feedback and that the line has been deleted
		String inputCommand3 = "delete 1";
		assertEquals("deleted from testfile.txt: \"this is a test line\"", TextBuddy.tryToDelete(testArrayList, inputCommand3));
		assertTrue(testArrayList.isEmpty());
	}
}

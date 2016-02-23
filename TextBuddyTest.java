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
		// creates a test arraylist, a valid input command and an invalid input command
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
		// creates a test arraylist and adds 2 lines to it
		ArrayList<String> testArrayList = new ArrayList<String>();
		testArrayList.add("this is test line 1");
		testArrayList.add("this is test line 2");
		
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
		assertEquals("deleted from testfile.txt: \"this is test line 1\"", TextBuddy.tryToDelete(testArrayList, inputCommand3));
		assertEquals(1, testArrayList.size());
		
		// checks that check that the correct line was deleted
		assertEquals("this is test line 2", testArrayList.get(0));
	}

	@Test
	public void testClear() {
		// creates a test arraylist and adds a line to it 
		ArrayList<String> testArrayList = new ArrayList<String>();
		testArrayList.add("this is a test line");
		
		// uses the clear function and check for correct feedback and that the arraylist has been emptied 
		assertEquals("all content deleted from testfile.txt", TextBuddy.clear(testArrayList));
		assertTrue(testArrayList.isEmpty());
	}

	@Test
	public void testPrintAllLines() {
		// creates a test arraylist 
		ArrayList<String> testArrayList = new ArrayList<String>();
		
		// uses the display function on empty arraylist and check for correct feedback
		assertEquals("testfile.txt is empty", TextBuddy.printAllLines(testArrayList));
		
		// adds 2 lines to the arraylist and checks the display feedback is correct
		testArrayList.add("this is test line 1");
		testArrayList.add("this is test line 2");
		assertEquals("1. this is test line 1\n2. this is test line 2", TextBuddy.printAllLines(testArrayList));
	}
	
	@Test
	public void testSortAllLines() {
		// creates a test arraylist and adds 2 lines to it
		ArrayList<String> testArrayList = new ArrayList<String>();
		testArrayList.add("Eve");
		testArrayList.add("Adam");
		
		// checks the arraylist before and after using the sort function, as well as for correct feedback
		assertEquals("Eve", testArrayList.get(0));
		assertEquals("All lines have been sorted alphabetically", TextBuddy.sortAllLines(testArrayList));
		assertEquals("Adam", testArrayList.get(0));
	}

	@Test
	public void testTryToSearch() {
		// creates a test arraylist and add a line to it
		ArrayList<String> testArrayList = new ArrayList<String>();
		testArrayList.add("this is a test line");
		
		//uses the search function with various command inputs and check for correct feedback
		assertEquals("Please include search keyword", TextBuddy.tryToSearch(testArrayList, "search "));
		assertEquals("There is no line containing the keyword cat", TextBuddy.tryToSearch(testArrayList, "search cat"));
		assertEquals("1. this is a test line", TextBuddy.tryToSearch(testArrayList, "search test"));
	}
}

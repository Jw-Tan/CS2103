import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This program allows the user to create a file with the given file name
 * and subsequently add, delete, or show the text contained in the file.
 * It is compatible with an existing filename, and the originally present
 * text in the file will not be lost unless the user chooses to delete any
 * or all line(s) via this program. The file is saved after each command.
 * 
 * Available commands:
 * 1) add <text> 		   - Add the input text into the file.
 * 2) delete <line number> - Deletes the specified line from the file.
 * 3) display              - Shows all text, if any, in the file line by line, with line numbers.
 * 4) clear                - Deletes all text within the file. 
 * 5) exit                 - Terminates the program.
 * 
 * Assumptions:
 * 1) Lower case for each command is used (as shown above).
 * 2) Correct command format is used (as shown above). Otherwise, feedback will be provided.
 * 
 * @author Tan Jie Wei
 *
 */

public class TextBuddy {

	private static ArrayList<String> currentStrings;
	private static Scanner sc;
	private static String inputCommand, fileName;
	private static final int INDEX_OF_FILENAME = 0;
	private static final String MESSAGE_WELCOME = "Welcome to TextBuddy. %1$s is ready for use";
	private static final String MESSAGE_ALL_CLEARED = "all content deleted from %1$s";
	private static final String MESSAGE_EMPTY_FILE = "%1$s is empty";
	private static final String MESSAGE_LINE_ADDED = "added to %1$s: \"%2$s\"";
	private static final String MESSAGE_NO_TEXT = "Please include desired text in same line with \"add\"";
	private static final String MESSAGE_LINE_DELETED = "deleted from %1$s: \"%2$s\"";
	private static final String MESSAGE_NO_LINE_NUMBER = "Please include desired line number in same line with \"delete\"";
	private static final String MESSAGE_INVALID_LINE_NUMBER = "Invalid line number entered. Please try again";
	private static final String MESSAGE_INVALID_COMMAND = "Invalid command. Please try again";	

	/**
	 * Main method of the program.
	 * 
	 * @param args Contains the filename
	 */
	public static void main(String[] args) throws IOException {

		currentStrings = new ArrayList<String>();

		fileName = args[INDEX_OF_FILENAME];

		File myFile = new File(fileName);

		checkIfFileExistsAndImportIfExists(myFile);

		printWelcomeMessage();

		startWaitingForInput();
	}

	/**
	 * Checks if file with same name already exists.
	 * If so, extract all text already present.
	 * If not, creates a new file with the given name.
	 * 
	 * @param f The file to be checked.
	 */
	public static void checkIfFileExistsAndImportIfExists(File f) throws IOException, FileNotFoundException {
		if (!f.exists()) {
			f.createNewFile();
		} else {
			importLinesFromFile(f);
		}
	}

	/**
	 * Adds all lines of text of the file into an arraylist.
	 * 
	 * @param f The file from which text will be extracted.
	 */
	public static void importLinesFromFile(File f) throws FileNotFoundException {
		Scanner scanner = new Scanner(f);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			currentStrings.add(line);
		}
		scanner.close();
	}

	public static void printWelcomeMessage() {
		System.out.println(String.format(MESSAGE_WELCOME, fileName));
	}	

	/**
	 * Main loop of the program.
	 */
	public static void startWaitingForInput() throws IOException {
		sc = new Scanner(System.in);
		while (true) {
			askForCommand();
			storeCommandFromInput();
			checkForExitCommand();
			System.out.println(doCommand(inputCommand));
			saveFile();
		}
	}

	public static void askForCommand() {
		System.out.print("command: ");
	}

	public static void storeCommandFromInput() {
		inputCommand = sc.nextLine();
	}

	public static void checkForExitCommand() {
		if (inputCommand.equals("exit")) {
			System.exit(0);
		}
	}

	/**
	 * Compares the command given and performs the appropriate method.
	 * Informs user if an invalid command was enter.
	 * 
	 * @param command User input command.
	 */
	public static String doCommand(String command) {
		if (command.startsWith("add ")) {
			return tryToAdd(currentStrings, command);
		} else if (command.equals("display")) {
			return printAllLines();
		} else if (command.equals("clear")) {
			return clear(currentStrings);
		} else if (command.startsWith("delete ")) {
			return tryToDelete(currentStrings, command);
		} else {
			return MESSAGE_INVALID_COMMAND;
		}
	}

	/**
	 * Checks if the add command is accompanied by text to be added.
	 * If so, add the line to the arraylist. If not, informs the user.
	 * 
	 * @param arrayList The arraylist to which the intended line will be added.
	 * @param command User input command.
	 */
	public static String tryToAdd(ArrayList<String> arrayList, String command) {
		if (hasTextToAdd(command)) {	
			return addLine(arrayList, extractTextFromCommand(command));
		} else {
			return MESSAGE_NO_TEXT;
		}
	}
	
	/**
	 * Checks if the user input includes text to add.
	 * Inputs like "add " are invalid and must be detected.
	 * 
	 * @param  command User input command.
	 * @return Whether command length is more than 4.
	 */
	public static boolean hasTextToAdd(String command) {
		return (command.length() > 4);
	}
	
	public static String extractTextFromCommand(String command) {
		String stringToAdd = command.substring(4);
		return stringToAdd;
	}

	/**
	 * Adds the text to the arraylist and informs the user of it.
	 * 
	 * @param line The line to be added.
	 */
	public static String addLine(ArrayList<String> arrayList, String line) {
		arrayList.add(line);
		return String.format(MESSAGE_LINE_ADDED, fileName, line);
	}

	/**
	 * Checks if the add command is of the correct format.
	 * If so, extracts the line number and attempts to delete that line.
	 * If not, informs the user. 
	 */
	public static String tryToDelete(ArrayList<String> arrayList, String command) {
		if (isCommandLongEnough(command)) {
			int lineToDelete = extractLineNumberFromCommand(command);
			return deleteLine(arrayList, lineToDelete);
		} else {
			return MESSAGE_NO_LINE_NUMBER;
		}
	}
	
	public static boolean isCommandLongEnough(String command) {
		return (command.length() > 7);
	}
	
	public static int extractLineNumberFromCommand(String command) {
		try {
			int lineToDelete = Integer.parseInt(command.substring(7));
			return lineToDelete;
		} catch (NumberFormatException e) {
			return 0;
        }
	}

	/**
	 * Checks whether the line number is valid relative to the amount of lines currently stored.
	 * If so, deletes that line and update the user. Otherwise, informs the user.
	 * 
	 * @param lineNumber The number corresponding to the line that is to be deleted.
	 */
	public static String deleteLine(ArrayList<String> arrayList, int lineNumber) {
		if (lineNumber <= 0 || lineNumber > arrayList.size()) {
			return MESSAGE_INVALID_LINE_NUMBER;
		} else {
			String deletedString = arrayList.get(lineNumber - 1);
			arrayList.remove(lineNumber - 1);
			return String.format(MESSAGE_LINE_DELETED, fileName, deletedString);
		}
	}

	public static String clear(ArrayList<String> arrayList) {
		arrayList.clear();
		return String.format(MESSAGE_ALL_CLEARED, fileName);
	}

	public static String printAllLines() {
		if (currentStrings.isEmpty()) {
			return String.format(MESSAGE_EMPTY_FILE, fileName);
		} else {
			return printEachLineWithNumbering();
		}
	}

	/**
	 * Prints each stored line preceded by an ascending count number.
	 */
	public static String printEachLineWithNumbering() {		
		String allLines = "";
		int count = 0;
		for (String s : currentStrings) {
			count++;
			allLines += count + ". " + s + "\n";
		}
		return allLines;
	}

	/**
	 * Writes all lines from the arraylist, if any, into the file.
	 */
	public static void saveFile() throws IOException {
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false));
		makeNewFile();
		if (currentStrings.isEmpty()) {
			writer.close();
			return;
		} else {
			for (String s : currentStrings) {
				writer.write(s);
				writer.newLine();
			}
		}
		writer.close();
	}

	/**
	 * Creates a new file with the respective filename.
	 */
	public static void makeNewFile() throws IOException {
		File f = new File(fileName);
		f.createNewFile();
	}
}
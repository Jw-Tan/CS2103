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
	private static final String WELCOME_MESSAGE = "Welcome to TextBuddy. %1$s is ready for use";
	private static final String ALL_CLEARED_FEEDBACK = "all content deleted from %1$s";
	private static final String FILE_IS_EMPTY = "%1$s is empty";
	private static final String LINE_ADDED_FEEDBACK = "added to %1$s: \"%2$s\"";
	private static final String NO_TEXT_TO_ADD_ERROR = "Please include desired text in same line with \"add\"";
	private static final String LINE_DELETED_FEEDBACK = "deleted from %1$s: \"%2$s\"";
	private static final String NO_LINE_NUMBER_ERROR = "Please include desired line number in same line with \"delete\"";
	private static final String INVALID_LINE_NUMBER_ERROR = "Invalid line number entered. Please try again.";
	private static final String INVALID_COMMAND_ERROR = "Invalid command. Please try again.";	

	/**
	 * Main method of the program.
	 * 
	 * @param args Contains the filename
	 */
	public static void main(String[] args) throws IOException {

		currentStrings = new ArrayList<String>();

		fileName = args[INDEX_OF_FILENAME];

		File myFile = new File(fileName);

		checkIfFileExists(myFile);

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
	private static void checkIfFileExists(File f) throws IOException, FileNotFoundException {
		if (!f.exists()) {
			f.createNewFile();
		} else {
			importLinesFromFile(f);
		}
	}

	/**
	 * Adds all lines of text in the file into an arraylist.
	 * 
	 * @param f       The file from which text will be extracted.
	 */
	private static void importLinesFromFile(File f) throws FileNotFoundException {
		Scanner scanner = new Scanner(f);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			currentStrings.add(line);
		}
		scanner.close();
	}

	/**
	 * Shows to user the welcome message with the filename that was used. 
	 */
	private static void printWelcomeMessage() {
		System.out.println(String.format(WELCOME_MESSAGE, fileName));
	}	

	/**
	 * Main loop of the program.
	 */
	private static void startWaitingForInput() throws IOException {
		sc = new Scanner(System.in);
		while (true) {
			askForCommand();
			storeCommandFromInput();
			checkForExitCommand();
			doCommand();
			saveFile();
		}
	}

	/**
	 * Shows to user a query for command.
	 */
	private static void askForCommand() {
		System.out.print("command: ");
	}

	/**
	 * Stores command typed by user.
	 */
	private static void storeCommandFromInput() {
		inputCommand = sc.nextLine();
	}

	/**
	 * Checks if the command given was "exit" and terminates accordingly.
	 */
	private static void checkForExitCommand() {
		if (inputCommand.equals("exit")) {
			System.exit(0);
		}
	}

	/**
	 * Compares the command given and performs the appropriate method.
	 * Informs user if an invalid command was enter.
	 */
	private static void doCommand() {
		if (inputCommand.startsWith("add ")) {
			tryToAdd();
		} else if (inputCommand.equals("display")) {
			printAllLines();
		} else if (inputCommand.equals("clear")) {
			clear();
		} else if (inputCommand.startsWith("delete ")) {
			tryToDelete();
		} else {
			System.out.println(INVALID_COMMAND_ERROR);
		}
	}

	/**
	 * Checks if the add command is accompanied by text to be added.
	 * If so, add the line to the arraylist. If not, informs the user.
	 */
	private static void tryToAdd() {
		if (inputCommand.length() > 4) {
			String stringToAdd = inputCommand.substring(4);
			addLine(stringToAdd);
		} else {
			System.out.println(NO_TEXT_TO_ADD_ERROR);
		}
	}

	/**
	 * Adds the text to the arraylist and informs the user of it.
	 * 
	 * @param line The line to be added.
	 */
	private static void addLine(String line) {
		currentStrings.add(line);
		System.out.println(String.format(LINE_ADDED_FEEDBACK, fileName, line));
	}

	/**
	 * Checks if the add command is of the correct format.
	 * If so, extracts the line number and attempts to delete that line.
	 * If not, informs the user. 
	 */
	private static void tryToDelete() {
		if (inputCommand.length() > 7) {
			int lineToDelete = Integer.parseInt(inputCommand.substring(inputCommand.length() - 1));
			deleteLine(lineToDelete);
		} else {
			System.out.println(NO_LINE_NUMBER_ERROR);
		}
	}

	/**
	 * Checks whether the line number is valid relative to the amount of lines currently stored.
	 * If so, deletes that line and update the user. Otherwise, informs the user.
	 * 
	 * @param lineNumber The number corresponding to the line that is to be deleted.
	 */
	private static void deleteLine(int lineNumber) {
		if (lineNumber <= currentStrings.size() && lineNumber > 0) {
			String deletedString = currentStrings.get(lineNumber - 1);
			currentStrings.remove(lineNumber - 1);
			System.out.println(String.format(LINE_DELETED_FEEDBACK, fileName, deletedString));
		} else {
			System.out.println(INVALID_LINE_NUMBER_ERROR);
		}
	}

	/**
	 * Deletes all lines and informs the user.
	 */
	private static void clear() {
		currentStrings.clear();
		System.out.println(String.format(ALL_CLEARED_FEEDBACK, fileName));
	}

	/**
	 * Attempt to print all stored lines.
	 * If no lines are stored, informs the user.
	 */
	private static void printAllLines() {
		if (currentStrings.isEmpty()) {
			System.out.println(String.format(FILE_IS_EMPTY, fileName));
		} else {
			printEachLineWithNumbering();
		}
	}

	/**
	 * Prints each stored line preceded by an ascending count number.
	 */
	private static void printEachLineWithNumbering() {
		int count = 0;
		for (String s : currentStrings) {
			count++;
			System.out.println(count + ". " + s);
		}
	}

	/**
	 * Writes all lines in the arraylist, if any, into the file.
	 */
	private static void saveFile() throws IOException {
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
	private static void makeNewFile() throws IOException {
		File f = new File(fileName);
		f.createNewFile();
	}
}
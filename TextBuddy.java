import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TextBuddy {

	private static ArrayList<String> currentStrings;
	private static Scanner sc;
	private static String inputCommand, tempString, fileName;


	public static void main(String[] args) throws IOException {
		
		currentStrings = new ArrayList<String>();
		fileName = args[0];

		File f = new File(fileName); //refactor these
		
		checkIfFileExists(f);
		
		printWelcomeMessage();
		
		startWaitingForInput();
	}

	private static void checkIfFileExists(File f) throws IOException, FileNotFoundException {
		if (!f.exists()) {
			f.createNewFile();
		} else {
			importLinesFromFile(f);
		}
	}

	private static void importLinesFromFile(File f) throws FileNotFoundException {
		Scanner scanner = new Scanner(f);
		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			currentStrings.add(line);
		}
		scanner.close();
	}
	
	private static void printWelcomeMessage() {
		System.out.println("\n" + "Welcome to TextBuddy. " + fileName + " is ready for use\n"); //lines like this make column
	}	
	
	private static void startWaitingForInput() throws IOException {
		sc = new Scanner(System.in);
		while (true) {
			askCommand();
			getCommandFromInput();
			checkForExitCommand();
			doCommand();
			saveFile();
		}
	}

	private static void askCommand() {
		System.out.print("command: ");
	}

	private static void getCommandFromInput() {
		inputCommand = sc.nextLine();
		System.out.print("\n");
	}

	private static void checkForExitCommand() {
		if (inputCommand.equals("exit")) {
			System.exit(0);
		}
	}
	
	private static void doCommand() {
		if (inputCommand.startsWith("add ")) {
			if (inputCommand.length() > 4) {
				tempString = inputCommand.substring(4);
				addLine(tempString);
			} else {
				System.out.println("Invalid command structure. Please include desired text in same line with \"add\"\n");
			}
		} else if (inputCommand.equals("display")) {
			printAllLines();
		} else if (inputCommand.equals("clear")) {
			clear();
		} else if (inputCommand.startsWith("delete ")) {
			if (inputCommand.length() > 7) {
				int lineToDelete = Integer.parseInt(inputCommand.substring(inputCommand.length() - 1));
				deleteLine(lineToDelete);
			} else {
				System.out.println("Invalid command structure. Please include desired line number in same line with \"delete\"\n");
			}
		} else {
			System.out.println("Invalid command. Please try again.\n");
		}
	}
	
	private static void addLine(String line) {
		currentStrings.add(line);
		System.out.println("added to " + fileName + ": \"" + line + "\"\n");
	}
	
	private static void deleteLine(int lineNumber) {
		if (lineNumber <= currentStrings.size() && lineNumber > 0) {
			tempString = currentStrings.get(lineNumber - 1);
			currentStrings.remove(lineNumber - 1);
			System.out.println("deleted from " + fileName + ": \"" + tempString + "\"\n");
		} else {
			System.out.println("Invalid line number entered. Please try again.\n");
		}
	}
	
	private static void clear() {
		currentStrings.clear();
		System.out.println("all content deleted from " + fileName + "\n");
	}
	
	private static void printAllLines() {
		if (currentStrings.isEmpty()) {
			System.out.println(fileName + " is empty\n");
		} else {
			int count = 0;
			for (String s : currentStrings) {
				count++;
				System.out.println(count + ". " + s + "\n");
			}
		}
	}
	
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
	
	private static void makeNewFile() throws IOException {
		File f = new File(fileName);
		f.createNewFile();
	}
}

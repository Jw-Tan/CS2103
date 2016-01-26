import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.io.BufferedWriter;

public class TextBuddy {
	
	private static Scanner sc;
	private static String inputCommand, tempString, fileName;
	private static ArrayList<String> currentStrings;

	public static void main(String[] args) throws IOException {
		
		fileName = args[0];
		currentStrings = new ArrayList<String>();
		
		/**
		 * Check whether file with same name already exists.
		 * If yes, extract all present lines into array list.
		 * Else, create new file with that name. 
		 */
		File f = new File(fileName); //refactor these
		if (!f.exists()) {
			f.createNewFile();
		} else {
			Scanner scanner = new Scanner(f);
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				currentStrings.add(line);
			}
			scanner.close();
		}
		
		sc = new Scanner(System.in); //do I need to SLAP a line like this?
		
		// for all the prints, should I combine into a function??? esp @ the individual methods below
		System.out.println("\n" + "Welcome to TextBuddy. " + fileName + " is ready for use" + "\n"); //note I print empty line first
		
		getCommand();
		
		checkCommand();
		
//		System.out.println(currentStrings.get(0));
//		System.out.println(currentStrings.get(1));
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, true));
		
//		System.out.println("command?");
		String input = sc.nextLine();
//		
		writer.write(input);
		writer.newLine();
//		writer.newLine();
//		
		writer.close();	
		sc.close();
		
		//what if file originally contained text, but user did clear, how to write? if cannot, just make new empty file w same name?
		//or perhaps regardless what commands were ran, just always create a new file and write (if any)??
	}
	
	public static void getCommand() {
		System.out.print("command: ");
		inputCommand = sc.next();
		System.out.print("\n");
	}
	
	public static void checkCommand() {
		if (inputCommand.equals("add")) {
			tempString = sc.nextLine();
			addLine(tempString);
		} else if (inputCommand.equals("display")) {
			printAllLines();
		} else if (inputCommand.equals("clear")) {
			clear();
		} else if (inputCommand.equals("delete")) {
			int lineToDelete = sc.nextInt();
			deleteLine(lineToDelete);
		} else if (inputCommand.equals("exit")) {
			return; //need to terminate here!
		}
	}
	
	public static void addLine(String line) {
		currentStrings.add(line);
		System.out.println("added to " + fileName + ": \"" + line + "\"" + "\n");
		//NOTE i think there is extra space at start of var line!!! try out. ( i take the pound cake!)
	}
	
	public static void deleteLine(int lineNumber) {
		tempString = currentStrings.get(lineNumber - 1);
		currentStrings.remove(lineNumber - 1);
		System.out.println("deleted from " + fileName + ": \"" + tempString + "\"" + "\n");
	}
	
	public static void clear() {
		currentStrings.clear();
		System.out.println("all content deleted from " + fileName + "\n");
	}
	
	public static void printAllLines() {
		if (currentStrings.isEmpty()) {
			System.out.println(fileName + " is empty" + "\n");
		} else {
			int count = 0;
			for (String s : currentStrings) {
				count++;
				System.out.println(count + ". " + s + "\n");
			}
		}
	}
}

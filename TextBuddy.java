import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.BufferedWriter;

public class TextBuddy {

	public static void main(String[] args) throws IOException {		
		File f = new File(args[0]);
		System.out.println("file exists: " + f.exists());
		if (!f.exists()) {
			f.createNewFile();
			System.out.println("file exists: " + f.exists());
		}
		
		BufferedWriter writer = new BufferedWriter(new FileWriter(args[0], true));
		Scanner sc = new Scanner(System.in);
		System.out.println("command?");
		String input = sc.nextLine();
		
		writer.write(input);
		writer.newLine();
		writer.newLine();
		
		writer.close();	
		sc.close();

//		FileOutputStream fos = new FileOutputStream(args[0]);
//		BufferedOutputStream bos = new BufferedOutputStream(fos);
//		int count = 1;
//		bos.write("test line " + Integer.toString(count) +"\n");
//		bos.write("test line 1" + "\n");
//		count++;
//		bos.close();
//		System.out.println(args[0] + " is successfully copied to " + args[1]);
	}
}

package Firewall;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Pattern;

/**
 * The Main class demonstrates the functionality of the Firewall class.
 */

public class Main {

	public static void main(String[] args) throws FileNotFoundException {
		Firewall fw = new Firewall("test.txt");
		File file = new File("test.txt");
		Scanner input = new Scanner(file);
		while (input.hasNextLine()) {
			String line = input.nextLine();
			String [] inputs = line.split(Pattern.quote(","));
			System.out.println(fw.accept_packet(inputs[0], inputs[1], inputs[2], inputs[3]));
		}
	}

}

package game;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class GameFileGenerator {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.print("enter a file name: ");
		String fileName = input.nextLine();
		File outputText = new File(fileName);
		try {
			Solitaire game = new Solitaire();
			String outputString = game.toString();
			FileWriter fWriter = new FileWriter(outputText);
			fWriter.write(outputString);
			fWriter.close();
		}
		catch(IOException e) {
			System.out.println("Error with I/O");
		}
		input.close();
		System.out.println();
	}
	
}

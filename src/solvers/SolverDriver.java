package solvers;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import game.Solitaire;
import heuristics.FoundationHeuristic;

public class SolverDriver {

	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.print("enter file path: ");
		String filePath = input.nextLine();
		File inputText = new File(filePath);
		System.out.print("enter a file name: ");
		String fileName = input.nextLine();
		File outputText = new File(fileName);
		try {
			Scanner scanner = new Scanner(inputText);
			Scanner gameText = scanner.useDelimiter("\\Z");
			String inputString = gameText.next();
			scanner.close();
			gameText.close();
			Solitaire game = new Solitaire(inputString);
			System.out.print("enter a maximum depth: ");
			int maxDepth = input.nextInt();
			System.out.print("enter memory limit: ");
			int maxPrev = input.nextInt();
			System.out.print("enter depth for dls: ");
			int dlsDepth = input.nextInt();
			boolean prunes;
			System.out.print("enter a negative integer to not prune: ");
			if(input.nextInt()<0) {
				prunes = false;
			}
			else {
				prunes = true;
			}
			FoundationHeuristic fhueristic = new FoundationHeuristic();
			StagedDeepeningSolver gameSolver = 
					new StagedDeepeningSolver(maxDepth, maxPrev, prunes, dlsDepth, fhueristic);
			String moves = gameSolver.convertMovesToString(gameSolver.solve(game));
			FileWriter moveWriter = new FileWriter(outputText);
			moveWriter.write(moves);
			moveWriter.close();
		}
		catch(IOException e) {
			System.out.println("Error with I/O");
		}
		input.close();
	}

}

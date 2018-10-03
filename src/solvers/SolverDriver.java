package solvers;
/* this class basically just allows one to solve a solitaire game using the console. The first line read in must be
 * a filepath to a textfile which has a string representing a solitaire game state in it. System will prompt for
 * inputs such as maximum depth, amount of games remembered, and whether or not the solver will prune moves.
 * It will print out moves to a file with each row representing a move
 */

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
			System.out.print("enter a negative integer to not prune: ");
			boolean prunes;
			if(input.nextInt()<0) {
				prunes = false;
			}
			else {
				prunes = true;
			}
			System.out.print("enter memory limit: ");
			int maxPrev = input.nextInt();
			System.out.print("enter depth for dls: ");
			int dlsDepth = input.nextInt();
			FoundationHeuristic fhueristic = new FoundationHeuristic();
			StagedDeepeningSolver gameSolver = 
					new StagedDeepeningSolver(maxDepth, prunes, maxPrev, dlsDepth, fhueristic);
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

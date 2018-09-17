package heuristics;

import java.util.LinkedList;
import java.util.Comparator;
import game.Solitaire;
import game.Move;
import structures.Pair;

public abstract class SolitaireHeuristic implements Comparator<Pair<Solitaire, LinkedList<Move>>> {
	
	abstract public int calculateHeuristic(Solitaire game);
}

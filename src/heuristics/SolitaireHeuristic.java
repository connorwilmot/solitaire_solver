package heuristics;
/* Abstract heuristic class that implements the comparator interface to compare an associated solitaire
 * and list of moves. This allows a priority queue to be ordered by the heuristic. It also has a public method for
 * calculating a heuristic on a solitaire game in case one wants to do that.
 */

import java.util.LinkedList;
import java.util.Comparator;
import game.Solitaire;
import game.Move;
import structures.Pair;

public abstract class SolitaireHeuristic implements Comparator<Pair<Solitaire, LinkedList<Move>>> {
	
	abstract public int calculateHeuristic(Solitaire game);
}

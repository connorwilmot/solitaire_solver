package solvers;
/* implentation of a solitaire solver, and more specifically QueueSolver. Uses whatever heuristic passed as an
 * argument. Implements newOpenList by returning a priority queue ordered by heuristic
 */

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import game.Move;
import game.Solitaire;
import heuristics.SolitaireHeuristic;
import structures.Pair;

public class GreedyBestFirstSolver extends QueueSolver {

	private SolitaireHeuristic heuristic;
	
	public GreedyBestFirstSolver(int maxDepth, boolean prunes, int maxPrev, SolitaireHeuristic heuristic) {
		super(maxDepth, prunes, maxPrev);
		this.heuristic = heuristic;
	}

	public SolitaireHeuristic getHeuristic() {
		return heuristic;
	}

	public void setHeuristic(SolitaireHeuristic heuristic) {
		this.heuristic = heuristic;
	}

	@Override
	protected Queue<Pair<Solitaire, LinkedList<Move>>> newOpenList() {
		return new PriorityQueue<Pair<Solitaire, LinkedList<Move>>>(heuristic);
	}

}

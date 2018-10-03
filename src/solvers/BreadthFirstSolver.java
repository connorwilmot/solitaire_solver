package solvers;
/* class for a breadth first search solver. most of the implementation for this class is in its super class 
 * QueueSolver. The only implemented method is that for creating a new openList, which returns arraydeque which will
 * be used as a queue.
 */

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;

import game.Move;
import game.Solitaire;
import structures.Pair;

public class BreadthFirstSolver extends QueueSolver {
	
	public BreadthFirstSolver(int depth, int maxPrevious, boolean prunes) {
		super(depth, maxPrevious, prunes);
	}

	@Override
	protected Queue<Pair<Solitaire, LinkedList<Move>>> newOpenList() {
		return new ArrayDeque<Pair<Solitaire, LinkedList<Move>>>();
	}
	
}

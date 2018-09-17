package solvers;

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

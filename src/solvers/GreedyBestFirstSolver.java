package solvers;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

import game.Move;
import game.Solitaire;
import heuristics.SolitaireHeuristic;
import structures.Pair;

public class GreedyBestFirstSolver extends QueueSolver {

	private SolitaireHeuristic heuristic;
	
	public GreedyBestFirstSolver(int maxDepth, int maxPrev, boolean prunes, SolitaireHeuristic heuristic) {
		super(maxDepth, maxPrev, prunes);
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

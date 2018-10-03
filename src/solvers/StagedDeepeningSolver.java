package solvers;
/* variation of best first search solver where instead of adding games to openlist which are a move away, it does
 * a depth limited search with each game that is taken off the list, adding games which are exactly the depth limit
 * away in moves to the openlist. Has different methods depending on whether or not the search is done with
 * pruning or memory.
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import game.Move;
import game.Solitaire;
import heuristics.SolitaireHeuristic;
import structures.Pair;

public class StagedDeepeningSolver extends GreedyBestFirstSolver {

	private int depthOfDLS;
	
	public StagedDeepeningSolver(int maxDepthMult, boolean prunes, int maxPrev, int dlsDepth, SolitaireHeuristic heuristic) {
		super(maxDepthMult*dlsDepth, prunes, maxPrev, heuristic);
		depthOfDLS = dlsDepth;
	}
	
	public int getDepthOfDLS() {
		return depthOfDLS;
	}

	public void setDepthOfDLS(int depthOfDLS) {
		this.depthOfDLS = depthOfDLS;
	}

	@Override
	protected void addGamesToOpenListMemoryPruning(Solitaire game, LinkedList<Move> movesToGame) {
		recursiveRoutineMemoryPruning(game, movesToGame, depthOfDLS);
	}

	@Override
	protected void addGamesToOpenListMemoryNoPrun(Solitaire game, LinkedList<Move> movesToGame) {
		recursiveRoutineMemoryNoPrun(game, movesToGame, depthOfDLS);
	}

	@Override
	protected void addGamesToOpenListNoMemPruning(Solitaire game, LinkedList<Move> movesToGame) {
		recursiveRoutineNoMemPruning(game, movesToGame, depthOfDLS);
	}

	@Override
	protected void addGamesToOpenListNoMemNoPrun(Solitaire game, LinkedList<Move> movesToGame) {
		recursiveRoutineNoMemNoPrun(game, movesToGame, depthOfDLS);
	}

	private void recursiveRoutineMemoryPruning(Solitaire game, LinkedList<Move> movesToGame, int height) {
		if(!isBaseCase(game, movesToGame, height)) {
			HashSet<Solitaire> prevGames = this.getPrevGames();
			ArrayList<Move> currentMoves = game.listPrunedLegalMoves();
			int maxPrev = this.getMaxPrev();
			for(Move move: currentMoves) {
				movesToGame.add(move);
				game.makeLegalMove(move);
				if(maxPrev<=prevGames.size()) {
					prevGames.clear();
				}
				if(prevGames.add(new Solitaire(game))) {
					recursiveRoutineMemoryPruning(game, movesToGame, height-1);
				}
				game.reverseLegalMove(move);
				movesToGame.removeLast();
			}
		}
	}

	private void recursiveRoutineMemoryNoPrun(Solitaire game, LinkedList<Move> movesToGame, int height) {
		if(!isBaseCase(game, movesToGame, height)) {
			HashSet<Solitaire> prevGames = this.getPrevGames();
			ArrayList<Move> currentMoves = game.listLegalMoves();
			int maxPrev = this.getMaxPrev();
			for(Move move: currentMoves) {
				movesToGame.add(move);
				game.makeLegalMove(move);
				if(maxPrev<=prevGames.size()) {
					prevGames.clear();
				}
				if(prevGames.add(new Solitaire(game))) {
					recursiveRoutineMemoryNoPrun(game, movesToGame, height-1);
				}
				game.reverseLegalMove(move);
				movesToGame.removeLast();
			}
		}
	}

	private void recursiveRoutineNoMemPruning(Solitaire game, LinkedList<Move> movesToGame, int height) {
		if(!isBaseCase(game, movesToGame, height)) {
			ArrayList<Move> currentMoves = game.listPrunedLegalMoves();
			for(Move move: currentMoves) {
				movesToGame.add(move);
				game.makeLegalMove(move);
				recursiveRoutineNoMemPruning(game, movesToGame, height-1);
				game.reverseLegalMove(move);
				movesToGame.removeLast();
			}
		}
	}

	private void recursiveRoutineNoMemNoPrun(Solitaire game, LinkedList<Move> movesToGame, int height) {
		if(!isBaseCase(game, movesToGame, height)) {
			ArrayList<Move> currentMoves = game.listLegalMoves();
			for(Move move: currentMoves) {
				movesToGame.add(move);
				game.makeLegalMove(move);
				recursiveRoutineNoMemNoPrun(game, movesToGame, height-1);
				game.reverseLegalMove(move);
				movesToGame.removeLast();
			}
		}
	}
	
	//returns true if it hits a base case
	private boolean isBaseCase(Solitaire game, LinkedList<Move> moves, int height){
		if(game.isGameWon()) {
			Solitaire newGame = new Solitaire(game);
			LinkedList<Move> newMoves = new LinkedList<Move>(moves);
			this.getOpenList().offer(new Pair<Solitaire, LinkedList<Move>>(newGame, newMoves));
			return true;
		}
		else if(height<=0) {
			Solitaire newGame = new Solitaire(game);
			LinkedList<Move> newMoves = new LinkedList<Move>(moves);
			this.getOpenList().offer(new Pair<Solitaire, LinkedList<Move>>(newGame, newMoves));
			return true;
		}
		else {
			return false;
		}
	}

}

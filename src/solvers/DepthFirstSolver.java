package solvers;
/* an implementation of a solitaire solver using a depth first search. different methods allow the search to be
 * limited in depth, to have memory of games seen, or to be pruned
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import game.Move;
import game.Solitaire;

public class DepthFirstSolver extends SolitaireSolver{

	public DepthFirstSolver(int depth, int maxPrevious, boolean prunes) {
		super(depth, maxPrevious, prunes);
	}

	@Override
	protected LinkedList<Move> solveUnlimMemoryPruning(Solitaire game) {
		return solveUnlimMemoryPruning(game, new LinkedList<Move>());
	}
	
	private LinkedList<Move> solveUnlimMemoryPruning(Solitaire game, LinkedList<Move> moves) {
		HashSet<Solitaire> prevGames = this.getPrevGames();
		int maxPrev = this.getMaxPrev();
		if(game.isGameWon()) {
			return moves;
		}
		ArrayList<Move> currentMoves = game.listPrunedLegalMoves();
		for(Move move: currentMoves) {
			moves.add(move);
			game.makeLegalMove(move);
			if(maxPrev<=prevGames.size()) {
				prevGames.clear();
			}
			if(prevGames.add(new Solitaire(game))) {
				LinkedList<Move> winningMoves = solveUnlimMemoryPruning(game, moves);
				if(winningMoves!=null) {
					return winningMoves;
				}
			}
			game.reverseLegalMove(move);
			moves.removeLast();
		}
		return null;
	}

	@Override
	protected LinkedList<Move> solveUnlimMemoryNoPrun(Solitaire game) {
		return solveUnlimMemoryNoPrun(game, new LinkedList<Move>());
	}
	
	private LinkedList<Move> solveUnlimMemoryNoPrun(Solitaire game, LinkedList<Move> moves) {
		HashSet<Solitaire> prevGames = this.getPrevGames();
		int maxPrev = this.getMaxPrev();
		if(game.isGameWon()) {
			return moves;
		}
		ArrayList<Move> currentMoves = game.listLegalMoves();
		for(Move move: currentMoves) {
			moves.add(move);
			game.makeLegalMove(move);
			if(maxPrev<=prevGames.size()) {
				prevGames.clear();
			}
			if(prevGames.add(new Solitaire(game))) {
				LinkedList<Move> winningMoves = solveUnlimMemoryNoPrun(game, moves);
				if(winningMoves!=null) {
					return winningMoves;
				}
			}
			game.reverseLegalMove(move);
			moves.removeLast();
		}
		return null;
	}

	@Override
	protected LinkedList<Move> solveUnlimNoMemPruning(Solitaire game) {
		return solveUnlimNoMemPruning(game, new LinkedList<Move>());
	}
	
	private LinkedList<Move> solveUnlimNoMemPruning(Solitaire game, LinkedList<Move> moves) {
		if(game.isGameWon()) {
			return moves;
		}
		ArrayList<Move> currentMoves = game.listPrunedLegalMoves();
		for(Move move: currentMoves) {
			moves.add(move);
			game.makeLegalMove(move);
			LinkedList<Move> winningMoves = solveUnlimNoMemPruning(game, moves);
			if(winningMoves!=null) {
				return winningMoves;
			}
			game.reverseLegalMove(move);
			moves.removeLast();
		}
		return null;
	}

	@Override
	protected LinkedList<Move> solveUnlimNoMemNoPrun(Solitaire game) {
		return solveUnlimNoMemNoPrun(game, new LinkedList<Move>());
	}
	
	private LinkedList<Move> solveUnlimNoMemNoPrun(Solitaire game, LinkedList<Move> moves) {
		if(game.isGameWon()) {
			return moves;
		}
		ArrayList<Move> currentMoves = game.listLegalMoves();
		for(Move move: currentMoves) {
			moves.add(move);
			game.makeLegalMove(move);
			LinkedList<Move> winningMoves = solveUnlimNoMemNoPrun(game, moves);
			if(winningMoves!=null) {
				return winningMoves;
			}
			game.reverseLegalMove(move);
			moves.removeLast();
		}
		return null;
	}

	@Override
	protected LinkedList<Move> solveLimitMemoryPruning(Solitaire game) {
		return solveLimitMemoryPruning(game, new LinkedList<Move>());
	}
	
	private LinkedList<Move> solveLimitMemoryPruning(Solitaire game, LinkedList<Move> moves) {
		HashSet<Solitaire> prevGames = this.getPrevGames();
		int maxPrev = this.getMaxPrev();
		if(game.isGameWon()) {
			return moves;
		}
		if(this.getMaxDepth()<=moves.size()) {
			return null;
		}
		ArrayList<Move> currentMoves = game.listPrunedLegalMoves();
		for(Move move: currentMoves) {
			moves.add(move);
			game.makeLegalMove(move);
			if(maxPrev<=prevGames.size()) {
				prevGames.clear();
			}
			if(prevGames.add(new Solitaire(game))) {
				LinkedList<Move> winningMoves = solveLimitMemoryPruning(game, moves);
				if(winningMoves!=null) {
					return winningMoves;
				}
			}
			game.reverseLegalMove(move);
			moves.removeLast();
		}
		return null;
	}

	@Override
	protected LinkedList<Move> solveLimitMemoryNoPrun(Solitaire game) {
		return solveLimitMemoryNoPrun(game, new LinkedList<Move>());
	}
	
	private LinkedList<Move> solveLimitMemoryNoPrun(Solitaire game, LinkedList<Move> moves) {
		HashSet<Solitaire> prevGames = this.getPrevGames();
		int maxPrev = this.getMaxPrev();
		if(game.isGameWon()) {
			return moves;
		}
		if(this.getMaxDepth()<=moves.size()) {
			return null;
		}
		ArrayList<Move> currentMoves = game.listLegalMoves();
		for(Move move: currentMoves) {
			moves.add(move);
			game.makeLegalMove(move);
			if(maxPrev<=prevGames.size()) {
				prevGames.clear();
			}
			if(prevGames.add(new Solitaire(game))) {
				LinkedList<Move> winningMoves = solveLimitMemoryNoPrun(game, moves);
				if(winningMoves!=null) {
					return winningMoves;
				}
			}
			game.reverseLegalMove(move);
			moves.removeLast();
		}
		return null;
	}

	@Override
	protected LinkedList<Move> solveLimitNoMemPruning(Solitaire game) {
		return solveLimitNoMemPruning(game, new LinkedList<Move>());
	}
	
	private LinkedList<Move> solveLimitNoMemPruning(Solitaire game, LinkedList<Move> moves) {
		if(game.isGameWon()) {
			return moves;
		}
		if(this.getMaxDepth()<=moves.size()) {
			return null;
		}
		ArrayList<Move> currentMoves = game.listPrunedLegalMoves();
		for(Move move: currentMoves) {
			moves.add(move);
			game.makeLegalMove(move);
			LinkedList<Move> winningMoves = solveLimitNoMemPruning(game, moves);
			if(winningMoves!=null) {
				return winningMoves;
			}
			game.reverseLegalMove(move);
			moves.removeLast();
		}
		return null;
	}

	@Override
	protected LinkedList<Move> solveLimitNoMemNoPrun(Solitaire game) {
		return solveLimitNoMemNoPrun(game, new LinkedList<Move>());
	}
	
	private LinkedList<Move> solveLimitNoMemNoPrun(Solitaire game, LinkedList<Move> moves) {
		if(game.isGameWon()) {
			return moves;
		}
		if(this.getMaxDepth()<=moves.size()) {
			return null;
		}
		ArrayList<Move> currentMoves = game.listLegalMoves();
		for(Move move: currentMoves) {
			moves.add(move);
			game.makeLegalMove(move);
			LinkedList<Move> winningMoves = solveLimitNoMemNoPrun(game, moves);
			if(winningMoves!=null) {
				return winningMoves;
			}
			game.reverseLegalMove(move);
			moves.removeLast();
		}
		return null;
	}

}

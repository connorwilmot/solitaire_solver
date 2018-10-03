package solvers;
/* implementation of a solitaire solver which uses a depth limited search starting at the initial game. It
 * repeatedly does a search from the root and increases the depth of the search. Has options to have a maximum
 * depth for the overall search, to remember games seen, and to prune.
 */

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;

import game.Move;
import game.Solitaire;

public class IterativeDeepeningSolver extends SolitaireSolver {
	
	public IterativeDeepeningSolver(int depth, boolean prunes, int maxPrevious) {
		super(depth, prunes, maxPrevious);
	}

	@Override
	protected LinkedList<Move> solveUnlimMemoryPruning(Solitaire game) {
		for(int i=0; true; i++) {
			LinkedList<Move> movesToGame = new LinkedList<Move>();
			int isGameSolvable = recursiveDLSMemoryPruning(new Solitaire(game), movesToGame, i);
			if(0<isGameSolvable) {
				return movesToGame;
			}
			if(isGameSolvable<0) {
				break;
			}
		}
		return null;
	}

	@Override
	protected LinkedList<Move> solveUnlimMemoryNoPrun(Solitaire game) {
		for(int i=0; true; i++) {
			LinkedList<Move> movesToGame = new LinkedList<Move>();
			int isGameSolvable = recursiveDLSMemoryNoPrun(new Solitaire(game), movesToGame, i);
			if(0<isGameSolvable) {
				return movesToGame;
			}
			if(isGameSolvable<0) {
				break;
			}
		}
		return null;
	}

	@Override
	protected LinkedList<Move> solveUnlimNoMemPruning(Solitaire game) {
		for(int i=0; true; i++) {
			LinkedList<Move> movesToGame = new LinkedList<Move>();
			int isGameSolvable = recursiveDLSNoMemPruning(new Solitaire(game), movesToGame, i);
			if(0<isGameSolvable) {
				return movesToGame;
			}
			if(isGameSolvable<0) {
				break;
			}
		}
		return null;
	}

	@Override
	protected LinkedList<Move> solveUnlimNoMemNoPrun(Solitaire game) {
		for(int i=0; true; i++) {
			LinkedList<Move> movesToGame = new LinkedList<Move>();
			int isGameSolvable = recursiveDLSNoMemNoPrun(new Solitaire(game), movesToGame, i);
			if(0<isGameSolvable) {
				return movesToGame;
			}
			if(isGameSolvable<0) {
				break;
			}
		}
		return null;
	}

	@Override
	protected LinkedList<Move> solveLimitMemoryPruning(Solitaire game) {
		int maxDepth = this.getMaxDepth();
		for(int i=0; i<=maxDepth; i++) {
			LinkedList<Move> movesToGame = new LinkedList<Move>();
			int isGameSolvable = recursiveDLSMemoryPruning(new Solitaire(game), movesToGame, i);
			if(0<isGameSolvable) {
				return movesToGame;
			}
			if(isGameSolvable<0) {
				break;
			}
		}
		return null;
	}

	@Override
	protected LinkedList<Move> solveLimitMemoryNoPrun(Solitaire game) {
		int maxDepth = this.getMaxDepth();
		for(int i=0; i<=maxDepth; i++) {
			LinkedList<Move> movesToGame = new LinkedList<Move>();
			int isGameSolvable = recursiveDLSMemoryNoPrun(new Solitaire(game), movesToGame, i);
			if(0<isGameSolvable) {
				return movesToGame;
			}
			if(isGameSolvable<0) {
				break;
			}
		}
		return null;
	}

	@Override
	protected LinkedList<Move> solveLimitNoMemPruning(Solitaire game) {
		int maxDepth = this.getMaxDepth();
		for(int i=0; i<=maxDepth; i++) {
			LinkedList<Move> movesToGame = new LinkedList<Move>();
			int isGameSolvable = recursiveDLSNoMemPruning(new Solitaire(game), movesToGame, i);
			if(0<isGameSolvable) {
				return movesToGame;
			}
			if(isGameSolvable<0) {
				break;
			}
		}
		return null;
	}

	@Override
	protected LinkedList<Move> solveLimitNoMemNoPrun(Solitaire game) {
		int maxDepth = this.getMaxDepth();
		for(int i=0; i<=maxDepth; i++) {
			LinkedList<Move> movesToGame = new LinkedList<Move>();
			int isGameSolvable = recursiveDLSNoMemNoPrun(new Solitaire(game), movesToGame, i);
			if(0<isGameSolvable) {
				return movesToGame;
			}
			if(isGameSolvable<0) {
				break;
			}
		}
		return null;
	}

	private int recursiveDLSMemoryPruning(Solitaire game, LinkedList<Move> moves, int height) {
		int foundSol = -1;
		ArrayList<Move> currentMoves = game.listPrunedLegalMoves();
		if(game.isGameWon()) {
			return 1;
		}
		else if(currentMoves.isEmpty()) {
			return -1;
		}
		else if(height<=0) {
			return 0;
		}
		else {
			HashSet<Solitaire> prevGames = this.getPrevGames();
			int maxPrev = this.getMaxPrev();
			for(Move move: currentMoves) {
				moves.add(move);
				game.makeLegalMove(move);
				if(maxPrev<=prevGames.size()) {
					prevGames.clear();
				}
				if(prevGames.add(new Solitaire(game))) {
					int isWinningPath = recursiveDLSMemoryPruning(game, moves, height-1);
					if(0<isWinningPath) {
						return 1;
					}
					if(0==isWinningPath) {
						foundSol = 0;
					}
				}
				game.reverseLegalMove(move);
				moves.removeLast();
			}
			return foundSol;
		}
	}

	private int recursiveDLSMemoryNoPrun(Solitaire game, LinkedList<Move> moves, int height) {
		int foundSol = -1;
		ArrayList<Move> currentMoves = game.listLegalMoves();
		if(game.isGameWon()) {
			return 1;
		}
		else if(currentMoves.isEmpty()) {
			return -1;
		}
		else if(height<=0) {
			return 0;
		}
		else {
			HashSet<Solitaire> prevGames = this.getPrevGames();
			int maxPrev = this.getMaxPrev();
			for(Move move: currentMoves) {
				moves.add(move);
				game.makeLegalMove(move);
				if(maxPrev<=prevGames.size()) {
					prevGames.clear();
				}
				if(prevGames.add(new Solitaire(game))) {
					int isWinningPath = recursiveDLSMemoryPruning(game, moves, height-1);
					if(0<isWinningPath) {
						return 1;
					}
					if(0==isWinningPath) {
						foundSol = 0;
					}
				}
				game.reverseLegalMove(move);
				moves.removeLast();
			}
			return foundSol;
		}
	}

	private int recursiveDLSNoMemPruning(Solitaire game, LinkedList<Move> moves, int height) {
		int foundSol = -1;
		ArrayList<Move> currentMoves = game.listPrunedLegalMoves();
		if(game.isGameWon()) {
			return 1;
		}
		else if(currentMoves.isEmpty()) {
			return -1;
		}
		else if(height<=0) {
			return 0;
		}
		else {
			for(Move move: currentMoves) {
				moves.add(move);
				game.makeLegalMove(move);
				int isWinningPath = recursiveDLSNoMemPruning(game, moves, height-1);
				if(0<isWinningPath) {
					return 1;
				}
				if(0==isWinningPath) {
					foundSol = 0;
				}
				game.reverseLegalMove(move);
				moves.removeLast();
			}
			return foundSol;
		}
	}

	private int recursiveDLSNoMemNoPrun(Solitaire game, LinkedList<Move> moves, int height) {
		int foundSol = -1;
		ArrayList<Move> currentMoves = game.listLegalMoves();
		if(game.isGameWon()) {
			return 1;
		}
		else if(currentMoves.isEmpty()) {
			return -1;
		}
		else if(height<=0) {
			return 0;
		}
		else {
			for(Move move: currentMoves) {
				moves.add(move);
				game.makeLegalMove(move);
				int isWinningPath = recursiveDLSNoMemNoPrun(game, moves, height-1);
				if(0<isWinningPath) {
					return 1;
				}
				if(0==isWinningPath) {
					foundSol = 0;
				}
				game.reverseLegalMove(move);
				moves.removeLast();
			}
			return foundSol;
		}
	}

}

package solvers;
/* base class for solitaire solver. Has options for pruning, max depth, and the number of games remembered.
 * the main method is solve, which calls 8 different methods depending on the combination of options used. these
 * method must be implemented by subclasses, as must initialize and reset.
 */

import game.Solitaire;
import game.Move;

import java.util.HashSet;
import java.util.LinkedList;

abstract public class SolitaireSolver {
	
	private boolean pruning;
	private int maxDepth;
	private HashSet<Solitaire> prevGames;
	private int maxPrev;

	public SolitaireSolver(int depth, int maxPrevious, boolean prunes) {
		maxDepth = depth;
		maxPrev = maxPrevious;
		pruning = prunes;
	}

	public boolean isPruning() {
		return pruning;
	}

	public int getMaxDepth() {
		return maxDepth;
	}

	protected HashSet<Solitaire> getPrevGames() {
		return prevGames;
	}

	public int getMaxPrev() {
		return maxPrev;
	}
	
	public void setPruning(boolean pruning) {
		this.pruning = pruning;
	}

	public void setMaxDepth(int maxDepth) {
		this.maxDepth = maxDepth;
	}

	public void setMaxPrev(int maxPrev) {
		this.maxPrev = maxPrev;
	}
	
	protected void initialize(Solitaire game) {
		prevGames = new HashSet<Solitaire>(2*maxPrev, 1);
		prevGames.add(game);
	}
	
	protected void reset() {
		prevGames = new HashSet<Solitaire>(2*maxPrev, 1);
	}

	public LinkedList<Move> solve(Solitaire game) {
		LinkedList<Move> winningMoves;
		this.initialize(game);
		if(this.getMaxDepth()<0) {
			if(0<this.getMaxPrev()) {
				if(this.isPruning()) {
					winningMoves = solveUnlimMemoryPruning(new Solitaire(game));
				}
				else {
					winningMoves = solveUnlimMemoryNoPrun(new Solitaire(game));
				}
			}
			else {
				if(this.isPruning()) {
					winningMoves = solveUnlimNoMemPruning(new Solitaire(game));
				}
				else {
					winningMoves = solveUnlimNoMemNoPrun(new Solitaire(game));
				}
			}
		}
		else {
			if(0<this.getMaxPrev()) {
				if(this.isPruning()) {
					winningMoves = solveLimitMemoryPruning(new Solitaire(game));
				}
				else {
					winningMoves = solveLimitMemoryNoPrun(new Solitaire(game));
				}
			}
			else {
				if(this.isPruning()) {
					winningMoves = solveLimitNoMemPruning(new Solitaire(game));
				}
				else {
					winningMoves = solveLimitNoMemNoPrun(new Solitaire(game));
				}
			}
		}
		this.reset();
		return winningMoves;
	}

	abstract protected LinkedList<Move> solveUnlimMemoryPruning(Solitaire game);

	abstract protected LinkedList<Move> solveUnlimMemoryNoPrun(Solitaire game);

	abstract protected LinkedList<Move> solveUnlimNoMemPruning(Solitaire game);

	abstract protected LinkedList<Move> solveUnlimNoMemNoPrun(Solitaire game);

	abstract protected LinkedList<Move> solveLimitMemoryPruning(Solitaire game);

	abstract protected LinkedList<Move> solveLimitMemoryNoPrun(Solitaire game);

	abstract protected LinkedList<Move> solveLimitNoMemPruning(Solitaire game);

	abstract protected LinkedList<Move> solveLimitNoMemNoPrun(Solitaire game);

	public String convertMovesToString(LinkedList<Move> moves) {
		if(moves==null) {
			return "no solution found";
		}
		else {
			String sMoves = moves.toString();
			sMoves = sMoves.replace(", ", System.lineSeparator());
			sMoves = sMoves.substring(1, sMoves.length()-1);
			return sMoves;
		}
	}
}

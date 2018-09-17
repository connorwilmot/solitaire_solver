package solvers;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

import game.Move;
import game.Solitaire;
import structures.Pair;

abstract public class QueueSolver extends SolitaireSolver {

	private Queue<Pair<Solitaire, LinkedList<Move>>> openList;

	public QueueSolver(int depth, int maxPrevious, boolean prunes) {
		super(depth, maxPrevious, prunes);
	}
	
	protected Queue<Pair<Solitaire, LinkedList<Move>>> getOpenList() {
		return openList;
	}
	
	//subclasses must return a new openList
	abstract protected Queue<Pair<Solitaire, LinkedList<Move>>> newOpenList();
	
	@Override
	protected void initialize(Solitaire game) {
		super.initialize(game);
		openList = this.newOpenList();
		Solitaire gameCopy = new Solitaire(game);
		LinkedList<Move> movesToGame = new LinkedList<Move>();
		openList.add(new Pair<Solitaire, LinkedList<Move>>(gameCopy, movesToGame));
	}

	@Override
	protected void reset() {
		super.reset();
		openList = null;
	}

	@Override
	protected LinkedList<Move> solveUnlimMemoryPruning(Solitaire game){
		while(!openList.isEmpty()) {
			Pair<Solitaire, LinkedList<Move>> currGameAndMoves = openList.poll();
			Solitaire currentGame = currGameAndMoves.getKey();
			LinkedList<Move> movesToGame = currGameAndMoves.getValue();
			if(currentGame.isGameWon()) {
				return movesToGame;
			}
			this.addGamesToOpenListMemoryPruning(currentGame, movesToGame);
		}
		return null;
	}

	@Override
	protected LinkedList<Move> solveUnlimMemoryNoPrun(Solitaire game){
		while(!openList.isEmpty()) {
			Pair<Solitaire, LinkedList<Move>> currGameAndMoves = openList.poll();
			Solitaire currentGame = currGameAndMoves.getKey();
			LinkedList<Move> movesToGame = currGameAndMoves.getValue();
			if(currentGame.isGameWon()) {
				return movesToGame;
			}
			this.addGamesToOpenListMemoryNoPrun(currentGame, movesToGame);
		}
		return null;
	}

	@Override
	protected LinkedList<Move> solveUnlimNoMemPruning(Solitaire game){
		while(!openList.isEmpty()) {
			Pair<Solitaire, LinkedList<Move>> currGameAndMoves = openList.poll();
			Solitaire currentGame = currGameAndMoves.getKey();
			LinkedList<Move> movesToGame = currGameAndMoves.getValue();
			if(currentGame.isGameWon()) {
				return movesToGame;
			}
			this.addGamesToOpenListNoMemPruning(currentGame, movesToGame);
		}
		return null;
	}

	@Override
	protected LinkedList<Move> solveUnlimNoMemNoPrun(Solitaire game){
		while(!openList.isEmpty()) {
			Pair<Solitaire, LinkedList<Move>> currGameAndMoves = openList.poll();
			Solitaire currentGame = currGameAndMoves.getKey();
			LinkedList<Move> movesToGame = currGameAndMoves.getValue();
			if(currentGame.isGameWon()) {
				return movesToGame;
			}
			this.addGamesToOpenListNoMemNoPrun(currentGame, movesToGame);
		}
		return null;
	}

	@Override
	protected LinkedList<Move> solveLimitMemoryPruning(Solitaire game){
		while(!openList.isEmpty()) {
			Pair<Solitaire, LinkedList<Move>> currGameAndMoves = openList.poll();
			Solitaire currentGame = currGameAndMoves.getKey();
			LinkedList<Move> movesToGame = currGameAndMoves.getValue();
			if(currentGame.isGameWon()) {
				return movesToGame;
			}
			if(this.getMaxDepth()<=movesToGame.size()) {
				continue;
			}
			this.addGamesToOpenListMemoryPruning(currentGame, movesToGame);
		}
		return null;
	}

	@Override
	protected LinkedList<Move> solveLimitMemoryNoPrun(Solitaire game){
		while(!openList.isEmpty()) {
			Pair<Solitaire, LinkedList<Move>> currGameAndMoves = openList.poll();
			Solitaire currentGame = currGameAndMoves.getKey();
			LinkedList<Move> movesToGame = currGameAndMoves.getValue();
			if(currentGame.isGameWon()) {
				return movesToGame;
			}
			if(this.getMaxDepth()<=movesToGame.size()) {
				continue;
			}
			this.addGamesToOpenListMemoryNoPrun(currentGame, movesToGame);
		}
		return null;
	}

	@Override
	protected LinkedList<Move> solveLimitNoMemPruning(Solitaire game){
		while(!openList.isEmpty()) {
			Pair<Solitaire, LinkedList<Move>> currGameAndMoves = openList.poll();
			Solitaire currentGame = currGameAndMoves.getKey();
			LinkedList<Move> movesToGame = currGameAndMoves.getValue();
			if(currentGame.isGameWon()) {
				return movesToGame;
			}
			if(this.getMaxDepth()<=movesToGame.size()) {
				continue;
			}
			this.addGamesToOpenListNoMemPruning(currentGame, movesToGame);
		}
		return null;
	}

	@Override
	protected LinkedList<Move> solveLimitNoMemNoPrun(Solitaire game){
		while(!openList.isEmpty()) {
			Pair<Solitaire, LinkedList<Move>> currGameAndMoves = openList.poll();
			Solitaire currentGame = currGameAndMoves.getKey();
			LinkedList<Move> movesToGame = currGameAndMoves.getValue();
			if(currentGame.isGameWon()) {
				return movesToGame;
			}
			if(this.getMaxDepth()<=movesToGame.size()) {
				continue;
			}
			this.addGamesToOpenListNoMemNoPrun(currentGame, movesToGame);
		}
		return null;
	}

	protected void addGamesToOpenListMemoryPruning(Solitaire game, LinkedList<Move> movesToGame) {
		this.addGamesToOpenListMemory(game, movesToGame, game.listPrunedLegalMoves());
	}

	protected void addGamesToOpenListMemoryNoPrun(Solitaire game, LinkedList<Move> movesToGame) {
		this.addGamesToOpenListMemory(game, movesToGame, game.listLegalMoves());
	}

	protected void addGamesToOpenListNoMemPruning(Solitaire game, LinkedList<Move> movesToGame) {
		this.addGamesToOpenListNoMem(game, movesToGame, game.listPrunedLegalMoves());
	}

	protected void addGamesToOpenListNoMemNoPrun(Solitaire game, LinkedList<Move> movesToGame) {
		this.addGamesToOpenListNoMem(game, movesToGame, game.listLegalMoves());
	}

	private void addGamesToOpenListNoMem(Solitaire game, LinkedList<Move> movesToGame, ArrayList<Move> nextMoves) {
		for(Move nextMove: nextMoves) {
			Solitaire nextGame = new Solitaire(game);
			LinkedList<Move> movesToNextGame = new LinkedList<Move>(movesToGame);
			movesToNextGame.add(nextMove);
			nextGame.makeLegalMove(nextMove);
			openList.offer(new Pair<Solitaire, LinkedList<Move>>(nextGame, movesToNextGame));
		}
		
	}

	private void addGamesToOpenListMemory(Solitaire game, LinkedList<Move> movesToGame, ArrayList<Move> nextMoves) {
		HashSet<Solitaire> prevGames = this.getPrevGames();
		int maxPrev = this.getMaxPrev();
		for(Move nextMove: nextMoves) {
			Solitaire nextGame = new Solitaire(game);
			LinkedList<Move> movesToNextGame = new LinkedList<Move>(movesToGame);
			movesToNextGame.add(nextMove);
			nextGame.makeLegalMove(nextMove);
			if(maxPrev<=prevGames.size()) {
				prevGames.clear();
			}
			if(prevGames.add(nextGame)) {
				openList.offer(new Pair<Solitaire, LinkedList<Move>>(nextGame, movesToNextGame));
			}
		}
	}
	
}

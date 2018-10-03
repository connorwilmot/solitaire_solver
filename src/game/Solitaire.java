package game;
/* class which represents a particular variant of solitaire. A solitaire game has three fields: deck, cells,
 * tableau, and foundations. The default constructor creates a random initial set up for a solitaire game. A
 * Solitaire game can also be initialized with a string representing the state of the game. There is also a
 * constructor to make a somewhat shallow copy. Some of the important methods by purpose include:
 * listing legal moves, listing a pruned list of legal moves, making a legal move, reversing a legal move.
 * and seeing if a game is a winning state or not
 */

import java.util.ArrayList;
import java.util.HashSet;

public class Solitaire implements Cloneable{
	
	private Deck deck;
	private Cells cells;
	private Cascade[] tableau;
	private Foundation[] foundations;
	
	public Solitaire() {
		deck = new Deck();
		deck.makeStandardDeck();
		deck.shuffle();
		cells = new Cells(4);
		tableau = new Cascade[10];
		for (int i = 0; i < tableau.length; i++) {
			tableau[i] = new Cascade();
		}
		foundations = new Foundation[4];
		for (int i = 0; i < foundations.length; i++) {
			foundations[i] = new Foundation();
		}
		for(int i=0; i<50; i++) {
			tableau[i%10].placeCard(deck.drawCard());
		}
		cells.placeCard(deck.drawCard());
		cells.placeCard(deck.drawCard());
	}
	
	public Solitaire(String text) {
		deck = new Deck();
		//first ten lines are cascades, next are free cells, and foundation is last
		String[] textTokens = text.split(System.lineSeparator());
		//this sections parses the cells
		String sCells = textTokens[0];
		cells = new Cells(4, sCells);
		//this section parses the cascades
		tableau = new Cascade[10];
		for(int i=0; i<10; i++) {
			String sCascade = textTokens[1+i];
			tableau[i] = new Cascade(sCascade);
		}
		//this section parses the foundations
		foundations = new Foundation[4];
		for(int i= 0; i<4; i++) {
			String sFoundation = textTokens[11+i];
			foundations[i] = new Foundation(sFoundation);
		}
	}
	
	//somewhat shallow copy, does not copy cards
	public Solitaire(Solitaire game) {
		this.deck = new Deck(game.deck);
		this.cells = new Cells(game.cells);
		this.tableau = new Cascade[10];
		for (int i=0; i<this.tableau.length; i++) {
			this.tableau[i] = new Cascade(game.tableau[i]);
		}
		foundations = new Foundation[4];
		for (int i=0; i<foundations.length; i++) {
			this.foundations[i] = new Foundation(game.foundations[i]);
		}
	}
	
	public Deck getDeck() {
		return deck;
	}

	public Cells getCells() {
		return cells;
	}

	public Cascade[] getTableau() {
		return tableau;
	}

	public Foundation[] getFoundations() {
		return foundations;
	}

	public ArrayList<Move> listLegalMoves() {
		ArrayList<Move> moves = new ArrayList<Move>();
		moves.addAll(listLegalMovesFromCells());
		moves.addAll(listLegalMovesFromTab());
		return moves;
	}
	
	protected ArrayList<Move> listLegalMovesFromTab() {
		ArrayList<Move> moves = new ArrayList<Move>();
		for(int from=0; from<10; from++) {
			Cascade cascade = tableau[from];
			if(cascade.isEmpty()) {
				continue;
			}
			Card card = cascade.viewTopCard();
			//find out if can move to foundation and put in list
			Move foundMove = findLegalMoveToFoundUsing(card, from);
			if(foundMove!=null) {
				moves.add(foundMove);
			}
			//list moves to tableau
			moves.addAll(listLegalMovesToTabUsing(card, from));
			//find out if can moves to cells and put in list
			Move cellMove = findLegalMoveToCellsUsing(card, from);
			if(cellMove!=null) {
				moves.add(cellMove);
			}
		}
		return moves;
	}
	
	protected ArrayList<Move> listLegalMovesFromCells(){
		ArrayList<Move> moves = new ArrayList<Move>();
		int from = 10;
		for(Card card : cells) {
			//find out if can move to foundation and put in list
			Move foundMove = findLegalMoveToFoundUsing(card, from);
			if(foundMove!=null) {
				moves.add(foundMove);
			}
			//list moves to tableau
			moves.addAll(listLegalMovesToTabUsing(card, from));
		}
		return moves;
	}
	
	private ArrayList<Move> listLegalMovesToTabUsing(Card card, int from){
		ArrayList<Move> moves = new ArrayList<Move>();
		for(int to=0; to<10; to++) {
			Cascade cascade = tableau[to];
			if(cascade.isEmpty()) {
				if(card.getRank()==13) {
					moves.add(new Move((byte) from, card, (byte) to));
				}
			}
			else {
				Card cardOnCasc = cascade.viewTopCard();
				if(-1==card.compareRankTo(cardOnCasc)) {
					if(card.isSameSuit(cardOnCasc)) {
						moves.add(new Move((byte) from, card, (byte) to));
					}
				}
			}
		}
		return moves;
	}
	
	//returns null if there was no move to the foundations
	private Move findLegalMoveToFoundUsing(Card card, int from){
		int to = 11;
		//foundation is indexed by suit, so we get the foundation matching card's suit
		Foundation  foundation = foundations[card.getSuit().ordinal()];
		if(foundation.isEmpty()) {
			//check to see if card is an ace as nothing is on the foundation
			if(card.getRank()==1) {
				return new Move((byte) from, card, (byte) to);
			}
		}
		else {
			Card cardOnFound = foundation.viewCard();
			if(1==card.compareRankTo(cardOnFound)) {
				return new Move((byte) from, card, (byte) to);
			}
		}
		return null;
	}
	
	//returns null if there was no move to the cells
	private Move findLegalMoveToCellsUsing(Card card, int from){
		int to = 10;
		if(cells.availableFreeCell()) {
			return new Move((byte) from, card, (byte) to);
		}
		return null;
	}
	
	public ArrayList<Move> listPrunedLegalMoves(){
		ArrayList<Move> moves = new ArrayList<Move>();
		//check moves for cards from cells
		Move foundMove = findALegalMoveToFound();
		if(foundMove!=null) {
			moves.add(foundMove);
			return moves;
		}
		moves.addAll(listLegalMovesToTab());
		moves.addAll(listLegalMovesToCells());
		return moves;
	}
	
	private Move findALegalMoveToFound(){
		int from = 10;
		for(Card card: cells) {
			Move cellMove = findLegalMoveToFoundUsing(card, from);
			if(cellMove!=null) {
				return cellMove;
			}
		}
		for(from=0; from<10; from++) {
			Cascade cascade = tableau[from];
			if(cascade.isEmpty()) {
				continue;
			}
			Card card = cascade.viewTopCard();
			Move foundMove = findLegalMoveToFoundUsing(card, from);
			if(foundMove!=null) {
				return foundMove;
			}
		}
		return null;
	}
	
	protected ArrayList<Move> listLegalMovesToCells() {
		ArrayList<Move> moves = new ArrayList<Move>();
		int to = 10;
		if(cells.availableFreeCell()) {
			for(int from=0; from<10; from++) {
				Cascade cascade = tableau[from];
				if(!cascade.isEmpty()) {
					Card card = cascade.viewTopCard();
					moves.add(new Move((byte)from, card, (byte)to));
				}
			}
		}
		return moves;
	}
	
	protected ArrayList<Move> listLegalMovesToTab(){
		ArrayList<Move> moves = new ArrayList<Move>();
		int from = 10;
		for(Card card : cells) {
			//list moves to tableau
			Move cellMove = findLegalMoveToTabUsing(card, from);
			if(cellMove!=null) {
				moves.add(cellMove);
			}
		}
		for(from=0; from<10; from++) {
			Cascade cascade = tableau[from];
			if(cascade.isEmpty()) {
				continue;
			}
			Card card = cascade.viewTopCard();
			Move tabMove = findLegalMoveToTabUsing(card, from);
			if(tabMove!=null) {
				moves.add(tabMove);
			}
		}
		return moves;
	}
	
	//If the card is a king, this method will return a move to the first empty cascade
	//if the king came from the cells or if the king was not the only card in the cascade
	//otherwise it will return null for a king
	private Move findLegalMoveToTabUsing(Card card, int from){
		for(int to=0; to<10; to++) {
			Cascade cascade = tableau[to];
			if(cascade.isEmpty()) {
				if(card.getRank()==13) {
					if((10==from)) {
						return new Move((byte) from, card, (byte) to);
					}
					Cascade fromCasc = tableau[from];
					if(1<fromCasc.size()) {
						return new Move((byte) from, card, (byte) to);
					}
				}
			}
			else {
				Card cardOnDest = cascade.viewTopCard();
				if(-1==card.compareRankTo(cardOnDest)) {
					if(card.isSameSuit(cardOnDest)) {
						return new Move((byte) from, card, (byte) to);
					}
				}
			}
		}
		return null;
	}
	
	//This method assumes move is legal
	public void makeLegalMove(Move move) {
		int from = move.getFrom();
		Card card = move.getCard();
		int to = move.getTo();
		moveCardOff(card, from);
		moveCardOn(card, to);
	}

	//This method assume move was legal, and was just made
	public void reverseLegalMove(Move move) {
		int from = move.getFrom();
		Card card = move.getCard();
		int to = move.getTo();
		moveCardOff(card, to);
		moveCardOn(card, from);
	}
	
	private void moveCardOff(Card card, int from) {
		if(from<10) {
			tableau[from].takeTopCard();
		}
		else if(10<from) {
			int foundIndex = card.getSuit().ordinal();
			foundations[foundIndex].removeTopCard();
		}
		else {
			cells.takeCard(card);
		}
	}
	
	private void moveCardOn(Card card, int to) {
		if(to<10) {
			tableau[to].placeCard(card);
		}
		else if(10<to) {
			int foundIndex = card.getSuit().ordinal();
			foundations[foundIndex].placeCard(card);
		}
		else {
			cells.placeCard(card);
		}
	}
	
	public boolean isGameWon() {
		if(!cells.isEmpty()) {
			return false;
		}
		for(Cascade cascade: tableau) {
			if(!cascade.isEmpty()) {
				return false;
			}
		}
		return true;
	}
	
	//does a deep copy
	@Override
	public Object clone() throws CloneNotSupportedException {
		Solitaire game = (Solitaire) super.clone();
		game.deck = (Deck)game.deck.clone();
		game.cells = (Cells)game.cells.clone();
		for(int i=0; i<game.tableau.length; i++) {
			Cascade cascade = game.tableau[i];
			cascade = (Cascade) cascade.clone();
		}
		for(int i=0; i<game.foundations.length; i++) {
			Foundation foundation = game.foundations[i];
			foundation = (Foundation) foundation.clone();
		}
		return game;
	}
	
	@Override
	public boolean equals(Object oGame) {
		Solitaire game = (Solitaire) oGame;
		if(!deck.equals(game.deck)) {
			return false;
		}
		if(!cells.equals(game.cells)) {
			return false;
		}
		HashSet<Cascade> tab1 = new HashSet<Cascade>(100);
		HashSet<Cascade> tab2 = new HashSet<Cascade>(100);
		for(int i=0; i<10; i++) {
			tab1.add(this.tableau[i]);
			tab2.add(game.tableau[i]);
		}
		for(int i=0; i<10; i++) {
			if((!tab1.contains(game.tableau[i]))||(!tab2.contains(this.tableau[i]))) {
				return false;
			}
		}
		for(int i=0; i<foundations.length; i++) {
			if(!foundations[i].equals(game.foundations[i])) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		int hashNum = 0;
		hashNum -= deck.hashCode();
		hashNum += cells.hashCode();
		for(int i=0; i<10; i++) {
			hashNum += tableau[i].hashCode();
		}
		for(int i=0; i<4; i++) {
			hashNum += foundations[i].hashCode();
		}
		return hashNum;
	}

	@Override
	public String toString() {
		String sGame = cells.toString();
		for(Cascade cascade: tableau) {
			sGame += System.lineSeparator() + cascade.toString();
		}
		for(Foundation foundation: foundations) {
			sGame += System.lineSeparator() + foundation.toString();
		}
		return sGame;
	}
	
}

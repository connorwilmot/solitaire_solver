package game;
/* represents the free cells in a solitaire game. A linked list is used as insertion and deletion is quite common,
 * and a hashset would take up too much space with so few elements. Insertion and deletion by position are not
 * supported. it contains three constructors, one which initializes an empty list, another which
 * uses a string representing some cells to create a list, and another which does a shallow copy of some cells
 */

import java.util.Iterator;
import java.util.LinkedList;

public class Cells implements Iterable<Card>, Cloneable{
	
	private LinkedList<Card> cells;
	private int numCells;
	
	public Cells(int size) {
		numCells = size;
		cells = new LinkedList<Card>();
	}
	
	//format: one line where each card String is separated by a space
	public Cells(int size, String sCells) {
		numCells = size;
		cells = new LinkedList<Card>();
		if(sCells.charAt(0)=='x') {return;}
		String[] sCards = sCells.split(" ");
		for(String sCard: sCards) {
			Card card = new Card(sCard);
			cells.add(card);
		}
	}
	
	//shallow copy
	public Cells(Cells cells) {
		this.cells = new LinkedList<Card>(cells.cells);
		this.numCells = cells.numCells;
	}
		
	public int getNumCells() {
		return numCells;
	}
	
	public boolean availableFreeCell() {
		return 0 < (numCells-cells.size());
	}
	
	public boolean isEmpty() {
		return cells.isEmpty();
	}
	
	public int getNumOccupied() {
		return cells.size();
	}
	
	//returns true if placement was successful
	public boolean placeCard(Card card) {
		if(cells.size()<numCells) {
			cells.add(card);
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean takeCard(Card card) {
		return cells.remove(card);
	}
	
	public boolean containsCard(Card card) {
		return cells.contains(card);
	}

	@Override
	public Iterator<Card> iterator() {
		return cells.listIterator();
	}
	
	//returns a deep copy
	@SuppressWarnings("unchecked")
	@Override
	public Object clone() throws CloneNotSupportedException {
		Cells cells = (Cells) super.clone();
		cells.cells = (LinkedList<Card>) cells.cells.clone();
		for(Card card: cells.cells) {
			card = (Card) card.clone();
		}
		return cells;
	}
	
	@Override
	public boolean equals(Object oCells) {
		Cells cells = (Cells) oCells;
		if(this.cells.size()!=cells.cells.size()) {
			return false;
		}
		for(Card card1: this.cells) {
			if(!cells.cells.contains(card1)) {
				return false;
			}
		}
		for(Card card2: cells.cells) {
			if(!this.cells.contains(card2)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		int hashNum = 1;
		for(Card card: cells) {
			hashNum *= card.hashCode();
		}
		return hashNum;
	}

	@Override
	public String toString() {
		String sCells = "";
		if(cells.isEmpty()) {return "x";}
		for(Card card: cells) {
			sCells += card.toString() + " ";
		}
		return sCells.substring(0, sCells.length()-1);
	}
	
}

package game;
/* this class represents a foundation in solitaire. It is basically a wrapper for the ArrayDeque class which
 * is used as a stack. it contains three constructors, one which initializes an empty deque, another which
 * uses a string representing a foundation to create a deque where left to right on the string represents top
 * to bottom of the deque, and another which does a shallow copy of a cascade
 */

import java.util.ArrayDeque;
import java.util.Iterator;

public class Foundation implements Iterable<Card>, Cloneable{

	private ArrayDeque<Card> pile;
	
	public Foundation() {
		pile = new ArrayDeque<Card>();
	}
	
	//format: one line where each card String is separated by a space
	public Foundation(String sFoundation) {
		pile = new ArrayDeque<Card>();
		if(sFoundation.charAt(0)=='x') {return;}
		String[] sCards = sFoundation.split(" ");
		for(String sCard: sCards) {
			Card card = new Card(sCard);
			pile.add(card);
		}
	}
	
	//makes a shallow copy
	public Foundation(Foundation foundation) {
		this.pile = new ArrayDeque<Card>(foundation.pile);
	}
	
	public ArrayDeque<Card> getPile() {
		return pile;
	}

	public void placeCard(Card card) {
		pile.push(card);
	}
	
	public Card viewCard() {
		return pile.peek();
	}
	
	public boolean isEmpty() {
		return pile.isEmpty();
	}
	
	//should only ever be used if reversing a move
	public Card removeTopCard() {
		return pile.pop();
	}

	@Override
	public Iterator<Card> iterator() {
		return pile.iterator();
	}
	
	//returns a deep copy
	@Override
	public Object clone() throws CloneNotSupportedException {
		Foundation foundation = (Foundation)super.clone();
		foundation.pile = (ArrayDeque<Card>) foundation.pile.clone();
		for(Card card: foundation.pile) {
			card = (Card) card.clone();
		}
		return foundation;
	}
	
	@Override
	public boolean equals(Object oFoundation) {
		Foundation foundation = (Foundation) oFoundation;
		if(this.pile.size()!=foundation.pile.size()) {
			return false;
		}
		Iterator<Card> foundIterator = foundation.pile.iterator();
		for(Card card1: this.pile) {
			Card card2 = foundIterator.next();
			if(!card1.equals(card2)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		int hashNum = 0;
		int i = 1;
		for(Card card: pile) {
			hashNum += i*card.hashCode();
			i *= 3;
		}
		return hashNum;
	}

	@Override
	public String toString() {
		String sFoundation = "";
		if(pile.isEmpty()) {return "x";}
		for(Card card: pile) {
			sFoundation += card.toString() + " ";
		}
		return sFoundation.substring(0, sFoundation.length()-1);
	}
}

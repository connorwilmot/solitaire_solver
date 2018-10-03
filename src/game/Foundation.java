package game;
/* this class represents a foundation in solitaire. It is basically a wrapper for the ArrayDeque class which
 * is used as a stack. it contains three constructors, one which initializes an empty deque, another which
 * uses a string representing a foundation to create a deque where left to right on the string represents top
 * to bottom of the deque, and another which does a shallow copy of a foundation
 */

import java.util.ArrayDeque;
import java.util.Iterator;

public class Foundation implements Iterable<Card>, Cloneable{

	private ArrayDeque<Card> foundation;
	
	public Foundation() {
		foundation = new ArrayDeque<Card>();
	}
	
	//format: one line where each card String is separated by a space
	//empty foundation represented by an "x"
	public Foundation(String sFoundation) {
		foundation = new ArrayDeque<Card>();
		if(sFoundation.charAt(0)=='x') {return;}
		String[] sCards = sFoundation.split(" ");
		for(String sCard: sCards) {
			Card card = new Card(sCard);
			foundation.add(card);
		}
	}
	
	//makes a shallow copy
	public Foundation(Foundation foundation) {
		this.foundation = new ArrayDeque<Card>(foundation.foundation);
	}
	
	public ArrayDeque<Card> getFoundation() {
		return foundation;
	}

	public void placeCard(Card card) {
		foundation.push(card);
	}
	
	public Card viewCard() {
		return foundation.peek();
	}
	
	public boolean isEmpty() {
		return foundation.isEmpty();
	}
	
	//should only ever be used if reversing a move
	public Card removeTopCard() {
		return foundation.pop();
	}

	@Override
	public Iterator<Card> iterator() {
		return foundation.iterator();
	}
	
	//returns a deep copy
	@Override
	public Object clone() throws CloneNotSupportedException {
		Foundation foundation = (Foundation)super.clone();
		foundation.foundation = (ArrayDeque<Card>) foundation.foundation.clone();
		for(Card card: foundation.foundation) {
			card = (Card) card.clone();
		}
		return foundation;
	}
	
	@Override
	public boolean equals(Object oFoundation) {
		Foundation foundation = (Foundation) oFoundation;
		if(this.foundation.size()!=foundation.foundation.size()) {
			return false;
		}
		Iterator<Card> foundIterator = foundation.foundation.iterator();
		for(Card card1: this.foundation) {
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
		for(Card card: foundation) {
			hashNum += i*card.hashCode();
			i *= 3;
		}
		return hashNum;
	}

	//if cascade is empty it is represented with the character 'x'
	@Override
	public String toString() {
		String sFoundation = "";
		if(foundation.isEmpty()) {return "x";}
		for(Card card: foundation) {
			sFoundation += card.toString() + " ";
		}
		return sFoundation.substring(0, sFoundation.length()-1);
	}
}

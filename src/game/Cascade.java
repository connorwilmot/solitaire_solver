package game;
/* this class represents a cascade in solitaire. It is basically a wrapper for the ArrayDeque class which
 * is used as a stack. it contains three constructors, one which initializes an empty deque, another which
 * uses a string representing a cascade to create a deque where left to right on the string represents top
 * to bottom of the deque, and another which does a shallow copy of a cascade
 */

import java.util.ArrayDeque;
import java.util.Iterator;

public class Cascade implements Iterable<Card>, Cloneable{
	
	private ArrayDeque<Card> cascade;
	
	public Cascade() {
		cascade = new ArrayDeque<Card>();
	}
	
	//format: one line where each card String is separated by a space
	//empty cascade represented by an "x"
	public Cascade(String sCascade) {
		cascade = new ArrayDeque<Card>();
		if(sCascade.charAt(0)=='x') {return;}
		String[] sCards = sCascade.split(" ");
		for(String sCard: sCards) {
			Card card = new Card(sCard);
			cascade.add(card);
		}
	}
	
	//shallow copy
	public Cascade(Cascade cascade) {
		this.cascade = new ArrayDeque<Card>(cascade.cascade);
	}

	public Card viewTopCard() {
		return cascade.peek();
	}
	
	public boolean isEmpty() {
		return cascade.isEmpty();
	}
	
	public int size() {
		return cascade.size();
	}
	
	public Card takeTopCard() {
		return cascade.pop();
	}
	
	public void placeCard(Card card) {
		cascade.push(card);
	}

	@Override
	public Iterator<Card> iterator() {
		return cascade.iterator();
	}
	
	//returns a deep copy
	@Override
	public Object clone() throws CloneNotSupportedException {
		Cascade cascade = (Cascade)super.clone();
		cascade.cascade = (ArrayDeque<Card>)cascade.cascade.clone();
		for(Card card: cascade.cascade) {
			card = (Card) card.clone();
		}
		return cascade;
	}
	
	@Override
	public boolean equals(Object oCascade) {
		Cascade cascade = (Cascade) oCascade;
		if(this.cascade.size()!=cascade.cascade.size()) {
			return false;
		}
		Iterator<Card> cascadeIterator = cascade.cascade.iterator();
		for(Card card1: this.cascade) {
			Card card2 = cascadeIterator.next();
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
		for(Card card: cascade) {
			hashNum += i*card.hashCode();
			i *= 5;
		}
		return hashNum;
	}

	//if cascade is empty it is represented with the character 'x'
	@Override
	public String toString() {
		String sCascade = "";
		if(cascade.isEmpty()) {return "x";}
		for(Card card: cascade) {
			sCascade += card.toString() + " ";
		}
		return sCascade.substring(0, sCascade.length()-1);
	}
}

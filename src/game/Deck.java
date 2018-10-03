package game;
/* this class represents a deck in solitaire. It is basically a wrapper for the ArrayDeque class which
 * is used as a stack. it contains three constructors, one which initializes an empty deque, another which
 * uses a string representing a deck to create a deque where left to right on the string represents top
 * to bottom of the deque, and another which does a shallow copy of a deck
 */

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import game.Card.Suit;

public class Deck implements Iterable<Card>, Cloneable{

	private ArrayDeque<Card> deck;
	private Random shuffler;
	
	public Deck() {
		deck = new ArrayDeque<Card>();
		shuffler = new Random();
	}

	//a string representing a deck top is left, card strings separated by a blank
	public Deck(String sDeck) {
		deck = new ArrayDeque<Card>();
		String[] sCards = sDeck.split(" ");
		for(String sCard: sCards) {
			Card card = new Card(sCard);
			deck.push(card);
		}
	}
	
	//shallow copy of deck, but deep copy of shuffler
	public Deck(Deck deck) {
		this.deck = new ArrayDeque<Card>(deck.deck);
		this.shuffler = new Random();
	}
	
	public Card drawCard() {
		return deck.pop();
	}
	
	public int getNumCards() {
		return deck.size();
	}
	
	public boolean isEmpty() {
		return deck.isEmpty();
	}
	
	public void makeStandardDeck() {
		deck = new ArrayDeque<Card>();
		for(Suit suit: Suit.values()) {
			for(int i=1; i<14; i++) {
				byte rank = (byte) i;
				Card card = new Card(suit, rank);
				deck.push(card);
			}
		}
	}
	
	public void shuffle() {
		//copies deck structure to cards
		ArrayList<Card> cards = new ArrayList<Card>(deck);
		deck = new ArrayDeque<Card>();
		while(!cards.isEmpty()) {
			//takes random card off deck copy 'cards' and puts it on 'deck'
			int randInt = shuffler.nextInt(cards.size());
			Card card = cards.remove(randInt);
			deck.push(card);
		}
	}

	@Override
	public Iterator<Card> iterator() {
		return deck.iterator();
	}
	
	//returns a deep copy
	@Override
	public Object clone() throws CloneNotSupportedException {
		Deck deck = (Deck)super.clone();
		deck.deck = (ArrayDeque<Card>) deck.deck.clone();
		for(Card card: deck.deck) {
			card = (Card) card.clone();
		}
		deck.shuffler = new Random();
		return deck;
	}
	
	@Override
	public boolean equals(Object oDeck) {
		Deck deck = (Deck) oDeck;
		if(this.deck.size()!=deck.deck.size()) {
			return false;
		}
		Iterator<Card> deckIterator = deck.deck.iterator();
		for(Card card1: this.deck) {
			Card card2 = deckIterator.next();
			if(!card1.equals(card2)) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public int hashCode() {
		int hashNum = 0;
		double i = 1;
		for(Card card: deck) {
			hashNum += i*card.hashCode();
			i *= 1.5;
		}
		return hashNum;
	}

	@Override
	public String toString() {
		String sDeck = "";
		for(Card card: deck) {
			sDeck += card.toString() + " ";
		}
		return sDeck.substring(0, sDeck.length()-1);
	}
}

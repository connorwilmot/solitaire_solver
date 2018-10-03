package game;
/* This class represents a card in solitaire, it has three constructors, one where the suit and rank are specified,
 * one where the card is specified with a string, and the last creates a clone from another card
 * This class also contains an enum declaration for Suits. The card can also be converted to a two character string
 */

public class Card implements Cloneable{

	public enum Suit{
		Clubs, Diamonds, Hearts, Spades
	}
	private Suit suit;
	//Jacks, Queens, and Kings are 11-13 respectively
	private byte rank;
	public static final int ACE = 1;
	public static final int JACK = 11;
	public static final int QUEEN = 12;
	public static final int KING = 13;
	
	public Card(Suit suit, byte rank){
		this.suit = suit;
		this.rank = rank;
	}
	
	//String must be in format "xn", where x is the first letter of the suit, and n is the rank(exceptions below)
	//input is presumed to be correct
	public Card(String sCard) {
		char cSuit = sCard.charAt(0);
		char cRank = sCard.charAt(1);
		switch (cSuit) {
		case 'c': suit = Suit.Clubs; break;
		case 'h': suit = Suit.Hearts; break;
		case 's': suit = Suit.Spades; break;
		case 'd': suit = Suit.Diamonds; break;
		default: break;
		}
		switch (cRank) {
		//special case for 10, character representing 10 is 0
		case '0': rank = 10; break;
		case 'J': rank = 11; break;
		case 'Q': rank = 12; break;
		case 'K': rank = 13; break;
		default: rank = (byte)(Character.getNumericValue(cRank)); break;
		}
	}
	
	public Card(Card card) {
		this.suit = card.suit;
		this.rank = card.rank;
	}

	public Suit getSuit() {
		return suit;
	}

	public byte getRank() {
		return rank;
	}
	
	public int compareRankTo(Card card) {
		return rank-card.rank;
	}
	
	public boolean isSameSuit(Card card) {
		return suit==card.suit;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	@Override
	public boolean equals(Object oCard) {
		Card card = (Card) oCard;
		if(this.isSameSuit(card)) {
			if(this.compareRankTo(card)==0) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public int hashCode() {
		int suitNum = suit.ordinal();
		return (suitNum+1)*rank;
	}
	
	//turns a card into a two character string representation
	@Override
	public String toString() {
		String sSuit = suit.toString();
		char suitAbbr = Character.toLowerCase(sSuit.charAt(0));
		String sRank;
		switch (rank) {
		case 10: sRank = "0"; break;
		case 11: sRank = "J"; break;
		case 12: sRank = "Q"; break;
		case 13: sRank = "K"; break;
		default: sRank = Byte.toString(rank); break;
		}
		return suitAbbr + sRank;
	}
	
}

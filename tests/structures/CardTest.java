package structures;

import static org.junit.Assert.*;
import org.junit.Test;

import structures.Card.Suit;

public class CardTest {

	@Test (timeout = 1000)
	public void testToString() {
		Card aceOfClubs = new Card(Suit.Clubs, (byte)1);
		assertEquals("String for an ace of Clubs should be \"c1\"", "c1", aceOfClubs.toString());
		Card threeOfDiamonds = new Card(Suit.Diamonds, (byte)3);
		assertEquals("String for a three of Diamonds should be \"d3\"", "d3", threeOfDiamonds.toString());
		Card fiveOfHearts = new Card(Suit.Hearts, (byte)5);
		assertEquals("String for a five of Hearts should be \"h5\"", "h5", fiveOfHearts.toString());
		Card sevenOfSpades = new Card(Suit.Spades, (byte)7);
		assertEquals("String for a seven of Spades should be \"s7\"", "s7", sevenOfSpades.toString());
		Card tenOfClubs = new Card(Suit.Clubs, (byte)10);
		assertEquals("String for a ten of Clubs should be \"c0\"", "c0", tenOfClubs.toString());
		Card jackOfDiamonds = new Card(Suit.Diamonds, (byte)11);
		assertEquals("String for a jack of Diamonds should be \"dJ\"", "dJ", jackOfDiamonds.toString());
		Card queenOfHearts = new Card(Suit.Hearts, (byte)12);
		assertEquals("String for a queen of Hearts should be \"hQ\"", "hQ", queenOfHearts.toString());
		Card kingOfSpades = new Card(Suit.Spades, (byte)13);
		assertEquals("String for a king of Spades should be \"sK\"", "sK", kingOfSpades.toString());
	}

	@Test (timeout = 1000)
	public void testFromString() {
		Card twoOfClubs = new Card("c2");
		assertEquals("For String c2, the suit should be Clubs", Suit.Clubs, twoOfClubs.getSuit());
		assertEquals("For String c2, the rank should be two(2)", (byte)2, twoOfClubs.getRank());
		Card fourOfDiamonds = new Card("d4");
		assertEquals("For String d4, the suit should be Diamonds", Suit.Diamonds, fourOfDiamonds.getSuit());
		assertEquals("For String d4, the rank should be four(4)", (byte)4, fourOfDiamonds.getRank());
		Card sixOfHearts = new Card("h6");
		assertEquals("For String h6, the suit should be Hearts", Suit.Hearts, sixOfHearts.getSuit());
		assertEquals("For String h6, the rank should be six(6)", (byte)6, sixOfHearts.getRank());
		Card eightOfSpades = new Card("s8");
		assertEquals("For String s8, the suit should be Spades", Suit.Spades, eightOfSpades.getSuit());
		assertEquals("For String s8, the rank should be eight(8)", (byte)8, eightOfSpades.getRank());
		Card tenOfClubs = new Card("c0");
		assertEquals("For String c0, the suit should be Clubs", Suit.Clubs, tenOfClubs.getSuit());
		assertEquals("For String c0, the rank should be ten(10)", (byte)10, tenOfClubs.getRank());
		Card jackOfDiamonds = new Card("dJ");
		assertEquals("For String dJ, the suit should be Diamonds", Suit.Diamonds, jackOfDiamonds.getSuit());
		assertEquals("For String dJ, the rank should be jack(11)", (byte)11, jackOfDiamonds.getRank());
		Card queenOfHearts = new Card("hQ");
		assertEquals("For String hQ, the suit should be Hearts", Suit.Hearts, queenOfHearts.getSuit());
		assertEquals("For String hQ, the rank should be queen(12)", (byte)12, queenOfHearts.getRank());
		Card kingOfSpades = new Card("sK");
		assertEquals("For String sK, the suit should be Spades", Suit.Spades, kingOfSpades.getSuit());
		assertEquals("For String sK, the rank should be king(13)", (byte)13, kingOfSpades.getRank());
	}
}

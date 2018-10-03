package game;
/* A class representing a solitaire move. A move has three fields; two fields representing where the card came from
 * and where it is going in numbers (0-9 are cascades, 10 is a cell move, 11 is a foundation move),
 * and a field representing the card itself.
 */

public class Move {
	
	//isn't used in move class as enums are represented with integers
	public enum MoveLocation{
		Cascade0, Cascade1, Cascade2, Cascade3, Cascade4,
		Cascade5, Cascade6, Cascade7, Cascade8, Cascade9,
		Cells, Foundations
	}
	private byte from;
	private Card card;
	private byte to;

	public Move(byte from, Card card, byte to) {
		this.from = from;
		this.card = card;
		this.to = to;
	}
	
	public Move(MoveLocation from, Card card, MoveLocation to) {
		this.from = (byte)from.ordinal();
		this.card = card;
		this.to = (byte)to.ordinal();
	}
	
	//deep copy
	public Move(Move move) {
		this.from = move.from;
		this.card = new Card(move.card);
		this.to = move.to;
	}

	public byte getFrom() {
		return from;
	}
	
	public Card getCard() {
		return card;
	}

	public byte getTo() {
		return to;
	}
	
	//not implemented yet
	public boolean isLegalMove(Solitaire game) {
		return true;
	}
	
	public void reverseMove() {
		byte temp = from;
		from = to;
		to = temp;
	}
	
	public boolean isReverseMove(Move move) {
		if(!this.card.equals(move.card)) {
			return false;
		}
		else if(this.from!=move.to) {
			return false;
		}
		else if(this.to!=move.from) {
			return false;
		}
		else {
			return true;
		}
	}
	
	@Override
	public String toString() {
		String move = "";
		move += Byte.toString(from) + "-";
		move += Byte.toString(to) + "(";
		move += card.toString() + ")";
		return move;
	}
	
}

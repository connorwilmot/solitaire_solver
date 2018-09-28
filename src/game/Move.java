package game;
/* a class representing a solitaire move. A move has three fields; two fields representing where the card came from
 * in numbers, and a field representing the card itself.
 */

public class Move {
	
	private byte from;
	private Card card;
	private byte to;

	//0-9 are cascades, 10 is a cell move, 11 is a foundation move
	public Move(byte from, Card card, byte to) {
		this.from = from;
		this.card = card;
		this.to = to;
	}
	
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

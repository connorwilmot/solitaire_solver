package heuristics;
/* heuristic which calculates the number of cards covering each card that must be put on the foundation next.
 * This is a minimum number of moves which must be made in order to advance the game
 */

import java.util.LinkedList;

import game.Card;
import game.Cascade;
import game.Foundation;
import game.Move;
import game.Solitaire;
import structures.Pair;

public class CardsCoverFoundHeuristic extends SolitaireHeuristic {

	@Override
	public int compare(Pair<Solitaire, LinkedList<Move>> gAndM0, Pair<Solitaire, LinkedList<Move>> gAndM1) {
		Solitaire game0 = gAndM0.getKey();
		Solitaire game1 = gAndM1.getKey();
		int heuristic0 = calculateHeuristic(game0);
		int heuristic1 = calculateHeuristic(game1);
		int diff = heuristic0 - heuristic1;
		if(diff==0) {
			int numMove0 = gAndM0.getValue().size();
			int numMove1 = gAndM1.getValue().size();
			return numMove0 - numMove1;
		}
		else {
			return diff;
		}
	}

	@Override
	public int calculateHeuristic(Solitaire game) {
		int heuristic = 0;
		for(Cascade cascade: game.getTableau()) {
			int numCardsCovering = 0;
			for(Card card: cascade) {
				int foundIndex = card.getSuit().ordinal();
				Foundation foundation = game.getFoundations()[foundIndex];
				Card cardOnFound = foundation.viewCard();
				if(cardOnFound!=null) {
					if(1==card.compareRankTo(cardOnFound)) {
						heuristic += numCardsCovering;
					}
				}
				else {
					if(card.getRank()==1) {
						heuristic += numCardsCovering;
					}
				}
				numCardsCovering++;
			}
		}
		return heuristic;
	}

}

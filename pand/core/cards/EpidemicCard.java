package pand.core.cards;

import java.util.List;

import pandemie.core.ITurn;
import pandemie.core.cards.IEpidemic;
import pandemie.core.cards.IPlayerCard;
import pandemie.core.cards.IPropagationCard;

public class EpidemicCard implements IEpidemic, IPlayerCard {

	// quand une carte épidémie est tirée, elle est jouée immédiatement (avant la phase propagation et après les 4 actions du joueur ) 
	public void play(ITurn t) {
		t.getBoard().epidemic();
	}

	@Override
	public String toString() {
		return "EpidemicCard";
	}
	
	
	

}

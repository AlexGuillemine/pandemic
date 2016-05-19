package pand.core.cards;

import pandemie.core.ITurn;
import pandemie.core.cards.IKeepableCard;
import pandemie.core.cards.IPlayableCard;

public class QuietNight implements IKeepableCard, IPlayableCard {

	@Override
	public void play(ITurn t) {
		t.setPropagationDisabled(true);
	}

	@Override
	public String toString() {
		return "QuietNight";
	}
	
	
	
}

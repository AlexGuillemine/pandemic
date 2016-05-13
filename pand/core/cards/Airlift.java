package pand.core.cards;

import pandemie.core.ICity;
import pandemie.core.IPlayer;
import pandemie.core.ITurn;
import pandemie.core.cards.IAirlift;
import pandemie.core.cards.IKeepableCard;
import pandemie.core.cards.IPlayableCard;

public class Airlift implements IAirlift, IKeepableCard, IPlayableCard {

	private IPlayer choosen;
	private ICity destination;
	
	
	@Override
	public void play(ITurn t) {
		ICity currentLocation = choosen.getLocation();
		currentLocation.removePlayer(choosen);
		destination.addPlayer(choosen);
		choosen.setLocation(destination);
		t.getPlayer().removeCard(this);
	}

	public void setPlayerToMove(IPlayer choosen) {
		this.choosen = choosen;

	}

	public void setDestination(ICity destination) {
		this.destination = destination;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((choosen == null) ? 0 : choosen.hashCode());
		result = prime * result + ((destination == null) ? 0 : destination.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Airlift other = (Airlift) obj;
		if (choosen == null) {
			if (other.choosen != null)
				return false;
		} else if (!choosen.equals(other.choosen))
			return false;
		if (destination == null) {
			if (other.destination != null)
				return false;
		} else if (!destination.equals(other.destination))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return " Airlift ";
	}

	
	
}

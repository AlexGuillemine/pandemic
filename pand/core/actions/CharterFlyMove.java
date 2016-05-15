package pand.core.actions;

import pand.core.cards.PlayerCityCard;
import pandemie.core.ICity;
import pandemie.core.IPlayer;
import pandemie.core.ITurn;
import pandemie.core.actions.IMoveAction;

public class CharterFlyMove implements IMoveAction {
	
	private ICity possibleDestination;
	
	public CharterFlyMove(ICity possibleDestination){
		this.possibleDestination = possibleDestination; 
	}
	
	public int execute(ITurn t) {
		IPlayer currentPlayer = t.getPlayer();
		ICity currentCity = currentPlayer.getLocation();
		currentCity.removePlayer(currentPlayer);
		currentPlayer.setLocation(possibleDestination);
		possibleDestination.addPlayer(currentPlayer);
		currentPlayer.removeCard(new PlayerCityCard(currentCity));
		return 1;
	}

	public String toString() {
		return "CharterFlyMove [Destination=" + possibleDestination + "]";
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CharterFlyMove other = (CharterFlyMove) obj;
		if (possibleDestination == null) {
			if (other.possibleDestination != null)
				return false;
		} else if (!possibleDestination.equals(other.possibleDestination))
			return false;
		return true;
	}
	
	
	

}

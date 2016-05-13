package pand.core.actions;


import pandemie.core.ICity;
import pandemie.core.IPlayer;
import pandemie.core.ITurn;
import pandemie.core.actions.IMoveAction;

public class ShutlleMove implements IMoveAction {

	private ICity possibleDestination ;
	
	public ShutlleMove(ICity labCitie){
		possibleDestination = labCitie;
	}
	
	public int execute(ITurn t) {
		IPlayer currentPlayer = t.getPlayer();
		ICity currentCity = currentPlayer.getLocation();
		currentCity.removePlayer(currentPlayer);
		currentPlayer.setLocation(possibleDestination);
		possibleDestination.addPlayer(currentPlayer);
		return 1;
	}

	public String toString() {
		return "ShutlleMove [Destination=" + possibleDestination + "]";
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ShutlleMove other = (ShutlleMove) obj;
		if (possibleDestination == null) {
			if (other.possibleDestination != null)
				return false;
		} else if (!possibleDestination.equals(other.possibleDestination))
			return false;
		return true;
	}
	
	
	
	

}

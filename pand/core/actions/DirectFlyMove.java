package pand.core.actions;


import pandemie.core.ICity;
import pandemie.core.IPlayer;
import pandemie.core.ITurn;
import pandemie.core.actions.IMoveAction;
import pandemie.core.cards.IPlayerCityCard;

public class DirectFlyMove implements IMoveAction {
	
	private IPlayerCityCard destination;
	
	public DirectFlyMove(IPlayerCityCard destination){
		this.destination = destination;
	}
	
	public int execute(ITurn t) {
		IPlayer currentPlayer = t.getPlayer();
		ICity currentCity = currentPlayer.getLocation();
		ICity destination = this.destination.getCity();
		currentCity.removePlayer(currentPlayer);
		currentPlayer.setLocation(destination);
		destination.addPlayer(currentPlayer);
		currentPlayer.removeCard(this.destination);
		return 1;
	}

	@Override
	public String toString() {
		return "DirectFlyMove [destination=" + destination.toString() + "]";
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DirectFlyMove other = (DirectFlyMove) obj;
		if (destination == null) {
			if (other.destination != null)
				return false;
		} else if (!destination.equals(other.destination))
			return false;
		return true;
	}
	
	
	

}

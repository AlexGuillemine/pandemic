package pand.core.actions;

import pandemie.core.ICity;
import pandemie.core.IPlayer;
import pandemie.core.ITurn;
import pandemie.core.actions.IMoveAction;

public class SimpleMove implements IMoveAction {

	private ICity destination;
	
	public SimpleMove(ICity destination){
		this.destination = destination;
	}
	
	public int execute(ITurn t) {
		IPlayer curentPlayer = t.getPlayer();
		curentPlayer.getLocation().removePlayer(curentPlayer);
		curentPlayer.setLocation(destination);
		destination.addPlayer(curentPlayer);
		return 1;
	}
	
	public String toString(){
		return "Move to "+destination.getName().toString();
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SimpleMove other = (SimpleMove) obj;
		if (destination == null) {
			if (other.destination != null)
				return false;
		} else if (!destination.equals(other.destination))
			return false;
		return true;
	}
	
	
	

}

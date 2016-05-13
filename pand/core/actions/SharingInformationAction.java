package pand.core.actions;

import pandemie.core.IAction;
import pandemie.core.ICity;
import pandemie.core.IPlayer;
import pandemie.core.ITurn;
import pandemie.core.cards.IKeepableCard;
import pandemie.core.cards.IPlayerCard;
import pandemie.core.cards.IPlayerCityCard;

public class SharingInformationAction implements IAction {

	private IPlayer emettor;
	private IPlayer receptor;
	private ICity currentCity;
	
	public SharingInformationAction(IPlayer emettor, IPlayer receptor, ICity currentCity){
		this.emettor= emettor;
		this.receptor = receptor;
		this.currentCity= currentCity;
	}
	
	
	public int execute(ITurn t) {
		for(IKeepableCard currentCard : emettor.cardsToShare()){
			IPlayerCityCard currentCityCard = (IPlayerCityCard)currentCard;
			if(currentCityCard .getCity().equals(currentCity)){
				emettor.removeCard(currentCard);
				receptor.addCard(currentCard);
				return 1;
			}
		}
		return 0;
	}
	
	public String toString(){
		return emettor.toString()+" donne "+currentCity.toString()+" à "+receptor.toString();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SharingInformationAction other = (SharingInformationAction) obj;
		if (currentCity == null) {
			if (other.currentCity != null)
				return false;
		} else if (!currentCity.equals(other.currentCity))
			return false;
		if (emettor == null) {
			if (other.emettor != null)
				return false;
		} else if (!emettor.equals(other.emettor))
			return false;
		if (receptor == null) {
			if (other.receptor != null)
				return false;
		} else if (!receptor.equals(other.receptor))
			return false;
		return true;
	}
	
	

}

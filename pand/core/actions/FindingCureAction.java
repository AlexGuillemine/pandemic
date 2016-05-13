package pand.core.actions;

import java.util.List;
import java.util.Map;

import pand.core.Board;
import pandemie.core.ICard;
import pandemie.core.IPlayer;
import pandemie.core.ITurn;
import pandemie.core.actions.IFindingCureAction;
import pandemie.core.cards.IPlayerCityCard;
import pandemie.core.diseases.Disease;
import pandemie.core.diseases.DiseaseType;

public class FindingCureAction implements IFindingCureAction {

	
	private List<IPlayerCityCard> cards;
	private DiseaseType diseaseToCure;
	private IPlayer currentPlayer;
	
	public FindingCureAction(DiseaseType diseaseToCure){
		this.diseaseToCure = diseaseToCure; 
	}
	
	public int execute(ITurn t) {
		currentPlayer = t.getPlayer();
		if(this.checkCardsType()){
			Board board = (Board)t.getBoard();
			Map<DiseaseType, Disease> diseases = board.getDisease();
			diseases.get(diseaseToCure).setCureFound(true);
			t.getPlayer().removeAllCards(cards);
			return 1;
		}else{
			return 0;
		}
	}

	public boolean checkCardsType() {
		if(cards.size()<currentPlayer.nbCardsRequiredToCure()){
			return false;
		}
		
		for(IPlayerCityCard card : cards){
			if(!diseaseToCure.equals(card.getCity().getType())){
				return false;
			}
		}
		return true;
	}

	public List<IPlayerCityCard> getCards() {
		return cards;
	}

	public void setCards(List<IPlayerCityCard> res) {
		cards = res;
	}

	@Override
	public String toString() {
		String retour = "use : ";
		for(IPlayerCityCard card: cards){
			retour += card.toString()+", ";
		}
		retour +="to cure "+diseaseToCure.toString();
		return retour;
	}

	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FindingCureAction other = (FindingCureAction) obj;
		if (diseaseToCure != other.diseaseToCure)
			return false;
		return true;
	}
	
	

}

package pand.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import pand.core.actions.DirectFlyMove;
import pand.core.actions.FindingCureAction;
import pand.core.actions.SharingInformationAction;
import pand.core.actions.ShutlleMove;
import pand.core.actions.CharterFlyMove;
import pand.core.actions.SimpleMove;
import pand.core.cards.EpidemicCard;
import pand.core.cards.PlayerCityCard;
import pandemie.core.IAction;
import pandemie.core.IBoard;
import pandemie.core.ICard;
import pandemie.core.ICity;
import pandemie.core.IGame;
import pandemie.core.IPlayer;
import pandemie.core.ITurn;
import pandemie.core.ResearchLab;
import pandemie.core.Role;
import pandemie.core.actions.BuildResearchLabAction;
import pandemie.core.actions.IFindingCureAction;
import pandemie.core.actions.IMoveAction;
import pandemie.core.actions.TreatingAction;
import pandemie.core.cards.IAirlift;
import pandemie.core.cards.IKeepableCard;
import pandemie.core.cards.IPlayableCard;
import pandemie.core.cards.IPlayerCard;
import pandemie.core.cards.IPlayerCityCard;
import pandemie.core.diseases.Disease;
import pandemie.core.diseases.DiseaseType;

public class Player implements IPlayer {


	private Role role;
	private Board board;
	private List<IKeepableCard> cards;
	private ICity location;
	private int maximumNumberOfCards;
	private int numberOfActions;
	private List<IAction> possibleSpecialEventCardActions;


	public Player(Role role, IBoard board){
		this.role = role;
		this.board = (Board)board;
		ICity atlanta = this.board.getAtlantaCity();
		this.location = atlanta;
		atlanta.addPlayer(this);

		cards =  new ArrayList<IKeepableCard>(); 
		if(role.equals(Role.Archivist)){
			maximumNumberOfCards = 8;
		}else{
			maximumNumberOfCards = 7;
		}
		
		if(role.equals(Role.Generalist)){
			numberOfActions = 5;
		}else{
			numberOfActions = 4;
		}
		//total number of actions allowed per turn. the role can change this number (default : 4 )
		possibleSpecialEventCardActions = new ArrayList<IAction>() ;
	}

	public void addCard(IKeepableCard card) {
		cards.add(card);
	}

	public List<IPlayerCityCard> cardsToShare() {
		List<IPlayerCityCard> cardsToShare = new ArrayList<IPlayerCityCard>();
		for(IKeepableCard card : cards){
			if(card instanceof IPlayerCityCard){
				cardsToShare.add((IPlayerCityCard)card);
			}
		}
		return cardsToShare;
	}

	public List<IKeepableCard> getCards() {
		return cards;
	}

	public ICity getLocation() {
		return location;
	}

	public int getMaximumNumberOfCards() {
		return maximumNumberOfCards;
	}

	public int getNumberOfActions() {
		return numberOfActions;
	}

	public List<IAction> getPossibleActions(IGame game) {	
		List<IAction> possibleActions = new ArrayList<IAction>();
		possibleActions.addAll(this.getPossibleMoveActions(game));
		possibleActions.addAll(this.getPossibleTreatingActions());
		possibleActions.addAll(this.getPossibleSharingInformationActions(board));
		possibleActions.addAll(this.getPossibleFindingCureAction());
		possibleActions.addAll(this.getPossibleBuildResearchLabAction());
		return possibleActions;
	}

	public List<IMoveAction> getPossibleMoveActions(IGame game) {
		List<IMoveAction> possibleMoveActions = new ArrayList<IMoveAction>();
		Set<ICity> possibleDestinations = location.getNeighbors();
		for(ICity possibleDestination : possibleDestinations){
			possibleMoveActions.add(new SimpleMove(possibleDestination));
		}
		for(IPlayerCityCard possibleDestination : cardsToShare()){
			possibleMoveActions.add(new DirectFlyMove(possibleDestination));
		}
		if(hasCard(location)){
			for(ICity possibleDestination : board.getCities()){
				possibleMoveActions.add(new CharterFlyMove(possibleDestination));
			}
		}
		if(location.hasResearchLab()){
			List<ICity> labCities = ResearchLab.getInstance().getLabCities();
			for(ICity labCitie : labCities){
				if(!location.equals(labCitie)){
					possibleMoveActions.add(new ShutlleMove(labCitie));
				}
			}
		}
		return possibleMoveActions;
	}

	public List<IFindingCureAction> getPossibleFindingCureAction() {

		List<IFindingCureAction> possibleFindingCureAction = new ArrayList<IFindingCureAction>(); 
		Map<DiseaseType,Disease> disease = board.getDisease();
		if(location.hasResearchLab()){
			for(DiseaseType diseaseType : DiseaseType.values()){
				if(howManyCardOfType(diseaseType)>=nbCardsRequiredToCure() && !disease.get(diseaseType).isCureFound()){
					FindingCureAction findingCureAction = new FindingCureAction(diseaseType);
					List<IPlayerCityCard> cardsToCure = new ArrayList<IPlayerCityCard>();
					int nbCardsToCure = 0;
					for(IPlayerCityCard potentialCardToCure : cardsToShare()){
						if(potentialCardToCure.hasType(diseaseType) && nbCardsToCure < nbCardsRequiredToCure()){
							cardsToCure.add(potentialCardToCure);
							nbCardsToCure++;
						}
					}
					findingCureAction.setCards(cardsToCure);
					possibleFindingCureAction.add(findingCureAction);
				}
			}
		}
		return possibleFindingCureAction;
	}

	public List<IAction> getPossibleSharingInformationActions(IBoard b) {
		List<IAction> possibleSharingInformationActions = new ArrayList<IAction>();
		SharingInformationAction sharingInformationAction;
		IPlayer researcher = getResearcherInLocation();
		Set<IPlayer> playersInCity = location.getPlayers(); 
		if(playersInCity.size()<2){
			return possibleSharingInformationActions;
		}else{
			for(IPlayer playerInCity : playersInCity){
				if(!this.equals(playerInCity)){
					if(role.equals(Role.Researcher)){
						for(IPlayerCityCard card : cardsToShare()){
							sharingInformationAction = new SharingInformationAction(this, playerInCity, card.getCity());
							possibleSharingInformationActions.add(sharingInformationAction);
						}
					}else if(this.cardsToShare().contains(new PlayerCityCard(location))){
						sharingInformationAction = new SharingInformationAction(this, playerInCity, location);
						possibleSharingInformationActions.add(sharingInformationAction);
					}
					if(playerInCity.cardsToShare().contains(new PlayerCityCard(location))){
						sharingInformationAction = new SharingInformationAction(playerInCity, this, location);
						possibleSharingInformationActions.add(sharingInformationAction);
					}
				}
			}
			if(hasResearcherInLocation() && !getResearcherInLocation().equals(this)){
				for(IPlayerCityCard card : researcher.cardsToShare()){
					sharingInformationAction = new SharingInformationAction(researcher, this, card.getCity());
					possibleSharingInformationActions.add(sharingInformationAction);
				}
			}
			return possibleSharingInformationActions;
		}
	}

	public List<IAction> getPossibleSpecialEventCardActions() {
		return possibleSpecialEventCardActions;
	}

	public List<IAction> getPossibleTreatingActions() {
		List<IAction> possibleTreatingActions =  new ArrayList<IAction>(); 
		for(DiseaseType diseaseType : DiseaseType.values()){
			if(location.getDiseaseCubes(diseaseType)>0){
				possibleTreatingActions.add(new TreatingAction(diseaseType)); 
			}
		}
		return(possibleTreatingActions);
	}


	public List<IAction> getPossibleBuildResearchLabAction() {
		List<IAction> possibleBuildResearchLabAction = new ArrayList<IAction>(); 
		if(!location.hasResearchLab()){
			if(hasCard(location)){
				possibleBuildResearchLabAction.add(new BuildResearchLabAction(location));
			}else if(role.equals(Role.OperationsExpert)){
				possibleBuildResearchLabAction.add(new BuildResearchLabAction(location));
			}
		}
		return possibleBuildResearchLabAction;
	}

	public void buildResearchLab(ITurn t) {
		ResearchLab researchLab = ResearchLab.getInstance();
		researchLab.createResearchLab(location);
		if(!role.equals(Role.OperationsExpert)){
			removeCard(new PlayerCityCard(location));
		}
		

	}

	public Role getRole() {
		return role;
	}

	public List<IPlayableCard> getSpecialEventCards() {
		List<IPlayableCard> specialEventCards = new ArrayList<IPlayableCard>();
		for(IKeepableCard card : cards){
			if(card instanceof IAirlift){
				specialEventCards.add((IPlayableCard)card);
			}
		}
		return specialEventCards;
	}

	public boolean hasCard(ICity c) {
		List<IPlayerCityCard> playerCityCards = cardsToShare();
		for(IPlayerCityCard playerCityCard : playerCityCards){
			if(playerCityCard.getCity().equals(c)){
				return true;
			}
		}
		return false;
	}

	public boolean hasCard(IPlayerCard c) {
		if(cards.contains(c)){
			return true;
		}
		return false;
	}

	public boolean hasCard(IKeepableCard c) {
		if(cards.contains(c)){
			return true;
		}
		return false;
	}

	public boolean hasCards(List<? extends IKeepableCard> toDiscard) {
		if(cards.containsAll(toDiscard)){
			return true;
		}
		return false;
	}

	public boolean hasSomeThingToShare(IBoard b) {
		ICity currentLocation = getLocation();
		if(hasCard(currentLocation) || hasResearcherInLocation()){
			return true;
		}else{
			return false;
		}
	}

	public int howManyCardOfType(DiseaseType type) {
		int numberCardOftype = 0;
		for(IPlayerCityCard playerCityCard : cardsToShare()){
			if(playerCityCard.hasType(type)){
				numberCardOftype ++;
			}
		}
		return numberCardOftype;
	}

	public int nbCardsRequiredToCure() {
		if(role.equals(Role.Scientist)){
			return 4;
		}else{
			return 5;
		}

	}

	public void removeAllCards(List<? extends IKeepableCard> toDiscard) {
		for(IPlayerCard currentCard : cardsToShare()){
			if(toDiscard.contains(currentCard)){
				cards.remove(currentCard);
				board.discardCard(currentCard);
			}
		}

	}

	public void removeCard(IKeepableCard card) {
		for(IPlayerCard currentCard : cardsToShare()){
			if(card.equals(currentCard)){
				cards.remove(currentCard);
				board.discardCard(currentCard);
			}
		}
	}

	public void setLocation(ICity city) {
		this.location = city;

	}

	public boolean tooManyCards() {
		if(cards.size()>maximumNumberOfCards){
			return true;
		}else{
			return false;
		}
	}

	public void treatDisease(DiseaseType dt) {
		ICity currentLocation = this.getLocation();
		Map<DiseaseType,Disease> disease = board.getDisease();
		if(disease.get(dt).isCureFound() || this.role.equals(Role.Medic)){
			currentLocation.removeDeseaseCube(dt, currentLocation.getDiseaseCubes(dt));
		}else{
			currentLocation.removeDeseaseCube(dt, 1);
		}

	}

	public String toStringMain(){
		String retour = "";
		for(ICard card : this.cards){
			if(card instanceof EpidemicCard){
				retour = retour + ((EpidemicCard)card).toString() + "\n";
			}else if(card instanceof PlayerCityCard){
				retour = retour + ((PlayerCityCard)card).toString() + "\n";
			}
		}
		return(retour);
	}

	private boolean hasResearcherInLocation(){
		Set<IPlayer> players = location.getPlayers();
		for(IPlayer player : players){
			if(player.getRole().equals(Role.Researcher)){
				return true;
			}
		}
		return false;
	}

	private IPlayer getResearcherInLocation(){
		Set<IPlayer> players = location.getPlayers();
		for(IPlayer player : players){
			if(player.getRole().equals(Role.Researcher)){
				return player;
			}
		}
		return null;
	}

	public String toString(){
		return role.toString();
	}

}

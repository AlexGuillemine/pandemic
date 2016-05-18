package pand.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import javax.swing.JOptionPane;
import pand.core.cards.Airlift;
import pand.core.cards.EpidemicCard;
import pand.core.cards.PlayerCityCard;
import pand.core.cards.PropagationCard;
import pand.core.cards.QuietNight;
import pandemie.core.CityName;
import pandemie.core.Difficulty;
import pandemie.core.IBoard;
import pandemie.core.ICard;
import pandemie.core.ICity;
import pandemie.core.IInfectionRate;
import pandemie.core.ResearchLab;
import pandemie.core.cards.IAirlift;
import pandemie.core.cards.IEpidemic;
import pandemie.core.cards.IPlayerCard;
import pandemie.core.cards.IPropagationCard;
import pandemie.core.diseases.BlackDisease;
import pandemie.core.diseases.BlueDisease;
import pandemie.core.diseases.Disease;
import pandemie.core.diseases.DiseaseType;
import pandemie.core.diseases.RedDisease;
import pandemie.core.diseases.YellowDisease;
import pandemie.core.exceptions.LooseException;
import pandemie.impl.InfectionRate;
import utliitaires.ReadCVS;

public class Board implements IBoard {

	public final static int OUTBREAKS_LIMIT = 8;

	// attributs du plateau
	private int outBreaks; // niveau d'éclosion
	private List<IPropagationCard> propagationDiscard;
	private List<IPropagationCard> propagationStack;
	private List<IPlayerCard> playerStack;
	private List<IPlayerCard> playerDiscard;
	private IInfectionRate infectionRate; // vitesse de propagation
	private Set<ICity> villes;
	private Set<ICity> citiesWhoCanEclose; 
	private Difficulty difficulty;
	private List<ICard> removedFromGame; // à ajouter dans l'UML
	private int nbreJoueurs; 
	private ResearchLab researchLab; // pour gérer les centres de recherches 
	private List<IEpidemic> epidemicCards;
	private ICity atlanta;
	private Map<DiseaseType,Disease> disease;
	
	// constructeurs de board
	public Board(Difficulty diff, int nbreJoueurs){
		// création des villes
		this.difficulty = diff;
		this.nbreJoueurs = nbreJoueurs;

		// instanciation des piles et des défausses
		propagationDiscard = new ArrayList<IPropagationCard>();
		playerDiscard = new ArrayList<IPlayerCard>();
		playerStack = new ArrayList<IPlayerCard>();
		propagationStack = new ArrayList<IPropagationCard>();
		removedFromGame = new ArrayList<ICard>();
		


		// création des villes
		villes = new HashSet<ICity>();
		this.setVilles();
		
		// villes qui peuvent éclore
		citiesWhoCanEclose = new HashSet<ICity>();
		citiesWhoCanEclose.addAll(villes);

		// mise en place des villes voisines
		this.setVoisins();

		// on positionne le compteur d'éclosions à 0
		this.outBreaks = 0;

		// on initialise le marqueur d'infections sur le premier index
		this.infectionRate = new InfectionRate();


		// création des cartes propagations
		for(ICity currentCity : villes){
			propagationStack.add(new PropagationCard(currentCity));
		}
		Collections.shuffle(propagationStack);

		// création des cartes villes
		for(ICity currentCity : villes){
			playerStack.add(new PlayerCityCard(currentCity));
		}
		Collections.shuffle(playerStack);

		// création des cartes spéciales (une pour le moment airlift )
		IAirlift airlift = new Airlift();
		QuietNight quietNight = new QuietNight(); 
		playerStack.add((IPlayerCard)airlift);
		playerStack.add((IPlayerCard)quietNight);
		Collections.shuffle(propagationStack);

		atlanta = getAtlantaCity();
		
		// création du lab center d'atlanta 
		researchLab = ResearchLab.getInstance();
		researchLab.createResearchLab(getAtlantaCity());
		
		// crétion des maladies
		
		disease = new HashMap<DiseaseType, Disease>();
		disease.put(DiseaseType.BLACK, BlackDisease.getDisease());
		disease.put(DiseaseType.BLUE, BlueDisease.getDisease());
		disease.put(DiseaseType.RED, RedDisease.getDisease());
		disease.put(DiseaseType.YELLOW, YellowDisease.getDisease());
		

	}


	@Override
	public void addEpidemiesToStack() {
		Map<Difficulty, Integer> nbresEpidemicCards = new HashMap<Difficulty, Integer>(); 
		nbresEpidemicCards.put(Difficulty.NOVICE, 4);
		nbresEpidemicCards.put(Difficulty.NORMAL, 5);
		nbresEpidemicCards.put(Difficulty.EXPERT, 6);
		nbresEpidemicCards.put(Difficulty.LEGENDARY, 7);
		int nbreEpidemicCards = nbresEpidemicCards.get(this.difficulty);
		for(int i = 0;i<nbreEpidemicCards;i++){
			playerStack.add(new EpidemicCard());
			Collections.shuffle(playerStack);
		}
	}

	@Override
	public void discardAll(Collection<? extends IPlayerCard> toDiscard) {
		playerDiscard.addAll(0,toDiscard);
	}

	@Override
	public void discardCard(IPlayerCard card) {
		playerDiscard.add(card);
	}

	@Override
	public IPlayerCard drawPlayerCard() {
		IPlayerCard drawedPlayCard = playerStack.remove(0);
		return drawedPlayCard;
	}

	@Override
	public IPropagationCard drawPropagationCard() {
		IPropagationCard drawedProCard = propagationStack.remove(0);
		return drawedProCard;
	}

	@Override
	public void epidemic() {
		citiesWhoCanEclose.addAll(villes);
		int nbreCubes = 0;
		
		// on augmente la vitesse de propagation
		infectionRate.increaseInfectionRate();

		// on tire la carte située sous la pioche propagation et on place 3 cubes maladies sur cette ville
		IPropagationCard lastIPropagationCard = propagationStack.remove(propagationStack.size()-1); // la dernière carte est celle à la fin de la liste
		ICity cityToInfect = lastIPropagationCard.getCity();
		DiseaseType typeCityToInfect = cityToInfect.getType();
		if(!disease.get(typeCityToInfect).isEradicated()){
			nbreCubes = cityToInfect.addDiseaseCubes(typeCityToInfect, 3);
			removeDiseasePiece(3, typeCityToInfect);
			if(nbreCubes ==4){
				eclosion(cityToInfect);
			}
		}else{
			JOptionPane jop1 = new JOptionPane();
			jop1.showMessageDialog(null, "La carte "+lastIPropagationCard+" n' a aucun effet car la maladie correspondant est éradiquée", "Remède trouvée", JOptionPane.INFORMATION_MESSAGE);
		}
		
		this.discardPropagationCard(lastIPropagationCard);

		// on mélange la défausse propgation et on la remet sur le sommet de la pioche propagation

		Collections.shuffle(propagationDiscard);
		propagationStack.addAll(0, propagationDiscard);
		propagationDiscard.clear();

	}

	public Set<ICity> getCities() {
		return this.villes;
	}

	public Difficulty getDifficulty() {
		return this.difficulty;
	}

	public IInfectionRate getInfectionRate() {
		return this.infectionRate;
	}

	public int getOutBreaks() {
		return outBreaks;
	}

	public List<IPropagationCard> getPropagationDiscard() {
		return propagationDiscard;
	}

	public List<IPropagationCard> getPropagationStack() {
		return propagationStack;
	}

	public List<IPlayerCard> getPlayerDiscard() {
		return playerDiscard;
	}

	public List<IPlayerCard> getPlayerStack() {
		return playerStack;
	}

	public void discardPropagationCard(IPropagationCard card) {
		propagationDiscard.add(card);
	}

	public void addToPropagationStack(IPropagationCard card) {		
		propagationStack.add(0,card);
	}
	DiseaseType typeCityToInfect;
	int nbreCubesFinales ;
	public void initialInfections() {
		// on tire 6 cartes de la pile propagation
		int nbreCubes =0;
		for(int i=1;i<7;i++){

			if(i<=3){
				nbreCubes = 3;
			}else if(i<=5){
				nbreCubes = 2;
			}else if(i<=6){
				nbreCubes = 1;
			}
			
			IPropagationCard currentPropagationCard = this.drawPropagationCard();
			ICity cityToInfect = currentPropagationCard.getCity();
			typeCityToInfect = cityToInfect.getType();
			nbreCubesFinales = cityToInfect.addDiseaseCubes(cityToInfect.getType(), nbreCubes);
			removeDiseasePiece(nbreCubesFinales, typeCityToInfect);
			this.discardPropagationCard(currentPropagationCard);
		}
	}

	// méthode qui permet de faire la phase propagation ( dernier pahse d'un tour )
	// le jour tire un nombre de carte propagation égale à la vitesse de propagation et place un cube maladie sur chacunes des villes
	public Collection<? extends IPropagationCard> propagation() {
		citiesWhoCanEclose.addAll(villes);
		int currentRate = infectionRate.getCurrentRate();
		List<IPropagationCard> retour = new ArrayList<IPropagationCard>();
		for(int i=1;i<=currentRate;i++){
			int nbreCubes;
			IPropagationCard currentPropCard = this.drawPropagationCard();
			retour.add(currentPropCard);
			discardPropagationCard(currentPropCard);
			ICity cityToInfect = currentPropCard.getCity();
			DiseaseType typeCityToInfect = cityToInfect.getType();
			if(disease.get(typeCityToInfect).isEradicated()){
				JOptionPane jop1 = new JOptionPane();
				jop1.showMessageDialog(null, "La carte "+currentPropCard+" n' a aucun effet car la maladie correspondant est éradiquée", "Remède trouvée", JOptionPane.INFORMATION_MESSAGE);
			}else{
				nbreCubes = cityToInfect.addDiseaseCubes(typeCityToInfect, 1);
				removeDiseasePiece(1, typeCityToInfect);
				if(nbreCubes == 4){
					eclosion(cityToInfect);
				}
			}
			
		}
		return(retour);
	}

	// seules les cartes spéciales et épidémies peuvent être retirées du jeu ( joué une seule fois )
	public void removeCardFromGame(ICard card) {
		this.removedFromGame.add(card);
	}

	public List<ICard> getRemovedFromGame() {
		return removedFromGame;
	}

	private List<String> getListeVoisins(){

		ReadCVS obj = new ReadCVS("voisins.csv",";");
		List<String[]> matrixVoisins = obj.run();

		List<String> liaisonsVilles = new ArrayList<String>();
		String[] nomVilles = null;

		for(int i=0; i<matrixVoisins.size();i++){
			String[] voisins = matrixVoisins.get(i);
			if(i==0){
				nomVilles = voisins;
			}else{
				liaisonsVilles.add(voisins[0]);
				for(int j=0;j<voisins.length;j++){
					String voisin = voisins[j]; 
					if(voisin.equals("1")){

						liaisonsVilles.add(nomVilles[j]);
					}
				}
				liaisonsVilles.add(";");
			}
		}
		return(liaisonsVilles);
	}

	private void setVoisins(){

		List<String> listeVoisins = getListeVoisins();
		

		boolean villeCentrale = true;
		boolean villeVoisine = false;
		ICity centralCity = null;

		for(String nomVille : listeVoisins){
			if(nomVille.equals(";")){
				villeCentrale = true;
				villeVoisine = false;
			}else{
				if(villeCentrale){
					centralCity = getVille(nomVille);
					villeCentrale = false;
					villeVoisine = true;
				}else if(villeVoisine){
					centralCity.addNeighborCities(getVille(nomVille));
				}
			}
		}


	}


	private ICity getVille(String nomVille){
		CityName cityName = CityName.valueOf(nomVille);

		Iterator<ICity> iterator = villes.iterator();
		ICity currentCity = null;
		do{
			currentCity = iterator.next();


		}while(iterator.hasNext() &  currentCity.getName() != cityName );
		return(currentCity);
	}



	private void setVilles(){
		ReadCVS obj = new ReadCVS("listeVilles.csv",";");
		List<String[]> matrixVilles = obj.run();
		for(String[] ville : matrixVilles){
			ICity currentCity = new City(CityName.valueOf(ville[0]), DiseaseType.valueOf(ville[1]));
			this.villes.add(currentCity);
		}

	}

	public ICity getAtlantaCity(){
		if(this.atlanta == null){
			for(ICity currentCity : villes){
				if(currentCity.equals(new City(CityName.Atlanta, DiseaseType.BLUE))){
					return currentCity;
				}
			}
		}else{
			return this.atlanta;
		}
		

		return(null);
	}
	
	// pour faire la procédure d'éclosion
	private void eclosion(ICity cityToEclose) {
		JOptionPane jop1 = new JOptionPane();
		jop1.showMessageDialog(null, "Eclosion de "+cityToEclose, "Eclosion", JOptionPane.INFORMATION_MESSAGE);

		outBreaks++;
		if(outBreaks == Board.OUTBREAKS_LIMIT){
			throw new LooseException("You lose : too much eclosions !");
		}
		int nbCubesCityInfected = 0;
		int initialNbCubesCityInfected = 0;
		DiseaseType typeEclosion = cityToEclose.getType();
		citiesWhoCanEclose.remove(cityToEclose);
		Set<ICity> citiesInfectedByEclosion = cityToEclose.getNeighbors();
		Set<ICity> futurCitiesToEclose = new HashSet<ICity>();
		citiesInfectedByEclosion.retainAll(citiesWhoCanEclose);
		
		for(ICity cityInfectedByEclosion : citiesInfectedByEclosion){
			initialNbCubesCityInfected = cityInfectedByEclosion.getDiseaseCubes(typeEclosion);
			if(initialNbCubesCityInfected<3){
				nbCubesCityInfected = cityInfectedByEclosion.addDiseaseCubes(typeEclosion, 1);
				removeDiseasePiece(1, typeEclosion);
			}else{
				futurCitiesToEclose.add(cityInfectedByEclosion);
			}
		}
		for(ICity futurCityToEclose : futurCitiesToEclose){
			eclosion(futurCityToEclose);
		}
	}
	
	private void removeDiseasePiece(int diseasePiece, DiseaseType diseaseType){
		Disease disease = this.disease.get(diseaseType);
		disease.removeDiseasePiece(diseasePiece);
	}
	
	public Map<DiseaseType,Disease> getDisease(){
		return disease;
	}
	
	
	



}

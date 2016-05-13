package pand.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.swing.JOptionPane;

import pandemie.core.CityName;
import pandemie.core.ICity;
import pandemie.core.IPlayer;
import pandemie.core.diseases.DiseaseType;

public class City implements ICity {

	// modif diag de classe
	// ajouter attribut researchLab
	// ajouter attribut Player[]
	// rendre plus visible les voisins sur Ville
	// ajouter attributs players dans Ville
	// ajouter attributs infected


	// questions ?
	// faut t'il vérfier avec la classe ResearchLab qu'il n'y a aps déjà trop de labos ?

	// attributs de City

	private CityName cityName;
	private DiseaseType diseaseType;
	private Map<DiseaseType, Integer> diseaseCubes;
	private boolean researchLab;
	private Set<ICity> neighbors;
	private Set<IPlayer> players;
	private boolean infected;
	private boolean hasEclosed; 


	// contruscteur de la ville
	public City(CityName cityName, DiseaseType diseaseType){

		this.cityName = cityName;
		this.diseaseType = diseaseType;

		// une ville a 0 cubes maladies  
		diseaseCubes = new HashMap<DiseaseType, Integer>();
		diseaseCubes.put(diseaseType.BLACK, 0);
		diseaseCubes.put(diseaseType.BLUE, 0);
		diseaseCubes.put(diseaseType.RED, 0);
		diseaseCubes.put(diseaseType.YELLOW, 0);

		//pas de researchLab au départ
		researchLab = false;

		// pas de voisins au départ
		neighbors = new HashSet<ICity>();

		// pas de joueurs dans la ville au départ
		players = new HashSet<IPlayer>();

		//infection désactivé au départ
		infected = false;

	}


	@Override
	public int compareTo(ICity arg0) {
		if(arg0.getName() == this.cityName){
			return(0);
		}else{
			return(1); 
		}
	}

	@Override
	public CityName getName() {
		return cityName;
	}

	@Override
	public int getDiseaseCubes(DiseaseType type) {
		return diseaseCubes.get(type);
	}

	@Override
	public boolean hasResearchLab() {
		return researchLab;
	}

	@Override
	public Set<ICity> getNeighbors() {
		return neighbors;
	}

	@Override
	public DiseaseType getType() {
		return diseaseType;
	}

	@Override
	public Set<IPlayer> getPlayers() {
		return players;
	}

	@Override
	public int addDiseaseCubes(DiseaseType t, int i) {
		int nbreCubeInitiale = diseaseCubes.get(t);

			if(nbreCubeInitiale+i<=3){
				diseaseCubes.put(t, new Integer(nbreCubeInitiale+i));
				/*JOptionPane jop1 = new JOptionPane();
				jop1.showMessageDialog(null, "Ajout de" +i+" club maladie à "+this.toString(), "Propagation", JOptionPane.INFORMATION_MESSAGE);*/
				return(nbreCubeInitiale+i);
			}else{
				diseaseCubes.put(t, new Integer(3));
				return(4);
			}
	}

	@Override
	public void setResearchLab(boolean b) {
		researchLab = true;
	}

	@Override
	public void removeDeseaseCube(DiseaseType dt, int i) {
		int nbreCubre = diseaseCubes.get(dt);
		if(nbreCubre-i<0){
			diseaseCubes.put(dt, new Integer(0));
		}else{
			diseaseCubes.put(dt, new Integer(nbreCubre-i));
		}
	}

	@Override
	public boolean isNeighborOf(ICity other) {
		return neighbors.contains(other);
	}

	@Override
	public void addPlayer(IPlayer player) {
		players.add(player);
	}

	@Override
	public void removePlayer(IPlayer player) {
		if(players.contains(player)){
			players.remove(player);
		}
	}

	@Override
	public void addNeighborCities(ICity... c1) {
		for(ICity city : c1){
			neighbors.add(city);
		}
	}

	@Override
	public boolean infectionDisabled() {		
		return infected;
	}

	@Override
	public void disableInfection() {
		infected = false;

	}

	@Override
	public void enableInfection() {
		infected = true;

	}





	@Override
	public String toString() {
		return cityName.toString();
	}


	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		City other = (City) obj;
		if (cityName != other.cityName)
			return false;
		if (diseaseCubes == null) {
			if (other.diseaseCubes != null)
				return false;
		} else if (!diseaseCubes.equals(other.diseaseCubes))
			return false;
		if (diseaseType != other.diseaseType)
			return false;
		if (infected != other.infected)
			return false;
		if (players == null) {
			if (other.players != null)
				return false;
		} else if (!players.equals(other.players))
			return false;
		if (researchLab != other.researchLab)
			return false;
		return true;
	}


}

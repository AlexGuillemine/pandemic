package pand.core.cards;

import pand.core.City;
import pandemie.core.ICity;
import pandemie.core.cards.IPlayerCityCard;
import pandemie.core.diseases.DiseaseType;

public class PlayerCityCard implements IPlayerCityCard {
	
	
	
	private final ICity city;
	private final DiseaseType diseaseType;
	
	
	public PlayerCityCard(ICity city) {
		this.city = city;
		this.diseaseType = city.getType();
	}
	
	@Override
	public ICity getCity() {
		// TODO Auto-generated method stub
		return city;
	}

	@Override
	public boolean hasType(DiseaseType t) {
		// TODO Auto-generated method stub
		return t == diseaseType;
	}
	
	@Override
	public String toString() {
		return(city.toString());
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((diseaseType == null) ? 0 : diseaseType.hashCode());
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
		PlayerCityCard other = (PlayerCityCard) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		return true;
	}
	
	

}

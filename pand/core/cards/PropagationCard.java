package pand.core.cards;

import pand.core.City;
import pandemie.core.ICity;
import pandemie.core.cards.IPropagationCard;
import pandemie.core.diseases.DiseaseType;

public class PropagationCard implements IPropagationCard {

	
	private final ICity city;
	
	public PropagationCard(ICity city) {
		this.city = city;
	}
	
	
	public ICity getCity() {
		return city;
	}

	public String toString() {
		return city.getName().toString();
	}
	
	

}

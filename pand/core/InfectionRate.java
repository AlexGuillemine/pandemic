package pand.core;

import java.util.ArrayList;
import java.util.List;

import pandemie.core.IInfectionRate;

public class InfectionRate implements IInfectionRate {

	
	private List<Integer> rates;
	private int totalInfections;
	private int indexCurrentRate;
	
	public InfectionRate(){
		rates = new ArrayList<Integer>();
		rates.add(2);
		rates.add(2);
		rates.add(2);
		rates.add(3);
		rates.add(3);
		rates.add(4);
		rates.add(4);
		
		totalInfections = rates.size();
		indexCurrentRate = 0;
	}
	
	public List<Integer> getRates() {
		return rates;
	}

	public int getRemainingInfections() {
		return(totalInfections - indexCurrentRate);
	}

	public void increaseInfectionRate() {
		indexCurrentRate += 1; 

	}

	public int getCurrentRate() {
		return rates.get(indexCurrentRate);
	}

}


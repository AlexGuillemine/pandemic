package demarrage;

import java.util.Iterator;
import java.util.Set;

import pand.core.Board;
import pand.core.City;
import pand.core.Game;
import pand.core.Player;
import pandemie.core.CityName;
import pandemie.core.Difficulty;
import pandemie.core.IBoard;
import pandemie.core.ICity;
import pandemie.core.IGame;
import pandemie.core.diseases.DiseaseType;

import javax.swing.JOptionPane;

public class test {
  public static void main(String[] args) {
	  	IGame game = new Game(2, Difficulty.EXPERT);
	  	
		ICity atlanta = ((Board)game.getBoard()).getAtlantaCity();
		System.out.println(atlanta.hasResearchLab());
		
  }
}

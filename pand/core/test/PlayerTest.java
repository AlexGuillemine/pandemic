package pand.core.test;

import static org.junit.Assert.*;

import java.util.Map;

import pand.core.Board;
import pand.core.City;
import pand.core.Game;
import pand.core.Player;
import pandemie.core.CityName;
import pandemie.core.Difficulty;
import pandemie.core.Role;
import pandemie.core.diseases.DiseaseType;
import pandemie.core.ITurn;
import pandemie.impl.Turn;
import pand.core.cards.PlayerCityCard;

import org.junit.Test;

import pandemie.core.diseases.Disease;

	public class PlayerTest {
		Board board = new Board(Difficulty.NOVICE, 2);
		Game game = new Game(2, Difficulty.NOVICE);
		Player laura = new Player(Role.ContainmentSpecialist, board);
		Turn turn = new Turn(game, laura);
		City paris = new City(CityName.Paris, DiseaseType.BLACK);
		City alger = new City(CityName.Alger, DiseaseType.BLACK);
		Map<DiseaseType,Disease> diseases = board.getDisease();
		Disease disease = diseases.get(DiseaseType.BLUE);
		 
		@Test
		public void testTreatDisease() {
			
			paris.addDiseaseCubes(DiseaseType.BLUE, 3);
			int z = disease.getDiseasePiece() ; 
			System.out.println(z);
			laura.setLocation(paris);
			laura.treatDisease(DiseaseType.BLUE);
			int h = paris.getDiseaseCubes(DiseaseType.BLUE);
			System.out.println(h + "vilou" + disease.getDiseasePiece());
		}
		
}


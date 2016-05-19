package pand.core.test;
import pand.core.*;
import pandemie.core.CityName;
import pandemie.core.Difficulty;
import pandemie.core.ICity;
import pandemie.core.IPlayer;
import pandemie.core.IPlayer;
import pandemie.core.Role;
import pandemie.core.diseases.*;
import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

public class CityTest {
	//création des villes
	City paris = new City(CityName.Paris, DiseaseType.BLACK );
	City alger = new City(CityName.Alger, DiseaseType.BLACK );
	City bagdad = new City(CityName.Bagdad, DiseaseType.RED );
	City calcuta = new City(CityName.Calcuta, DiseaseType.RED );
	
	
	//création d'un plateau avec 3 joueurs
	
	Board board1 = new Board(Difficulty.NOVICE,3);
	
	Player alexandre=new Player(Role.Archivist, board1);
	Player laura=new Player(Role.ContainmentSpecialist, board1);
	Player aurelien=new Player(Role.Generalist, board1);

	
	@Test
	public void testSetResearchLab() {
		paris.setResearchLab(false);
		assertEquals(false,paris.hasResearchLab());
	}
	
	
	
	@Test
	public void testCompareTo() {
		assertEquals(paris.compareTo(alger), 1);
	}

	@Test
	public void testGetName() {
		assertEquals(paris.getName(), CityName.Paris);
	}

	@Test
	public void testHasResearchLab() {
		assertEquals(false,paris.hasResearchLab());
	}

	@Test
	public void testAddDiseaseCubes() {
		//ajout de 2 cubes noires et 1 cube rouge sur paris
		paris.addDiseaseCubes(DiseaseType.BLACK, 2);
		paris.addDiseaseCubes(DiseaseType.RED, 1);
		assertEquals(2,paris.getDiseaseCubes(DiseaseType.BLACK));
		assertEquals(1,paris.getDiseaseCubes(DiseaseType.RED));
		
	}

	@Test
	public void testRemoveDeseaseCube() {
		paris.addDiseaseCubes(DiseaseType.BLACK, 2);
		paris.addDiseaseCubes(DiseaseType.RED, 1);
		paris.removeDeseaseCube(DiseaseType.BLACK, 2);
		paris.removeDeseaseCube(DiseaseType.RED, 1);
		assertEquals(0,paris.getDiseaseCubes(DiseaseType.BLACK));
		assertEquals(0,paris.getDiseaseCubes(DiseaseType.RED));
	}

	@Test
	public void testIsNeighborOf() {
		paris.addNeighborCities(bagdad);
		paris.addNeighborCities(alger);
		assertEquals(true,paris.isNeighborOf(bagdad));
		assertEquals(false,paris.isNeighborOf(calcuta));
	}

	@Test
	public void testAddPlayer() {
		paris.addPlayer(aurelien);
		paris.addPlayer(alexandre);
		Set<IPlayer> expected = new HashSet<IPlayer>();
		expected.add(aurelien);
		expected.add(alexandre);
		assertEquals(expected ,paris.getPlayers());
	}

	@Test
	public void testRemovePlayer() {
		paris.addPlayer(aurelien);
		paris.addPlayer(alexandre);
		paris.removePlayer(alexandre);
		Set<IPlayer> expected = new HashSet<IPlayer>();
		expected.add(aurelien);
		assertEquals(expected ,paris.getPlayers());
	}

	@Test
	public void testAddNeighborCities() {
		paris.addNeighborCities(bagdad);
		paris.addNeighborCities(alger);
		Set<ICity> expected = new HashSet<ICity>();
		expected.add(bagdad);
		expected.add(alger);
		assertEquals(expected ,paris.getNeighbors());
	}

	@Test
	public void testInfectionDisabled() {
		assertEquals(false,paris.infectionDisabled());
	}

	@Test
	public void testDisableInfection() {
		assertEquals(false,paris.infectionDisabled());
		paris.enableInfection();
		assertEquals(true,paris.infectionDisabled());
		paris.disableInfection();
		assertEquals(false,paris.infectionDisabled());
	}

	@Test
	public void testEnableInfection() {
		assertEquals(false,paris.infectionDisabled());
		paris.enableInfection();
		assertEquals(true,paris.infectionDisabled());
	}
}


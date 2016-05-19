package pand.core.test;

import static org.junit.Assert.*;
import pand.core.Board;
import pand.core.City;
import pand.core.Game;
import pand.core.Player;
import pand.core.actions.SimpleMove;
import pandemie.core.CityName;
import pandemie.core.Difficulty;
import pandemie.core.IPlayer;
import pandemie.core.Role;
import pandemie.core.diseases.DiseaseType;
import pandemie.core.ITurn;
import pand.core.actions.ShutlleMove;
import pandemie.impl.Turn;
import pand.core.actions.CharterFlyMove;
import pand.core.actions.DirectFlyMove;
import pand.core.cards.PlayerCityCard;

import org.junit.Test;

	public class ActionTest {

		Board board = new Board(Difficulty.NOVICE, 2);
		Game game = new Game(2, Difficulty.NOVICE);

		Player alex = new Player(Role.Archivist, board);
		Player laura = new Player(Role.ContainmentSpecialist, board);

		ITurn turn = new Turn(game, laura);
		City calcuta = new City(CityName.Calcuta, DiseaseType.RED);
		City paris = new City(CityName.Paris, DiseaseType.BLACK);
		City alger = new City(CityName.Alger, DiseaseType.BLACK);
		SimpleMove simple = new SimpleMove(alger);
		ShutlleMove shutlle = new ShutlleMove(alger);
		CharterFlyMove charter = new CharterFlyMove(alger);

		PlayerCityCard calcard = new PlayerCityCard(calcuta);
		DirectFlyMove direct = new DirectFlyMove(calcard);

		@Test
		public void testSimpleMove() {
			laura.setLocation(paris);
			simple.execute(turn);
			simple.toString();

		}
		
		@Test
		public void testShuttlemove() {
			alger.setResearchLab(true);
			laura.setLocation(paris);
			shutlle.execute(turn);
			shutlle.toString();

		}
		@Test
		public void testCharterFlyMove() {
			laura.setLocation(paris);
			laura.hasCard(paris);
			charter.execute(turn);
			charter.toString();
		}
		@Test
		public void testCharterlouper() {
			laura.setLocation(paris);
			laura.hasCard(alger);
			charter.execute(turn);
			charter.toString();
		}
		
		
		@Test
		public void testDirectFlyMove() {
		laura.setLocation(paris);
			laura.hasCard(calcard);
			direct.execute(turn);

		}

	}
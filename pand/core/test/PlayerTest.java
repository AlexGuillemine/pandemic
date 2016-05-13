package pand.core.test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.swing.JOptionPane;

import org.junit.Test;

import pand.core.City;
import pand.core.Game;
import pand.core.Player;
import pand.core.cards.Airlift;
import pand.core.cards.EpidemicCard;
import pand.core.cards.PlayerCityCard;
import pand.core.cards.PropagationCard;
import pandemie.core.CityName;
import pandemie.core.Difficulty;
import pandemie.core.IBoard;
import pandemie.core.ICard;
import pandemie.core.ICity;
import pandemie.core.IPlayer;
import pandemie.core.Role;
import pandemie.core.cards.IKeepableCard;
import pandemie.core.cards.IPlayerCard;
import pandemie.core.diseases.DiseaseType;

public class PlayerTest {
	
	
	
	@Test
	public void howManyCardOfType(){
		Game game = new Game(2, Difficulty.NOVICE);
		IBoard board = game.getBoard();
		IPlayer player1 = game.getPlayers().get(0);
		System.out.println(((Player)player1).toStringMain());
		System.out.println(player1.howManyCardOfType(DiseaseType.BLACK));
	}
	
	@Test
	public void testCardsToShare(){
		Game game = new Game(2, Difficulty.NOVICE);
		IBoard board = game.getBoard();
		IPlayer player1 = game.getPlayers().get(0);
		System.out.println(((Player)player1).toStringMain());
		assertTrue(player1.getCards().containsAll(player1.cardsToShare()));
	}
	
	
	@Test
	public void testHasCardICity() {
		IPlayer player = new Player(Role.Archivist, new City(CityName.Alger,DiseaseType.BLACK));
		
		ICity alger1 = new City(CityName.Alger,DiseaseType.BLACK);
		ICity alger2 = new City(CityName.Alger,DiseaseType.BLACK);
		ICity atlanta = new City(CityName.Atlanta,DiseaseType.BLUE);
		ICity bagdad1 = new City(CityName.Bagdad,DiseaseType.RED);
		ICity bagdad2 = new City(CityName.Bagdad,DiseaseType.RED);
		ICity bangkok = new City(CityName.Bangkok,DiseaseType.YELLOW);
		
		ICard carte1 = new PlayerCityCard(alger1);
		ICard carte2 = new PlayerCityCard(atlanta);
		ICard carte3 = new PlayerCityCard(bagdad1);
		ICard carte4 = new Airlift();
		ICard carte6 = new Airlift();
		ICard carte7 = new PropagationCard(bangkok );
		ICard carte8 = new PlayerCityCard(bagdad2);
		
		player.addCard((IKeepableCard)carte1);
		player.addCard((IKeepableCard)carte2);
		player.addCard((IKeepableCard)carte4);
		
		assertTrue(player.hasCard(alger2));
		assertTrue(!player.hasCard(bagdad2));
			
	}

	@Test
	public void testHasCardIPlayerCard() {
	IPlayer player = new Player(Role.Archivist, new City(CityName.Alger,DiseaseType.BLACK));
		
		ICity alger1 = new City(CityName.Alger,DiseaseType.BLACK);
		ICity alger2 = new City(CityName.Alger,DiseaseType.BLACK);
		ICity atlanta = new City(CityName.Atlanta,DiseaseType.BLUE);
		ICity bagdad1 = new City(CityName.Bagdad,DiseaseType.RED);
		ICity bagdad2 = new City(CityName.Bagdad,DiseaseType.RED);
		ICity bangkok = new City(CityName.Bangkok,DiseaseType.YELLOW);
		
		ICard carte1 = new PlayerCityCard(alger1);
		ICard carte9 = new PlayerCityCard(alger2);
		ICard carte2 = new PlayerCityCard(atlanta);
		ICard carte3 = new PlayerCityCard(bagdad1);
		ICard carte4 = new Airlift();
		ICard carte6 = new Airlift();
		ICard carte7 = new EpidemicCard();
		ICard carte8 = new PlayerCityCard(bagdad2);
		
		player.addCard((IKeepableCard)carte1);
		player.addCard((IKeepableCard)carte2);
		player.addCard((IKeepableCard)carte4);
		
		assertTrue(player.hasCard((IPlayerCard)carte9));
		assertTrue(!player.hasCard((IPlayerCard)carte8));
		assertTrue(player.hasCard((IPlayerCard)carte6));
		assertTrue(!player.hasCard((IPlayerCard)carte7));
		
	}

	@Test
	public void testHasCardIKeepableCard() {
IPlayer player = new Player(Role.Archivist, new City(CityName.Alger,DiseaseType.BLACK));
		
		ICity alger1 = new City(CityName.Alger,DiseaseType.BLACK);
		ICity alger2 = new City(CityName.Alger,DiseaseType.BLACK);
		ICity atlanta = new City(CityName.Atlanta,DiseaseType.BLUE);
		ICity bagdad1 = new City(CityName.Bagdad,DiseaseType.RED);
		ICity bagdad2 = new City(CityName.Bagdad,DiseaseType.RED);
		ICity bangkok = new City(CityName.Bangkok,DiseaseType.YELLOW);
		
		ICard carte1 = new PlayerCityCard(alger1);
		ICard carte9 = new PlayerCityCard(alger2);
		ICard carte2 = new PlayerCityCard(atlanta);
		ICard carte3 = new PlayerCityCard(bagdad1);
		ICard carte4 = new Airlift();
		ICard carte6 = new Airlift();
		ICard carte7 = new PropagationCard(bangkok );
		ICard carte8 = new PlayerCityCard(bagdad2);
		
		player.addCard((IKeepableCard)carte1);
		player.addCard((IKeepableCard)carte2);
		player.addCard((IKeepableCard)carte4);
		
		assertTrue(player.hasCard((IKeepableCard)carte9));
		assertTrue(!player.hasCard((IKeepableCard)carte8));
		assertTrue(player.hasCard((IKeepableCard)carte6));
	}

	@Test
	public void testHasCards() {
		IPlayer player = new Player(Role.Archivist, new City(CityName.Alger,DiseaseType.BLACK));
		
		ICity alger1 = new City(CityName.Alger,DiseaseType.BLACK);
		ICity alger2 = new City(CityName.Alger,DiseaseType.BLACK);
		ICity atlanta = new City(CityName.Atlanta,DiseaseType.BLUE);
		ICity bagdad1 = new City(CityName.Bagdad,DiseaseType.RED);
		ICity bagdad2 = new City(CityName.Bagdad,DiseaseType.RED);
		ICity bangkok = new City(CityName.Bangkok,DiseaseType.YELLOW);
		
		ICard carte1 = new PlayerCityCard(alger1);
		ICard carte9 = new PlayerCityCard(alger2);
		ICard carte2 = new PlayerCityCard(atlanta);
		ICard carte3 = new PlayerCityCard(bagdad1);
		ICard carte4 = new Airlift();
		ICard carte6 = new Airlift();
		ICard carte7 = new PropagationCard(bangkok );
		ICard carte8 = new PlayerCityCard(bagdad2);
		
		player.addCard((IKeepableCard)carte1);
		player.addCard((IKeepableCard)carte2);
		player.addCard((IKeepableCard)carte4);
		
		List<IKeepableCard> selection = new ArrayList<IKeepableCard>();
		selection.add((IKeepableCard)carte9);
		selection.add((IKeepableCard)carte6);
		
		assertTrue(player.hasCards(selection));
		
		selection.removeAll(selection);
		selection.add((IKeepableCard)carte9);
		selection.add((IKeepableCard)carte8);
		
		assertTrue(!player.hasCards(selection));
		
		
		
	}

}

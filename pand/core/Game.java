package pand.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.JOptionPane;

import pand.core.actions.SimpleMove;
import pandemie.core.CityName;
import pandemie.core.Difficulty;
import pandemie.core.IBoard;
import pandemie.core.ICard;
import pandemie.core.ICity;
import pandemie.core.IGame;
import pandemie.core.IPlayer;
import pandemie.core.ITurn;
import pandemie.core.Role;
import pandemie.core.actions.IMoveAction;
import pandemie.core.cards.IKeepableCard;
import pandemie.core.cards.IPlayerCard;
import pandemie.core.cards.IPropagationCard;
import pandemie.core.diseases.BlackDisease;
import pandemie.core.diseases.BlueDisease;
import pandemie.core.diseases.Disease;
import pandemie.core.diseases.DiseaseType;
import pandemie.core.diseases.RedDisease;
import pandemie.core.diseases.YellowDisease;
import pandemie.impl.Turn;

public class Game implements IGame {

	// on veut implémenter game 
	// pour implémenter Game, on doit implémenter Board
	// donc on implémente Board

	// attributs
	private IBoard board;
	private List<IPlayer> players;
	private int indexJoueur;
	private boolean end;

	



	// constructeurs qui sert à mettre en place le jeu
	public Game(int nbplayers, Difficulty diff){
		board = new Board(diff, nbplayers);
		players = new ArrayList<IPlayer>();
		indexJoueur = 0;
		creationJoueurs(nbplayers);

		
		
		board.initialInfections();
		
		tirageInitialeDesCartes();
		
		board.addEpidemiesToStack();
		
		
	}

	public IBoard getBoard() {
		return board;
	}

	public ITurn nextTurn() {
		
		indexJoueur = (indexJoueur) % players.size();
		ITurn nextTurn = new Turn(this,players.get(indexJoueur)); 
		indexJoueur++;
		return nextTurn ;
	}

	public List<IPlayer> getPlayers() {
		return players;
	}

	public boolean isEnded() {
		return end;
	}

	public void setEnd() {
		end = false;
		Disease blackDisease = BlackDisease.getDisease();
		Disease yellowDisease = YellowDisease.getDisease();
		Disease blueDisease = BlueDisease.getDisease();
		Disease redDisease = RedDisease.getDisease();
		
		if(blackDisease.isCureFound()){
			if(yellowDisease.isCureFound()){
				if(blueDisease.isCureFound()){
					if(redDisease.isCureFound()){
						end = true;
						return;
					}
				}
			}
		}
		
		if(blackDisease.getDiseasePiece() <= -1){
			end = true;
			return;
		}
		if(blueDisease.getDiseasePiece() <= -1){
			end = true;
			return;
		}
		if(redDisease.getDiseasePiece() <= -1){
			end = true;
			return;
		}
		if(yellowDisease.getDiseasePiece() <= -1){
			end = true;
			return;
		}
		
		if(board.getOutBreaks() == Board.OUTBREAKS_LIMIT){
			end = true;
			return;
		}
		
	}
	
	private void tirageInitialeDesCartes(){
		Map<Integer, Integer> nbresInitialCards = new HashMap<Integer, Integer>(); 
		nbresInitialCards.put(2, 4);
		nbresInitialCards.put(3, 3);
		nbresInitialCards.put(4, 2);
		int nbreInitialCards = nbresInitialCards.get(this.players.size());
		for(IPlayer player : this.players){
			for(int i=0;i<nbreInitialCards;i++){
				player.addCard((IKeepableCard)board.drawPlayerCard());
			}
		}
	}
	
	public List<IPlayerCard> getAllPlayerCards(){
		List<IPlayerCard> allCards = new ArrayList<IPlayerCard>();
		
		for(IPlayer player : players){
			allCards.addAll(player.getCards());
		}
		allCards.addAll(board.getPlayerStack());
		allCards.addAll(board.getPlayerDiscard());
		return(allCards);
	}
	
	public void creationJoueurs(int nbplayers){
		int nbPlayers = 0;
		Role roleTire;
		List<Role> roles = new ArrayList<Role>();
		do{
			roleTire = tirageRole();
			if(!roles.contains(roleTire)){
				roles.add(roleTire);
				nbPlayers++;
				players.add(new Player(roleTire, board));
			}
		}while(nbPlayers != nbplayers);
		
	}
	
	public Role tirageRole(){
		Role[] roles = Role.values();
		Role role;
		Random rand = new Random();
		int randomNum;
		do{
			randomNum = rand.nextInt(10);
			role = roles[randomNum];
		}while(role.equals(Role.ContainmentSpecialist) || role.equals(Role.Epidemiologist));
			return(role);
	}
	


}

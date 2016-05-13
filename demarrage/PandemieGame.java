package demarrage;

import javafx.stage.Stage;
import pand.core.Game;
import pandemie.core.Difficulty;
import pandemie.gui.PandemieBoard;

public class PandemieGame extends PandemieBoard  {



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		int nbplayers = this.chooseNumberOfPlayers(primaryStage);
		Difficulty diff = this.chooseDifficulty(primaryStage);
		this.game = new Game(nbplayers, diff);
		this.displayBoardAndStart(primaryStage);
		
	}


}



package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class ClueGame extends JFrame {
	Board board = Board.getInstance();
	CardsPanel cardsPanel ;
	GameControlPanel controlPanel ;
	
	
	// Default constructor
	public ClueGame() {
		
		// Create board panel and add it to frame 
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		add(board, BorderLayout.CENTER);
		
		// Create game panel and add it to frame 
		controlPanel = new GameControlPanel(); 
		add(controlPanel, BorderLayout.SOUTH);
		
		// Create cards panel and add it to frame 
		cardsPanel = new CardsPanel(); 
		add(cardsPanel, BorderLayout.EAST);
		
		
		// Set default frame size, title etc. 
		setSize(750, 630); //630 was changed from 930 so that it fit on my screen
		setTitle("Clue");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
	}
	
	// This method will drive display updates. 
	@SuppressWarnings("unused")
	private void updateDisplay() {
		
	}
	
	// Main entry point for game
	public static void main(String[] args) {
		ClueGame clueGame = new ClueGame();
		clueGame.setVisible(true);
	}
}


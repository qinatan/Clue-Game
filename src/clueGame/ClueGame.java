package clueGame;

import java.awt.BorderLayout;

import javax.swing.JFrame;

public class ClueGame extends JFrame {
	// Default constructor
	public ClueGame() {
		JFrame frame = new JFrame(); 
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initialize();
		CardsPanel cardsPanel = new CardsPanel(); 
		GameControlPanel controlPanel = new GameControlPanel(); 
		frame.add(controlPanel, BorderLayout.SOUTH);
		frame.add(cardsPanel, BorderLayout.EAST);
		frame.add(board, BorderLayout.CENTER);
		frame.setSize(750, 930);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
	}
	
	// Main entry point for game
	public static void main(String[] args) {
		ClueGame clueGame = new ClueGame();
	}
}


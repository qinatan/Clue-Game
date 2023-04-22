/**
 * 
 * This class is the main driver for the GUI elements and the game
 * @author: Mike Eack
 * @author: John Omalley 
 * @author: Qina Tan 
 * @start Date: 4/9/2023
 * @collaborator: none 
 * @resources: none  
 */

package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Set;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;



public class GameControlPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private JTextField guess = new JTextField();
	private JTextField guessResult = new JTextField();
	private JTextField playerNameText = new JTextField();
	private JTextField rollText = new JTextField();
	private JButton nextButton;
	private JButton accButton; 
	private Player currPlayer; 
	Board board = Board.getInstance();

	// constructor
	public GameControlPanel() {
		setLayout(new GridLayout(2, 0));
		JPanel topPanel = createTopPanel();
		JPanel bottomPanel = createBottomPanel();
		add(topPanel);
		add(bottomPanel);
		
		currPlayer = board.getPlayersTurn();
		rollText.setText(String.valueOf(currPlayer.getRollNum()));
		repaint();
		
	}
	
	public JButton getNextButton() {
		return nextButton;
	}
	
	public JButton getACCButton() {
		return accButton ; 
	}

	private JPanel whoseTurn() {
		JPanel whoseTurn = new JPanel();
		JLabel label = new JLabel("Who's Turn:");
		currPlayer = board.getPlayersTurn();
		String playersName = currPlayer.getPlayerName();
		Color playersColor = currPlayer.getPlayerColor();
		playerNameText.setText(playersName);
		playerNameText.setBackground(playersColor);
		whoseTurn.add(label);
		whoseTurn.add(playerNameText);
		return whoseTurn;
	}

	private JPanel rollPanel() {
		JPanel roll = new JPanel();
		JLabel rollLabel = new JLabel("Roll:");
		rollText.setText(String.valueOf(currPlayer.getRollNum()));
		roll.add(rollLabel);
		roll.add(rollText);
		return roll;
	}

	private JPanel createTopPanel() {
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, 4));
		JPanel whoseTurn = whoseTurn();
		topPanel.add(whoseTurn);
		JPanel roll = rollPanel();
		topPanel.add(roll);
		ButtonGroup group = new ButtonGroup();
		JButton next = new JButton("NEXT!");
		nextButton = next; // This is here so that we can keep a lot of our old code

		JButton makeAccusation = new JButton("Make Accusation");
		accButton = makeAccusation ;  //This is needed for code continuity
		group.add(makeAccusation);
		group.add(next);
		topPanel.add(next);
		topPanel.add(makeAccusation);
		return topPanel;
	}

	private JPanel createBottomPanel() {
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 2));
		JPanel leftPanel = bottomLeftPanel();
		bottomPanel.add(leftPanel);
		JPanel rightPanel = bottomRightPanel();
		bottomPanel.add(rightPanel);
		return bottomPanel;
	}

	private JPanel bottomRightPanel() {
		JPanel bottomRightPanel = new JPanel();
		bottomRightPanel.setLayout(new GridLayout(1, 0));
		bottomRightPanel.add(guessResult);
		bottomRightPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result")); // Only using this for testing
		return bottomRightPanel;
	}

	private JPanel bottomLeftPanel() {
		JPanel bottomLeftPanel = new JPanel();
		bottomLeftPanel.setLayout(new GridLayout(1, 0));
		bottomLeftPanel.add(getGuess());
		bottomLeftPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess")); // Only using this for testing
		return bottomLeftPanel;
	}

	public void setGuess(String guess) {
		this.guess.setText(guess);
		Color color = board.getPlayersTurn().getPlayerColor(); 
		this.guess.setBackground(color); 
	}

	public void setGuessResult(String guessResult, Card guessResultCard) {
		if ((guessResult != null) && (guessResultCard != null)) {
			if (board.getPlayersTurn() instanceof Humanplayer) {
			this.guessResult.setText(guessResultCard.toString());
			}
			else {
				this.guessResult.setText(guessResult);
			}
		}
		
		for (Player player : board.getPlayerList())
		{
			
			if (guessResult != null && player.getHand().contains(guessResultCard))
			{
				Color color = player.getPlayerColor(); 
				this.guessResult.setBackground(color); 
			}
		}
	}
	
	public void setPlayerNameText(JTextField playerNameText) {
		this.playerNameText = playerNameText;
	}

	public JTextField getPlayerNameText() {
		return playerNameText;
	}
	
	public JTextField getRollText() {
		return rollText;
	}

	public void setRollText(JTextField rollText) {
		this.rollText = rollText;
	}
	
	public JTextField getGuessResult() {
		return guessResult;
	}


	public static void main(String[] args) {
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initializeForTest();
		board.setPlayersTurn(board.getPlayerList().get(0));

		JFrame frame = new JFrame(); // create the frame
		GameControlPanel panel = new GameControlPanel();
		frame.add(panel, BorderLayout.CENTER);
		frame.setSize(750, 180); // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true);
		// test filling the data
		panel.setGuess("I have no guess!");
		panel.setGuessResult("So you have nothing?", null );

	}

	public JTextField getGuess() {
		return guess;
	}

	public void setGuess(JTextField guess) {
		this.guess = guess;
	}
}

/**
 * 
 * This class is the main driver for the GUI elements and the game
 * @author: Mike Eact 
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
	private JTextField turn;
	private JTextField roll;
	private JTextField guess = new JTextField();
	private JTextField guessResult = new JTextField();
	private Board board = Board.getInstance();
	private String playersTurn;
	private JTextField playersNameText = new JTextField();
	private Color playersColor;
	private JTextField rollText = new JTextField();

	// constructor
	public GameControlPanel() {
		setLayout(new GridLayout(2, 0));
		JPanel topPanel = createTopPanel();
		JPanel bottomPanel = createBottomPanel();
		add(topPanel);
		add(bottomPanel);
		addMouseListener(new movePlayerClick());
	}

	private JPanel createTopPanel() {
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, 4));
		JPanel whoseTurn = whoseTurn();
		topPanel.add(whoseTurn);
		JPanel roll = roll();
		topPanel.add(roll);
		ButtonGroup group = new ButtonGroup();
		JButton next = new JButton("NEXT!");
		next.addActionListener(new NextButtonListener());
		JButton makeAccusation = new JButton("Make Accusation");
		makeAccusation.addActionListener(new makeAccusationButtonListener());
		group.add(makeAccusation);
		group.add(next);
		topPanel.add(next);
		topPanel.add(makeAccusation);
		return topPanel;
	}

	private class NextButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {

			humanPlayer player = (humanPlayer) board.getPlayer(0);

			//Checks if the player has moved or made an acc
			if (player.getIsHasPlayerACC() || player.getIsHasPlayerMoved()) {

				// This is basic psudocode that were going to be working through
//					next player 
//					roll dice
//					calcTagets
//					update game control panel

				// This works but well implment it later
				board.nextTurn(); // Switchs to the next player in list
				playersTurn = board.getPlayersTurn().getPlayerName();
				playersColor = board.getPlayersTurn().getPlayerColor();
				playersNameText.setText(playersTurn);
				
				
				playersNameText.setBackground(playersColor);
				String randomRoll = board.rollDie();
				rollText.setText(randomRoll);

			} else {
				// This works
				JOptionPane.showMessageDialog(null, "Please finish your turn", "Players turn",
						JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	private class makeAccusationButtonListener implements ActionListener {
		humanPlayer player = (humanPlayer) board.getPlayer(0);

		@Override
		public void actionPerformed(ActionEvent e) {
			player.setHasPlayerACC(true);
		}
	}


	private class movePlayerClick implements MouseListener {
		humanPlayer player = (humanPlayer) board.getPlayer(0);

		@Override
		public void mouseClicked(MouseEvent e) {

			// TODO: This doesn't work yet. Im not sure if this should be in here because it
			// has to deal with the board drawing
			if (board.clickContainsTarget(e.getX(), e.getY())) {
				//TODO:include here where we move player
				player.setHasPlayerMoved(true);
			} else {
				JOptionPane.showMessageDialog(null, "Please click on a vaild tile", "Error",
						JOptionPane.ERROR_MESSAGE);
			}

		}

		@Override
		public void mousePressed(MouseEvent e) {
		}// This should be left blank
		@Override
		public void mouseReleased(MouseEvent e) {
		}// This should be left blank
		@Override
		public void mouseEntered(MouseEvent e) {
		}// This should be left blank
		@Override
		public void mouseExited(MouseEvent e) {
		} // This should be left blank
	}

	private JPanel whoseTurn() {
		JPanel whoseTurn = new JPanel();
		JLabel label = new JLabel("Who's Turn:");
		String playersName = board.getPlayersTurn().getPlayerName();
		Color playersColor = board.getPlayersTurn().getPlayerColor();
		playersNameText.setText(playersName);
		playersNameText.setBackground(playersColor);
		whoseTurn.add(label);
		whoseTurn.add(playersNameText);
		return whoseTurn;
	}

	private JPanel roll() {
		JPanel roll = new JPanel();
		JLabel rollLabel = new JLabel("Roll:");
		String die = board.rollDie();
		rollText.setText(die);
		roll.add(rollLabel);
		roll.add(rollText);
		return roll;
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
		// JTextField someText = new JTextField("I have no guess");
		bottomRightPanel.add(guess);
		bottomRightPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess Result")); // Only using this for testing
		return bottomRightPanel;
	}

	private JPanel bottomLeftPanel() {
		JPanel bottomLeftPanel = new JPanel();
		bottomLeftPanel.setLayout(new GridLayout(1, 0));
		// JTextField someText = new JTextField("You have question?");
		bottomLeftPanel.add(guessResult);
		bottomLeftPanel.setBorder(new TitledBorder(new EtchedBorder(), "Guess")); // Only using this for testing
		return bottomLeftPanel;
	}

	public void setGuess(String guess) {
		this.guess.setText(guess);
	}

	public void setGuessResult(String guessResult) {
		this.guessResult.setText(guessResult);
	}

	public static void main(String[] args) {
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initializeForTest();
		board.setPlayersTurn(board.getPlayerList().get(0));
		GameControlPanel panel = new GameControlPanel(); // create the panel
		JFrame frame = new JFrame(); // create the frame
		frame.add(panel, BorderLayout.CENTER);
		frame.setSize(750, 180); // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true);
		// test filling the data
		// panel.setTurn(new ComputerPlayer("Col. Mustard", 0, 0, "orange"), 5);
		panel.setGuess("I have no guess!");
		panel.setGuessResult("So you have nothing?");

	}
}

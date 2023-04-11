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
	private JTextField roll; // TODO: removed the unused variables
	private JTextField guess = new JTextField();
	private JTextField guessResult = new JTextField();
	private Board board = Board.getInstance();
	private Player currPlayer;
	private String nextPlayerName;
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

		currPlayer = board.getPlayersTurn();
		// currPlay

		//System.out.println(currPlayer.getCurrCell());
		//TODO: This gets into an infinite loop
		board.calcTargets(currPlayer.getCurrCell(), currPlayer.getRollNum());

	}

	// Constructor for top half of the Jpanel
	private JPanel createTopPanel() {
		JPanel topPanel = new JPanel();
		topPanel.setLayout(new GridLayout(1, 4));
		JPanel whoseTurn = whoseTurn();
		topPanel.add(whoseTurn);

		JPanel roll = rollPanel();
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
			if (player.getIsHasPlayerACC() || player.getIsHasPlayerMoved()) {

				board.nextTurn(); // turn to next player in the player list
				currPlayer = board.getPlayersTurn();
				nextPlayerName = board.getPlayersTurn().getPlayerName();
				playersColor = currPlayer.getPlayerColor();
				playersNameText.setText(nextPlayerName);
				playersNameText.setBackground(playersColor);

				// Roll dice
				int randomRoll = board.rollDie();
				rollText.setText(String.valueOf(randomRoll));

				// calculate targets based on current boardCell and die number
				int currentRow = currPlayer.getPlayerRow();
				int currentCol = currPlayer.getPlayerCol();
				BoardCell currentLocation = board.getCell(currentRow, currentCol);
				board.calcTargets(currentLocation, randomRoll);
				System.out.println(board.getTargets());

				// Update control panel

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

	private JPanel whoseTurn() {
		JPanel whoseTurn = new JPanel();
		JLabel label = new JLabel("Who's Turn:");
		// TODO: refactor into whats below
		// currPlay.getName

		String playersName = board.getPlayersTurn().getPlayerName();
		Color playersColor = board.getPlayersTurn().getPlayerColor();
		currPlayer = board.getPlayersTurn();
		playersNameText.setText(playersName);
		playersNameText.setBackground(playersColor);
		whoseTurn.add(label);
		whoseTurn.add(playersNameText);
		return whoseTurn;
	}

	private JPanel rollPanel() {
		JPanel roll = new JPanel();
		JLabel rollLabel = new JLabel("Roll:");

//		int dieValue = board.rollDie();
//		String die = String.valueOf(dieValue);
//		rollText.setText(die);

		rollText.setText(String.valueOf(currPlayer.getRollNum()));
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

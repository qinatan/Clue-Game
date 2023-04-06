package clueGame;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel {
	private JTextField turnDisplay;
	private JTextField rollDisplay; 
	private JTextField guessDisplay; 
	private JTextField guessResultDisplay; 
	Board board = Board.getInstance();
	String playersTurn; 
	JTextField playersNameText = new JTextField();
	JTextField rollText = new JTextField(); 
	//constructor 
	public GameControlPanel () {
		setLayout(new GridLayout(2, 0)); 
		JPanel topPanel = createTopPanel(); 
		JPanel bottomPanel = createBottomPanel();
		add(topPanel);
		add(bottomPanel);
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
			System.out.println("Next Button Pressed");
			board.nextTurn();
			playersTurn = board.getPlayersTurn().getPlayerName();
			playersNameText.setText(playersTurn);
			String randomRoll = board.rollDie(); 
			rollText.setText(randomRoll);
		}
	}
	
	private class makeAccusationButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("Make Accusation Button Pressed");
		}
	}
	
	
	private JPanel whoseTurn() {
		JPanel whoseTurn = new JPanel();
		JLabel label = new JLabel("Who's Turn:");
		String playersName = board.getPlayersTurn().getPlayerName();
		// TODO get the color
	
		playersNameText.setText(playersName);
		whoseTurn.add(playersNameText);
		whoseTurn.add(label);
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
		JTextField someText = new JTextField("I have no guess");
		bottomRightPanel.add(someText);
		bottomRightPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result")); // Only using this for testing
		return bottomRightPanel;
	}
	
	private JPanel bottomLeftPanel() {
		JPanel bottomLeftPanel = new JPanel();
		bottomLeftPanel.setLayout(new GridLayout(1, 0));
		JTextField someText = new JTextField("You have question?");
		bottomLeftPanel.add(someText);
		bottomLeftPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess")); // Only using this for testing
		return bottomLeftPanel;
	}

	
	
	public static void main(String[] args) {
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initializeForTest();
		board.setPlayersTurn(board.getPlayerList().get(0));
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.add(panel, BorderLayout.CENTER);
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true);
		
	
	}
}

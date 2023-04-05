package clueGame;
import java.awt.BorderLayout;
import java.awt.GridLayout;

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
		JButton makeAccusation = new JButton("Make Accusation");
		group.add(makeAccusation);
		group.add(next);
		topPanel.add(next);
		topPanel.add(makeAccusation);
		topPanel.setBorder(new TitledBorder (new EtchedBorder(), "Top Panel")); // Only using this for testing
		return topPanel;
	}
	
	private JPanel whoseTurn() {
		JPanel whoseTurn = new JPanel();
		JLabel label = new JLabel("Who's Turn:");
		whoseTurn.add(label);
		whoseTurn.setBorder(new TitledBorder (new EtchedBorder(), "Who's Turn"));
		return whoseTurn;
	}
	
	private JPanel roll() {
		JPanel roll = new JPanel();
		JLabel rollLabel = new JLabel("ROll");
		roll.setBorder(new TitledBorder (new EtchedBorder(), "Roll")); // Only using this for testing
		roll.add(rollLabel);
		return roll;
	}
	
	private JPanel createBottomPanel() {
		JPanel bottomPanel = new JPanel();
		bottomPanel.setLayout(new GridLayout(1, 2));
		JPanel leftPanel = bottomLeftPanel();
		bottomPanel.add(leftPanel);
		JPanel rightPanel = bottomRightPanel();
		bottomPanel.add(rightPanel);
		bottomPanel.setBorder(new TitledBorder (new EtchedBorder(), "Bottom Panel")); // Only using this for testing
		return bottomPanel;
	}
	
	private JPanel bottomRightPanel() {
		JPanel bottomRightPanel = new JPanel();
		bottomRightPanel.setLayout(new GridLayout(1, 0));
		JTextField someText = new JTextField("Text");
		bottomRightPanel.add(someText);
		//bottomRightPanel.add(guessResultDisplay);
		bottomRightPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess Result")); // Only using this for testing
		return bottomRightPanel;
	}
	
	private JPanel bottomLeftPanel() {
		JPanel bottomLeftPanel = new JPanel();
		bottomLeftPanel.setLayout(new GridLayout(1, 0));
		//bottomLeftPanel.add(guessDisplay);
		JTextField someText = new JTextField("Text");
		bottomLeftPanel.add(someText);
		bottomLeftPanel.setBorder(new TitledBorder (new EtchedBorder(), "Guess")); // Only using this for testing
		return bottomLeftPanel;
	}

	
	
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		//frame.setContentPane(panel); // put the panel in the frame
		frame.add(panel, BorderLayout.CENTER);
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true);
	
	}
}

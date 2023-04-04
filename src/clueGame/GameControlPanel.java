package clueGame;
import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class GameControlPanel extends JPanel{
	private JTextField turnDisplay;
	private JTextField rollDisplay; 
	private JTextField guessDisplay; 
	private JTextField guessResultDisplay; 
	//constructor 
	public GameControlPanel () {
		// TODO Auto-generated constructor stub
	}
	public void createDisplayPanel()
	{
		JPanel displayPanel = new JPanel(); 
		JLabel turnLabel = new JLabel("Whose turn?"); 
		turnDisplay = new JTextField(20); 
		displayPanel.add(turnLabel); 
		displayPanel.add(turnDisplay); 
		
		
	}
	
	
	public static void main(String[] args) {
		GameControlPanel panel = new GameControlPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.setContentPane(panel); // put the panel in the frame
		frame.setSize(750, 180);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true); // make it visible
		
		// test filling in the data
		panel.setTurn(new ComputerPlayer( "Col. Mustard", 0, 0, "orange"), 5);
		panel.setGuess( "I have no guess!");
		panel.setGuessResult( "So you have nothing?");
	}
}

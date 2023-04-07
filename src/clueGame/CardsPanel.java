package clueGame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class CardsPanel extends JPanel {
	Board board = Board.getInstance();
	JPanel knownCardsPanel = new JPanel();
	JPanel roomCardsPanel = new JPanel();
	JPanel weaponCardsPanel = new JPanel();
	JPanel peopleCardsPanel = new JPanel();
	
	JPanel peopleHandPanel = new JPanel();
	JPanel roomHandPanel = new JPanel();
	JPanel weaponHandPanel = new JPanel();
	
	// Constructor
	public CardsPanel() {
		
		setLayout(new GridLayout(3, 1));
		setBorder(new TitledBorder (new EtchedBorder(), "Known Cards"));
		JPanel peopleCardsPanel = peopleCardsPanel();
		add(peopleCardsPanel);
		JPanel roomCardsPanel = roomCardsPanel();
		add(roomCardsPanel);
		JPanel weaponCardsPanel = weaponCardsPanel();
		add(weaponCardsPanel);
	}
	
	
	public void updatePanels() {
		removeAll(); 
		weaponCardsPanel.removeAll();
		peopleCardsPanel.removeAll();
		roomCardsPanel.removeAll();
		setLayout(new GridLayout(3, 1));
		JPanel peopleCardsPanel = peopleCardsPanel();
		JPanel roomCardsPanel = roomCardsPanel();
		JPanel weaponCardsPanel = weaponCardsPanel();
		peopleHandPanel.removeAll();
		roomHandPanel.removeAll(); 
		weaponHandPanel.removeAll(); 
		add(peopleCardsPanel); 
		add(roomCardsPanel); 
		add(weaponCardsPanel); 
		
	}
	
	
	private JPanel peopleCardsPanel() {
		JPanel peopleCardsPanel = new JPanel();
		peopleCardsPanel.setLayout(new GridLayout(2, 0));
		peopleCardsPanel.setBorder(new TitledBorder (new EtchedBorder(), "peopleCards"));
		ArrayList<JTextField> seenPeopleCards = getSeenCards(CardType.PERSON, board.getPlayerList().get(0)); // Assuming we are chihiro
		ArrayList<JTextField> seenPeopleCardsFromHand = getHandCards(CardType.PERSON, board.getPlayerList().get(0));  // Assuming we are chihiro
		// Adds seen cards 
		JPanel seenPanel = new JPanel();
		seenPanel.setLayout(new GridLayout(0,1));
		JLabel label = new JLabel("Seen:");
		seenPanel.add(label);
		for (JTextField card: seenPeopleCards) {
			
			seenPanel.add(card);
		}
		peopleCardsPanel.add(seenPanel);
		// Adds card from hand
		JPanel peopleHandPanel = new JPanel();
		peopleHandPanel.setLayout(new GridLayout(0,1));
		JLabel handLabel = new JLabel("In Hand:");
		peopleHandPanel.add(handLabel);
		for (JTextField card: seenPeopleCardsFromHand) {
			peopleHandPanel.add(card);
		}
		peopleCardsPanel.add(peopleHandPanel);
		return peopleCardsPanel;
		
	}
	
	private JPanel roomCardsPanel() {
		JPanel roomCardsPanel = new JPanel();
		roomCardsPanel.setLayout(new GridLayout(2, 0));
		roomCardsPanel.setBorder(new TitledBorder (new EtchedBorder(), "roomCards"));
		ArrayList<JTextField> seenRoomCards = getSeenCards(CardType.ROOM, board.getPlayerList().get(0));  // Assuming we are chihiro
		ArrayList<JTextField> seenRoomCardsFromHand = getHandCards(CardType.ROOM, board.getPlayerList().get(0));  // Assuming we are chihiro
		// Adds seen cards 
		JPanel seenPanel = new JPanel();
		seenPanel.setLayout(new GridLayout(0,1)); 
		JLabel roomLabel = new JLabel("Seen:");
		seenPanel.add(roomLabel);
		for (JTextField card: seenRoomCards) {
			seenPanel.add(card);
		}
		roomCardsPanel.add(seenPanel);
		// Adds card from hand
		roomHandPanel = new JPanel();
		roomHandPanel.setLayout(new GridLayout(0,1)); 
		JLabel handLabel = new JLabel("In Hand:");
		roomHandPanel.add(handLabel);
		for (JTextField card: seenRoomCardsFromHand) {
			roomHandPanel.add(card);
		}
		roomCardsPanel.add(roomHandPanel);
		return roomCardsPanel;
	}
	
	private JPanel weaponCardsPanel() {
		weaponCardsPanel.setLayout(new GridLayout(3,0));
		weaponCardsPanel.setBorder(new TitledBorder (new EtchedBorder(), "weaponCards"));
		ArrayList<JTextField> seenWeaponCardsText = getSeenCards(CardType.WEAPON, board.getPlayerList().get(0));  // Assuming we are chihiro
		ArrayList<JTextField> seenWeaponCardsFromHand = getHandCards(CardType.WEAPON, board.getPlayerList().get(0));  // Assuming we are chihiro
		// Adds seen cards 
		JPanel seenPanel = new JPanel();
		seenPanel.setLayout(new GridLayout(0,1));
		JLabel seenLabel = new JLabel("Seen:");
		//weaponCardsPanel.add(seenLabel);
		seenPanel.add(seenLabel);
		for (JTextField seenWeaponText: seenWeaponCardsText) {
			
			seenPanel.add(seenWeaponText);
		}
		weaponCardsPanel.add(seenPanel);
		
		// Adds card from hand
		JPanel weaponHandPanel = new JPanel();
		weaponHandPanel.setLayout(new GridLayout(0,1));
		JLabel handLabel = new JLabel("In Hand:");
		weaponHandPanel.add(handLabel);
		for (JTextField card: seenWeaponCardsFromHand) {
			
			weaponHandPanel.add(card);
		}
		weaponCardsPanel.add(weaponHandPanel);
		return weaponCardsPanel;
	}
	
	
	private ArrayList<JTextField> getSeenCards(CardType cardType, Player player) {
		ArrayList<JTextField> seenCardsList =  new ArrayList<JTextField>();
		if (player.getSeenMap().containsKey(cardType)) {
			for (Card card: player.getSeenMap().get(cardType)) {
				Color cardColor = card.getcardColor(); 
				JTextField seenCard = new JTextField();
				seenCard.setText(card.getCardName());
				seenCard.setBackground(cardColor);
				seenCardsList.add(seenCard);
			}
			return seenCardsList;
		}
		return seenCardsList;
	}
	
	private ArrayList<JTextField> getHandCards(CardType cardType, Player player) {
		ArrayList<JTextField> seenCardsList = new ArrayList<JTextField>();
		
		for( Card card: player.getHand()) {
			if (cardType == card.getCardType()) {
				Color cardColor = card.getcardColor(); 
				JTextField seenCard = new JTextField();
				seenCard.setText(card.getCardName());
				seenCard.setBackground(cardColor);
				seenCardsList.add(seenCard);
			}
		}
		return seenCardsList;
	}
	
	
	
	public static void main(String[] args) throws InterruptedException {
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initializeForTest();
		Player testingPlayer = board.getPlayer(0); 
		JFrame frame = new JFrame();  // create the frame 
		CardsPanel cardsPanel = new CardsPanel(); 
		frame.add(cardsPanel, BorderLayout.CENTER);
		frame.setSize(180, 750);  // size the frame
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		
		ArrayList<Card> dealtCards = board.getPlayerList().get(1).getHand(); 
		
		for (int j = 0; j < dealtCards.size(); j++)
		{
			CardType cardType = dealtCards.get(j).getCardType(); 
			Card testingCard = dealtCards.get(j); 
			testingPlayer.addToSeenMap(cardType,testingCard);
		}
		
		cardsPanel.updatePanels();

	
	}
	
}

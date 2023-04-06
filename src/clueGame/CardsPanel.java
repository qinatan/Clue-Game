package clueGame;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;

public class CardsPanel extends JPanel {
	Board board = Board.getInstance();
	// Constructor 
	public CardsPanel() {
		JPanel knownCardsPanel = new JPanel();
		knownCardsPanel.setLayout(new GridLayout(3, 0));
		knownCardsPanel.setBorder(new TitledBorder (new EtchedBorder(), "Known Cards"));
		JPanel peopleCardsPanel = peopleCardsPanel();
		knownCardsPanel.add(peopleCardsPanel);
		JPanel roomCardsPanel = roomCardsPanel();
		knownCardsPanel.add(roomCardsPanel);
		JPanel weaponCardsPanel = weaponCardsPanel();
		knownCardsPanel.add(weaponCardsPanel);
	}
	
	
	private JPanel peopleCardsPanel() {
		JPanel peopleCardsPanel = new JPanel();
		peopleCardsPanel.setLayout(new GridLayout(2, 0));
		peopleCardsPanel.setBorder(new TitledBorder (new EtchedBorder(), "peopleCards"));
		ArrayList<JTextField> seenPeopleCards = getSeenCards(CardType.PERSON, board.getPlayerList().get(0)); // Assuming we are chihiro
		ArrayList<JTextField> seenPeopleCardsFromHand = getHandCards(CardType.PERSON, board.getPlayerList().get(0));  // Assuming we are chihiro
		// Adds seen cards 
		for (JTextField card: seenPeopleCards) {
			System.out.println("here");
			peopleCardsPanel.add(card);
		}
		
		// Adds card from hand
		for (JTextField card: seenPeopleCardsFromHand) {
			peopleCardsPanel.add(card);
		}
		
		return peopleCardsPanel;
		
	}
	
	private JPanel roomCardsPanel() {
		JPanel roomCardsPanel = new JPanel();
		roomCardsPanel.setLayout(new GridLayout(2, 0));
		roomCardsPanel.setBorder(new TitledBorder (new EtchedBorder(), "roomCards"));
		ArrayList<JTextField> seenRoomCards = getSeenCards(CardType.ROOM, board.getPlayerList().get(0));  // Assuming we are chihiro
		ArrayList<JTextField> seenRoomCardsFromHand = getHandCards(CardType.ROOM, board.getPlayerList().get(0));  // Assuming we are chihiro
		// Adds seen cards 
		for (JTextField card: seenRoomCards) {
			System.out.println("here");
			roomCardsPanel.add(card);
		}
		
		// Adds card from hand
		for (JTextField card: seenRoomCardsFromHand) {
			roomCardsPanel.add(card);
		}
		
		return roomCardsPanel;
	}
	
	private JPanel weaponCardsPanel() {
		JPanel weaponCardsPanel = new JPanel();
		weaponCardsPanel.setLayout(new GridLayout(2, 0));
		weaponCardsPanel.setBorder(new TitledBorder (new EtchedBorder(), "weaponCards"));
		ArrayList<JTextField> seenWeaponCards = getSeenCards(CardType.WEAPON, board.getPlayerList().get(0));  // Assuming we are chihiro
		ArrayList<JTextField> seenWeaponCardsFromHand = getHandCards(CardType.WEAPON, board.getPlayerList().get(0));  // Assuming we are chihiro
		// Adds seen cards 
		for (JTextField card: seenWeaponCards) {
			System.out.println("here");
			weaponCardsPanel.add(card);
		}
		
		// Adds card from hand
		for (JTextField card: seenWeaponCardsFromHand) {
			weaponCardsPanel.add(card);
		}
		return weaponCardsPanel;
	}
	
	
	private ArrayList<JTextField> getSeenCards(CardType cardType, Player player) {
		ArrayList<JTextField> seenCards =  new ArrayList<JTextField>();
		if (player.getSeenMap().containsKey(cardType)) {
			for (Card card: player.getSeenMap().get(cardType)) {
				JTextField seenCard = new JTextField();
				seenCard.setText(card.getCardName());
				seenCards.add(seenCard);
			}
			return seenCards;
		}
		return seenCards;
	}
	
	private ArrayList<JTextField> getHandCards(CardType cardType, Player player) {
		ArrayList<JTextField> seenCards = new ArrayList<JTextField>();
		for( Card card: player.getHand()) {
			if (cardType == card.getCardType()) {
				JTextField seenCard = new JTextField();
				seenCard.setText(card.getCardName());
				seenCards.add(seenCard);
			}
		}
		return seenCards;
	}
	
	
	public static void main(String[] args) {
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initializeForTest();
		CardsPanel cardsPanel = new CardsPanel();  // create the panel
		JFrame frame = new JFrame();  // create the frame 
		frame.add(cardsPanel, BorderLayout.CENTER);
		frame.setSize(180, 750);  // size the frame
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
		frame.setVisible(true);
	}
	
}

package clueGame;

import java.awt.BorderLayout;
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
	// Constructor 
	public CardsPanel() {
		
		setLayout(new GridLayout(3, 0));
		setBorder(new TitledBorder (new EtchedBorder(), "Known Cards"));
		JPanel peopleCardsPanel = peopleCardsPanel();
		add(peopleCardsPanel);
		JPanel roomCardsPanel = roomCardsPanel();
		add(roomCardsPanel);
		JPanel weaponCardsPanel = weaponCardsPanel();
		add(weaponCardsPanel);
	}
	
	public void updatedPanels() {
		peopleCardsPanel.removeAll(); 
		add(peopleCardsPanel); 
		roomCardsPanel.removeAll();
		add(roomCardsPanel); 
		weaponCardsPanel.removeAll(); 
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
			System.out.println("here");
			seenPanel.add(card);
		}
		peopleCardsPanel.add(seenPanel);
		// Adds card from hand
		JPanel handPanel = new JPanel();
		handPanel.setLayout(new GridLayout(0,1));
		JLabel handLabel = new JLabel("In Hand:");
		handPanel.add(handLabel);
		for (JTextField card: seenPeopleCardsFromHand) {
			handPanel.add(card);
		}
		peopleCardsPanel.add(handPanel);
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
		seenPanel.setLayout(new GridLayout(0,1));//make size change
		JLabel label = new JLabel("Seen:");
		seenPanel.add(label);
		for (JTextField card: seenRoomCards) {
			System.out.println("here");
			seenPanel.add(card);
		}
		roomCardsPanel.add(seenPanel);
		// Adds card from hand
		JPanel handPanel = new JPanel();
		handPanel.setLayout(new GridLayout(0,1));//made size change
		JLabel handLabel = new JLabel("In Hand:");
		handPanel.add(handLabel);
		for (JTextField card: seenRoomCardsFromHand) {
			handPanel.add(card);
		}
		roomCardsPanel.add(handPanel);
		return roomCardsPanel;
	}
	
	private JPanel weaponCardsPanel() {
		
		weaponCardsPanel.setLayout(new GridLayout(2, 0));
		weaponCardsPanel.setBorder(new TitledBorder (new EtchedBorder(), "weaponCards"));
		ArrayList<JTextField> seenWeaponCards = getSeenCards(CardType.WEAPON, board.getPlayerList().get(0));  // Assuming we are chihiro
		ArrayList<JTextField> seenWeaponCardsFromHand = getHandCards(CardType.WEAPON, board.getPlayerList().get(0));  // Assuming we are chihiro
		// Adds seen cards 
		JPanel seenPanel = new JPanel();
		seenPanel.setLayout(new GridLayout(0,1));
		JLabel label = new JLabel("Seen:");
		seenPanel.add(label);
		weaponCardsPanel.add(label);
		for (JTextField card: seenWeaponCards) {
			System.out.println("here");
			seenPanel.add(card);
		}
		weaponCardsPanel.add(seenPanel);
		
		// Adds card from hand
		JPanel handPanel = new JPanel();
		JLabel handLabel = new JLabel("In Hand:");
		handPanel.setLayout(new GridLayout(0,1));
		handPanel.add(handLabel);
		for (JTextField card: seenWeaponCardsFromHand) {
			handPanel.add(card);
		}
		weaponCardsPanel.add(handPanel);
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
	
	
	
	public static void main(String[] args) throws InterruptedException {
		Board board = Board.getInstance();
		board.setConfigFiles("ClueLayout.csv", "ClueSetup.txt");
		board.initializeForTest();
		//board.initialize();
		Player testingPlayer = board.getPlayer(0); 
		JFrame frame = new JFrame();  // create the frame 
	
		for (int i = 0; i < 6; i++)
		{
			Card testingCard1 = new Card (CardType.ROOM,"Plant Room" );
			testingPlayer.addToSeenMap(CardType.ROOM, testingCard1);
		}
		CardsPanel cardsPanel = new CardsPanel();  // create the panel
		frame.add(cardsPanel, BorderLayout.CENTER);
		frame.setSize(180, 750);  // size the frame
		frame.setVisible(true);
		cardsPanel.updatedPanels();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // allow it to close
	
			
		
	}
	
}

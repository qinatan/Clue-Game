package clueGame;

public class Card /* TODO: implements Comparable need to do this */ {
	private String cardName;
	private CardType cardType;
	private int dealCount = 0;
	
	// constructors
	 public Card(CardType type, String cardName) {
		 this.cardType = type; 
		 this.cardName = cardName;
	 }
	 
	 
	public Card(String cardName, String type) {
		this.cardName = cardName;
		switch (type) {
		case "Room":
			this.cardType = CardType.ROOM;
			break;
		case "Weapon":
			this.cardType = CardType.WEAPON;
			break;
		case "Player":
			this.cardType = CardType.PERSON;
			break;
		}

	}

	// setters
	public void setCardName(String cardName) {
		this.cardName = cardName;
	}

	public void setCardType(CardType cardType) {
		this.cardType = cardType;
	}

	// getters
	public String getCardName() {
		return cardName;
	}

	public CardType getCardType() {
		return cardType;
	}

	// TODO: I think we need to do this to get that test passing @Override
	public boolean equals(Card target) {
		if (this.cardType == target.cardType && this.cardName.equals(target.cardName)){
			return true;
		}
		else {
			return false; 
		}
	}

	@Override
	public String toString() {
		return "Card [cardName=" + cardName + ", cardType=" + cardType + "]";
	}
	
	public void setDealCount() {
		dealCount ++ ; 
	}

}

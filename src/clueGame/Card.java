package clueGame;

public class Card {
	private String cardName; 
	private CardType cardType; 
	//constructor 
	
	public Card(String cardName, CardType cardType)
	{
		this.cardName = cardName;
		switch(cardType) {
		case ROOM: this.cardType = CardType.ROOM; 
					break; 
		case WEAPON: this.cardType = CardType.WEAPON; 
					break; 
		case PERSON: this.cardType = CardType.PERSON;
					break; 
		}
					
	}

	
	//setters 
	public void setCardName(String cardName)
	{
		this.cardName = cardName;
	}
	public void setCardType(CardType cardType)
	{
		this.cardType = cardType; 
	}
	
	//getters
	public String getCardName()
	{
		return cardName;
	}
	public CardType getCardType()
	{
		return cardType; 
	}

	
	
	public boolean equals(Card target){
		return false;
	};

}

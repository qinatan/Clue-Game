package clueGame;

import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

import javax.swing.JPanel;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.*;
import java.awt.Graphics;

/**
 * Board
 * 
 * Board class contains variables that represent objects in the game board, and
 * methods that organize the objects and handle movements of objects. Board
 * class is responsible for 1) loading setupConfigFile and initialized roomMap
 * with the room name and room initial; 2) loading layoutConfigFile and indicate
 * information of each cell in CSV file accordingly; 3)calculate adjacency of
 * each cells according to game rules; 4) calculate the target of each cell
 * according to the rolling die 5) handle suggestion from every player
 * 
 * @author michaeleack
 * @author johnOmalley Date: 3/7/23 Collaborators: None Sources: None
 * @author Qina Tan
 */
public class Board extends JPanel {
	private static Board boardInstance = new Board();
	private int cols, rows;
	private BoardCell[][] grid;
	private Map<BoardCell, Set<BoardCell>> adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
	private ArrayList<BoardCell> visited = new ArrayList<BoardCell>();
	private Set<BoardCell> targets;
	private Map<Character, Room> roomMap = new HashMap<Character, Room>();
	private ArrayList<Card> fullDeck, dealtDeck, peopleDeck, roomDeck, weaponDeck;
	private ArrayList<Player> playerList;
	private String layoutConfig, setupConfig;
	private final static int TYPE = 0;
	private final static int NAME = 1;
	private final static int SYMBOL = 2;
	private final static int ROW = 3;
	private final static int COLUMN = 4;
	private static Solution solution;
	private Player playerTurn;

	private int cellWidth, cellHeight; // TODO: change these to private with getters and setters

	// constructor is private to ensure only one can be created
	private Board() {
		super();
	}

	// this method returns the only Board
	public static Board getInstance() {
		return boardInstance;
	}

	public ArrayList<Card> getWeaponDeck() {
		return weaponDeck;
	}

	public void setRoomDeck(ArrayList<Card> roomDeck) {
		this.roomDeck = roomDeck;
	}

	public ArrayList<Card> getPeopleDeck() {
		return peopleDeck;
	}

	public void setPeopleDeck(ArrayList<Card> peopleDeck) {
		this.peopleDeck = peopleDeck;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g); // THIS MUST BE the first item within paintComponent
		cellWidth = (getWidth()) / cols;
		cellHeight = (getHeight()) / rows;

		// Draw board cells and room names
		for (BoardCell[] cells : grid) {
			for (BoardCell c : cells) {
				c.drawBoardCell(cellWidth, cellHeight, g);
				c.drawRoomNames(cellWidth, cellHeight, g);
			}
		}

		// Draw players
		for (Player player : playerList) {
			player.drawPlayer(cellWidth, cellHeight, g);
		}
	}

	/*
	 * initialize the board (since we are using singleton pattern)
	 */
	public void initialize() {
		targets = new HashSet<BoardCell>();
		playerList = new ArrayList<Player>();
		fullDeck = new ArrayList<Card>();
		dealtDeck = new ArrayList<Card>();
		peopleDeck = new ArrayList<Card>();
		roomDeck = new ArrayList<Card>();
		weaponDeck = new ArrayList<Card>();

		try {
			loadSetupConfig();
		} catch (BadConfigFormatException | IOException e1) {
			e1.printStackTrace();
		}
		try {
			loadLayoutConfig();
		} catch (FileNotFoundException | BadConfigFormatException e) {
			e.printStackTrace();
		}

		setPlayersTurn(getPlayerList().get(0));

		setGame();
	}

	public void setConfigFiles(String layoutConfig, String setupConfig) {
		this.layoutConfig = "data/" + layoutConfig;
		this.setupConfig = "data/" + setupConfig;
	}

	public Room getRoom(char c) {
		Room room = roomMap.get(c);
		return room;
	}

	public Room getRoom(BoardCell cell) {
		Character symbol = cell.getCellSymbol();
		Room room = getRoom(symbol);
		return room;
	}

	public int getNumRows() {
		return rows;
	}

	public int getNumColumns() {
		return cols;
	}

	public BoardCell getCell(int row, int col) {
		BoardCell BoardCell = grid[row][col];
		return BoardCell;
	}

	// Reads in setUp Config file, creating the Room Objects, & populating the
	// roomMap
	public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException, IOException {
		File setupFile = new File(setupConfig);
		Scanner myReader = new Scanner(setupFile);
		int lineNum = 0;
		while (myReader.hasNextLine()) {
			String line = myReader.nextLine();
			// Check for comments
			if (line.contains("//")) {
				continue;
			}

			// If not a comment, split by ", "
			String[] result = line.split(", ");
			String itemType = result[TYPE];

			if (!itemType.equals("Room") && !itemType.equals("Space") && !itemType.equals("Player")
					&& !itemType.equals("Weapon")) {
				myReader.close();
				throw new BadConfigFormatException("Error in setup file on line: " + lineNum);
			}

			Card newCard = null;

			// create a new card for each object except "Space"
			if (!itemType.contains("Space")) {
				newCard = new Card(result[NAME], result[TYPE]);
				fullDeck.add(newCard);
			}

			if (itemType.contains("Room") || itemType.contains("Space")) {
				Character roomSymbol = result[SYMBOL].charAt(0);
				// Creates a new room for each line of setup file
				Room room = new Room(result[NAME], roomSymbol);
				// Adds each room to roomMap
				roomMap.put(roomSymbol, room);
				if (newCard != null) {
					roomDeck.add(newCard);
				}
			} else if (itemType.contains("Player")) {
				peopleDeck.add(newCard);
				Player newPlayer = null;
				// human player
				if (result[NAME].contains("Chihiro Ogino")) {
					newPlayer = newHumanPlayer(result);

				} else { // computer players
					newPlayer = newComputerPlayer(result);
				}
				playerList.add(newPlayer);
			} else if (itemType.contains("Weapon")) {
				weaponDeck.add(newCard);
			}

			lineNum++;
		}
		myReader.close();
	}

	private Player newHumanPlayer(String[] result) {
		Player newPlayer;
		newPlayer = new humanPlayer(result[NAME], result[SYMBOL], result[ROW], result[COLUMN]);
		return newPlayer;
	}

	private Player newComputerPlayer(String[] result) {
		Player newPlayer;
		newPlayer = new computerPlayer(result[NAME], result[SYMBOL], result[ROW], result[COLUMN]);
		return newPlayer;
	}

	/**
	 * loadLayoutConfig() Performs 4 Major Functions: 1. Reads in the layout file to
	 * determine board size 2. Reads in the layout file to build the grid of
	 * boardCell objects 3. Runs each cell through the gridCellClassifier function
	 * to set boardCell specific variables 4. Creates the adjacency list for each
	 * cell based on the boardCell variables
	 * 
	 * @throws FileNotFoundException
	 * @throws BadConfigFormatException
	 */
	public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {
		File layoutFile = gridSizeCalculator();

		gridInitializer();

		boardInitializer(layoutFile);

		adjMatrixIntializer();
	}

	private void adjMatrixIntializer() {
		// Creates adjacency matrix for the entire board
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				calculateCellAdj(i, j); // Sets the adjList for the current Cell
				adjMtx.put(grid[i][j], grid[i][j].getAdjList());
			}
		}
	}

	private void boardInitializer(File layoutFile) throws FileNotFoundException, BadConfigFormatException {
		// Reads in file second time to create the board
		Scanner myReader2 = new Scanner(layoutFile);
		int row = 0;
		while (myReader2.hasNextLine()) {
			String line = myReader2.nextLine();
			String[] result = line.split(",");
			for (int col = 0; col < cols; col++) {
				// Checks for bad config file
				if (!roomMap.containsKey(result[col].charAt(0))) {
					myReader2.close();
					throw new BadConfigFormatException(
							"Letter found in config file that is not a known room: " + result[col].charAt(0));
				}

				grid[row][col].setCellSymbol(result[col]);

				// Sets boardCell variables: isDoor, isRoom, isRoomCenterCell,
				// isSecretPassageway, isRoomLabel
				gridCellClassifier(row, col, result);

			}
			row++;
		}
		myReader2.close();
	}

	private void gridInitializer() {
		// Initializes a grid[rows][cols] of empty boardCells
		grid = new BoardCell[rows][cols];
		for (int col = 0; col < cols; col++) {
			for (int row = 0; row < rows; row++) {
				grid[row][col] = new BoardCell(row, col);
			}
		}
	}

	// Why does this method return a file and not just be void
	private File gridSizeCalculator() throws FileNotFoundException, BadConfigFormatException {
		// reads in file once to find numRows, numColumns
		File layoutFile = new File(layoutConfig);
		Scanner myReader = new Scanner(layoutFile);
		int numRows = 0;
		int firstRowCols = 0;
		while (myReader.hasNextLine()) {
			String line = myReader.nextLine();
			String[] result = line.split(",");
			if (firstRowCols == 0) {
				firstRowCols = result.length;
			} else {
				int curRowCols = result.length;
				if (curRowCols != firstRowCols) {
					myReader.close();
					throw new BadConfigFormatException("Bad Config File found. Inconsistent number of columns.");
				}
			}
			numRows++;
		}
		rows = numRows;
		cols = firstRowCols;

		myReader.close();
		return layoutFile;
	}

	/**
	 * checks if the second character is an arrow to a door location
	 */
	private boolean isDoorArrow(char arrow) {
		if (arrow == '<' || arrow == '>' || arrow == '^' || arrow == 'v') {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Sets boardCell variables: isDoor, isRoom, isRoomCenterCell,
	 * isSecretPassageway. e.g. Takes in a room cell, sets isRoom = true
	 * 
	 */
	private void gridCellClassifier(int row, int col, String[] result) {
		// sets cell to "room" if not a walkway or unused square,
		// TODO: So walkways and unused cells have isRoom = false, but are in roomMap
		if (!result[col].equals("X") && result[col].charAt(0) != 'W') {
			grid[row][col].setIsRoom(true);
		}

		if (result[col].equals("X")) {
			grid[row][col].setIsUnused(true);
		}

		if (result[col].equals("W")) {
			grid[row][col].setIsWalkway(true);
		}

		// Door, Secret Passage, Room Center Cell, Room Label
		if (result[col].length() >= 2) {
			// Doorway found and the next character is an arrow to the doors location
			if (result[col].charAt(0) == 'W' && isDoorArrow(result[col].charAt(1))) {
				grid[row][col].setIsDoor(true);
				// Set door direction
				grid[row][col].setDoorDirection(result[col].charAt(1));
			}
			// Room Label found
			else if (result[col].charAt(1) == '#') {
				grid[row][col].setIsLabel(true);
				// Set this cell to the Room's labelCell
				Room room = roomMap.get(result[col].charAt(0));
				room.setLabelCell(grid[row][col]);

			}
			// Center Cell Label Found
			else if (result[col].charAt(1) == '*') {
				grid[row][col].setIsRoomCenterCell(true);
				// Set this cell to the Room's centerCell
				Room room = roomMap.get(result[col].charAt(0));
				room.setCenterCell(grid[row][col]);

			}
			// Secret Passageway found. Add secretPassageway Destination Char to Room object
			else {
				grid[row][col].setIsSecretPassage(true);
				// Sets current cell's passageway variable
				grid[row][col].setSecretPassage(result[col].charAt(1));
				// Sets current rooms passageway variable
				Room currRoom = roomMap.get(result[col].charAt(0));
				currRoom.setHasSecretPassage(true);
				// Sets the rooms passage room to the destination of the secret Passage
				currRoom.setPassageRoom(result[col].charAt(1));
			}
		}
	}

	// Helper function for setAdjList
	// Processes Walkways ONLY
	private void addWalkwayAdj(BoardCell cell, Direction direction) {
		switch (direction) {
		case RIGHT:
			BoardCell adjCell = grid[cell.getRowNum()][cell.getColumnNum() + 1];
			isValidAdj(cell, adjCell);
			break;

		case LEFT:
			BoardCell adjCell1 = grid[cell.getRowNum()][cell.getColumnNum() - 1];
			isValidAdj(cell, adjCell1);
			break;

		case UP:
			BoardCell adjCell2 = grid[cell.getRowNum() - 1][cell.getColumnNum()];
			isValidAdj(cell, adjCell2);
			break;

		case DOWN:
			BoardCell adjCell3 = grid[cell.getRowNum() + 1][cell.getColumnNum()];
			isValidAdj(cell, adjCell3);
			break;

		default:
			break;

		}

	}

	/*
	 * Helper function to check if the cells adj can be added to if it can be added
	 * to it is added to. If not it returns.
	 */
	private void isValidAdj(BoardCell cell, BoardCell adjCell) {
		if (!adjCell.isRoom() && adjCell.getCellSymbol() != 'X') {
			cell.addAdjacency(adjCell);
		}
	}

	private void calculateCellAdj(int row, int col) {

		// If unused cell: Do not create adjList for it
		BoardCell currCell = grid[row][col];
		if ((currCell.getCellSymbol() == 'X')) {
			return;
		}

		// If doorway cell: adds the center of the room to the doors adjList
		// Also adds the doorway to the centerCells adjList
		if (currCell.isDoorway()) {

			switch (currCell.getDoorDirection()) {
			case RIGHT:
				BoardCell roomCell = grid[currCell.getRowNum()][currCell.getColumnNum() + 1];
				Room currRoom = roomMap.get(roomCell.getCellSymbol());
				currCell.addAdjacency(currRoom.getCenterCell());
				currRoom.getCenterCell().addAdjacency(currCell);

				break;
			case LEFT:
				BoardCell roomCell1 = grid[currCell.getRowNum()][currCell.getColumnNum() - 1];
				Room currRoom1 = roomMap.get(roomCell1.getCellSymbol());
				currCell.addAdjacency(currRoom1.getCenterCell());
				currRoom1.getCenterCell().addAdjacency(currCell);

				break;
			case UP:
				BoardCell roomCell2 = grid[currCell.getRowNum() - 1][currCell.getColumnNum()];
				Room currRoom2 = roomMap.get(roomCell2.getCellSymbol());
				currCell.addAdjacency(currRoom2.getCenterCell());
				currRoom2.getCenterCell().addAdjacency(currCell);

				break;
			case DOWN:
				BoardCell roomCell3 = grid[currCell.getRowNum() + 1][currCell.getColumnNum()];
				Room currRoom3 = roomMap.get(roomCell3.getCellSymbol());
				currCell.addAdjacency(currRoom3.getCenterCell());
				currRoom3.getCenterCell().addAdjacency(currCell);

				break;
			default:
				return;
			}
		}

		// Add secretPassage destination to center cell's adjList
		if (currCell.isRoomCenter()) {
			Room currRoom = roomMap.get(currCell.getCellSymbol());
			if (currRoom.isHasSecretPassage()) {
				Character nextRoomChar = currRoom.getPassageRoom();
				currCell.addAdjacency(roomMap.get(nextRoomChar).getCenterCell());
			}
		}

		if (!currCell.isRoom()) {
			// Check if on top edge
			if (row == 0) {

				// if yes, check if on left edge
				if (col == 0) {
					addWalkwayAdj(currCell, Direction.RIGHT);

					addWalkwayAdj(currCell, Direction.DOWN);
				}
				// check if on right edge
				else if (col == cols - 1) {
					addWalkwayAdj(currCell, Direction.LEFT);
					addWalkwayAdj(currCell, Direction.DOWN);
				}
				// otherwise, the normal top edge case
				else if (col != cols - 1 && col != 0) {
					addWalkwayAdj(currCell, Direction.RIGHT);
					addWalkwayAdj(currCell, Direction.LEFT);
					addWalkwayAdj(currCell, Direction.DOWN);
				}
			}
			// check if on bottom edge
			else if (row == rows - 1) {
				// if yes, check if on left
				if (col == 0) {
					addWalkwayAdj(currCell, Direction.UP);
					addWalkwayAdj(currCell, Direction.RIGHT);
				}
				// check if on right edge
				if (col == rows - 1) {
					addWalkwayAdj(currCell, Direction.UP);
					addWalkwayAdj(currCell, Direction.LEFT);
				}
				// Else normal bottom edge case
				else if (col != 0) {
					addWalkwayAdj(currCell, Direction.UP);
					addWalkwayAdj(currCell, Direction.LEFT);
					addWalkwayAdj(currCell, Direction.RIGHT);
				}
			}

			// Check if on left edge
			else if (col == 0) {
				addWalkwayAdj(currCell, Direction.UP);
				addWalkwayAdj(currCell, Direction.DOWN);
				addWalkwayAdj(currCell, Direction.RIGHT);
			}

			// Check if on right edge
			else if (col == cols - 1) {
				addWalkwayAdj(currCell, Direction.UP);
				addWalkwayAdj(currCell, Direction.DOWN);
				addWalkwayAdj(currCell, Direction.LEFT);
			}

			// Else add all surrounding cells to adjList
			else {
				addWalkwayAdj(currCell, Direction.UP);
				addWalkwayAdj(currCell, Direction.DOWN);
				addWalkwayAdj(currCell, Direction.RIGHT);
				addWalkwayAdj(currCell, Direction.LEFT);
			}
		}

	}

	public Set<BoardCell> getAdjList(int i, int j) {
		BoardCell tempCell = grid[i][j];
		return tempCell.getAdjList();
	}

	public Set<BoardCell> getTargets() {
		return targets;
	}

	public void calcTargets(BoardCell cell, int pathLength) {
		visited.clear();
		targets.clear();
		visited.add(cell);
		findAllTargets(cell, pathLength);

	}

	public void findAllTargets(BoardCell startCell, int pathLength) {
		for (BoardCell adjCell : startCell.getAdjList()) {
			if (visited.contains(adjCell) || adjCell.isOccupied()) {
				continue;
			}
			visited.add(adjCell);
			if (pathLength == 1) {
				if (!adjCell.isOccupied()) {
			
					targets.add(adjCell);
					adjCell.setIsTargetCell(true); //flag for highlightCell
					visited.remove(adjCell);
				}
			} else {
				if (adjCell.isRoomCenter()) {
					targets.add(adjCell);
					adjCell.setIsTargetCell(true);
					visited.remove(adjCell);
				} else {
					findAllTargets(adjCell, pathLength - 1);
					visited.remove(adjCell);
				}
			}
		}
	}

	/*
	 * initial function to setup and deal with the cards
	 */
	private void setGame() {
		createSolution();
		dealCards();

	}

	public void createSolution() {
		Collections.shuffle(peopleDeck);
		Collections.shuffle(roomDeck);
		Collections.shuffle(weaponDeck);
		Random random = new Random();
		int randomPerson = random.nextInt(peopleDeck.size());
		int randomRoom = random.nextInt(roomDeck.size());
		int randomWeapon = random.nextInt(weaponDeck.size());
		Board.solution = new Solution(peopleDeck.get(randomPerson), roomDeck.get(randomRoom),
				weaponDeck.get(randomWeapon));

		// update dealtStack after removing solution cards
		dealtDeck.addAll(fullDeck);
		dealtDeck.remove(peopleDeck.get(randomPerson));
		dealtDeck.remove(roomDeck.get(randomRoom));
		dealtDeck.remove(weaponDeck.get(randomWeapon));
	}

	// return first disapproval card that matching to suggesting card from other
	// players, except the suggesting player
	public Card handleSuggestion(Card suggestedCard1, Card sugguestedCard2, Card suggestedCard3,
			Player suggestingPlayer) {
		Card disprovedCard = null;

		for (int i = 0; i < playerList.size(); i++) {
			Player player = playerList.get(i);

			if (player != suggestingPlayer) {
				disprovedCard = player.disproveSuggestion(suggestedCard1, sugguestedCard2, suggestedCard3);

				if (disprovedCard != null) {
					break; // no need to look further when we have a disproved card
				}
			}
		}

		return disprovedCard;
	}

	// dealing cards to every player one at a time
	public void dealCards() {
		Collections.shuffle(dealtDeck);
		while (!dealtDeck.isEmpty()) {
			for (Player player : playerList) {
				Color playerColor = player.getPlayerColor();
				Card dealtCard = dealtDeck.remove(0);
				dealtCard.setCardColor(playerColor);
				player.updateHand(dealtCard);
			}
		}
	}

	public Boolean checkAccusation(Card Room, Card Person, Card Weapon) {
		Solution solution = getSolution();
		if (solution.getRoom().equals(Room) && solution.getPerson().equals(Person)
				&& solution.getWeapon().equals(Weapon)) {
			return true;
			
		} else {
			return false;
		}
	}

	// TODO: This should be moved out of the for tests section
	// TODO: This was being moved into the player class
	public int rollDie() {

		Random randomRoll = new Random();
		int randomDie = randomRoll.nextInt(6) + 1;
		// String Die = "" + randomDie;
		return randomDie;
	}

	/*
	 * This method should be checking if the mouse is being clicked on a cell that
	 * is in the targets list
	 */
	public boolean clickContainsTarget(int mouseX, int mouseY) {

		
		System.out.println(getTargets().size());
		for (BoardCell targetCell : targets) {
			//System.out.println(targets);
			// BoardCell cell = targetCell;
			int row = targetCell.getRowNum();
			int col = targetCell.getColumnNum();
			Rectangle rectangle = new Rectangle(row, col, cellWidth, cellHeight);
			if (rectangle.contains(new Point(mouseX, mouseY))) {
				System.out.println("here"); 
				return true;
			}
		}
		return false;
	}

	// ************** Methods for unit testing purposes only *************//
	public int getCellWidth() {
		return this.cellWidth;
	}

	public int getCellHeight() {
		return this.cellHeight;
	}

	public int getNumPlayerCards() {
		return peopleDeck.size();
	}

	public int getNumRoomCards() {
		return roomDeck.size();
	}

	public int getNumWeaponCards() {
		return weaponDeck.size();
	}

	public int getNumCards() {
		return fullDeck.size();
	}

	public Player getPlayer(int index) {
		return playerList.get(index);
	}

	public int getDealtDeckSize() {
		return dealtDeck.size();
	}

	public int getNumHumanPlayers() {
		int numHumanPlayers = 0;
		for (int i = 0; i < playerList.size(); i++) {

			if (playerList.get(i) instanceof humanPlayer) {
				numHumanPlayers++;
			}
		}
		return numHumanPlayers;
	}

	public int getNumCompPlayers() {
		int numComputerPlayers = 0;
		for (int i = 0; i < playerList.size(); i++) {

			if (playerList.get(i) instanceof computerPlayer) {
				numComputerPlayers++;
			}
		}
		return numComputerPlayers;
	}

	public ArrayList<Player> getPlayerList() {
		return playerList;
	}

	// TODO: is this a duplicate with get targets?
//	public Set<BoardCell> getTargetList() {
//		return this.targets;
//	}

	public Map<Character, Room> getRoomMap() {
		return roomMap;
	}

	public ArrayList<Card> getRoomDeck() {
		return roomDeck;
	}

	public void movePlayer(int i, int j, Player player) {
		player.setPlayerLocation(i, j);

	}

	public static Solution getSolution() {
		return solution;
	}

	public Player getPlayersTurn() {
		return playerTurn;
	}

	/**
	 * Switches the current player to the next player in the PlayersList also
	 * restarts the list when it gets to the bottom
	 */
	// TODO: These should be moved out of the only for tests sections as they are
	// needed elsewhere
	public void nextTurn() {
		// human player's turn if already iterate to the last player
		if (getPlayerList().indexOf(getPlayersTurn()) == getPlayerList().size() - 1) {
			this.playerTurn = getPlayerList().get(0);
			playerTurn.setHasPlayerACC(false);
			playerTurn.setHasPlayerMoved(false);

		} else {
			this.playerTurn = getPlayerList().get(getPlayerList().indexOf(getPlayersTurn()) + 1);
			playerTurn.setHasPlayerACC(false);
			playerTurn.setHasPlayerMoved(false);

		}
	}

	public void setPlayersTurn(Player playersTurn) {
		this.playerTurn = playersTurn;
	}

	public void initializeForTest() {
		targets = new HashSet<BoardCell>();
		playerList = new ArrayList<Player>();
		fullDeck = new ArrayList<Card>();
		dealtDeck = new ArrayList<Card>();
		peopleDeck = new ArrayList<Card>();
		roomDeck = new ArrayList<Card>();
		weaponDeck = new ArrayList<Card>();

		try {
			loadSetupConfig();
		} catch (BadConfigFormatException | IOException e1) {
			e1.printStackTrace();
		}
		try {
			loadLayoutConfig();
		} catch (FileNotFoundException | BadConfigFormatException e) {
			e.printStackTrace();
		}

		setPlayersTurn(getPlayerList().get(0));
		setGameForTest();
	}

	public void setGameForTest() {
		createSolutionForTest();
		dealCardsForTest();
	}

	private void dealCardsForTest() {
		while (!dealtDeck.isEmpty()) {
			for (Player player : playerList) {
				Color playerColor = player.getPlayerColor();
				Card dealtCard = dealtDeck.remove(0);
				dealtCard.setCardColor(playerColor);
				player.updateHand(dealtCard);
			}
		}
	}

	private void createSolutionForTest() {

		// initialized solution to be the first person, first room, first weapon
		Board.solution = new Solution(peopleDeck.get(0), roomDeck.get(0), weaponDeck.get(0));
		// update dealtStack after removing solution cards
		dealtDeck.addAll(fullDeck);
		dealtDeck.remove(peopleDeck.get(0));
		dealtDeck.remove(roomDeck.get(0));
		dealtDeck.remove(weaponDeck.get(0));

	}

}

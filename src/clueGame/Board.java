package clueGame;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.io.*;

/**
 * BoardCell
 * 
 * @author michaeleack
 * @author johnOmalley Date: 3/7/23 Collaborators: None Sources: None
 */
public class Board {
	private static Board boardInstance = new Board();
	private int cols;
	private int rows;
	private BoardCell[][] grid;
	private Map<BoardCell, Set<BoardCell>> adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
	private ArrayList<BoardCell> visited = new ArrayList<BoardCell>();
	private Set<BoardCell> targets = new HashSet<BoardCell>();
	private Map<Character, Room> roomMap = new HashMap<Character, Room>();
	private String layoutConfig;
	private String setupConfig;
	private final static int TYPE = 0;
	private final static int ROOMNAME = 1;
	private final static int ROOMSYMBOL = 2;

	// constructor is private to ensure only one can be created
	private Board() {
		super();
	}

	// this method returns the only Board
	public static Board getInstance() {
		return boardInstance;
	}

	/*
	 * initialize the board (since we are using singleton pattern)
	 */
	public void initialize() {
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
			else {
				String[] result = line.split(", ");
				String itemType = result[TYPE];
				if (!itemType.equals("Room") && !itemType.equals("Space")) {
					myReader.close();
					throw new BadConfigFormatException("Error in setup file on line: " + lineNum);
				}
				// Populates roomMap
				Character roomSymbol = result[ROOMSYMBOL].charAt(0);
				// Creates a new room for each line of setup file
				Room room = new Room(result[ROOMNAME], roomSymbol);

				// Adds each room to roomMap
				roomMap.put(roomSymbol, room);
			}
			lineNum++;
		}
		myReader.close();
	}

	/**
	 * loadLayoutConfig() Performs 4 Major Functions: 1. Reads in the layout file to
	 * determine board size 2. Reads in the layout file to build the grid of
	 * boardCell objects 3. Runs each cell through the gridCellClassified function
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
				grid[row][col].setCellSymbol(result[col]);
				// Checks for bad config file
				if (!roomMap.containsKey(result[col].charAt(0))) {
					myReader2.close();
					throw new BadConfigFormatException(
							"Letter found in config file that is not a known room: " + result[col].charAt(0));
				}

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

	/*
	 * checks if the second character is an arrow to a door location
	 */
	private boolean isDoorArrow(char arrow) {
		if (arrow == '<' || arrow == '>' || arrow == '^' || arrow == 'v') {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * Sets boardCell variables: isDoor, isRoom, isRoomCenterCell,
	 * isSecretPassageway. e.g. Takes in a room cell, sets isRoom = true
	 * 
	 */
	private void gridCellClassifier(int row, int col, String[] result) {
		// sets cell to "room" if not a walkway or unused square,
		if (!result[col].equals("X") && result[col].charAt(0) != 'W') {
			grid[row][col].setIsRoom(true);
		}

		// Door, Secret Passage, Room Center Cell, Room Label
		if (result[col].length() == 2) {
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
					addWalkwayAdj(currCell, Direction.RIGHT); // This is probably bad practice but I think it will
																// work
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
					visited.remove(adjCell);
				}
			} else {
				if (adjCell.isRoomCenter()) {
					targets.add(adjCell);
					visited.remove(adjCell);
				} else {
					findAllTargets(adjCell, pathLength - 1);
					visited.remove(adjCell);
				}
			}
		}
	}
}

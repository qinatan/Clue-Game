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
 * @author johnOmalley 
 * Date: 3/7/23 Collaborators: None Sources: None
 */
public class Board {
	/*
	 * variable and methods used for singleton pattern All variable and method names
	 * should use lower camelCase except static finals(consts) variables should use
	 * descriptive names that reveal intent most of these should be private unless
	 * we have a really good reason
	 */
	private static Board theInstance = new Board();
	private int COLS; // TODO: we should clean this up, the variables don't work as finals but should
	private int ROWS; // TODO: Same thing
	private BoardCell[][] grid ;
	private Map<BoardCell, Set<BoardCell>> adjMtx = new HashMap<BoardCell, Set<BoardCell>>();
	private ArrayList<BoardCell> visitedList = new ArrayList<BoardCell>();
	private Set<BoardCell> targetsSet = new HashSet<BoardCell>();
	private Map<Character, Room> roomMap = new HashMap<Character, Room>();
	private String layoutConfig;
	private String setupConfig;

	// constructor is private to ensure only one can be created
	private Board() {
		super();
	}

	// this method returns the only Board
	public static Board getInstance() {
		return theInstance;
	}

	/*
	 * initialize the board (since we are using singleton pattern)
	 */
	public void initialize() {
		try {
			loadSetupConfig();
		} catch (FileNotFoundException | BadConfigFormatException e1) {
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
		return ROWS;
	}

	public int getNumColumns() {
		return COLS;
	}

	public BoardCell getCell(int row, int col) {
		BoardCell BoardCell = grid[row][col];
		return BoardCell;
	}

	// Reads in setUp Config file, creating the Room Objects, & populating the roomMap
	public void loadSetupConfig() throws FileNotFoundException, BadConfigFormatException {
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
				String resultZero = result[0];
				if (!resultZero.equals("Room") && !resultZero.equals("Space")) {
					throw new BadConfigFormatException("Error in setup file on line: " + lineNum);
				}
				// Populates roomMap
				Character roomSymbol = result[2].charAt(0);
				// Creates a new room for each line of setup file 
				Room room = new Room(result[1], roomSymbol);
				
				// Adds each room to roomMap
				roomMap.put(roomSymbol, room);
			}
			lineNum++;
		}
		myReader.close();
	}

	public void loadLayoutConfig() throws FileNotFoundException, BadConfigFormatException {
		// reads in file once to find numRows, numColumns
		File layoutFile = new File(layoutConfig);
		Scanner myReader = new Scanner(layoutFile);
		int rows = 0;
		int firstRowCols = 0;
		while (myReader.hasNextLine()) {
			if (firstRowCols == 0) {
				String line = myReader.nextLine();
				String[] result = line.split(",");
				firstRowCols = result.length;
			} else {
				String line = myReader.nextLine();
				String[] result = line.split(",");
				int curRowCols = result.length;
				if (curRowCols != firstRowCols) {
					myReader.close();
					throw new BadConfigFormatException("Bad Config File found. Inconsistent number of columns.");
				}
			}
			rows++;
		}

		ROWS = rows;
		COLS = firstRowCols;

		myReader.close();

		// Build grid of empty BoardCells
		grid = new BoardCell[ROWS][COLS];
		for (int col = 0; col < COLS; col++) {
			for (int row = 0; row < ROWS; row++) {
				grid[row][col] = new BoardCell(row, col);
			}
		}

		// Reads in file second time to create the board
		Scanner myReader2 = new Scanner(layoutFile);
		int row = 0;
		while (myReader2.hasNextLine()) {
			String line = myReader2.nextLine();
			String[] result = line.split(",");
			for (int col = 0; col < COLS ; col++) {
				// sets BoardCell symbol for each BoardCell

				grid[row][col].setCellSymbol(result[col]);
				// Checks for bad config file
				if (!roomMap.containsKey(result[col].charAt(0))) {
					throw new BadConfigFormatException(
							"Letter found in config file that is not a known room: " + result[col].charAt(0));
				}

				// Sets boardCell variables: isDoor, isRoom, isRoomCenterCell, isSecretPassageway, isRoomLabel
				gridCellClassifier(row, result, col);
			}

			row++;
		}

		for (int i = 0; i < ROWS -1; i++) {
			for (int j = 0; j < COLS -1; j++) {
				setAdjList(i, j); // Sets the adjList for the current Cell
				adjMtx.put(grid[i][j], grid[i][j].getAdjList());
			}
		}
	}

	
	// Sets boardCell variables: isDoor, isRoom, isRoomCenterCell, isSecretPassageway
	// e.g. Takes in a room cell, sets isRoom = true
	private void gridCellClassifier(int row, String[] result, int col) {
		// sets cell to "room" if not a walkway or unused square,
		if (!result[col].equals("X") && !result[col].equals("W")) {
			grid[row][col].setIsRoom(true);
		} 
		
		// Door, Secret Passage, Room Center Cell, Room Label
		if (result[col].length() == 2) {
			// Doorway found
			if (result[col].charAt(0) == 'W') {
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
			// Secret Passageway found
			else {
				grid[row][col].setSecretPassage(result[col].charAt(1));
				Room room = roomMap.get(result[col].charAt(1));
				room.setHasSecretPassage(true);
				// Sets the rooms passage room to the destination of the secret Passage 
				room.setPassageRoom(roomMap.get(grid[row][col].getSecretPassage()));
			}

		}
	}
	
	// Helper function for setAdjList
	// Processes Walkways ONLY 
	private void addWalkwayAdj(BoardCell cell, Direction direction) {

		switch (direction) {
		case RIGHT:
			BoardCell adjCell = grid[cell.getRowNum()][cell.getColumnNum() + 1];
			cell.addAdjacency(adjCell);
			break;
			
		case LEFT:
			BoardCell adjCell1 = grid[cell.getRowNum()][cell.getColumnNum() - 1];
			cell.addAdjacency(adjCell1);
			break;
			
		case UP:
			BoardCell adjCell2 = grid[cell.getRowNum() - 1][cell.getColumnNum()];
			cell.addAdjacency(adjCell2);
			break;
			
		case DOWN:
			BoardCell adjCell3 = grid[cell.getRowNum() + 1][cell.getColumnNum()];
			cell.addAdjacency(adjCell3);
			break;
			
		default:
			break;

		}

	}

	private void setAdjList(int row, int col) {
		
		// Checks if it's an unused cell
		BoardCell currCell = grid[row][col];
		if ((currCell.getCellSymbol() == 'X')) {
			return;
		}

		// Takes in a doorway cell and adds the center of the room to the doors adjList
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
				Room nextRoom = currRoom.getPassageRoom();
				currCell.addAdjacency(nextRoom.centerCell);
		}
		
		if (!currCell.isRoom()) {
			// Check if on top edge
			if (row == 0) {
	
				// if yes, check if on left edge
				if (col == 0) {
					addWalkwayAdj(currCell, Direction.RIGHT); // This is probably bad practice but I think it will work
					addWalkwayAdj(currCell, Direction.DOWN);
				}
				// check if on right edge
				else if (col == COLS - 1) {
					addWalkwayAdj(currCell, Direction.LEFT);
					addWalkwayAdj(currCell, Direction.DOWN);
				}
				// otherwise, the normal top edge case
				else if (col != COLS - 1 && col != 0) {
					addWalkwayAdj(currCell, Direction.RIGHT);
					addWalkwayAdj(currCell, Direction.LEFT);
					addWalkwayAdj(currCell, Direction.DOWN);
				}
			}
			// check if on bottom edge
			else if (row == ROWS - 1) {
				// if yes, check if on left
				if (col == 0) {
					addWalkwayAdj(currCell, Direction.UP);
					addWalkwayAdj(currCell, Direction.RIGHT);
				}
				// check if on right edge
				if (col == ROWS - 1) {
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
			else if (col == COLS - 1) {
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
	}

	/*
	 * // Traverse grid populating AdjMatrix for (int col = 0; col < COLS; col++) {
	 * for (int row = 0; row < ROWS; row++) { adjMtx.put(grid[row][col],
	 * grid[row][col].getAdjList()); } }
	 */

	public Set<BoardCell> getAdjList(int i, int j) {
		// TODO Change this method. This is incorrect just to make the Junit test not
		// have errors
		BoardCell tempCell = grid[i][j];

		return tempCell.getAdjList();
	}

	public Set<BoardCell> getTargets() {
		// TODO Change this method. This is incorrect just to make the Junit test not
		// have errors
		int i = 1;
		int j = 1;
		targetsSet.add(getCell(i, j));
		return targetsSet;
	}

	public void calcTargets(BoardCell cell, int i) {
		// TODO Auto-generated method stub

	}

}

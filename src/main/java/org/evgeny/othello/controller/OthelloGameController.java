package org.evgeny.othello.controller;

import java.util.ArrayList;
import java.util.List;

import static org.evgeny.othello.controller.Direction.*;

public class OthelloGameController {
    private static int STANDARD_BOARD_SIZE = 8;
    private final int rows;
    private final int columns;
    private Side[][] board;
    private boolean gameFinished = false;

    private Side nextMoveSide = Side.DARK;

    //building standard board
    public OthelloGameController() {
        this.rows = STANDARD_BOARD_SIZE;
        this.columns = STANDARD_BOARD_SIZE;

        this.board = new Side[rows][columns];

        for (int i=0;i<rows;i++)
            for (int j=0;j<columns;j++)
                board[i][j]= Side.NONE;
        board[3][3]= Side.LIGHT;
        board[3][4]= Side.DARK;
        board[4][3]= Side.DARK;
        board[4][4]= Side.LIGHT;
    }

    //building custom board
    public OthelloGameController(Side[][] board){
        this.board = board;
        this.rows = board.length;
        if (rows < 1 || rows > 9){
            throw new IllegalArgumentException("only support boards with 1 to 9 rows");
        }

        this.columns = board[0].length;
        if (columns < 1 || columns>26){
            throw new IllegalArgumentException("only support boards with 1 to 26 columns");
        }

        this.findNextMoveSideOrFinishTheGame();
    }

    public void makeMove(Cell cell) {
        if (getSide(cell) != Side.NONE){
            throw new IllegalArgumentException("invalid move, not an empty cell");
        }

        if (gameFinished){
            throw new IllegalArgumentException("invalid move, game finished");
        }

        boolean validMove = false;
        for(Direction direction : values()){
            if (this.runDirection(cell, direction))
                validMove = true;
        }

        if (!validMove){
            throw new IllegalArgumentException("invalid move");
        }

        nextMoveSide = this.oppositeSide();

        findNextMoveSideOrFinishTheGame();
    }

    private void findNextMoveSideOrFinishTheGame() {
        if (!validMovesAvailable()){
            //pass back if no valid moves available, finish the game if no valid moves for both sides
            nextMoveSide = this.oppositeSide();
            if (!validMovesAvailable()) {
                gameFinished = true;
            }
        }
    }

    private boolean validMovesAvailable() {
        for (int i=0;i<rows;i++){
            for (int j=0;j<columns;j++){
                if (board[i][j] == Side.NONE) {
                    for (Direction direction : values()) {
                        if (this.runDirection(new Cell(i,j), direction, true))
                            return true;
                    }
                }
            }
        }
        return false;
    }

    //returns true if any tokens changed side
    private boolean runDirection(Cell cell, Direction direction) {
        return this.runDirection(cell, direction, false);
    }

    //returns true if any tokens changed side
    //in dry mode no tokens are actually converted, this is needed to check if any valid moves left
    private boolean runDirection(Cell startCell, Direction direction, boolean dryRun) {
        boolean foundEnemy = false;

        Cell iterator = startCell;
        List<Cell> visitedCells = new ArrayList<>();
        visitedCells.add(iterator);

        while (true){
            iterator = findNextCell(iterator, direction);
            //fallen off the board check
            if (iterator.getRow()<0 || iterator.getRow()>rows-1 || iterator.getColumn()<0 || iterator.getColumn() > columns-1){
                return false;
            }

            if (getSide(iterator) == Side.NONE){
                return false;
            } else if (getSide(iterator) == this.oppositeSide()){
                foundEnemy = true;
            } else if (getSide(iterator) == nextMoveSide){
                if (foundEnemy){
                    if (!dryRun)
                        this.assignToSide(visitedCells, nextMoveSide);
                    return true;
                } else {
                    return false;
                }
            }
            visitedCells.add(iterator);
        }
    }

    private Cell findNextCell(Cell cell, Direction direction) {
        return new Cell(cell.getRow()+direction.getRowStep(), cell.getColumn()+direction.getColumnStep());
    }

    private void assignToSide(List<Cell> cells, Side side) {
        for (Cell cell : cells){
            board[cell.getRow()][cell.getColumn()]=side;
        }
    }

    private Side oppositeSide() {
        if (nextMoveSide == Side.DARK) {
            return Side.LIGHT;
        } else {
            return Side.DARK;
        }
    }

    public String printBoard() {
        String result = "";
        for (int i =0;i<rows;i++){
            result+=(i+1)+" ";
            for (int j=0;j<columns;j++){
                result+=board[i][j].getCharacter();
            }
            result+=System.lineSeparator();
        }

        result+="  ";
        for (int j = 0;j<columns;j++){
            result+=(char) ('a'+j);
        }

        return result;
    }

    public boolean isGameFinished() {
        return gameFinished;
    }

    public String printGameResult() {
        if (!gameFinished){
            throw new IllegalStateException("Game is still ongoing");
        }

        String result = "No further moves available"+System.lineSeparator();

        int countDark = 0;
        int countLight = 0;
        for (int i=0;i<rows;i++){
            for (int j=0;j<columns;j++) {
                if (board[i][j] == Side.DARK) {
                    countDark++;
                } else if (board[i][j] == Side.LIGHT){
                    countLight++;
                }
            }
        }

        if (countDark>countLight){
            result+="Player '"+ Side.DARK.getCharacter()+"' wins ( "+countDark+" vs "+countLight+" )";
        } else if (countLight>countDark){
            result+="Player '"+ Side.LIGHT.getCharacter()+"' wins ( "+countLight+" vs "+countDark+" )";
        } else {
            result+="It's a draw ("+countDark+" each)";
        }
        return result;
    }

    public Side getNextMoveSide() {
        return nextMoveSide;
    }

    private Side getSide(Cell cell){
        return board[cell.getRow()][cell.getColumn()];
    }
}

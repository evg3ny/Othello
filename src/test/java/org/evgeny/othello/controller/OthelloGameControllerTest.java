package org.evgeny.othello.controller;

import org.evgeny.othello.moveparser.MoveParser;
import org.evgeny.othello.testutil.BoardFromStringBuilder;
import org.evgeny.othello.testutil.MultiLineStringBuilder;
import org.junit.Assert;
import org.junit.Test;

public class OthelloGameControllerTest {

    private MoveParser moveParser = new MoveParser();

    @Test
    public void testInitialization(){
        OthelloGameController gameController = new OthelloGameController();
        String expected = new MultiLineStringBuilder()
                .add("1 --------")
                .add("2 --------")
                .add("3 --------")
                .add("4 ---OX---")
                .add("5 ---XO---")
                .add("6 --------")
                .add("7 --------")
                .add("8 --------")
                .add("  abcdefgh").build();

        Assert.assertEquals(expected, gameController.printBoard());
    }

    @Test
    public void testTwoLinesConversion(){
        String board = new MultiLineStringBuilder()
                .add("1 --------")
                .add("2 --------")
                .add("3 --------")
                .add("4 ---OX---")
                .add("5 ---XO---")
                .add("6 ----O---")
                .add("7 ---X----")
                .add("8 --------")
                .add("  abcdefgh").build();

        OthelloGameController gameController = new OthelloGameController(BoardFromStringBuilder.buildBoard(board));
        gameController.makeMove(moveParser.parseMove("5f"));

        String expected = new MultiLineStringBuilder()
                .add("1 --------")
                .add("2 --------")
                .add("3 --------")
                .add("4 ---OX---")
                .add("5 ---XXX--")
                .add("6 ----X---")
                .add("7 ---X----")
                .add("8 --------")
                .add("  abcdefgh").build();

        Assert.assertEquals(expected, gameController.printBoard());
    }

    @Test
    public void testNoValidMovesDarkSideWins(){
        String board = new MultiLineStringBuilder()
                .add("1 XXX")
                .add("2 XOO")
                .add("3 XO-")
                .add("  abc").build();
        OthelloGameController gameController = new OthelloGameController(BoardFromStringBuilder.buildBoard(board));
        gameController.makeMove(moveParser.parseMove("3c"));

        Assert.assertTrue(gameController.isGameFinished());

        String expected = new MultiLineStringBuilder()
                .add("No further moves available")
                .add("Player 'X' wins ( 9 vs 0 )")
                .build();

        Assert.assertEquals(expected, gameController.printGameResult());
    }

    @Test
    public void testDraw(){
        String board = new MultiLineStringBuilder()
                .add("1 XXXX")
                .add("2 XXXX")
                .add("3 OOOO")
                .add("4 OOOO")
                .add("  abcd").build();
        OthelloGameController gameController = new OthelloGameController(BoardFromStringBuilder.buildBoard(board));
        Assert.assertTrue(gameController.isGameFinished());

        String expected = new MultiLineStringBuilder()
                .add("No further moves available")
                .add("It's a draw (8 each)")
                .build();

        Assert.assertEquals(expected, gameController.printGameResult());
    }

    @Test(expected=IllegalArgumentException.class)
    public void testMoveOnTakenCell(){
        String board = new MultiLineStringBuilder()
                .add("1 XXX")
                .add("2 XOO")
                .add("3 XO-")
                .add("  abc").build();

        OthelloGameController gameController = new OthelloGameController(BoardFromStringBuilder.buildBoard(board));
        gameController.makeMove(moveParser.parseMove("b2"));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testMoveOnFinishedGame(){
        String board = new MultiLineStringBuilder()
                .add("1 XXX")
                .add("2 X-O")
                .add("3 XOX")
                .add("  abc").build();

        OthelloGameController gameController = new OthelloGameController(BoardFromStringBuilder.buildBoard(board));
        gameController.makeMove(moveParser.parseMove("b2"));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testInvalidMove(){
        String board = new MultiLineStringBuilder()
                .add("1 XXX")
                .add("2 X-O")
                .add("3 XO-")
                .add("  abc").build();

        OthelloGameController gameController = new OthelloGameController(BoardFromStringBuilder.buildBoard(board));
        gameController.makeMove(moveParser.parseMove("b2"));
    }

    @Test(expected=IllegalArgumentException.class)
    public void testNoRowsCustomBoard(){
        new OthelloGameController(new Side[0][5]);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testTooManyRowsCustomBoard(){
        new OthelloGameController(new Side[10][5]);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testNoColumnsCustomBoard(){
        new OthelloGameController(new Side[1][0]);
    }

    @Test(expected=IllegalArgumentException.class)
    public void testTooManyColumnsCustomBoard(){
        new OthelloGameController(new Side[1][27]);
    }

    @Test(expected=IllegalStateException.class)
    public void testPrintResultsOnAnUnfinishedGame(){
        String board = new MultiLineStringBuilder()
                .add("1 XXXX")
                .add("2 XXXX")
                .add("3 OOOO")
                .add("4 O-OO")
                .add("  abcd").build();
        OthelloGameController gameController = new OthelloGameController(BoardFromStringBuilder.buildBoard(board));

        gameController.printGameResult();
    }
}

package org.evgeny.othello.testutil;

import org.evgeny.othello.controller.Side;
import org.junit.Assert;
import org.junit.Test;

public class BoardFromStringBuilderTest {

    @Test
    public void testBuildBoard(){
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
        Side[][] board = BoardFromStringBuilder.buildBoard(expected);
        Assert.assertEquals(board[3][3], Side.LIGHT);
        Assert.assertEquals(board[3][4], Side.DARK);
        Assert.assertEquals(board[7][7], Side.NONE);
    }
}

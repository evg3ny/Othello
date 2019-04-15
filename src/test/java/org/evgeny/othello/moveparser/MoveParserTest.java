package org.evgeny.othello.moveparser;

import org.evgeny.othello.controller.Cell;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MoveParserTest {
    private MoveParser moveParser;

    @Before
    public void beforeTest(){
        moveParser = new MoveParser();
    }

    @Test
    public void testRowColumn(){
        Assert.assertEquals(new Cell(3,2), moveParser.parseMove("4c"));
    }

    @Test
    public void testColumnRow(){
        Assert.assertEquals(new Cell(5,1), moveParser.parseMove("b6"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testTwoLetters(){
        moveParser.parseMove("aa");
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMoreThanTwoCharacters(){
        moveParser.parseMove("5ac");
    }
}

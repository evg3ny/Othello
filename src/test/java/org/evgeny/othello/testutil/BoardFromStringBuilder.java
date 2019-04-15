package org.evgeny.othello.testutil;

import org.evgeny.othello.controller.Side;

public class BoardFromStringBuilder {

    public static Side[][] buildBoard(String expected) {
        Side[][] result = null;//can't init the array until we parse the first line
        String[] lines = expected.split(System.lineSeparator());
        for (int i = 0;i<lines.length-1;i++){//discard last line (with coordinates)
            String line = lines[i].trim().substring(2); //discard first two characters, with row number and space
            if (result == null){
                result = new Side[lines.length-1][line.length()];
            }
            char[] lineAsChars = line.toCharArray();
            for (int j = 0;j<lineAsChars.length;j++){
                result[i][j] = charToSide(lineAsChars[j]);
            }
        }
        return result;
    }

    private static Side charToSide(char character) {
        for (Side side : Side.values()){
            if (side.getCharacter() == character)
                return side;
        }
        throw new IllegalArgumentException("Unknown side character "+character);
    }
}

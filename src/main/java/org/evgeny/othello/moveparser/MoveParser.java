package org.evgeny.othello.moveparser;

import org.evgeny.othello.controller.Cell;

public class MoveParser {

    public Cell parseMove(String move){
        if (move.length()!=2){
            throw new IllegalArgumentException("invalid move, only 2 character moves are accepted");
        }

        char columnChar;
        char rowChar;

        if (Character.isAlphabetic(move.charAt(0)) && Character.isDigit(move.charAt(1))){
            columnChar = move.charAt(0);
            rowChar = move.charAt(1);
        } else if (Character.isAlphabetic(move.charAt(1)) && Character.isDigit(move.charAt(0))){
            columnChar = move.charAt(1);
            rowChar = move.charAt(0);
        } else {
            throw new IllegalArgumentException("invalid move, one character needs to alphabetic and the other a digit");
        }

        int column =columnChar-'a';
        int row = rowChar-'1';

        return new Cell(row, column);
    }
}

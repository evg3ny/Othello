package org.evgeny.othello;

import org.evgeny.othello.controller.OthelloGameController;
import org.evgeny.othello.moveparser.MoveParser;

import java.util.Arrays;

public class Othello {

    public static void main(String[] args){
        String board = Othello.playGame("f5, 6f, f7, 4f, f3, 3e, d3, c5");
        System.out.println(board);
    }

    private static String playGame(String moves) {
        OthelloGameController gameController = new OthelloGameController();
        MoveParser moveParser = new MoveParser();

        Arrays.stream(moves.split(",")).map(s -> s.trim())
                .forEach(s -> gameController.makeMove(moveParser.parseMove(s)));

        return gameController.printBoard();
    }
}

package org.evgeny.othello;

import org.evgeny.othello.controller.OthelloGameController;
import org.evgeny.othello.moveparser.MoveParser;

import java.util.Scanner;

public class OthelloInteractive {

    public static void main(String[] args){
        OthelloGameController gameController = new OthelloGameController();
        MoveParser moveParser = new MoveParser();
        while(true){
            System.out.println(gameController.printBoard());
            System.out.print("Player '"+gameController.getNextMoveSide().getCharacter()+"' move: ");
            Scanner scanner = new Scanner(System.in);
            try {
                gameController.makeMove(moveParser.parseMove(scanner.nextLine()));

                if (gameController.isGameFinished()){
                    System.out.println(gameController.printGameResult());
                    System.exit(0);
                }
            } catch (IllegalArgumentException e){
                System.out.println("Invalid move. Please try again.");
            }
        }
    }
}

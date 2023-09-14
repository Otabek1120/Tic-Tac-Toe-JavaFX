package views;

import java.util.Scanner;

import model.IntermediateAI;
import model.TicTacToeGame;
import model.TicTacToeStrategy;

public class TTTConsole {

    public static void main(String[] args) {
        TicTacToeGame game = new TicTacToeGame();

        TicTacToeStrategy strategy = new IntermediateAI();
        game.setComputerPlayerStrategy(strategy);

        System.out.println(game.toString() + "\n");
        while (!gameOver(game)) {
            humanMove(game);
            System.out.println(game + "\n");
        }

        if (game.didWin('X')) {
            System.out.println("X wins");
        } else if (game.didWin('O')) {
            System.out.println("O wins");
        } else {
            System.out.println("Tie");
        }

    }

    private static void humanMove(TicTacToeGame game) {
        Scanner input = new Scanner(System.in);

        System.out.print("Enter row and column: ");
        int row = input.nextInt();
        int col = input.nextInt();
        if (game.available(row, col)) {
            game.humanMove(row, col, false);
        }
    }

    private static boolean gameOver(TicTacToeGame game) {
        return  game.didWin('X') || 
                game.didWin('O') ||
                game.tied();
    }
}

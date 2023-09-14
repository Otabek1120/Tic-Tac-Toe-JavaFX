package model;

import java.util.Random;

public class IntermediateAI implements TicTacToeStrategy {

    private static Random gen;

    public IntermediateAI() {
        gen = new Random();
    }

    @Override
    public OurPoint desiredMove(TicTacToeGame theGame) {
        if (theGame.maxMovesRemaining() == 0) {
            throw new IGotNowhereToGoException(" -- No moves remaining!!!");
        }

        // Making a win move
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // System.out.println(theGame.toString());
                if (canWin(i, j, theGame)) {
                    return new OurPoint(i, j);
                }
            }
        }

        // Making a blocking move
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                // System.out.println(theGame.toString());
                if (canBlock(i, j, theGame)) {
                    return new OurPoint(i, j);
                }
            }
        }

        // Random move
        return randomMove(theGame);
    }

    

    private boolean canWin(int row, int col, TicTacToeGame theGame) {
        if (!theGame.available(row, col)) {
            return false;
        }

        char[][] gameBoard = theGame.getTicTacToeBoard();

        if (checkRow(gameBoard, row, 'O')) {
            return true;
        } else if (checkCol(gameBoard, col, 'O')) {
            return true;
        } else if ((row == 0 && col == 0) ||
                (row == 2 && col == 2) ||
                (row == 1 && col == 1)) {
            if (checDiag1(gameBoard, 'O')) {
                return true;
            }
        } else if ((row == 0 && col == 2) ||
                (row == 1 && col == 1) ||
                (row == 2 && col == 0)) {
            if (checkDiag2(gameBoard, 'O')) {
                return true;
            }
        }

        return false;
    }

    private boolean canBlock(int row, int col, TicTacToeGame theGame) {
        if (!theGame.available(row, col)) {
            return false;
        }

        char[][] gameBoard = theGame.getTicTacToeBoard();

        if (checkRow(gameBoard, row, 'X')) {
            return true;
        } else if (checkCol(gameBoard, col, 'X')) {
            return true;
        } else if ((row == 0 && col == 0) ||
                (row == 2 && col == 2) ||
                (row == 1 && col == 1)) {
            if (checDiag1(gameBoard, 'X')) {
                return true;
            }
        } else if ((row == 0 && col == 2) ||
                (row == 1 && col == 1) ||
                (row == 2 && col == 0)) {
            if (checkDiag2(gameBoard, 'X')) {
                return true;
            }
        }

        return false;
    }

    private OurPoint randomMove(TicTacToeGame theGame) {
        boolean flag = true;
        while (flag) {
            int row = gen.nextInt(3);
            int col = gen.nextInt(3);
            if (theGame.available(row, col)) {
                flag = false;
                return new OurPoint(row, col);
            }
        }
        return null; // Avoid a compile-time error
    }

    private boolean checkRow(char[][] gameBoard, int row, char c) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            if (gameBoard[row][i] == c) {
                count++;
            }
        }
        if (count == 2) {
            return true;
        }
        return false;
    }

    private boolean checkCol(char[][] gameBoard, int col, char c) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            if (gameBoard[i][col] == c) {
                count++;
            }
        }
        if (count == 2) {
            return true;
        }
        return false;
    }

    private boolean checDiag1(char[][] gameBoard, char c) {
        int count = 0;
        for (int i = 0; i < 3; i++) {
            if (gameBoard[i][i] == c) {
                count++;
            }
        }
        if (count == 2) {
            return true;
        }
        return false;
    }

    private boolean checkDiag2(char[][] gameBoard, char c) {
        int count = 0;
        for (int i = 2; i >= 0; i--) {
            if (gameBoard[i][2 - i] == c) {
                count++;
            }
        }
        if (count == 2) {
            return true;
        }
        return false;
    }

}

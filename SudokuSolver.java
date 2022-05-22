
import java.util.Scanner;

public class SudokuSolver {
    /* 9x9 grid */
    private static int GRID_SIZE;

    public static void main(String[] args) {

        System.out.print("Enter board size: ");
        Scanner sc = new Scanner(System.in);
        GRID_SIZE = sc.nextInt();

        System.out.println("Enter sudoku elements (blanks = 0): ");

        int[][] board = new int[GRID_SIZE][GRID_SIZE];

        for (int i = 0; i < GRID_SIZE; i++) {
            for (int j = 0; j < GRID_SIZE; j++) {
                board[i][j] = sc.nextInt();
            }
        }

        System.out.println("Your Board: ");
        printBoard(board);


        sc.close();
        /*
         * int[][] board = {
         * { 8, 0, 0, 0, 0, 0, 0, 0, 0 },
         * { 0, 0, 3, 6, 0, 0, 0, 0, 0 },
         * { 0, 7, 0, 0, 9, 0, 2, 0, 0 },
         * { 0, 5, 0, 0, 0, 7, 0, 0, 0 },
         * { 0, 0, 0, 0, 4, 5, 7, 0, 0 },
         * { 0, 0, 0, 1, 0, 0, 0, 3, 0 },
         * { 0, 0, 1, 0, 0, 0, 0, 6, 8 },
         * { 0, 0, 8, 5, 0, 0, 0, 1, 0 },
         * { 0, 9, 0, 0, 0, 0, 4, 0, 0 }
         * };
         */

        // time
        long start = System.currentTimeMillis();
        long end;
        long elapsed;
        if (solve(board)) {
            end = System.currentTimeMillis();
            elapsed = end - start;
            System.out.println("\n\n Solved in " + elapsed + " milliseconds!");
            printBoard(board);
        } else {
            System.out.println("Unsolvable Board");
        }
    }

    // helper to print a pretty board
    private static void printBoard(int[][] board) {
        for (int row = 0; row < GRID_SIZE; row++) {

            if (row % 3 == 0) {
                for (int k = 0; k < GRID_SIZE; k++) {
                    System.out.print("  _ ");
                }
            }
            System.out.println();

            for (int col = 0; col < GRID_SIZE; col++) {
                if (col % 3 == 0) {
                    System.out.print(" | ");
                }
                System.out.print(" " + board[row][col] + " ");
            }
            System.out.println(" |");

        }
        for (int k = 0; k < GRID_SIZE; k++) {
            System.out.print("  _ ");
        }
        System.out.println();
    }

    // helper method to detect if 'number' is in 'row', given our 'board'
    private static boolean isNumberInRow(int[][] board, int number, int row) {
        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[row][i] == number) {
                return true;
            }
        }
        return false;
    }

    // helper method to detect if 'number' is in 'col', given our 'board'
    private static boolean isNumberInColumn(int[][] board, int number, int col) {
        for (int i = 0; i < GRID_SIZE; i++) {
            if (board[i][col] == number) {
                return true;
            }
        }
        return false;
    }

    // helper method to detect if 'number' is in the 3x3 square
    private static boolean isNumberInBox(int[][] board, int number, int row, int col) {
        // gives us the coordinate of the top left position of section
        int localBoxRow = row - row % 3;
        int localBoxCol = col - col % 3;
        for (int i = localBoxRow; i < localBoxRow + 3; i++) {
            for (int j = localBoxCol; j < localBoxCol + 3; j++) {
                if (board[i][j] == number) {
                    return true;
                }
            }
        }
        return false;
    }

    // helper method to detect is all three condition are valid
    private static boolean isNumberValid(int[][] board, int number, int row, int col) {
        return !isNumberInRow(board, number, row) && !isNumberInColumn(board, number, col)
                && !isNumberInBox(board, number, row, col);
    }

    private static boolean solve(int[][] board) {
        // find first empty space in the board
        for (int row = 0; row < GRID_SIZE; row++) {
            for (int col = 0; col < GRID_SIZE; col++) {
                // empty spaces in the board are represented by zeros
                if (board[row][col] == 0) {
                    // try all possible number and check their validity
                    for (int num = 1; num <= GRID_SIZE; num++) {
                        if (isNumberValid(board, num, row, col)) {
                            board[row][col] = num;
                            // recursive call to check the board for empty spaces again
                            if (solve(board)) {
                                return true;
                            } else {
                                // set that place back to zero if there is no way
                                // of completing the board with the previous number there
                                board[row][col] = 0;
                            }

                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

} // end of class
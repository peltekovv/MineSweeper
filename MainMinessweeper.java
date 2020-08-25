import java.util.Random;
import java.util.Scanner;

public class MainMinessweeper {
    public static int rows;
    public static int cols;
    public static int countGameOver = 0;

    public static void main(String[] args) {



        int[][] board;
        boolean[][] flagMatrix;

        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter the difficulty level:");
        System.out.println("Press 0 for BEGINNER (9 * 9 Cells and 10 mines)");
        System.out.println("Press 1 for INTERMEDIATE (16 * 16 Cells and 40 mines)");
        System.out.println("Press 2 for ADVANCED (24 * 24 Cells and 99 mines)");

        int level = Integer.parseInt(scanner.nextLine());

        switch (level) {
            case 0: {

                rows = 9;

                cols = 9;
                board = new int[rows][cols];
                flagMatrix = new boolean[rows][cols];
                break;
            }
            case 1: {

                rows = 16;

                cols = 16;
                board = new int[rows][cols];
                flagMatrix = new boolean[rows][cols];
                break;
            }
            case 2: {

                rows = 24;

                cols = 24;
                board = new int[rows][cols];
                flagMatrix = new boolean[rows][cols];
                break;
            }
            default:
                throw new IllegalStateException("Unexpected value: " + level);
        }


        plotBoard(cols, board);
        calculateCounts(rows, cols, board);
        System.out.println("abate count");
        print(rows, cols, board);
        int r, c;
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        displayFlagMatrix(rows, cols, flagMatrix);
        while (true) {
            System.out.println("=========================================================");
            Scanner input = new Scanner(System.in);
            System.out.println("enter row");
            r = input.nextInt();
            System.out.println("enter col");
            c = input.nextInt();
            if (play(r, c, flagMatrix, board)) {
                System.out.println("You Lose");
                break;
            }
            System.out.println("ok");
            if (checkGameOver(rows)) {
                displayFlagMatrix(rows,cols,flagMatrix);
                System.out.println("You Win Game");
                break;
            }
            displayFlagMatrix(rows,cols,flagMatrix);
            System.out.println("+++++++++++++++++++++++++++++++++++++++++++++++++++++");
            print(rows,cols,board);

        }

    }

    public static void plotBoard(int col, int[][] board) {
        int noOfBomb = col;
        while (noOfBomb > 0) {
            int i = getRandomNumber(col);
            int j = getRandomNumber(col);
            if (board[i][j] != -1) {
                board[i][j] = -1;
                noOfBomb--;
            }
        }
    }

    public static int getRandomNumber(int col) {
        Random t = new Random();
        int num;
        {
            num = t.nextInt(col);
        }
        while (num > col) ;
        return num;
    }

    public static void calculateCounts(int row, int col, int[][] board) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (board[i][j] != -1)
                    board[i][j] = getAdjBomb(i, j, board, row, col);
            }
        }
    }

    static int getAdjBomb(int i, int j, int[][] board, int row, int col) {
        int num = 0;
        for (int k = i - 1; k <= (i + 1); k++) {
            for (int l = j - 1; l <= (j + 1); l++) {
                if (isValid(k, l, row, col) && (board[k][l] == -1)) {
                    num++;
                }
            }
        }
        return num;
    }

    static boolean isValid(int i, int j, int row, int col) {
        return ((i >= 0 && i < row) && (j >= 0 && j < col));
    }

    static boolean isValid(int i, int j) {
        return ((i >= 0 && i < rows) && (j >= 0 && j < cols));
    }

    static public void print(int row, int col, int[][] board) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                System.out.print(" " + board[i][j]);
            }
            System.out.println("");
        }
    }

    static public void displayFlagMatrix(int row, int col, boolean[][] flagMatrix) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (flagMatrix[i][j]) {
                    System.out.print(" " + "*");
                } else {
                    System.out.print(" " + "O");
                }
            }
            System.out.println(" ");
        }
    }

    static public boolean play(int r, int c, boolean[][] flagMatrix, int[][] board) {
        if (!(isValid(r, c))) {
            System.out.println("Please Enter row and col in correct range");
            return false;
        }
        if (flagMatrix[r][c]) {
            System.out.println("This is a open position please enter other col and row");
            return false;
        }
        if (board[r][c] == -1)
            return true;
        if (board[r][c] != 0) {
            countGameOver++;
            flagMatrix[r][c] = true;
        } else {
            flagMatrix[r][c] = true;
            countGameOver++;
            openSpace(r, c, board, flagMatrix);
        }
        return false;
    }

    static void openSpace(int r, int c, int[][] board, boolean[][] flagMatrix) {
        for (int k = r - 1; k <= (r + 1); k++) {
            for (int l = c - 1; l <= (c + 1); l++) {
                if (isValid(k, l) && (board[k][l] != -1) && (!flagMatrix[k][l])) {
                    flagMatrix[k][l] = true;
                    countGameOver++;
                    /*if (board[k][l] == 0) {
                        openSpace(k, l, board, flagMatrix);
                    }*/
                }
            }
        }
    }

    static public boolean checkGameOver(int row) {
        return (countGameOver == (row * row) - (row));
    }

}

 
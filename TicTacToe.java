import java.util.Scanner;

public class TicTacToe {
    private static char[][] board = new char[3][3];
    private static char currentPlayer = 'X';
    private static String playerXName;
    private static String playerOName;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("🎮 Welcome to Tic Tac Toe!");
        System.out.println("Instructions:");
        System.out.println("- Two players take turns.");
        System.out.println("- Enter your move as two numbers: row and column (e.g., 0 1 for row 0, column 1).");
        System.out.println("- Rows and columns are numbered from 0 to 2.\n");

        // Get player names
        System.out.print("Enter name for Player X: ");
        playerXName = scanner.nextLine().trim();
        System.out.print("Enter name for Player O: ");
        playerOName = scanner.nextLine().trim();

        boolean playAgain;
        do {
            initializeBoard();
            boolean gameEnded = false;

            while (!gameEnded) {
                printBoard();
                String currentName = (currentPlayer == 'X') ? playerXName : playerOName;
                System.out.println(currentName + "'s turn, enter your move (row and column):");

                int row = -1, col = -1;
                while (true) {
                    String input = scanner.nextLine();
                    String[] tokens = input.trim().split("\\s+");

                    if (tokens.length != 2) {
                        System.out.println("❌ Invalid input. Enter two numbers separated by space.");
                        continue;
                    }

                    try {
                        row = Integer.parseInt(tokens[0]);
                        col = Integer.parseInt(tokens[1]);
                    } catch (NumberFormatException e) {
                        System.out.println("❌ Invalid input. Please enter numeric values.");
                        continue;
                    }

                    if (isValidMove(row, col)) {
                        break;
                    } else {
                        System.out.println("❌ Invalid move. Cell is occupied or out of bounds. Try again.");
                    }
                }

                board[row][col] = currentPlayer;

                if (checkWin()) {
                    printBoard();
                    System.out.println("🎉 " + currentName + " (" + currentPlayer + ") wins!");
                    gameEnded = true;
                } else if (checkDraw() || isStrategicDraw()) {
                    printBoard();
                    System.out.println("🤝 It's a draw!");
                    gameEnded = true;
                } else {
                    currentPlayer = (currentPlayer == 'X') ? 'O' : 'X';
                }
            }

            System.out.println("🔁 Do you want to play again? (yes/no)");
            playAgain = scanner.nextLine().trim().equalsIgnoreCase("yes");
        } while (playAgain);

        System.out.println("👋 Thanks for playing!");
        scanner.close();
    }

    private static void initializeBoard() {
        currentPlayer = 'X';
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                board[i][j] = ' ';
            }
        }
    }

    private static void printBoard() {
        System.out.println("\nCurrent board:");
        System.out.println("  0   1   2");
        for (int i = 0; i < 3; i++) {
            System.out.print(i + " ");
            for (int j = 0; j < 3; j++) {
                System.out.print(board[i][j]);
                if (j < 2) System.out.print(" | ");
            }
            System.out.println();
            if (i < 2) System.out.println("  ---------");
        }
        System.out.println();
    }

    private static boolean isValidMove(int row, int col) {
        return row >= 0 && row < 3 && col >= 0 && col < 3 && board[row][col] == ' ';
    }

    private static boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == currentPlayer &&
                 board[i][1] == currentPlayer &&
                 board[i][2] == currentPlayer) ||
                (board[0][i] == currentPlayer &&
                 board[1][i] == currentPlayer &&
                 board[2][i] == currentPlayer)) {
                return true;
            }
        }

        return (board[0][0] == currentPlayer &&
                board[1][1] == currentPlayer &&
                board[2][2] == currentPlayer) ||
               (board[0][2] == currentPlayer &&
                board[1][1] == currentPlayer &&
                board[2][0] == currentPlayer);
    }

    private static boolean checkDraw() {
        if (checkWin()) return false;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') return false;
            }
        }
        return true;
    }

    private static boolean isStrategicDraw() {
        String[][] lines = {
            { "" + board[0][0], "" + board[0][1], "" + board[0][2] },
            { "" + board[1][0], "" + board[1][1], "" + board[1][2] },
            { "" + board[2][0], "" + board[2][1], "" + board[2][2] },
            { "" + board[0][0], "" + board[1][0], "" + board[2][0] },
            { "" + board[0][1], "" + board[1][1], "" + board[2][1] },
            { "" + board[0][2], "" + board[1][2], "" + board[2][2] },
            { "" + board[0][0], "" + board[1][1], "" + board[2][2] },
            { "" + board[0][2], "" + board[1][1], "" + board[2][0] }
        };

        for (String[] line : lines) {
            boolean hasX = false, hasO = false;
            for (String cell : line) {
                if (cell.equals("X")) hasX = true;
                if (cell.equals("O")) hasO = true;
            }
            if (!(hasX && hasO)) return false;
        }

        return true;
    }
}
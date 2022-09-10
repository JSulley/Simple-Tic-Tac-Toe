import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Starting game...");
        char[] entries = new char[] {'_', '_', '_', '_', '_', '_', '_', '_', '_'};

        // Ask player 1 which character they want
        System.out.println("Player 1 choose between X and O.");
        Scanner scanner = new Scanner(System.in);
        
        // X's turn depends on which icon player 1 chose
        boolean XTurn = checkIcon(scanner);

        displayBoard(entries);

        // Initialize game variables
        boolean XWon = false;
        boolean OWon = false;

        // Initialize winnerArr: Length 2 boolean array containing values indicating 
        // whether X and O won
        boolean[] winnerArr = new boolean[2];

        // Declare game result
        String result;

        // Create game loop
        while (true) {
            // Get coordinates from user and update entries
            scanner = new Scanner(System.in);
            entries = updateEntries(scanner, XTurn, entries);
            
            displayBoard(entries);

            // Determine if a player won or a draw occurred
            // First get number of X's and O's on the board and update countArr
            // Initialize countArr: Length 2 integer array containing number of X's and O's
            int[] countArr = countFilledEntries(entries);

            int XCount = countArr[0];
            int OCount = countArr[1];

            // Determine winner based on current entries and update winnerArr
            getWinner(entries, winnerArr);
        
            XWon = winnerArr[0];
            OWon = winnerArr[1];

            // Neither player won but all 9 cells are filled (Draw)
            if (!(XWon || OWon) && (XCount+OCount == 9)) {
                result = "Draw";
                break;
            } else if (XWon || OWon) {
                result = XWon ? "X wins" : "O wins";
                break;
            }

            // Switch turns
            XTurn = !XTurn;
        }
        // Display result
        System.out.println(result);
        System.out.println("Thanks for playing!");
    }
    
    public static void displayBoard(char[] entries) {
        String border = "---------";
        String sideBorder = "| ";
        String row;
        char entry;
        
        System.out.println(border);
        for (int i = 0; i < 3; i++) {
            row = "";
            row += sideBorder;
            for (int j = 0; j < 3; j++) {
                entry = entries[3*i + j];
                row += entry + " ";
            }
            row += sideBorder;
            System.out.println(row.trim());
        }
        System.out.println(border);
    }

    public static char[] updateEntries(Scanner scanner, boolean XTurn, char[] entries) {
        boolean validInput = false;
        int coord1;
        int coord2;

        while (!validInput) {
            if (!scanner.hasNextInt()) {
                System.out.println("You should enter numbers!");
                scanner = new Scanner(System.in);
            } else if (scanner.hasNextInt()) {
                coord1 = scanner.nextInt();
                coord2 = scanner.nextInt();

                if (coord1 < 0 || coord1 > 3 || coord2 < 0 || coord2 > 3) {
                    System.out.println("Coordinates should be from 1 to 3!");
                    scanner = new Scanner(System.in);
                } else if (entries[3*coord1 + coord2 - 4] != '_') {
                    System.out.println("This cell is occupied! Choose another one!");
                    scanner = new Scanner(System.in);
                } else {
                    entries[3*coord1 + coord2 - 4] = XTurn ? 'X' : 'O';
                    validInput = !validInput;
                }
            }
        }

        return entries;
    }
    
    public static int[] countFilledEntries(char[] entries) {
        int[] countArr = new int[2];
        for (char entry : entries) {
            switch (entry) {
                case 'X':
                    countArr[0]++;
                    break;
                case 'O':
                    countArr[1]++;
                    break;
            }
        }
        return countArr;
    }

    public static void getWinner(char[] entries, boolean[] winnerArr) {
        int[][] possibleWinIndexes = new int[][] {{0, 4, 8},
                                                  {1, 4, 7},
                                                  {2, 4, 6},
                                                  {3, 4, 5},
                                                  {0, 1, 2},
                                                  {0, 3, 6},
                                                  {2, 5, 8},
                                                  {6, 7, 8}};
	    int sum;
        char entry;
        
        for (int[] arr : possibleWinIndexes) {
            sum = 0;

            for (int idx : arr) {
                entry = entries[idx];
                switch (entry) {
                    case 'X':
                        sum++;
                        break;
                    case 'O':
                        sum--;
                        break;
                }
            }

            switch (sum) {
                case 3:
                    winnerArr[0] = true;
                    break;
                case -3:
                    winnerArr[1] = true;
                    break;
            }
        
            if (winnerArr[0] || winnerArr[1]) {
                break;
            }
        }
    }

    public static boolean checkIcon(Scanner scanner) {
        boolean validInput = false;
        char selectedIcon = scanner.next().charAt(0);
        boolean XTurn = true;  // Assume player 1 selected 'X'

        while (!validInput) {
            if (selectedIcon == 'O' || selectedIcon == 'X') {
                XTurn = selectedIcon == 'X';
                break;
            } else {
                System.out.println("Invalid icon: Please select either X or O!");
                scanner = new Scanner(System.in);
                selectedIcon = scanner.next().charAt(0);
            }
        }
        return XTurn;
    }
}
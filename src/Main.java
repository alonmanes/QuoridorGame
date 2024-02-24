import java.util.*;
public class Main
{
        public static void main(String[] args)
        {
            GameBoard gameBoard = new GameBoard();
            Scanner scanner = new Scanner(System.in);
            int playerTurn = 0;
            printBoard(gameBoard);
            // Game loop
            while (true) {
                Player currentPlayer = (playerTurn % 2 == 0) ? gameBoard.getPlayer1() : gameBoard.getPlayer2();
                System.out.println(currentPlayer.getName() + "'s turn. Type 'wall' to place a wall or use WASD keys to move:");
                String input = scanner.nextLine().trim().toLowerCase();


                if (input.equals("wall") && currentPlayer.getWalls() > 0) {
                    // Prompt for wall placement details
                    System.out.println("Enter wall placement (format: x y orientation), e.g., 3 4 h for horizontal wall at (3,4):");
                    String wallInput = scanner.nextLine().trim().toLowerCase();
                    String[] wallDetails = wallInput.split(" ");

                    if (wallDetails.length == 3) {
                        int x = Integer.parseInt(wallDetails[0]);
                        int y = Integer.parseInt(wallDetails[1]);
                        boolean isHorizontal = wallDetails[2].equals("h");

                        if (gameBoard.placeWall(new Point(x, y), isHorizontal)) {
                            currentPlayer.useWall(); // Assuming you have a method to decrement wall count for a player
                            playerTurn++; // Switch turns after successful wall placement
                        } else {
                            System.out.println("Invalid wall placement. Try again.");
                        }
                    } else {
                        System.out.println("Invalid wall command. Format: x y orientation (h or v)");
                    }
                } else if (input.matches("[wasd]")) { // Player chooses to move
                    Point currentPosition = currentPlayer.getPawn().getPosition();
                    Point newPosition = new Point(currentPosition.getX(), currentPosition.getY());

                    switch (input) {
                        case "w": newPosition.setY(currentPosition.getY() - 1); break;
                        case "a": newPosition.setX(currentPosition.getX() - 1); break;
                        case "s": newPosition.setY(currentPosition.getY() + 1); break;
                        case "d": newPosition.setX(currentPosition.getX() + 1); break;
                    }

                    if (gameBoard.isMoveLegal(currentPlayer.getPawn(), newPosition)) {
                        currentPlayer.getPawn().move(newPosition);
                        playerTurn++; // Switch turns after moving
                    } else {
                        System.out.println("Illegal move. Try again.");
                    }
                } else {
                    System.out.println("Invalid input. Type 'wall' to place a wall or use 'WASD' to move.");
                }

                // Reprint the board after each action
                printBoard(gameBoard);
            }
        }

    public static void printBoard(GameBoard gameBoard) {
        final int size = 9; // Board size
        for (int y = 0; y < size; y++) {
            for (int x = 0; x < size; x++) {
                // Player positions
                if (gameBoard.getPlayer1().getPawn().getPosition().equals(new Point(x, y))) {
                    System.out.print(" * ");
                } else if (gameBoard.getPlayer2().getPawn().getPosition().equals(new Point(x, y))) {
                    System.out.print(" # ");
                } else {
                    System.out.print(" . ");
                }

                // Vertical walls or space
                if (x < size - 1) {
                    System.out.print(gameBoard.hasVerticalWallAt(new Point(x, y)) ? "|" : " ");
                }
            }
            System.out.println();

            // Horizontal walls for the current row
            if (y < size - 1) {
                for (int x = 0; x < size; x++) {
                    System.out.print(gameBoard.hasHorizontalWallAt(new Point(x, y)) ? "----" : "    ");
                    if (x < size - 1) {
                        System.out.print(" ");
                    }
                }
                System.out.println();
            }
        }
    }

}
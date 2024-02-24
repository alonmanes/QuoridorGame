public class Player {
    private String name;
    private Pawn pawn; // Assuming Pawn class is already defined
    private int walls; // Number of walls the player has

    public Player(String name, Point startPosition, String color) {
        this.name = name;
        this.pawn = new Pawn(startPosition, color);
        this.walls = 10;
    }

    public String getName() {
        return name;
    }

    public Pawn getPawn() {
        return pawn;
    }

    // Method to move the player's pawn, if the move is legal
    public boolean movePawn(GameBoard gameBoard, Point newPosition) {
        // Boundary check
        if (newPosition.getX() >= 0 && newPosition.getX() < 9 &&
                newPosition.getY() >= 0 && newPosition.getY() < 9) {
            // Check if the move is legal (considering walls and other pawns)
            if (gameBoard.isMoveLegal(this.pawn, newPosition)) {
                this.pawn.move(newPosition);
                return true;
            }
        }
        return false;
    }

    public boolean placeWall(GameBoard gameBoard, Point wallPosition, boolean isHorizontal) {
        if (walls > 0 && gameBoard.placeWall(wallPosition, isHorizontal)) {
            walls--; // Decrease wall count after successful placement
            return true;
        }
        return false;
    }

    public int getWalls() {
        return walls;
    }

    // Setter method for walls, in case you need to initialize or update the wall count
    public void setWalls(int walls) {
        this.walls = walls;
    }

    public void useWall() {
        if (this.walls > 0) {
            this.walls--; // Decrement the number of walls
        } else {
            System.out.println(name + " has no more walls to place.");
        }
    }
}

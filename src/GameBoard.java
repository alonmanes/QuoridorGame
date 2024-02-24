import java.util.*;
public class GameBoard {
    private Graph graph;
    private final int size = 9; // Board size for Quoridor is 9x9
    private Player player1;
    private Player player2;
    private List<Wall> walls; // List to keep track of all placed walls

    public GameBoard() {
        this.graph = new Graph();
        this.walls = new ArrayList<>(); // Initializing the walls list
        initializeBoard();
        player1 = new Player("Player 1", new Point(4, 0), "White");
        player2 = new Player("Player 2", new Point(4, 8), "Black");
    }
    private void initializeBoard() {
        // Initialize vertices for all board positions
        for (int x = 0; x < size; x++) {
            for (int y = 0; y < size; y++) {
                graph.addVertex(new Point(x, y));
            }
        }

        // Initialize edges
        for (int x = 0; x < size; x++)
        {
            for (int y = 0; y < size; y++)
            {
                if (x > 0)
                    graph.addEdge(new Point(x, y), new Point(x - 1, y)); // Connect left
                if (y > 0)
                    graph.addEdge(new Point(x, y), new Point(x, y - 1)); // Connect above
                if (x < size - 1)
                    graph.addEdge(new Point(x, y), new Point(x + 1, y)); // Connect right
                if (y < size - 1)
                    graph.addEdge(new Point(x, y), new Point(x, y + 1)); // Connect below

            }
        }

    }
    public boolean isMoveLegal(Pawn pawn, Point newPosition) {
        Point currentPosition = pawn.getPosition();
        // Boundary check
        if (newPosition.getX() < 0 || newPosition.getX() >= size || newPosition.getY() < 0 || newPosition.getY() >= size) {
            return false;
        }
        // Check if the move is one step away and not diagonal
        int dx = Math.abs(currentPosition.getX() - newPosition.getX());
        int dy = Math.abs(currentPosition.getY() - newPosition.getY());
        if (dx + dy != 1) {
            return false;
        }
        // Check if there's a wall blocking the move
        if (!graph.hasEdge(currentPosition, newPosition)) {
            return false;
        }
        return true;
    }
    public boolean placeWall(Point start, boolean isHorizontal) {
        Point end = isHorizontal ? new Point(start.getX() + 1, start.getY()) : new Point(start.getX(), start.getY() + 1);

        // Check if wall is within bounds and doesn't overlap existing walls
        if (!isValidWallPosition(start, end, isHorizontal)) {
            return false;
        }

        // Place the wall by removing edges from the graph
        if (isHorizontal) {
            graph.removeEdge(new Point(start.getX(), start.getY() - 1), start);
            graph.removeEdge(new Point(end.getX(), end.getY() - 1), end);
        } else {
            graph.removeEdge(new Point(start.getX() - 1, start.getY()), start);
            graph.removeEdge(new Point(end.getX() - 1, end.getY()), end);
        }

        // Add this wall to the list of placed walls
        walls.add(new Wall(start, isHorizontal));
        return true;
    }

    private boolean isValidWallPosition(Point wallPosition, boolean isHorizontal) {
        // Determine the second point that the wall will cover
        Point secondPoint = isHorizontal ?
                new Point(wallPosition.getX() + 1, wallPosition.getY()) :
                new Point(wallPosition.getX(), wallPosition.getY() + 1);

        // Check if either point of the wall is outside the board boundaries
        if (wallPosition.getX() < 0 || wallPosition.getX() >= size - 1 || wallPosition.getY() < 0 || wallPosition.getY() >= size - 1 ||
                secondPoint.getX() >= size || secondPoint.getY() >= size) {
            return false;
        }

        // Check for overlap with existing walls
        for (Wall wall : walls) {
            Point existingWallSecondPoint = wall.isHorizontal() ?
                    new Point(wall.getPosition().getX() + 1, wall.getPosition().getY()) :
                    new Point(wall.getPosition().getX(), wall.getPosition().getY() + 1);

            if (wall.getPosition().equals(wallPosition) || wall.getPosition().equals(secondPoint) ||
                    existingWallSecondPoint.equals(wallPosition) || existingWallSecondPoint.equals(secondPoint)) {
                return false; // The wall overlaps with an existing wall
            }
        }

        return true; // The wall is within bounds and does not overlap with existing walls
    }



    // Method to check for a horizontal wall at a given position
    public boolean hasHorizontalWallAt(Point position) {
        for (Wall wall : walls) {
            if (wall.isHorizontal() && wall.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    // Method to check for a vertical wall at a given position
    public boolean hasVerticalWallAt(Point position) {
        for (Wall wall : walls) {
            if (!wall.isHorizontal() && wall.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public int getSize() {
        return size;
    }
}
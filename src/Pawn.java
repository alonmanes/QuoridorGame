public class Pawn extends Entity {
    private String color; // Attribute specific to Pawn

    public Pawn(Point position, String color) {
        super(position); // Call to Entity's constructor
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }



    // Method to move the pawn to a new position
    public void move(Point newPosition) {
        setPosition(newPosition);
    }
}
public class Wall extends Entity {
    private boolean isHorizontal; // Attribute specific to Wall

    public Wall(Point position, boolean isHorizontal) {
        super(position); // Call to Entity's constructor
        this.isHorizontal = isHorizontal;
    }

    public boolean isHorizontal() {
        return isHorizontal;
    }

    public void setHorizontal(boolean horizontal) {
        isHorizontal = horizontal;
    }

    // Other methods specific to Wall
}
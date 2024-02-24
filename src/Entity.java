public abstract class Entity {
    protected Point position; // Common attribute for all entities, assuming Point class is defined elsewhere

    public Entity(Point position) {
        this.position = position;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

}
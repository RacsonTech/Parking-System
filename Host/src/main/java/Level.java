public class Level {
    private final int id;
    private final int totalSpaces;
    private final int availableSpaces;

    public Level(int id, int totalSpaces, int availableSpaces) {
        this.id = id;
        this.totalSpaces = totalSpaces;
        this.availableSpaces = availableSpaces;
    }

    public int getId() {
        return id;
    }

    public int getTotalSpaces() {
        return totalSpaces;
    }

    public int getAvailableSpaces() {
        return availableSpaces;
    }
}


public class Section {
    private final int id;
    private final int levelId;
    private final int numOfSpaces;
    private final int availableSpaces;

    public Section(int id, int levelId, int numOfSpaces, int availableSpaces) {
        this.id = id;
        this.levelId = levelId;
        this.numOfSpaces = numOfSpaces;
        this.availableSpaces = availableSpaces;
    }

    public int getId() {
        return id;
    }

    public int getLevelId() {
        return levelId;
    }

    public int getNumOfSpaces() {
        return numOfSpaces;
    }

    public int getAvailableSpaces() {
        return availableSpaces;
    }
}

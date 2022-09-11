
public class Sections {
    private final int id;
    private final int levelId;
    private final int numOfSpaces;

    public Sections(int id, int levelId, int numOfSpaces) {
        this.id = id;
        this.levelId = levelId;
        this.numOfSpaces = numOfSpaces;
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
}

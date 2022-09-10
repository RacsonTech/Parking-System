public class Level {
  private final int levelId;
  private final int numOfSpaces;

  public Level(int levelId, int numOfSpaces) {
    this.levelId = levelId;
    this.numOfSpaces = numOfSpaces;
  }

  public int getLevelId() {
    return levelId;
  }

  public int getNumOfSpaces() {
    return numOfSpaces;
  }
}

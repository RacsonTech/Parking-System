import java.util.List;

public class Sections {
  private final int sectionId;
  private final int numOfSpaces;
  private List<Camera> cameraList;
  private List<Display> displayList;


  public Sections(int sectionId, int numOfSpaces) {
    this.sectionId = sectionId;
    this.numOfSpaces = numOfSpaces;
  }

  public int getSectionId() {
    return sectionId;
  }

  public int getNumOfSpaces() {
    return numOfSpaces;
  }


}

public class Display {
  private final int displayId;
  private final int sectionId;

  public Display(int displayId, int sectionId) {
    this.displayId = displayId;
    this.sectionId = sectionId;
  }

  public int getDisplayId() {
    return displayId;
  }

  public int getSectionId() {
    return sectionId;
  }
}

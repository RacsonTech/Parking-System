public class Display {
    private final int displayId;
    private final int sectionId;
    private final int number;

    public Display(int displayId, int sectionId, int number) {
        this.displayId = displayId;
        this.sectionId = sectionId;
        this.number = number;
    }

    public int getDisplayId() {
        return displayId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public int getNumber() {
        return number;
    }

}

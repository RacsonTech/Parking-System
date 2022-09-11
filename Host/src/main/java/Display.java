public class Display {
    private final int displayId;
    private final int sectionId;
    private final int number;
    private final String ipAddress;

    public Display(int displayId, int sectionId, int number, String ipAddress) {
        this.displayId = displayId;
        this.sectionId = sectionId;
        this.number = number;
        this.ipAddress = ipAddress;
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

    public String getIpAddress() {
        return ipAddress;
    }
}

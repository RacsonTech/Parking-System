import java.util.ArrayList;

public class ParkingGarage {
    private String garageName;
    private int totalSpaces;
    private int numLevels;
    private int availableSpaces;
    private ArrayList<Camera> cameraArrayList;
    private ArrayList<Display> displayArrayList;

    // Constructor
    public ParkingGarage() {
    }

    public String getGarageName() {
        return garageName;
    }

    public int getTotalSpaces() {
        return totalSpaces;
    }

    public int getAvailableSpaces() {
        return availableSpaces;
    }

    public int getNumLevels() {
        return numLevels;
    }

    public Camera getCamera(int cameraId) {

        // For each camera in the camera list...
        for (Camera camera : cameraArrayList) {

            if (cameraId == camera.getCameraId()) {
                return camera;
            }
        }
        return null;
    }

    public void setGarageName(String garageName) {
        this.garageName = garageName;
    }

    public void setTotalSpaces(int totalSpaces) {
        this.totalSpaces = totalSpaces;
    }

    public void setNumLevels(int numLevels) {
        this.numLevels = numLevels;
    }

    public void setAvailableSpaces(int availableSpaces) {
        this.availableSpaces = availableSpaces;
    }

    public void setCameraArrayList(ArrayList<Camera> cameraArrayList) {
        this.cameraArrayList = cameraArrayList;
    }

    public void setDisplayArrayList(ArrayList<Display> displayArrayList) {
        this.displayArrayList = displayArrayList;
    }

    public void addCamera(Camera camera) {
        cameraArrayList.add(camera);
    }

    public boolean removeCamera(int cameraId) {
        Camera camera = getCamera(cameraId);
        cameraArrayList.remove(camera);
        return true;
    }

    public void printGarageInfo() {
        System.out.println("Parking Garage: \t\t\t" + garageName);
        System.out.println("No. of Total Spaces: \t\t" + totalSpaces);
        System.out.println("No. of Available Spaces: \t" + availableSpaces);
        System.out.println("No. of Levels: \t\t\t\t" + numLevels);
    }

    public void printCameraList() {

        System.out.format("%2s %12s %12s\n", "Camera ID", "Section ID", "IP Address");

        // For each camera in the camera list...
        for (Camera camera : cameraArrayList) {
            System.out.format("%4s %13s %19s\n", camera.getCameraId(), camera.getSectionId(), camera.getIpAddress());
        }
    }

    public void printDisplayList() {

        System.out.format("%2s %12s %15s %12s\n", "DisplayID", "SectionID", "DisplayNumber", "IP Address");

        // For each camera in the camera list...
        for (Display display : displayArrayList) {
            System.out.format("%4s %13s %13s %19s \n", display.getId(), display.getSectionId(), display.getNumber(), display.getIpAddress());
        }
    }
}
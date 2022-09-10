public class CameraRecord {
  private final int cameraId;
  private final int changedSpaces;

  // Constructor
  public CameraRecord(int id, int spaces) {
    cameraId = id;
    changedSpaces = spaces;
  }

  public int getCameraId() {
    return cameraId;
  }

  public int getChangedSpaces() {
    return changedSpaces;
  }


}

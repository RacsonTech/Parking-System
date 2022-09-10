public class Camera {
  private int cameraId;
  private int sectionId;

  private String ipAddress;

  // Constructor
  public Camera(int cameraId, int sectionId, String ipAddress) {
    this.cameraId = cameraId;
    this.sectionId = sectionId;
    this.ipAddress = ipAddress;
  }

  public int getCameraId() {
    return cameraId;
  }

  public int getSectionId() {
    return sectionId;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public void setCameraId(int cameraId) {
    this.cameraId = cameraId;
  }

  public void setSectionId(int sectionId) {
    this.sectionId = sectionId;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

}

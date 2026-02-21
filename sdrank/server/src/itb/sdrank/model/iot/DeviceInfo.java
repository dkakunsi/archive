package itb.sdrank.model.iot;

public class DeviceInfo {
  private String id;
  private String name;
  private String operationMode;
  private String timezone;
  private Boolean mobile;
  private Boolean indoor;
  private Owner owner;
  private Location location;

  public DeviceInfo() {
    super();
  }

  public DeviceInfo(String id, String name, Location location) {
    super();
    this.id = id;
    this.name = name;
    this.location = location;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getOperationMode() {
    return operationMode;
  }

  public void setOperationMode(String operationMode) {
    this.operationMode = operationMode;
  }

  public String getTimezone() {
    return timezone;
  }

  public void setTimezone(String timezone) {
    this.timezone = timezone;
  }

  public Boolean isMobile() {
    return mobile;
  }

  public void setMobile(Boolean mobile) {
    this.mobile = mobile;
  }

  public Boolean isIndoor() {
    return indoor;
  }

  public void setIndoor(Boolean indoor) {
    this.indoor= indoor;
  }

  public Owner getOwner() {
    return owner;
  }

  public void setOwner(Owner owner) {
    this.owner = owner;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }
}

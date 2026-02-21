package itb.sdrank.model;

import java.util.List;

import itb.sdrank.exception.AttributeNotFoundException;
import itb.sdrank.model.iot.DeviceInfo;
import itb.sdrank.model.iot.Location;
import itb.sdrank.model.iot.Owner;
import itb.sdrank.model.iot.Quality;
import itb.sdrank.model.iot.ResourceInfo;
import itb.sdrank.model.iot.ServiceInfo;

public class Candidate extends Model {
  private DeviceInfo deviceInfo;
  private ResourceInfo resourceInfo;
  private ServiceInfo serviceInfo;

  public Candidate() {
    super();
    deviceInfo = new DeviceInfo();
    resourceInfo = new ResourceInfo();
    serviceInfo = new ServiceInfo();
  }

  public Candidate(DeviceInfo deviceInfo, ResourceInfo resourceInfo, ServiceInfo serviceInfo) {
    super();
    this.deviceInfo = deviceInfo;
    this.resourceInfo = resourceInfo;
    this.serviceInfo = serviceInfo;
    this.id = deviceInfo.getId();
  }
  
  public DeviceInfo getDeviceInfo() {
    return deviceInfo;
  }

  public ResourceInfo getResourceInfo() {
    return resourceInfo;
  }

  public ServiceInfo getServiceInfo() {
    return serviceInfo;
  }

  public boolean isActive() {
    return deviceInfo.getOperationMode().equals("active");
  }

  public Float getRange() {
    return resourceInfo.getRange();
  }

  public Location getLocation() {
    return deviceInfo.getLocation();
  }

  public String getAttribute() {
    return resourceInfo.getAttribute();
  }

  public List<Quality> getQualities() {
    return resourceInfo.getQualities();
  }

  public String getEndpoint() {
    return serviceInfo.getEndpoint();
  }

  public boolean inRange(Location other) {
    return getLocation().inRange(other, getRange());
  }

  public Owner getOwner() {
    return deviceInfo.getOwner();
  }

  public Boolean isCovered(Location location) {
    return inRange(location);
  }

  public Float getQuality(String key) throws AttributeNotFoundException {
    for (Quality quality : getQualities()) {
      if (key.equals(quality.getType()))
        return quality.getValue();
    }

    throw new AttributeNotFoundException(
        String.format("Atribut kualitas %s tidak ditemukan.", key));
  }

  public Float countCoverageScore(Location location) {
    Double distanceInMeters = getLocation().getDistance(location) * 1000;
    return new Float((1 - (distanceInMeters / getRange())));
  }

  @Override
  public String toString() {
    return "Candidate [deviceInfo=" + deviceInfo + ", resourceInfo=" + resourceInfo
        + ", serviceInfo=" + serviceInfo + ", id=" + id + "]";
  }
}

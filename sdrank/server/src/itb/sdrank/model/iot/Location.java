package itb.sdrank.model.iot;

public class Location {
  private Double latitude;
  private Double longitude;
  private String building;
  private String floor;
  private String room;

  public Location() {
    super();
  }

  public Location(Double latitude, Double longitude) {
    super();
    this.latitude = latitude;
    this.longitude = longitude;
  }

  public Double getLatitude() {
    return latitude;
  }

  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }

  public Double getLongitude() {
    return longitude;
  }

  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }

  public String getBuilding() {
    return building;
  }

  public void setBuilding(String building) {
    this.building = building;
  }

  public String getFloor() {
    return floor;
  }

  public void setFloor(String floor) {
    this.floor = floor;
  }

  public String getRoom() {
    return room;
  }

  public void setRoom(String room) {
    this.room = room;
  }

  public Double degreeToRadian(final Double degree) {
    return (degree * Math.PI / 180);
  }

  public Double getDistance(final Location other) {
    final int R = 6371; // Radius of the earth in KM (3,959 miles)
    Double latDistance = degreeToRadian(latitude - other.getLatitude());
    Double lonDistance = degreeToRadian(longitude - other.getLongitude());
    Double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
        + Math.cos(degreeToRadian(other.getLatitude())) * Math.cos(degreeToRadian(latitude))
            * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
    Double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    Double distance = R * c;

    return distance;
  }

  public boolean inRange(Location other, Float range) {
    Double distanceInMeters = getDistance(other) * 1000;
    return (distanceInMeters < range);
  }
}

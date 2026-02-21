package itb.sdrank.model.iot;

public class Quality {
  private String type;
  private String unit;
  private Float value;

  public Quality() {
    super();
  }
  
  public Quality(String type, String unit, Float value) {
    super();
    this.type = type;
    this.unit = unit;
    this.value = value;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getUnit() {
    return unit;
  }

  public void setUnit(String unit) {
    this.unit = unit;
  }

  public Float getValue() {
    return value;
  }

  public void setValue(Float value) {
    this.value = value;
  }
}

package itb.sdrank.model.iot;

import java.util.ArrayList;
import java.util.List;

public class ResourceInfo {
  private String name;
  private String type;
  private Float range;
  private String rangeUnit;
  private Float sampling;
  private String samplingUnit;
  private String attribute;
  private String attributeUnit;
  private String token;
  private List<Quality> qualities;

  public ResourceInfo() {
    super();
    qualities = new ArrayList<>();
  }

  public ResourceInfo(Float range) {
    super();
    this.range = range;
    qualities = new ArrayList<>();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public Float getRange() {
    return range;
  }

  public void setRange(Float range) {
    this.range = range;
  }

  public Float getSampling() {
    return sampling;
  }

  public void setSampling(Float sampling) {
    this.sampling = sampling;
  }

  public String getRangeUnit() {
    return rangeUnit;
  }

  public void setRangeUnit(String rangeUnit) {
    this.rangeUnit = rangeUnit;
  }

  public String getSamplingUnit() {
    return samplingUnit;
  }

  public void setSamplingUnit(String samplingUnit) {
    this.samplingUnit = samplingUnit;
  }

  public String getAttribute() {
    return attribute;
  }

  public void setAttribute(String attribute) {
    this.attribute = attribute;
  }

  public String getAttributeUnit() {
    return attributeUnit;
  }

  public void setAttributeUnit(String attributeUnit) {
    this.attributeUnit = attributeUnit;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public List<Quality> getQualities() {
    return qualities;
  }

  public void setQualities(List<Quality> qualities) {
    this.qualities = qualities;
  }
}

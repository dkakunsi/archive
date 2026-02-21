package itb.sdrank.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Quality field to build rank.<br />
 * The field consist of PROCESSING TIME, PRECISION, COVERAGE, COST, and DISCOVERY.
 */
public enum QualityField {
  RESPONSE_TIME("responseTime", Direction.DECREASING), 
  COVERAGE("coverage", Direction.DECREASING),
  PRECISION("precision", Direction.INCREASING),
  COST("cost", Direction.DECREASING);

  private String value;
  private Direction direction;

  private QualityField(String value, Direction direction) {
    this.value = value;
    this.direction = direction;
  }

  public String getValue() {
    return value;
  }
  
  public Direction getDirection() {
    return direction;
  }

  public static List<String> getQualities() {
    return new ArrayList<>(
        Arrays.asList(RESPONSE_TIME.value, COVERAGE.value, PRECISION.value, COST.value));
  }

  public static boolean isExist(String attributeName) {
    for (String field : getQualities()) {
      if (field.equalsIgnoreCase(attributeName))
        return true;
    }
    return false;
  }

  public enum Direction {
    INCREASING, DECREASING
  }
}

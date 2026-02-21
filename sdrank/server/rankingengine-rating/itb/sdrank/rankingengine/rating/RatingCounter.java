package itb.sdrank.rankingengine.rating;

import itb.sdrank.model.QualityField.Direction;

public class RatingCounter {
  private Float max;
  private Float min;

  public RatingCounter(Float max, Float min) {
    super();
    this.max = max;
    this.min = min;
  }

  public Float getMax() {
    return max;
  }

  public Float getMin() {
    return min;
  }
  
  public Float countRate(Float value, Direction direction) throws OutOfRangeException {
    if (value < min || value > max)
      throw new OutOfRangeException("Value must in range between min and max");

    if (direction.equals(Direction.DECREASING))
      return countDecreasingRate(value);
    return countIncreasingRate(value);
  }
  
  private Float countIncreasingRate(Float value) {
    return (value - min) / (max - min);
  }
  
  private Float countDecreasingRate(Float value) {
    return (max - value) / (max - min);
  }
}
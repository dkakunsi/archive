package itb.sdrank.rankingengine.rating.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import itb.sdrank.model.QualityField.Direction;
import itb.sdrank.rankingengine.rating.OutOfRangeException;
import itb.sdrank.rankingengine.rating.RatingCounter;

public class RatingCounterTest {
  private RatingCounter counter;
  private Float min;
  private Float max;

  @Before
  public void setUp() throws Exception {
    min = 10f;
    max = 100f;
    counter = new RatingCounter(max, min);
  }

  @Test
  public void testCountIncreasingRate_Maximum() throws OutOfRangeException {
    Float score = counter.countRate(max, Direction.INCREASING);
    assertEquals(score, new Float(1f));
  }

  @Test
  public void testCountIncreasingRate_Minimum() throws OutOfRangeException {
    Float score = counter.countRate(min, Direction.INCREASING);
    assertEquals(score, new Float(0f));
  }

  @Test
  public void testCountIncreasingRate_Between() throws OutOfRangeException {
    Float score = counter.countRate(50f, Direction.INCREASING);
    assertEquals(score, new Float(4f/9f));
  }

  @Test(expected = OutOfRangeException.class)
  public void testCountIncreasingRate_LessThanMinimum() throws OutOfRangeException {
    counter.countRate(5f, Direction.INCREASING);
  }

  @Test(expected = OutOfRangeException.class)
  public void testCountIncreasingRate_GreaterThanMaximum() throws OutOfRangeException {
    counter.countRate(105f, Direction.INCREASING);
  }

  @Test
  public void testCountDecreasingRate_Minimum() throws OutOfRangeException {
    Float score = counter.countRate(min, Direction.DECREASING);
    assertEquals(score, new Float(1f));
  }

  @Test
  public void testCountDecreasingRate_Maximum() throws OutOfRangeException {
    Float score = counter.countRate(max, Direction.DECREASING);
    assertEquals(score, new Float(0f));
  }

  @Test
  public void testCountDecreasingRate_Between() throws OutOfRangeException {
    Float score = counter.countRate(50f, Direction.DECREASING);
    assertEquals(score, new Float(5f/9f));
  }

  @Test(expected = OutOfRangeException.class)
  public void testCountDecreasingRate_LessThanMinimum() throws OutOfRangeException {
    counter.countRate(5f, Direction.DECREASING);
  }

  @Test(expected = OutOfRangeException.class)
  public void testCountDecreasingRate_GreaterThanMaximum() throws OutOfRangeException {
    counter.countRate(105f, Direction.DECREASING);
  }

}

package itb.sdrank.test;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import itb.sdrank.model.iot.Location;

public class LocationTest {
  private Location location;

  @Before
  public void setUp() throws Exception {
    location = new Location(new Double(10), new Double(10));
  }

  @Test
  public void testGetDistance() {
    Location other = new Location(new Double(10), new Double(10));
    Double distance = location.getDistance(other);
    assertEquals(new Double(0), distance);
  }
}

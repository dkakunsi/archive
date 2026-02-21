package itb.sdrank.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import itb.sdrank.description.WSDL;
import itb.sdrank.exception.AttributeNotFoundException;
import itb.sdrank.exception.DeviceDescriptionException;
import itb.sdrank.model.Candidate;
import itb.sdrank.model.iot.Quality;

public class WsdlTest {
  private WSDL wsdl;

  @Before
  public void setUp() throws Exception {
    String pathname = "C:\\Users\\dkakunsi\\Documents\\IF6099 Tesis\\xml\\wsdl\\210899281b.wsdl";
    wsdl = new WSDL(new File(pathname));
  }

  @Test
  public void testGetCandidates() throws AttributeNotFoundException {
    List<Candidate> candidates = wsdl.getCandidates("temperature");
    assertFalse(candidates.isEmpty());
    assertTrue(candidates.size() == 2);
    
    Candidate candidate = candidates.get(0);
    assertEquals(candidate.getEndpoint(), "http://201.27.111.70/temperature");
    assertEquals(candidate.getRange(), new Float(200));
    assertEquals(candidate.getAttribute(), "temperature");
    assertEquals(candidate.getQuality("responseTime"), new Float(100));
    assertEquals(candidate.getQuality("precision"), new Float(20));
    assertEquals(candidate.getQuality("cost"), new Float(10000));
    assertEquals(candidate.getOwner().getName(), "pt.telkom");
    assertEquals(candidate.getOwner().getHomepage(), "http://www.telkom.co.id");
    
    candidate = candidates.get(1);
    assertEquals(candidate.getEndpoint(), "coap://201.27.111.70/temperature");
    assertEquals(candidate.getRange(), new Float(200));
    assertEquals(candidate.getAttribute(), "temperature");
    assertEquals(candidate.getQuality("responseTime"), new Float(100));
    assertEquals(candidate.getQuality("precision"), new Float(20));
    assertEquals(candidate.getQuality("cost"), new Float(10000));
    assertEquals(candidate.getOwner().getName(), "pt.telkom");
    assertEquals(candidate.getOwner().getHomepage(), "http://www.telkom.co.id");
  }
  
  @Test
  public void testSetQualityUpdate() throws DeviceDescriptionException {
    Quality quality = new Quality("cost", "idr", 122099f);
    wsdl.setQuality("temperature", quality);
    wsdl.save();
  }
  
  @Test
  public void testSetQualityAdd() throws DeviceDescriptionException {
    Quality quality = new Quality("accuracy", "percent", 80f);
    wsdl.setQuality("temperature", quality);
    wsdl.save();
  }
  
  @Test
  public void testSetLocation() throws DeviceDescriptionException {
    wsdl.setLocation(new Double(10), new Double(10));
    wsdl.save();
  }
  
  @Test
  public void testSetOperationMode() throws DeviceDescriptionException {
    wsdl.setOperationMode(false);
    wsdl.save();
  }
  
  @After
  public void destroy() throws DeviceDescriptionException {
    wsdl.setLocation(new Double(-6.901066), new Double(107.619125));
    wsdl.setQuality("temperature", new Quality("cost", "idr", 10000f));
    wsdl.setOperationMode(true);
    wsdl.save();
  }
}

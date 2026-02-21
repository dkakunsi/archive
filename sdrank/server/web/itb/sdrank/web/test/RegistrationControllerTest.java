package itb.sdrank.web.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.lucene.document.Document;
import org.apache.lucene.queryparser.classic.ParseException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;
import org.xml.sax.SAXException;

import itb.sdrank.DescriptionStorage;
import itb.sdrank.DiscoveryEngine;
import itb.sdrank.RankingCache;
import itb.sdrank.SelectionManager;
import itb.sdrank.description.WSDL;
import itb.sdrank.discoveryengine.lucene.DocumentField;
import itb.sdrank.discoveryengine.lucene.test.LuceneDiscoveryEngineTest;
import itb.sdrank.exception.DeviceDescriptionException;
import itb.sdrank.exception.NotificationException;
import itb.sdrank.exception.RepositoryException;
import itb.sdrank.exception.SDRankException;
import itb.sdrank.model.DeviceDescription;
import itb.sdrank.model.QualityField;
import itb.sdrank.model.Ranking;
import itb.sdrank.model.RankingItem;
import itb.sdrank.model.Criteria;
import itb.sdrank.model.iot.Location;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { TestConfig.class })
public class RegistrationControllerTest {
  @Resource
  private WebApplicationContext wac;
  private MockMvc mockMvc;
  @Resource
  private DiscoveryEngine luceneDiscoveryEngine;
  @Resource
  private DescriptionStorage fsStorage;
  @Resource
  private RankingCache redisCache;
  @Resource
  private SelectionManager selectionManager;
  @Resource
  private SelectionManager selector;

  private List<DeviceDescription> deviceDescriptions;
  private String path;
  private File file1;
  private File file2;

  @Before
  public void setUp() throws Exception {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    path = "C:\\Users\\dkakunsi\\Documents\\IF6099 Tesis\\xml\\wsdl";
    file1 = new File(String.format("%s\\%s", path, "210899281.wsdl")); // 5 temperature
    file2 = new File(String.format("%s\\%s", path, "210899282.wsdl")); // 2 temperature
    deviceDescriptions = new ArrayList<>();
    deviceDescriptions.add(new WSDL(file1));
    deviceDescriptions.add(new WSDL(file2));
  }

  @Test
  public void testAddWsdl() throws Exception {
    selectionManager.add(deviceDescriptions);

    List<Float> qualityValues = new ArrayList<>();
    qualityValues.add(new Float(7));
    qualityValues.add(new Float(3));
    qualityValues.add(new Float(1));
    qualityValues.add(new Float(1 / 7f));
    qualityValues.add(new Float(1 / 5f));
    qualityValues.add(new Float(1));

    Criteria criteria = new Criteria("temperature", new Location(-6.900272, 107.618562),
        qualityValues);
    RankingItem oldItem = selector.getItems(criteria, 1).iterator().next();
    Ranking oldRanking = redisCache.get(oldItem.getRankingId());

    String file4Path = String.format("%s\\210899284.wsdl", path);
    byte[] wsdlContent = Files.readAllBytes(Paths.get(file4Path));
    MockMultipartFile wsdlFile = new MockMultipartFile("descriptionFile", "210899284.wsdl",
        "application/xml", wsdlContent);
    MockMultipartHttpServletRequestBuilder mockMultipartHttpServletRequestBuilder = MockMvcRequestBuilders
        .multipart("/description").file(wsdlFile);

    ResultActions resultActions = null;
    try {
      resultActions = mockMvc.perform(mockMultipartHttpServletRequestBuilder);
    } catch (NestedServletException e) {
      if (!(e.getCause() instanceof NotificationException))
        throw e;
    }

    resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    resultActions.andExpect(
        jsonPath(".message").value("Description file has been added to repository and indexed"));
    resultActions.andDo(MockMvcResultHandlers.print());

    DeviceDescription deviceDescription = new WSDL(new File(file4Path));
    assertDeviceDescription(deviceDescription.getId());

    Ranking newRanking = redisCache.get(oldItem.getRankingId());
    RankingItem newItem = newRanking.getItems(1).iterator().next();

    assertEquals("210899284", newItem.getCandidateId());
    assertTrue(oldRanking.getItems().size() < newRanking.getItems().size());
    assertFalse(oldItem.getCandidateId().equals(newItem.getCandidateId()));
  }

  @Test
  public void testAddWsdls() throws Exception {
    File file3 = new File(String.format("%s\\%s", path, "210899283.wsdl")); // humidity
    File file4 = new File(String.format("%s\\%s", path, "210899284.wsdl")); // temperature
    File file5 = new File(String.format("%s\\%s", path, "210899285.wsdl")); // humidity

    byte[] wsdlContent1 = Files.readAllBytes(file1.toPath());
    byte[] wsdlContent2 = Files.readAllBytes(file2.toPath());
    byte[] wsdlContent3 = Files.readAllBytes(file3.toPath());
    byte[] wsdlContent4 = Files.readAllBytes(file4.toPath());
    byte[] wsdlContent5 = Files.readAllBytes(file5.toPath());
    MockMultipartFile wsdlFile1 = new MockMultipartFile("descriptionFiles", "210899281.wsdl",
        "application/xml", wsdlContent1);
    MockMultipartFile wsdlFile2 = new MockMultipartFile("descriptionFiles", "210899282.wsdl",
        "application/xml", wsdlContent2);
    MockMultipartFile wsdlFile3 = new MockMultipartFile("descriptionFiles", "210899283.wsdl",
        "application/xml", wsdlContent3);
    MockMultipartFile wsdlFile4 = new MockMultipartFile("descriptionFiles", "210899284.wsdl",
        "application/xml", wsdlContent4);
    MockMultipartFile wsdlFile5 = new MockMultipartFile("descriptionFiles", "210899285.wsdl",
        "application/xml", wsdlContent5);

    MockMultipartHttpServletRequestBuilder mockMultipartHttpServletRequestBuilder = MockMvcRequestBuilders
        .multipart("/descriptions").file(wsdlFile1).file(wsdlFile2).file(wsdlFile3).file(wsdlFile4)
        .file(wsdlFile5);

    ResultActions resultActions = mockMvc.perform(mockMultipartHttpServletRequestBuilder);
    resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    resultActions.andExpect(
        jsonPath(".message").value("Description files has been added to repository and indexed"));
    resultActions.andDo(MockMvcResultHandlers.print());

    assertDeviceDescription(new WSDL(file1).getId());
    assertDeviceDescription(new WSDL(file2).getId());
    assertDeviceDescription(new WSDL(file3).getId());
    assertDeviceDescription(new WSDL(file4).getId());
    assertDeviceDescription(new WSDL(file5).getId());
  }

  private void assertDeviceDescription(String documentId) throws SAXException, IOException,
      RepositoryException, ParseException, DeviceDescriptionException {
    DeviceDescription deviceDescription = fsStorage.get(documentId);
    assertNotNull(deviceDescription);
    assertTrue(deviceDescription.getFile().isFile());
    Document document = LuceneDiscoveryEngineTest.getDocument(deviceDescription.getId());
    assertEquals(deviceDescription.getId(), document.get(DocumentField.ID));
  }

  @Test
  public void testUpdateQuality() throws Exception {
    File file3 = new File(String.format("%s\\%s", path, "210899284.wsdl"));

    DeviceDescription deviceDescription1 = new WSDL(file1);
    DeviceDescription deviceDescription2 = new WSDL(file2);
    DeviceDescription deviceDescription3 = new WSDL(file3);

    List<DeviceDescription> deviceDescriptions = new ArrayList<>();
    deviceDescriptions.add(deviceDescription1);
    deviceDescriptions.add(deviceDescription2);
    deviceDescriptions.add(deviceDescription3);
    selectionManager.add(deviceDescriptions);

    List<Float> qualityValues = new ArrayList<>();
    qualityValues.add(new Float(7));
    qualityValues.add(new Float(3));
    qualityValues.add(new Float(1));
    qualityValues.add(new Float(1 / 7f));
    qualityValues.add(new Float(1 / 5f));
    qualityValues.add(new Float(1));
    Criteria criteria = new Criteria("temperature", new Location(-6.900272, 107.618562),
        qualityValues);
    RankingItem oldItem = selector.getItems(criteria, 1).iterator().next();
    Ranking oldRanking = redisCache.get(oldItem.getRankingId());

    MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
        .put(
            String.format("/description/%s/resource/%s", deviceDescription3.getId(), "temperature"))
        .contentType(MediaType.APPLICATION_JSON)
        .content("{ \"type\": \"responseTime\", \"unit\": \"milsec\", \"value\": \"100000\" }");

    ResultActions resultActions = null;
    try {
      resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
    } catch (NestedServletException e) {
      if (!(e.getCause() instanceof NotificationException))
        throw e;
    }

    resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    resultActions.andDo(MockMvcResultHandlers.print());

    DeviceDescription deviceDescription = fsStorage.get(deviceDescription3.getId());
    assertTrue(deviceDescription3.getCandidates("temperature").get(0)
        .getQuality(QualityField.PRECISION.getValue()).equals(deviceDescription
            .getCandidates("temperature").get(0).getQuality(QualityField.PRECISION.getValue())));

    assertFalse(deviceDescription3.getCandidates("temperature").get(0)
        .getQuality(QualityField.RESPONSE_TIME.getValue())
        .equals(deviceDescription.getCandidates("temperature").get(0)
            .getQuality(QualityField.RESPONSE_TIME.getValue())));

    Ranking newRanking = redisCache.get(oldItem.getRankingId());
    RankingItem newItem = newRanking.getItems(1).iterator().next();

    assertTrue(oldRanking.getItems().size() == newRanking.getItems().size());
    assertFalse(oldItem.getScore().equals(newItem.getScore()));
    assertEquals("210899281", newItem.getCandidateId());
  }

  @Test
  public void testUpdateLocation() throws Exception {
    File file3 = new File(String.format("%s\\%s", path, "210899284.wsdl"));

    DeviceDescription deviceDescription1 = new WSDL(file1);
    DeviceDescription deviceDescription2 = new WSDL(file2);
    DeviceDescription deviceDescription3 = new WSDL(file3);

    List<DeviceDescription> deviceDescriptions = new ArrayList<>();
    deviceDescriptions.add(deviceDescription1);
    deviceDescriptions.add(deviceDescription2);
    deviceDescriptions.add(deviceDescription3);
    selectionManager.add(deviceDescriptions);

    List<Float> qualityValues = new ArrayList<>();
    qualityValues.add(new Float(7));
    qualityValues.add(new Float(3));
    qualityValues.add(new Float(1));
    qualityValues.add(new Float(1 / 7f));
    qualityValues.add(new Float(1 / 5f));
    qualityValues.add(new Float(1));
    Criteria criteria = new Criteria("temperature", new Location(-6.900272, 107.618562),
        qualityValues);
    RankingItem oldItem = selector.getItems(criteria, 1).iterator().next();
    Ranking oldRanking = redisCache.get(oldItem.getRankingId());

    MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.put(String
        .format("/description/%s/latitude/%f/longitude/%f", deviceDescription3.getId(), 10f, 10f));

    ResultActions resultActions = null;
    try {
      resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
    } catch (NestedServletException e) {
      if (!(e.getCause() instanceof NotificationException))
        throw e;
    }

    resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    resultActions.andDo(MockMvcResultHandlers.print());

    DeviceDescription deviceDescription = fsStorage.get(deviceDescription3.getId());
    assertFalse(deviceDescription3.getCandidates("temperature").get(0).getLocation()
        .equals(deviceDescription.getCandidates("temperature").get(0).getLocation()));

    Ranking newRanking = redisCache.get(oldItem.getRankingId());
    RankingItem newItem = newRanking.getItems(1).iterator().next();

    assertTrue(oldRanking.getItems().size() > newRanking.getItems().size());
    assertFalse(oldItem.getCandidateId().equals(newItem.getCandidateId()));
    assertEquals("210899281", newItem.getCandidateId());
  }

  @Test
  public void testUpdateOperationMode() throws Exception {
    File file3 = new File(String.format("%s\\%s", path, "210899284.wsdl"));

    List<Float> qualityValues = new ArrayList<>();
    qualityValues.add(new Float(7));
    qualityValues.add(new Float(3));
    qualityValues.add(new Float(1));
    qualityValues.add(new Float(1 / 7f));
    qualityValues.add(new Float(1 / 5f));
    qualityValues.add(new Float(1));

    DeviceDescription deviceDescription1 = new WSDL(file1);
    DeviceDescription deviceDescription2 = new WSDL(file2);
    DeviceDescription deviceDescription3 = new WSDL(file3);

    List<DeviceDescription> deviceDescriptions = new ArrayList<>();
    deviceDescriptions.add(deviceDescription1);
    deviceDescriptions.add(deviceDescription2);
    deviceDescriptions.add(deviceDescription3);
    selectionManager.add(deviceDescriptions);

    Criteria criteria = new Criteria("temperature", new Location(-6.900272, 107.618562),
        qualityValues);
    RankingItem oldItem = selector.getItems(criteria, 1).iterator().next();
    Ranking oldRanking = redisCache.get(oldItem.getRankingId());

    MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.put(
        String.format("/description/%s/operation/mode/%s", deviceDescription3.getId(), "false"));

    ResultActions resultActions = null;
    try {
      resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
    } catch (NestedServletException e) {
      if (!(e.getCause() instanceof NotificationException))
        throw e;
    }

    resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    resultActions.andDo(MockMvcResultHandlers.print());

    Ranking newRanking = redisCache.get(oldItem.getRankingId());
    RankingItem newItem = newRanking.getItems(1).iterator().next();

    assertFalse(oldItem.getCandidateId().equals(newItem.getCandidateId()));
    assertEquals("210899281", newItem.getCandidateId());
    assertTrue(oldRanking.getItems().size() > newRanking.getItems().size());
  }

  @After
  public void destroy() throws SDRankException {
    luceneDiscoveryEngine.clean();
    fsStorage.clean();
    redisCache.clean();
  }
}

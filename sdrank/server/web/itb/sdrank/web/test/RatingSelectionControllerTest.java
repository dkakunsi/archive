package itb.sdrank.web.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import itb.sdrank.DescriptionStorage;
import itb.sdrank.DiscoveryEngine;
import itb.sdrank.RankingCache;
import itb.sdrank.SelectionManager;
import itb.sdrank.description.WSDL;
import itb.sdrank.exception.SDRankException;
import itb.sdrank.model.DeviceDescription;
import itb.sdrank.model.Ranking;
import itb.sdrank.model.RankingItem;
import itb.sdrank.model.Criteria;
import itb.sdrank.model.iot.Location;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { TestConfig.class })
public class RatingSelectionControllerTest {
  @Resource
  private WebApplicationContext wac;
  @Resource
  private DiscoveryEngine luceneDiscoveryEngine;
  @Resource
  private DescriptionStorage fsStorage;
  @Resource
  private RankingCache redisCache;
  @Resource
  private SelectionManager selectionManager;

  private MockMvc mockMvc;
  private List<DeviceDescription> deviceDescriptions;
  private DeviceDescription deviceDescription1;
  private DeviceDescription deviceDescription2;
  private DeviceDescription deviceDescription3;
  private DeviceDescription deviceDescription4;
  private DeviceDescription deviceDescription5;

  @Before
  public void setUp() throws Exception {
    this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    String path = "C:\\Users\\dkakunsi\\Documents\\IF6099 Tesis\\xml\\wsdl";
    deviceDescription1 = new WSDL(new File(String.format("%s\\210899281.wsdl", path)));
    deviceDescription2 = new WSDL(new File(String.format("%s\\210899282.wsdl", path)));
    deviceDescription3 = new WSDL(new File(String.format("%s\\210899283.wsdl", path)));
    deviceDescription4 = new WSDL(new File(String.format("%s\\210899284.wsdl", path)));
    deviceDescription5 = new WSDL(new File(String.format("%s\\210899285.wsdl", path)));

    deviceDescriptions = new ArrayList<>();
    deviceDescriptions.add(deviceDescription1);
    deviceDescriptions.add(deviceDescription2);
    deviceDescriptions.add(deviceDescription3);
    deviceDescriptions.add(deviceDescription4);
    deviceDescriptions.add(deviceDescription5);
  }

  @Test
  public void testGetResource_CriteriaTemperature() throws Exception {
    selectionManager.add(deviceDescriptions);

    MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
        .get("/temperature/location/-6.900272, 107.618562/number/1")
        .param("comparisonValues", "7,1,4,5").contentType(MediaType.TEXT_PLAIN);

    ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
    resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    resultActions.andExpect(jsonPath(".url").value("http://203.27.112.70/temperature"));
    resultActions.andExpect(jsonPath(".candidateId").value("210899284"));
    resultActions.andDo(MockMvcResultHandlers.print());
  }

  @Test
  public void testGetResource_CriteriaTemperature_Same() throws Exception {
    selectionManager.add(deviceDescriptions);

    MockHttpServletRequestBuilder mockHttpServletRequestBuilder1 = MockMvcRequestBuilders
        .get("/temperature/location/-6.900272, 107.618562/number/1")
        .param("comparisonValues", "7,1,4,5").contentType(MediaType.TEXT_PLAIN);

    ResultActions resultActions1 = mockMvc.perform(mockHttpServletRequestBuilder1);
    resultActions1.andExpect(MockMvcResultMatchers.status().isOk());
    resultActions1.andExpect(jsonPath(".url").value("http://203.27.112.70/temperature"));
    resultActions1.andExpect(jsonPath(".candidateId").value("210899284"));
    resultActions1.andDo(MockMvcResultHandlers.print());

    String content1 = resultActions1.andReturn().getResponse().getContentAsString();

    MockHttpServletRequestBuilder mockHttpServletRequestBuilder2 = MockMvcRequestBuilders
        .get("/temperature/location/-6.900272, 107.618562/number/1")
        .param("comparisonValues", "7,1,4,5").contentType(MediaType.TEXT_PLAIN);

    ResultActions resultActions2 = mockMvc.perform(mockHttpServletRequestBuilder2);
    resultActions2.andExpect(MockMvcResultMatchers.status().isOk());
    resultActions2.andExpect(jsonPath(".url").value("http://203.27.112.70/temperature"));
    resultActions2.andExpect(jsonPath(".candidateId").value("210899284"));
    resultActions2.andDo(MockMvcResultHandlers.print());

    String content2 = resultActions2.andReturn().getResponse().getContentAsString();

    assertTrue(content1.equals(content2));
  }

  @Test
  public void testGetResource_CriteriaTemperature_DiffLocation() throws Exception {
    selectionManager.add(deviceDescriptions);

    MockHttpServletRequestBuilder mockHttpServletRequestBuilder1 = MockMvcRequestBuilders
        .get("/temperature/location/-6.900272, 107.618562/number/1")
        .param("comparisonValues", "7,1,4,5").contentType(MediaType.TEXT_PLAIN);

    ResultActions resultActions1 = mockMvc.perform(mockHttpServletRequestBuilder1);
    resultActions1.andExpect(MockMvcResultMatchers.status().isOk());
    resultActions1.andExpect(jsonPath(".url").value("http://203.27.112.70/temperature"));
    resultActions1.andExpect(jsonPath(".candidateId").value("210899284"));
    resultActions1.andDo(MockMvcResultHandlers.print());

    String content1 = resultActions1.andReturn().getResponse().getContentAsString();

    MockHttpServletRequestBuilder mockHttpServletRequestBuilder2 = MockMvcRequestBuilders
        .get("/temperature/location/10, 10/number/1")
        .param("comparisonValues", "7,1,4,5").contentType(MediaType.TEXT_PLAIN);

    ResultActions resultActions2 = mockMvc.perform(mockHttpServletRequestBuilder2);
    resultActions2.andExpect(MockMvcResultMatchers.status().isOk());
    resultActions2.andDo(MockMvcResultHandlers.print());

    String content2 = resultActions2.andReturn().getResponse().getContentAsString();

    assertFalse(content1.equals(content2));
  }

  @Test
  public void testGetResource_CriteriaTemperature_DiffComparisonValue() throws Exception {
    selectionManager.add(deviceDescriptions);

    MockHttpServletRequestBuilder mockHttpServletRequestBuilder1 = MockMvcRequestBuilders
        .get("/temperature/location/-6.900272, 107.618562/number/1")
        .param("comparisonValues", "7,1,4,5").contentType(MediaType.TEXT_PLAIN);

    ResultActions resultActions1 = mockMvc.perform(mockHttpServletRequestBuilder1);
    resultActions1.andExpect(MockMvcResultMatchers.status().isOk());
    resultActions1.andExpect(jsonPath(".url").value("http://203.27.112.70/temperature"));
    resultActions1.andExpect(jsonPath(".candidateId").value("210899284"));
    resultActions1.andDo(MockMvcResultHandlers.print());

    String content1 = resultActions1.andReturn().getResponse().getContentAsString();

    MockHttpServletRequestBuilder mockHttpServletRequestBuilder2 = MockMvcRequestBuilders
        .get("/temperature/location/-6.900272, 107.618562/number/1")
        .param("comparisonValues", "6,1,4,5").contentType(MediaType.TEXT_PLAIN);

    ResultActions resultActions2 = mockMvc.perform(mockHttpServletRequestBuilder2);
    resultActions2.andExpect(MockMvcResultMatchers.status().isOk());
    resultActions2.andExpect(jsonPath(".url").value("http://203.27.112.70/temperature"));
    resultActions2.andExpect(jsonPath(".candidateId").value("210899284"));
    resultActions2.andDo(MockMvcResultHandlers.print());

    String content2 = resultActions2.andReturn().getResponse().getContentAsString();

    assertFalse(content1.equals(content2));
  }

  @Test
  public void testGetResource_CriteriaHumidity() throws Exception {
    selectionManager.add(deviceDescriptions);

    MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
        .get("/humidity/location/-6.900272, 107.618562/number/1")
        .param("comparisonValues", "7,1,4,5").contentType(MediaType.TEXT_PLAIN);

    ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
    resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    resultActions.andExpect(jsonPath(".url").value("http://203.27.112.50/humidity"));
    resultActions.andDo(MockMvcResultHandlers.print());
  }

  @Test
  public void testGetResource_CriteriaOutOfRange() throws Exception {
    selectionManager.add(deviceDescriptions);

    MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
        .get("/temperature/location/90, 90/number/1")
        .param("comparisonValues", "7,1,4,5").contentType(MediaType.TEXT_PLAIN);

    ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
    resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    resultActions.andExpect(jsonPath(".message").value("No candidates available for your request"));
    resultActions.andDo(MockMvcResultHandlers.print());
  }

  @Test
  public void testGetResource_CriteriaNotAvailable() throws Exception {
    selectionManager.add(deviceDescriptions);

    MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
        .get("/temperature/location/-6.900272, 107.618562/number/3")
        .param("comparisonValues", "7,1,4,5").contentType(MediaType.TEXT_PLAIN);

    ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
    resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    resultActions.andExpect(jsonPath(".message").value("No available item"));
  }

  @Test
  public void testGetResource_CriteriaNotAvailableResource() throws Exception {
    selectionManager.add(deviceDescriptions);

    MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
        .get("/apaaja/location/-6.900272, 107.618562/number/1")
        .param("comparisonValues", "7,1,4,5").contentType(MediaType.TEXT_PLAIN);

    ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
    resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    resultActions.andExpect(jsonPath(".message").value("No candidates available for your request"));
  }

  private RankingItem generateRankAndGetService() throws SDRankException {
    selectionManager.add(deviceDescriptions);
    Vector<Float> comparisonValues = new Vector<>();
    comparisonValues.add(new Float(7));
    comparisonValues.add(new Float(1));
    comparisonValues.add(new Float(4));
    comparisonValues.add(new Float(5));

    Location location = new Location(new Double(-6.900272), new Double(107.618562));
    Criteria criteria = new Criteria("temperature", location, comparisonValues);

    return selectionManager.getItems(criteria, 1).iterator().next();
  }

  @Test
  public void testGetResource_RankingId() throws Exception {
    RankingItem item = generateRankAndGetService();

    MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
        .get(String.format("/ranking/%s/number/1", item.getRankingId()));

    ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
    resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    resultActions.andExpect(jsonPath(".candidateId").value(item.getCandidateId()));
    resultActions.andDo(MockMvcResultHandlers.print());
  }

  @Test
  public void testGetResource_RankingIdUnavailable() throws Exception {
    generateRankAndGetService();

    MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
        .get("/ranking/100/number/1");

    ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
    resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    resultActions.andExpect(jsonPath(".message").value("Ranking is unavailable"));
  }

  @Test
  public void testGetResource_RankingIdAndCandidateId() throws Exception {
    RankingItem item = generateRankAndGetService();

    MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get(String
        .format("/ranking/%s/candidate/%s/number/1", item.getRankingId(), item.getCandidateId()));

    ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
    resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    resultActions.andDo(MockMvcResultHandlers.print());

    Ranking ranking = redisCache.get(item.getRankingId());
    for (RankingItem ri : ranking.getItems(1)) {
      assertFalse(ri.getCandidateId().equals(item.getCandidateId()));
    }
  }

  @Test
  public void testGetResource_RankingIdUnavailableAndCandidateId() throws Exception {
    RankingItem item = generateRankAndGetService();

    MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
        .get(String.format("/ranking/100/candidate/%s/number/1", item.getCandidateId()));

    ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
    resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    resultActions.andExpect(jsonPath(".message").value("Ranking is unavailable"));
  }

  @Test
  public void testGetResource_RankingIdAndCandidateIdUnavailable() throws Exception {
    RankingItem item = generateRankAndGetService();

    MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders
        .get(String.format("/ranking/%s/candidate/100/number/1", item.getRankingId()));

    ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
    resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    resultActions.andExpect(jsonPath(".message").value("Berhasil"));
    resultActions.andExpect(jsonPath(".candidateId").value(item.getCandidateId()));
  }

  @Test
  public void testGetResource_RankingIdAndCandidateIdNoAvailableItem() throws Exception {
    RankingItem item = generateRankAndGetService();

    MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get(String
        .format("/ranking/%s/candidate/%s/number/2", item.getRankingId(), item.getCandidateId()));

    ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
    resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    resultActions.andExpect(jsonPath(".message").value("No available item"));
  }

  @Test
  public void testGetResource_RankingIdAndCandidateIdNoAvailableItemForNumber() throws Exception {
    RankingItem item = generateRankAndGetService();

    MockHttpServletRequestBuilder mockHttpServletRequestBuilder = MockMvcRequestBuilders.get(String
        .format("/ranking/%s/candidate/%s/number/10", item.getRankingId(), item.getCandidateId()));

    ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);
    resultActions.andExpect(MockMvcResultMatchers.status().isOk());
    resultActions.andExpect(jsonPath(".message").value("No available item"));
  }

  @After
  public void destroy() throws SDRankException {
    luceneDiscoveryEngine.clean();
    fsStorage.clean();
    redisCache.clean();
  }
}

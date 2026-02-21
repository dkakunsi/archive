package itb.sdrank.discoveryengine.lucene.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.xml.sax.SAXException;

import itb.sdrank.DescriptionStorage;
import itb.sdrank.description.WSDL;
import itb.sdrank.discoveryengine.lucene.DocumentField;
import itb.sdrank.discoveryengine.lucene.LuceneConfig;
import itb.sdrank.discoveryengine.lucene.LuceneDiscoveryEngine;
import itb.sdrank.exception.DeviceDescriptionException;
import itb.sdrank.exception.DiscoveryException;
import itb.sdrank.exception.RepositoryException;
import itb.sdrank.model.Candidate;
import itb.sdrank.model.Criteria;
import itb.sdrank.model.DeviceDescription;
import itb.sdrank.model.iot.Location;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { LuceneConfig.class })
public class LuceneDiscoveryEngineTest {
	@Resource(name = "luceneDiscoveryEngine")
	private LuceneDiscoveryEngine discoveryEngine;
  @Resource(name = "fsStorage")
	private DescriptionStorage storage;

	private String filename;
	private String filepath;

	private List<DeviceDescription> deviceDescriptions;
	private DeviceDescription deviceDescription1;
	private DeviceDescription deviceDescription2;
	private DeviceDescription deviceDescription3;
	private DeviceDescription deviceDescription4;
	private DeviceDescription deviceDescription5;

	@Before
	public void init() throws DeviceDescriptionException, RepositoryException {
	  String path = "C:\\Users\\dkakunsi\\Documents\\IF6099 Tesis\\xml\\wsdl";
		filename = "210899281.wsdl";
		filepath = String.format("%s\\%s", path, filename);

		deviceDescription1 = new WSDL(new File(String.format("%s\\%s", path, "210899281.wsdl"))); // 5 temperature
		deviceDescription2 = new WSDL(new File(String.format("%s\\%s", path, "210899282.wsdl"))); // 2 temperature
		deviceDescription3 = new WSDL(new File(String.format("%s\\%s", path, "210899283.wsdl"))); // 5 humidity
		deviceDescription4 = new WSDL(new File(String.format("%s\\%s", path, "210899284.wsdl"))); // 4 temperature
		deviceDescription5 = new WSDL(new File(String.format("%s\\%s", path, "210899285.wsdl"))); // 4 humidity

		deviceDescriptions = new ArrayList<>();
		deviceDescriptions.add(deviceDescription1);
		deviceDescriptions.add(deviceDescription2);
		deviceDescriptions.add(deviceDescription3);
		deviceDescriptions.add(deviceDescription4);
		deviceDescriptions.add(deviceDescription5);
		
		discoveryEngine.setRepository(storage);
	}

	@Test
	public void testId()
			throws ParserConfigurationException, IOException, SAXException, DiscoveryException, ParseException, DeviceDescriptionException {
	  DeviceDescription deviceDescription = new WSDL(new File(filepath));
		discoveryEngine.add(deviceDescription);

		// check id
		String id = deviceDescription.getId();
		Document document = getDocument(id);
		assertEquals(deviceDescription.getId(), document.get(DocumentField.ID));
	}

	@Test
	public void testIds() throws DiscoveryException, IOException, ParseException {
		discoveryEngine.add(deviceDescriptions);
		assertEquals(5, getIndexSize());

		// check if the files exist in index
		String id = deviceDescription1.getId();
		Document document = getDocument(id);
		assertEquals(id, document.get(DocumentField.ID));
		assertEquals(deviceDescription1.getId(), document.get(DocumentField.ID));

		id = deviceDescription2.getId();
		document = getDocument(id);
		assertEquals(id, document.get(DocumentField.ID));
		assertEquals(deviceDescription2.getId(), document.get(DocumentField.ID));

		id = deviceDescription3.getId();
		document = getDocument(id);
		assertEquals(id, document.get(DocumentField.ID));
		assertEquals(deviceDescription3.getId(), document.get(DocumentField.ID));

		id = deviceDescription4.getId();
		document = getDocument(id);
		assertEquals(id, document.get(DocumentField.ID));
		assertEquals(deviceDescription4.getId(), document.get(DocumentField.ID));

		id = deviceDescription5.getId();
		document = getDocument(id);
		assertEquals(id, document.get(DocumentField.ID));
		assertEquals(deviceDescription5.getId(), document.get(DocumentField.ID));
	}

	public static Document getDocument(String indexId) throws IOException, ParseException {
		Analyzer analyzer = LuceneDiscoveryEngine.getAnalyzer();
		Directory directory = FSDirectory.open(LuceneDiscoveryEngine.DIRECTORY_PATH);
		DirectoryReader reader = DirectoryReader.open(directory);
		IndexSearcher searcher = new IndexSearcher(reader);
		QueryParser parser = new QueryParser(DocumentField.ID, analyzer);
		Query query = parser.parse(indexId);
		TopDocs hit = searcher.search(query, 1000);
		ScoreDoc[] hits = hit.scoreDocs;

		Document document = searcher.doc(hits[0].doc);

		reader.close();
		directory.close();
		analyzer.close();

		return document;
	}

	public static int getIndexSize() throws IOException {
		Directory directory = FSDirectory.open(LuceneDiscoveryEngine.DIRECTORY_PATH);
		IndexReader reader = DirectoryReader.open(directory);
		for (int i = 0; i < reader.numDocs(); i++) {
			Document document = reader.document(i);
			String indexId = document.get(DocumentField.ID);
			String documentId = document.get(DocumentField.ID);

			System.out.println(String.format("'%s':'%s'", indexId, documentId));
		}
		reader.close();
		directory.close();
		
		return reader.numDocs();
	}

	@Test
	public void testIndexesOneByOne() throws DiscoveryException, IOException, ParseException {
		String id;
		Document document;

		discoveryEngine.add(deviceDescription1);
		discoveryEngine.add(deviceDescription2);
		discoveryEngine.add(deviceDescription3);
		discoveryEngine.add(deviceDescription4);
		discoveryEngine.add(deviceDescription5);
		assertEquals(5, getIndexSize());

		id = deviceDescription1.getId();
		document = getDocument(id);
		assertEquals(id, document.get(DocumentField.ID));
		assertEquals(deviceDescription1.getId(), document.get(DocumentField.ID));

		id = deviceDescription2.getId();
		document = getDocument(id);
		assertEquals(id, document.get(DocumentField.ID));
		assertEquals(deviceDescription2.getId(), document.get(DocumentField.ID));

		id = deviceDescription3.getId();
		document = getDocument(id);
		assertEquals(id, document.get(DocumentField.ID));
		assertEquals(deviceDescription3.getId(), document.get(DocumentField.ID));

		id = deviceDescription4.getId();
		document = getDocument(id);
		assertEquals(id, document.get(DocumentField.ID));
		assertEquals(deviceDescription4.getId(), document.get(DocumentField.ID));

		id = deviceDescription5.getId();
		document = getDocument(id);
		assertEquals(id, document.get(DocumentField.ID));
		assertEquals(deviceDescription5.getId(), document.get(DocumentField.ID));
	}

	@Test
	public void testDiscoverTemperature() throws DiscoveryException, RepositoryException {
	  storage.save(deviceDescriptions);
		discoveryEngine.add(deviceDescriptions);

		Location location = new Location(new Double(-6.900272), new Double(107.618562));
		Criteria criteria = new Criteria("temperature", location, null);
		List<Candidate> candidates = discoveryEngine.discover(criteria);
		assertFalse(candidates.isEmpty());

		Candidate candidate = candidates.get(0);
		assertEquals(deviceDescription1.getId(), candidate.getId());
	}

	@Test
	public void testDiscoverHumidity() throws DiscoveryException, RepositoryException {
    storage.save(deviceDescriptions);
		discoveryEngine.add(deviceDescriptions);

    Location location = new Location(new Double(-6.900272), new Double(107.618562));
		Criteria criteria = new Criteria("humidity", location, null);
		List<Candidate> candidates = discoveryEngine.discover(criteria);
		assertFalse(candidates.isEmpty());

		Candidate candidate = candidates.get(0);
		assertEquals(deviceDescription3.getId(), candidate.getId());
	}

  @After
  public void destroy() {
		try {
			discoveryEngine.clean();
			storage.clean();
		} catch (DiscoveryException | RepositoryException e) {
			e.printStackTrace();
		}
	}

}

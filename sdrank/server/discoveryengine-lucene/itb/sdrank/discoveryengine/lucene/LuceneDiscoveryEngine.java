package itb.sdrank.discoveryengine.lucene;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.en.EnglishAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import itb.sdrank.DescriptionStorage;
import itb.sdrank.DiscoveryEngine;
import itb.sdrank.exception.DeviceDescriptionException;
import itb.sdrank.exception.DiscoveryException;
import itb.sdrank.exception.RepositoryException;
import itb.sdrank.model.Candidate;
import itb.sdrank.model.Criteria;
import itb.sdrank.model.DeviceDescription;

@Service("luceneDiscoveryEngine")
public class LuceneDiscoveryEngine implements DiscoveryEngine {
  public static final Path DIRECTORY_PATH = Paths
      .get(String.format("%s\\index", DeviceDescription.FILEPATH));
  private Directory directory;
  private DescriptionStorage repository;

  @Override
  public List<Candidate> discover(Criteria criteria) throws DiscoveryException {
    List<Candidate> candidates = new ArrayList<>();
    for (Candidate candidate : discover(criteria.getQuery(), DocumentField.RESOURCE)) {
      if (candidate.isActive() && candidate.inRange(criteria.getLocation()))
        candidates.add(candidate);
    }
    
    return candidates;
  }

  private List<Candidate> discover(String keyword, String field) throws DiscoveryException {
    List<Candidate> candidates = new ArrayList<>();

    try {
      directory = FSDirectory.open(DIRECTORY_PATH);
      DirectoryReader reader = DirectoryReader.open(directory);
      IndexSearcher searcher = new IndexSearcher(reader);
      Analyzer analyzer = getAnalyzer();

      QueryParser parser = new QueryParser(field, analyzer);
      Query query = parser.parse(keyword);
      TopDocs hit = searcher.search(query, 1000);

      ScoreDoc[] hits = hit.scoreDocs;
      for (int i = 0; i < hits.length; i++) {
        Document document = searcher.doc(hits[i].doc);
        loadRelatedCandidates(candidates, keyword, document.get(DocumentField.ID));
      }

      analyzer.close();
      reader.close();
      directory.close();
    } catch (IOException | ParseException e) {
      throw new DiscoveryException(e);
    }

    return candidates;
  }
  
  private void loadRelatedCandidates(List<Candidate> candidates, String keyword, String deviceId) {
    try {
      DeviceDescription deviceDescription = repository.get(deviceId);
      candidates.addAll(deviceDescription.getCandidates(keyword));
    } catch (RepositoryException e) {
    }
  }

  @Override
  public void add(DeviceDescription deviceDescription) throws DiscoveryException {
    try {
      directory = FSDirectory.open(DIRECTORY_PATH);
      Analyzer analyzer = getAnalyzer();
      IndexWriterConfig config = new IndexWriterConfig(analyzer);
      IndexWriter writer = new IndexWriter(directory, config);

      Document document = build(deviceDescription);
      deleteDocumentIfExist(document.get(DocumentField.ID), writer, analyzer);
      writer.addDocument(document);

      writer.close();
      analyzer.close();
      directory.close();
    } catch (IOException | SAXException | DeviceDescriptionException e) {
      throw new DiscoveryException(e);
    }
  }

  private void deleteDocumentIfExist(String deviceId, IndexWriter writer, Analyzer analyzer) {
    try {
      QueryParser parser = new QueryParser(DocumentField.ID, analyzer);
      Query query = parser.parse(deviceId);
      writer.deleteDocuments(query);
    } catch (IOException | ParseException e) {

    }
  }

  @Override
  public void add(List<DeviceDescription> deviceDescriptions) throws DiscoveryException {
    try {
      directory = FSDirectory.open(DIRECTORY_PATH);
      Analyzer analyzer = getAnalyzer();
      IndexWriterConfig config = new IndexWriterConfig(analyzer);
      IndexWriter writer = new IndexWriter(directory, config);

      for (DeviceDescription deviceDescription : deviceDescriptions) {
        try {
          Document document = build(deviceDescription);
          deleteDocumentIfExist(document.get(DocumentField.ID), writer, analyzer);
          writer.addDocument(document);
        } catch (SAXException | IOException | DeviceDescriptionException e) {
          continue;
        }
      }

      writer.close();
      directory.close();
      analyzer.close();
    } catch (IOException e) {
      throw new DiscoveryException(e);
    }
  }

  private Document build(DeviceDescription deviceDescription) throws IOException, SAXException, DeviceDescriptionException {
    if (deviceDescription == null)
      throw new IOException("File is not available.");

    Document document = new Document();
    document.add(new Field(DocumentField.ID, deviceDescription.getId(), TextField.TYPE_STORED));
    document.add(new Field(DocumentField.RESOURCE,
        tokenize(DocumentField.RESOURCE, deviceDescription.getResources()), TextField.TYPE_STORED));

    return document;
  }

  public static Analyzer getAnalyzer() {
    return new EnglishAnalyzer();
  }

  private String tokenize(String fieldName, String content) throws IOException {
    String tokenizedContent = "";
    Analyzer analyzer = getAnalyzer();
    TokenStream tokenStream = analyzer.tokenStream(fieldName, new StringReader(content));
    CharTermAttribute charTermAttribute = tokenStream.addAttribute(CharTermAttribute.class);

    tokenStream.reset();
    while (tokenStream.incrementToken()) {
      String term = charTermAttribute.toString();
      tokenizedContent = String.format("%s %s", tokenizedContent, term);
    }

    tokenStream.end();
    tokenStream.close();
    analyzer.close();

    return tokenizedContent;
  }

  public void clean() throws DiscoveryException {
    try {
      FileUtils.cleanDirectory(DIRECTORY_PATH.toFile());
    } catch (IOException e) {
      throw new DiscoveryException(e);
    }
  }

  @Override
  public void setRepository(DescriptionStorage repository) {
    this.repository = repository;
  }
}

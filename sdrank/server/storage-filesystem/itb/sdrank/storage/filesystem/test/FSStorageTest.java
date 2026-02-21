package itb.sdrank.storage.filesystem.test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import itb.sdrank.DescriptionStorage;
import itb.sdrank.description.WSDL;
import itb.sdrank.exception.DeviceDescriptionException;
import itb.sdrank.exception.RepositoryException;
import itb.sdrank.model.DeviceDescription;
import itb.sdrank.storage.filesystem.FSConfig;
import itb.sdrank.storage.filesystem.FSStorage;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { FSConfig.class })
public class FSStorageTest {
  @Resource(name = "fsStorage")
  private DescriptionStorage repository;
  private DeviceDescription wsdl;

  @Before
  public void init() throws DeviceDescriptionException {
    String path = "C:\\Users\\dkakunsi\\Documents\\IF6099 Tesis\\xml\\wsdl\\210899281.wsdl";
    wsdl = new WSDL(new File(path));
  }

  @Test
  public void testAdd() throws RepositoryException, IOException {
    repository.save(wsdl);

    File newFile = new File(String.format("%s\\%s.wsdl", FSStorage.directory, wsdl.getId()));
    assertNotNull(newFile);
    assertTrue(newFile.isFile());
  }

  @Test
  public void testGet() throws RepositoryException {
    repository.save(wsdl);

    DeviceDescription retrievedWsdl = repository.get(wsdl.getId());
    assertNotNull(retrievedWsdl);

    File newFile = retrievedWsdl.getFile();
    assertNotNull(newFile);
    assertTrue(newFile.isFile());
  }

  @After
  public void destroy() throws RepositoryException {
    repository.clean();
  }
}

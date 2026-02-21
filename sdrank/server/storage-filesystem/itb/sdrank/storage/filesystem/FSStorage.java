package itb.sdrank.storage.filesystem;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Component;

import itb.sdrank.DescriptionStorage;
import itb.sdrank.description.WSDL;
import itb.sdrank.exception.DeviceDescriptionException;
import itb.sdrank.exception.RepositoryException;
import itb.sdrank.model.DeviceDescription;;

@Component("fsStorage")
public class FSStorage implements DescriptionStorage {
  public static final Path directory = Paths.get(String.format("%s\\repo", DeviceDescription.FILEPATH));

  @Override
  public DeviceDescription get(String id) throws RepositoryException {
    File file = new File(String.format("%s\\%s.wsdl", directory, id));
    if (!file.isFile()) {
      throw new RepositoryException("File not exist");
    }

    try {
      return new WSDL(file);
    } catch (DeviceDescriptionException e) {
      throw new RepositoryException(e);
    }
  }

  @Override
  public void save(DeviceDescription entity) throws RepositoryException {
    try {
      clean(get(entity.getId()).getFile());
    } catch (IOException | RepositoryException e) {
      System.out.println(String.format("'SDRank Message' => Deleting: %s", e.getMessage()));
    }

    try {
      List<String> lines = new ArrayList<String>();
      lines.add(entity.getContent());
      Path path = Paths.get(String.format("%s\\%s.wsdl", directory, entity.getId()));
      Files.write(path, lines, Charset.forName("UTF-8"));
    } catch (IOException e) {
      throw new RepositoryException(e);
    }
  }

  @Override
  public void save(List<DeviceDescription> entities) throws RepositoryException {
    for (DeviceDescription wsdl : entities) {
      try {
        save(wsdl);
      } catch (RepositoryException e) {
        continue;
      }
    }
  }

  @Override
  public void clean() throws RepositoryException {
    try {
      clean(new File(directory.toString()));
    } catch (IOException e) {
      throw new RepositoryException(e);
    }
  }

  public static void clean(File file) throws IOException {
    if (file.isFile()) {
      Files.delete(file.toPath());
    } else if (file.isDirectory()) {
      FileUtils.cleanDirectory(file);
    } else {
      throw new IOException("No file or directory");
    }
  }
}

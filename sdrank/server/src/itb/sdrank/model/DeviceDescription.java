package itb.sdrank.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import itb.sdrank.exception.DeviceDescriptionException;
import itb.sdrank.model.iot.DeviceInfo;
import itb.sdrank.model.iot.Quality;
import itb.sdrank.model.iot.ResourceInfo;
import itb.sdrank.model.iot.ServiceInfo;

public abstract class DeviceDescription extends Entity {
  public static final Path FILEPATH = Paths.get("C:\\Users\\dkakunsi\\Documents\\XML");
  protected File file;
  protected DeviceInfo deviceInfo;
  protected Map<String, ResourceInfo> resourceInfos;
  protected Map<String, ServiceInfo> serviceInfos;
  protected List<String> attributes;

  protected DeviceDescription(File file) throws DeviceDescriptionException {
    super();
    this.file = file;
    resourceInfos = new HashMap<>();
    serviceInfos = new HashMap<>();
    attributes = new ArrayList<>();
    parse();
  }

  protected DeviceDescription(File file, String id) throws DeviceDescriptionException {
    super();
    this.file = file;
    this.id = id;
    resourceInfos = new HashMap<>();
    serviceInfos = new HashMap<>();
    attributes = new ArrayList<>();
    parse();
  }

  public File getFile() {
    return file;
  }

  public String getResources() {
    String resources = "";
    for (String str : getAttributes()) {
      resources = String.format("%s %s", resources, str);
    }
    return resources;
  }

  private void parse() throws DeviceDescriptionException {
    initialize();
    deviceInfo = parseDevice();
    resourceInfos = parseResources();
    serviceInfos = parseServices();
    attributes = setAttributes();
    this.id = deviceInfo.getId();
  }

  private List<String> setAttributes() {
    List<String> strings = new ArrayList<>();
    for (Map.Entry<String, ResourceInfo> entry : resourceInfos.entrySet()) {
      ResourceInfo resourceInfo = entry.getValue();
      strings.add(resourceInfo.getAttribute());
    }

    return strings;
  }

  public String getContent() throws IOException {
    String content = "";
    int binaryContent;
    FileInputStream fileInputStream = new FileInputStream(file);

    while ((binaryContent = fileInputStream.read()) != -1) {
      content = String.format("%s%c", content, (char) binaryContent);
    }

    fileInputStream.close();
    return content;
  }

  public List<Candidate> getCandidates(String attribute) {
    List<Candidate> candidates = new ArrayList<>();

    if (getResources().contains(attribute)) {

      for (Map.Entry<String, ServiceInfo> entry : serviceInfos.entrySet()) {
        ServiceInfo serviceInfo = entry.getValue();
        ResourceInfo resourceInfo = getResourceInfo(serviceInfo.getInterfaceName());

        if (resourceInfo.getAttribute().equals(attribute)) {
          candidates.add(new Candidate(deviceInfo, resourceInfo, serviceInfo));
        }
      }
    }

    return candidates;
  }

  private ResourceInfo getResourceInfo(String interfaceName) {
    return resourceInfos.get(interfaceName);
  }

  public List<String> getAttributes() {
    return attributes;
  }

  public abstract void setQuality(String resourceName, Quality quality)
      throws DeviceDescriptionException;

  public abstract DeviceDescription save() throws DeviceDescriptionException;

  protected abstract Map<String, ServiceInfo> parseServices() throws DeviceDescriptionException;

  protected abstract Map<String, ResourceInfo> parseResources() throws DeviceDescriptionException;

  protected abstract DeviceInfo parseDevice() throws DeviceDescriptionException;

  protected abstract void initialize() throws DeviceDescriptionException;

  public abstract void setLocation(Double latitude, Double longitude) throws DeviceDescriptionException;
  
  public abstract void setOperationMode(boolean active) throws DeviceDescriptionException;

  public abstract void setUri(String uri) throws DeviceDescriptionException;
}

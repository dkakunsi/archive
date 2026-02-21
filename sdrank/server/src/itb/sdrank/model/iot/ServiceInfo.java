package itb.sdrank.model.iot;

import java.util.ArrayList;
import java.util.List;

public class ServiceInfo {
  private String endpoint;
  private String name;
  private List<String> effects;
  private String interfaceName;

  public ServiceInfo() {
    super();
    effects = new ArrayList<>();
  }

  public ServiceInfo(String endpoint, String name, String interfaceName) {
    super();
    this.endpoint = endpoint;
    this.name = name;
    this.interfaceName = interfaceName;
    effects = new ArrayList<>();
  }

  public String getEndpoint() {
    return endpoint;
  }

  public void setEndpoint(String endpoint) {
    this.endpoint = endpoint;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public List<String> getEffects() {
    return effects;
  }

  public void setEffects(List<String> effects) {
    this.effects = effects;
  }

  public void addEffect(String effect) {
    effects.add(effect);
  }

  public String getInterfaceName() {
    return interfaceName;
  }

  public void setInterfaceName(String interfaceName) {
    this.interfaceName = interfaceName;
  }
}

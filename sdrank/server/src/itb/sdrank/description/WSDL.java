package itb.sdrank.description;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import itb.sdrank.exception.DeviceDescriptionException;
import itb.sdrank.model.DeviceDescription;
import itb.sdrank.model.iot.DeviceInfo;
import itb.sdrank.model.iot.Location;
import itb.sdrank.model.iot.Owner;
import itb.sdrank.model.iot.Quality;
import itb.sdrank.model.iot.ResourceInfo;
import itb.sdrank.model.iot.ServiceInfo;

public class WSDL extends DeviceDescription {
  private DocumentBuilderFactory documentBuilderFactory;
  private DocumentBuilder documentBuilder;
  private Document document;

  public WSDL(File file) throws DeviceDescriptionException {
    super(file);
  }

  public WSDL(File file, String id) throws DeviceDescriptionException {
    super(file, id);
  }

  protected void initialize() throws DeviceDescriptionException {
    try {
      documentBuilderFactory = DocumentBuilderFactory.newInstance();
      documentBuilder = documentBuilderFactory.newDocumentBuilder();
      document = documentBuilder.parse(file);
      document.normalize();
    } catch (ParserConfigurationException | SAXException | IOException e) {
      throw new DeviceDescriptionException(e);
    }
  }

  @Override
  public void setQuality(String resourceName, Quality quality) throws DeviceDescriptionException {
    NodeList resourceInterfaces = document.getElementsByTagName("iot:resourceInterface");
    Element resourceInterface = (Element) getElement(resourceInterfaces, "domainAttribute",
        resourceName);
    NodeList resourceInterfaceChildren = resourceInterface.getElementsByTagName("iot:quality");
    Element qualityElement;

    try {
      qualityElement = (Element) getElement(resourceInterfaceChildren, "name", quality.getType());
      qualityElement.setAttribute("value", quality.getValue().toString());
    } catch (DeviceDescriptionException e) {
      qualityElement = document.createElement("iot:quality");
      qualityElement.setAttribute("name", quality.getType());
      qualityElement.setAttribute("value", quality.getValue().toString());
      qualityElement.setAttribute("unit", quality.getUnit());
      resourceInterface.appendChild(qualityElement);
    }
  }

  @Override
  public void setLocation(Double latitude, Double longitude) throws DeviceDescriptionException {
    NodeList globalLocations = document.getElementsByTagName("iot:globalLocation");
    Element globalLocation = (Element) globalLocations.item(0);
    globalLocation.setAttribute("latitude", latitude.toString());
    globalLocation.setAttribute("longitude", longitude.toString());
  }

  private Node getElement(NodeList elements, String attributeName, String attributeValue)
      throws DeviceDescriptionException {
    Node node = null;
    int idxElement;
    int idxAttribute;

    for (idxElement = 0; idxElement < elements.getLength(); idxElement++) {
      Node element = elements.item(idxElement);
      NamedNodeMap elementAttributes = element.getAttributes();

      for (idxAttribute = 0; idxAttribute < elementAttributes.getLength(); idxAttribute++) {
        Node elementAttribute = elementAttributes.item(idxAttribute);
        if (elementAttribute.getNodeName().equals(attributeName)
            && elementAttribute.getNodeValue().equals(attributeValue)) {
          node = element;
          break;
        }
      }
    }

    if (node == null)
      throw new DeviceDescriptionException(
          String.format("Element %s:%s not found.", attributeName, attributeValue));
    return node;
  }

  private Attr getAttribute(Element element, String attributeName)
      throws DeviceDescriptionException {
    Attr attribute = null;
    int idxAttribute;

    NamedNodeMap attributes = element.getAttributes();
    for (idxAttribute = 0; idxAttribute < attributes.getLength(); idxAttribute++) {
      attribute = (Attr) attributes.item(idxAttribute);
      if (attribute.getNodeName().equals(attributeName)) {
        break;
      }
    }

    if (attribute == null)
      throw new DeviceDescriptionException("Attribute not found.");
    return attribute;
  }

  @Override
  protected Map<String, ServiceInfo> parseServices() {
    Map<String, ServiceInfo> serviceInfos = new HashMap<>();
    ServiceInfo serviceInfo;
    String serviceName = "";
    String interfaceName = "";
    String endpointAddress;
    String endpointName;
    int idxServiceElement;
    int idxEndpointElement;

    NodeList serviceElements = document.getElementsByTagName("service");
    for (idxServiceElement = 0; idxServiceElement < serviceElements
        .getLength(); idxServiceElement++) {
      Element serviceElement = (Element) serviceElements.item(idxServiceElement);
      serviceName = serviceElement.getAttribute("name");
      interfaceName = getValue(serviceElement.getAttribute("interface"));

      NodeList endpointElements = serviceElement.getElementsByTagName("endpoint");
      for (idxEndpointElement = 0; idxEndpointElement < endpointElements
          .getLength(); idxEndpointElement++) {
        Element endpointElement = (Element) endpointElements.item(idxEndpointElement);
        endpointAddress = endpointElement.getAttribute("address");
        endpointName = endpointElement.getAttribute("name");

        serviceInfo = new ServiceInfo(endpointAddress, serviceName, interfaceName);
        serviceInfos.put(String.format("%s:%s", serviceName, endpointName), serviceInfo);
      }
    }

    return serviceInfos;
  }

  @Override
  protected Map<String, ResourceInfo> parseResources() {
    Map<String, ResourceInfo> resourceInfos = new HashMap<>();
    ResourceInfo resourceInfo;
    int idxInterfaceElement;
    NodeList interfaceElements = document.getElementsByTagName("iot:resourceInterface");

    for (idxInterfaceElement = 0; idxInterfaceElement < interfaceElements
        .getLength(); idxInterfaceElement++) {
      Element interfaceElement = (Element) interfaceElements.item(idxInterfaceElement);
      resourceInfo = new ResourceInfo();
      resourceInfo.setName(interfaceElement.getAttribute("name"));
      resourceInfo.setType(interfaceElement.getAttribute("resourceType"));
      resourceInfo.setRange(Float.valueOf(interfaceElement.getAttribute("resourceRange")));
      resourceInfo.setRangeUnit(interfaceElement.getAttribute("rangeUnit"));
      resourceInfo.setSampling(Float.valueOf(interfaceElement.getAttribute("sampling")));
      resourceInfo.setSamplingUnit(interfaceElement.getAttribute("samplingUnit"));
      resourceInfo.setAttribute(interfaceElement.getAttribute("domainAttribute"));
      resourceInfo.setAttributeUnit(interfaceElement.getAttribute("attributeUnit"));
      resourceInfo.setQualities(getQualities(interfaceElement));

      resourceInfos.put(resourceInfo.getName(), resourceInfo);
    }

    return resourceInfos;
  }

  private List<Quality> getQualities(Element interfaceElement) {
    List<Quality> qualities = new ArrayList<>();
    String type;
    String unit;
    Float value;
    int idxChildElement;

    NodeList qualityElements = interfaceElement.getElementsByTagName("iot:quality");
    for (idxChildElement = 0; idxChildElement < qualityElements.getLength(); idxChildElement++) {
      Element qualityElement = (Element) qualityElements.item(idxChildElement);

      try {
        type = getAttribute(qualityElement, "name").getValue();
        unit = getAttribute(qualityElement, "unit").getValue();
        value = Float.valueOf(getAttribute(qualityElement, "value").getValue());
        qualities.add(new Quality(type, unit, value));
      } catch (DeviceDescriptionException e) {
        // do nothing. next quality.
      }
    }

    return qualities;
  }

  @Override
  protected DeviceInfo parseDevice() {
    DeviceInfo deviceInfo = new DeviceInfo();
    NodeList descriptionElements = document.getElementsByTagName("iot:deviceDescription");
    if (descriptionElements.getLength() > 0) {
      Element descriptionElement = (Element) descriptionElements.item(0);
      deviceInfo.setId(descriptionElement.getAttribute("deviceId"));
      deviceInfo.setName(descriptionElement.getAttribute("deviceName"));
      deviceInfo.setOperationMode(descriptionElement.getAttribute("operationMode"));
      deviceInfo.setMobile(Boolean.valueOf(descriptionElement.getAttribute("mobility")));
      deviceInfo.setIndoor(Boolean.valueOf(descriptionElement.getAttribute("indoor")));
      deviceInfo.setOwner(getOwner((Element) descriptionElement));
      deviceInfo.setLocation(getLocation((Element) descriptionElement));
    }

    return deviceInfo;
  }

  public Owner getOwner(Element descriptionElement) {
    Owner owner = new Owner();
    NodeList childElements = descriptionElement.getElementsByTagName("iot:owner");
    if (childElements.getLength() > 0) {
      Element ownerElement = (Element) childElements.item(0);
      owner.setName(ownerElement.getAttribute("name"));
      owner.setHomepage(ownerElement.getAttribute("homepage"));
    }

    return owner;
  }

  public Location getLocation(Element descriptionElement) {
    Location location = new Location();
    NodeList childElements = descriptionElement.getElementsByTagName("iot:globalLocation");
    if (childElements.getLength() > 0) {
      Element globalLocationElement = (Element) childElements.item(0);
      location.setLatitude(Double.valueOf(globalLocationElement.getAttribute("latitude")));
      location.setLongitude(Double.valueOf(globalLocationElement.getAttribute("longitude")));
    }

    childElements = descriptionElement.getElementsByTagName("iot:localLocation");
    if (childElements.getLength() > 0) {
      Element localLocationElement = (Element) childElements.item(0);
      location.setBuilding(localLocationElement.getAttribute("building"));
      location.setFloor(localLocationElement.getAttribute("floor"));
      location.setRoom(localLocationElement.getAttribute("room"));
    }

    return location;
  }

  private String getValue(String str) {
    String value = str;
    if (str.contains(":")) {
      value = str.split(":")[1];
    }
    return value;
  }

  @Override
  public void setOperationMode(boolean active) {
    NodeList descriptionElements = document.getElementsByTagName("iot:deviceDescription");
    if (descriptionElements.getLength() > 0) {
      Element descriptionElement = (Element) descriptionElements.item(0);
      if (active) {
        descriptionElement.setAttribute("operationMode", "active");
      } else {
        descriptionElement.setAttribute("operationMode", "non-active");
      }
    }
  }

  @Override
  public void setUri(String uri) {
    NodeList endpointElements = document.getElementsByTagName("endpoint");
    if (endpointElements.getLength() > 0) {
      for (int i = 0; i < endpointElements.getLength(); i++) {
        Element endpointElement = (Element) endpointElements.item(i);
        endpointElement.setAttribute("address", uri);
      }
    }
  }

  @Override
  public DeviceDescription save() throws DeviceDescriptionException {
    try {
      TransformerFactory transformerFactory = TransformerFactory.newInstance();
      Transformer transformer = transformerFactory.newTransformer();
      DOMSource source = new DOMSource(document);
      StreamResult streamResult = new StreamResult(file);
      transformer.transform(source, streamResult);

      return new WSDL(file);
    } catch (TransformerException e) {
      throw new DeviceDescriptionException(e);
    }
  }
}

package itb.sdrank.web.controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import itb.sdrank.SelectionManager;
import itb.sdrank.description.WSDL;
import itb.sdrank.exception.SDRankException;
import itb.sdrank.exception.DeviceDescriptionException;
import itb.sdrank.model.DeviceDescription;
import itb.sdrank.model.QualityField;
import itb.sdrank.model.iot.Quality;
import itb.sdrank.storage.filesystem.FSStorage;
import itb.sdrank.web.SimpleResponse;

@RestController
public class RegistrationController {
  @Resource
  private SelectionManager selectionManager;

  private Path temporaryFile = Paths.get(String.format("%s\\temp", DeviceDescription.FILEPATH));

  @RequestMapping(method = RequestMethod.POST, value = "/description")
  @ResponseStatus(code = HttpStatus.OK)
  public SimpleResponse add(@RequestParam MultipartFile descriptionFile) throws SDRankException {
    try {
      DeviceDescription deviceDescription = toDeviceDescription(descriptionFile);
      selectionManager.add(deviceDescription);
      clean(deviceDescription.getFile());
      return new SimpleResponse("Description file has been added to repository and indexed");
    } catch (IllegalStateException | IOException e) {
      throw new SDRankException(e);
    }
  }

  @RequestMapping(method = RequestMethod.POST, value = "/descriptions")
  @ResponseStatus(code = HttpStatus.OK)
  public SimpleResponse add(@RequestParam MultipartFile[] descriptionFiles) throws SDRankException {
    try {
      List<DeviceDescription> deviceDescriptions = toFile(descriptionFiles);
      selectionManager.add(deviceDescriptions);
      clean(temporaryFile.toFile());
      return new SimpleResponse("Description files has been added to repository and indexed");
    } catch (IllegalStateException | IOException e) {
      throw new SDRankException(e);
    }
  }

  @RequestMapping(method = RequestMethod.PUT, value = "/description/{id}/resource/{resourceName}")
  @ResponseStatus(code = HttpStatus.OK)
  public SimpleResponse update(@PathVariable String id, @PathVariable String resourceName,
      @RequestBody Quality quality) throws SDRankException {
    
    if (!QualityField.isExist(quality.getType())) {
      throw new SDRankException(String.format("Quality attribute not recognized. Available quality: %s",
          QualityField.getQualities()));
    }

    selectionManager.updateQuality(id, resourceName, quality);
    return new SimpleResponse("Quality attribute updated");
  }

  @RequestMapping(method = RequestMethod.PUT, value = "/description/{id}/latitude/{latitude}/longitude/{longitude}")
  @ResponseStatus(code = HttpStatus.OK)
  public SimpleResponse update(@PathVariable String id, @PathVariable Double latitude,
      @PathVariable Double longitude) throws SDRankException {
    selectionManager.updateLocation(id, latitude, longitude);
    return new SimpleResponse("Location updated");
  }

  @RequestMapping(method = RequestMethod.PUT, value = "/description/{id}/operation/mode/{operationMode}")
  @ResponseStatus(code = HttpStatus.OK)
  public SimpleResponse update(@PathVariable String id, @PathVariable Boolean operationMode, @RequestParam String uri) throws SDRankException {
    selectionManager.updateOperationMode(id, operationMode, uri);
    return new SimpleResponse("Operation mode updated");
  }

  private DeviceDescription toDeviceDescription(MultipartFile multipartFile)
      throws DeviceDescriptionException {
    DeviceDescription deviceDescription = null;
    String fileType = FilenameUtils.getExtension(multipartFile.getOriginalFilename());

    try {
      if (fileType.equals("wsdl")) {
        deviceDescription = new WSDL(toFile(multipartFile));
      } else {
        throw new DeviceDescriptionException(
            String.format("File with extension '%s' is not supported.", fileType));
      }
    } catch (IOException e) {
      throw new DeviceDescriptionException(e);
    }

    return deviceDescription;
  }

  private File toFile(MultipartFile multipartFile) throws IOException {
    String filename = multipartFile.getOriginalFilename();
    File file = new File(String.format("%s\\%s", temporaryFile, filename));
    multipartFile.transferTo(file);
    return file;
  }

  private List<DeviceDescription> toFile(MultipartFile[] multipartFiles)
      throws IOException, DeviceDescriptionException {
    List<DeviceDescription> files = new ArrayList<>();
    for (MultipartFile multipartfile : multipartFiles) {
      files.add(toDeviceDescription(multipartfile));
    }
    return files;
  }

  private void clean(File file) throws IOException {
    FSStorage.clean(file);
  }

}

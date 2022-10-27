package com.api.controller;

import com.api.ResponseMessage;
import com.api.helper.CSVHelper;
import com.api.model.APIModel;
import com.api.service.CSVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
@Controller
@CrossOrigin("http://localhost:8080")
@RequestMapping("/api/csv")
public class APIController {
  private final CSVService fileService;
  @Autowired
  public APIController(CSVService csvService){
    this.fileService = csvService;
  }
    @GetMapping(value = "/index")
    public String homePage() {
      return "indexPrevious";
    }

    @GetMapping(value = "/cleardb")
    public ResponseEntity<ResponseMessage> clearDb() {
      String message = "Database Cleared!";
      fileService.clearDatabase();
      return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
    }

  @PostMapping("/upload")
  public ResponseEntity<ResponseMessage> uploadFile(@RequestParam("file") MultipartFile file) {
    String message = "";
    if (CSVHelper.hasCSVFormat(file)) {
      int extensionIndex = file.getOriginalFilename().lastIndexOf(".");
      String fileNameWithoutExtension = file.getOriginalFilename().substring(0,extensionIndex);
      try {
        fileService.save(file);
        message = "Uploaded the file successfully: " + file.getOriginalFilename();
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/api/csv/download/")
                .path(fileNameWithoutExtension+".txt")
                .toUriString();

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message,fileDownloadUri));
      } catch (Exception e) {
        e.printStackTrace();
        message = "Could not upload the file: " + file.getOriginalFilename() + "!";
        return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message,""));
      }
    }

    message = "Please upload a csv file!";
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message,""));
  }


  @GetMapping("/apimodels")
  public ResponseEntity<List<APIModel>> getAllApiModels() {
    try {
      List<APIModel> apiModels = fileService.getAllApiModels();

      if (apiModels.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }
      return new ResponseEntity<>(apiModels, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }

  }

  @GetMapping("/download/{fileName:.+}")
  public ResponseEntity<Resource> downloadFile(@PathVariable String fileName) {
    InputStreamResource file = new InputStreamResource(fileService.load());
    int extensionIndex = fileName.lastIndexOf(".");
    String fileNameWithoutExtension = fileName.substring(0,extensionIndex);
    return ResponseEntity.ok()
        .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename="+fileNameWithoutExtension+".txt")
        .contentType(MediaType.parseMediaType("application/csv"))
        .body(file);
  }

}

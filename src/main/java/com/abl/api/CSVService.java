package com.abl.api;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CSVService {
    @Autowired
    APIModelRepository repository;
    public void save(MultipartFile file) {
        try
        {
          List<APIModel> apimodels = CSVHelper.csvToAPIModels(file.getInputStream());
          repository.saveAll(apimodels);
        } catch (IOException e) {
          throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }
    public ByteArrayInputStream load() {
        List<APIModel> apimodels = repository.findAll();
    
        ByteArrayInputStream in = CSVHelper.apiModelToCSV(apimodels);
        return in;
    }
    public List<APIModel> getAllApiModels() {
        return repository.findAll();
    }
}

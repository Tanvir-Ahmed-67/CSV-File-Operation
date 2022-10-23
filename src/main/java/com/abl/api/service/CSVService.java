package com.abl.api.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.abl.api.model.ExchangeCodeMapperModel;
import com.abl.api.repository.APIModelRepository;
import com.abl.api.helper.CSVHelper;
import com.abl.api.model.APIModel;
import com.abl.api.repository.ExchangeCodeMapperModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CSVService {
    @Autowired
    APIModelRepository repository;
    @Autowired
    ExchangeCodeMapperModelRepository exchangeCodeMapperModelRepository;

    Map<String,String> exchangeCodeMappingForService= null;
    public Map<String,String> mapExchangeCode(){
        List<ExchangeCodeMapperModel> exchangeCodeMapperModels = loadExchangeCodeMapperModel();
        Map<String,String> exchangeCodeMapping = new HashMap<String,String>();
        for(ExchangeCodeMapperModel exchangeCodeMapperModel: exchangeCodeMapperModels){
            exchangeCodeMapping.put(exchangeCodeMapperModel.getNrta(),exchangeCodeMapperModel.getExCode());
        }
        return exchangeCodeMapping;
    }


    public void save(MultipartFile file) {
        try
        {
          List<APIModel> apimodels = CSVHelper.csvToAPIModels(file.getInputStream());
            exchangeCodeMappingForService = mapExchangeCode();
          for(APIModel apiModel: apimodels){
              apiModel.setExCode(exchangeCodeMappingForService.get(apiModel.getExCode()));
          }
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

    public List<ExchangeCodeMapperModel> loadExchangeCodeMapperModel() {
        return exchangeCodeMapperModelRepository.findAll();
    }
    public List<APIModel> getAllApiModels() {
        return repository.findAll();
    }
}

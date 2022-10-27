package com.api.service;

import com.api.helper.ApiDataServiceHelper;
import com.api.model.ApiDataModel;
import com.api.model.ExchangeCodeMapperModel;
import com.api.repository.ApiDataModelRepository;
import com.api.repository.ExchangeCodeMapperModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ApiDataService {
    @Autowired
    ApiDataModelRepository repository;
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
          List<ApiDataModel> apimodels = ApiDataServiceHelper.csvToAPIModels(file.getInputStream());
            exchangeCodeMappingForService = mapExchangeCode();
          for(ApiDataModel apiDataModel : apimodels){
              apiDataModel.setExCode(exchangeCodeMappingForService.get(apiDataModel.getExCode()).trim());
              if(apiDataModel.getExCode().equals("7119")){
                  apiDataModel.setEnteredDate(modifyDateFormat(apiDataModel.getEnteredDate()));
              }
          }
          repository.saveAll(apimodels);
        } catch (IOException e) {
          throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }
    public ByteArrayInputStream load() {
        List<ApiDataModel> apimodels = repository.findAll();
        ByteArrayInputStream in = ApiDataServiceHelper.apiModelToCSV(apimodels);
        return in;
    }

    public List<ExchangeCodeMapperModel> loadExchangeCodeMapperModel() {
        return exchangeCodeMapperModelRepository.findAll();
    }
    public List<ApiDataModel> getAllApiModels() {
        return repository.findAll();
    }

    public void clearDatabase(){
        truncateApi_data_table();
       // truncateHibernetSequenceTable();
        truncateSeqTable();
        initializeSeqTable();
    }
    public String modifyDateFormat(String date){
        DateFormat formatter;
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date1 = formatter.parse(date);
            formatter = new SimpleDateFormat("yyMMdd");
            date = formatter.format(date1);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        return date;
    }

    @Transactional
    public void truncateApi_data_table() {
        repository.truncateApiDataTable();
    }
    @Transactional
    public void truncateHibernetSequenceTable() {
        repository.truncateHibernateSequenceTable();
    }
    @Transactional
    public void truncateSeqTable() {
        repository.truncateSeqTable();
    }
    @Transactional
    public void initializeSeqTable() {
        repository.initializeSeqTable();
    }
}

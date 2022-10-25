package com.abl.api.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.abl.api.model.ExchangeCodeMapperModel;
import com.abl.api.repository.APIModelRepository;
import com.abl.api.helper.CSVHelper;
import com.abl.api.model.APIModel;
import com.abl.api.repository.ExchangeCodeMapperModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
              apiModel.setExCode(exchangeCodeMappingForService.get(apiModel.getExCode()).trim());
              if(apiModel.getExCode().equals("7119")){
                  apiModel.setEnteredDate(modifyDateFormat(apiModel.getEnteredDate()));
              }
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

    public void clearDatabase(){
        truncateApi_data_table();
        truncateHibernetSequenceTable();
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

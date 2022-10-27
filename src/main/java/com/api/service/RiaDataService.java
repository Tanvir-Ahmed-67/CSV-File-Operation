package com.api.service;

import com.api.helper.RiaDataServiceHelper;
import com.api.model.RiaDataModel;
import com.api.repository.RiaDataModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class RiaDataService {
    @Autowired
    RiaDataModelRepository repository;

    public void save(MultipartFile file) {
        try
        {
            List<RiaDataModel> riaDataModels = RiaDataServiceHelper.csvToRiaDataModels(file.getInputStream());
            repository.saveAll(riaDataModels);
        } catch (IOException e) {
            throw new RuntimeException("fail to store csv data: " + e.getMessage());
        }
    }
    public ByteArrayInputStream load() {
        List<RiaDataModel> riaDataModelList = repository.findAll();
        ByteArrayInputStream in = RiaDataServiceHelper.riaDataModelsToCSV(riaDataModelList);
        return in;
    }

    public void clearDatabase(){
        truncateRiaDataTable();
        truncateHibernetSequenceTable();
        truncateRiaSeqTable();
        initializeRiaSeqTable();
    }

    public List<RiaDataModel> getAllRiaDataModels() {
        return repository.findAll();
    }

    @Transactional
    public void truncateRiaDataTable() {
        repository.truncateRiaDataTable();
    }
    @Transactional
    public void truncateHibernetSequenceTable() {
        repository.truncateHibernateSequenceTable();
    }
    @Transactional
    public void truncateRiaSeqTable() {
        repository.truncateRiaSeqTable();
    }
    @Transactional
    public void initializeRiaSeqTable() {
        repository.initializeRiaSeqTable();
    }
}

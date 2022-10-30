package com.api.repository;

import com.api.model.ApiDataModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ApiDataModelRepository extends JpaRepository<ApiDataModel, Integer>{
    @Modifying
    @Transactional
    @Query(
            value = "truncate table api_data_table",
            nativeQuery = true
    )
    void truncateApiDataTable();
    @Modifying
    @Transactional
    @Query(
            value = "truncate table hibernate_sequence",
            nativeQuery = true
    )
    void truncateHibernateSequenceTable();
    @Modifying
    @Transactional
    @Query(
            value = "truncate table seq",
            nativeQuery = true
    )
    void truncateSeqTable();
    @Modifying
    @Transactional
    @Query(
            value = "insert into seq values('1')",
            nativeQuery = true
    )
    void initializeSeqTable();
}

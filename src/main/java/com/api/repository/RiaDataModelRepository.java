package com.api.repository;

import com.api.model.RiaDataModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RiaDataModelRepository extends JpaRepository<RiaDataModel, Integer> {
    @Modifying
    @Transactional
    @Query(
            value = "truncate table ria_data_table",
            nativeQuery = true
    )
    void truncateRiaDataTable();
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
            value = "truncate table ria_seq",
            nativeQuery = true
    )
    void truncateRiaSeqTable();
    @Modifying
    @Transactional
    @Query(
            value = "insert into ria_seq values('1')",
            nativeQuery = true
    )
    void initializeRiaSeqTable();
}

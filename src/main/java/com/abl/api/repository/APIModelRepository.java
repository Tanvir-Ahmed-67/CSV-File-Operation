package com.abl.api.repository;

import com.abl.api.model.APIModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface APIModelRepository extends JpaRepository<APIModel, Integer>{
    
}

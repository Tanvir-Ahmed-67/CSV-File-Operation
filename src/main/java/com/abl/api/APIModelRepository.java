package com.abl.api;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface APIModelRepository extends JpaRepository<APIModel, Integer>{
    
}

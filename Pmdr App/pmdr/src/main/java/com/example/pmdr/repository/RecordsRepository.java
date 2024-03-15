package com.example.pmdr.repository;

import com.example.pmdr.model.Records;

import java.util.List;
import java.time.OffsetDateTime;

import org.springframework.data.repository.CrudRepository;

public interface RecordsRepository extends CrudRepository<Records, Long>{
    List<Records> findByTask(String task);
    List<Records> findByStartTime(OffsetDateTime startTime); 
    List<Records> findByEndTime(OffsetDateTime endTime);
    List<Records> findByTaskAndStartTime(String task, OffsetDateTime startTime);
    List<Records> findByTaskAndEndTime(String task, OffsetDateTime endTime);
    List<Records> findByTaskAndStartTimeAndEndTime(String task, OffsetDateTime startTime, OffsetDateTime endTime);
    List<Records> findByStartTimeAndEndTime(OffsetDateTime startTime, OffsetDateTime endTime);
    List<Records> findByStartTimeGreaterThanEqual(OffsetDateTime startTime); 
    List<Records> findByEndTimeLessThanEqual(OffsetDateTime endTime);
    
}

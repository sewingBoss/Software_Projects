package com.example.pmdr.controller;

import com.example.pmdr.model.Records;
import com.example.pmdr.repository.RecordsRepository;

import java.util.List;
import java.time.OffsetDateTime;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/records")
public class RecordsController {
    @Autowired
    public RecordsRepository recordsRepository;
    public RecordsController(RecordsRepository recordsRepository){
        this.recordsRepository = recordsRepository;
    }

    //save session
    /*There is no data validation for duplicate times since people may multitask */
    @PutMapping
    public Records saveSession(@RequestBody Records body) {
        Records savedRecord = this.recordsRepository.save(body);
        return savedRecord;
    }
    
    //get from id
    @GetMapping("/{id}")
    public ResponseEntity<Records> getFromId(@PathVariable Long id) {
        Records record = this.recordsRepository.findById(id).orElse(null);
        if (record != null) {
            return new ResponseEntity<>(record, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    //get all records from task
    @GetMapping("/task")
    public List<Records> getAllFormTopic(@RequestParam String task) {
        List<Records> records = this.recordsRepository.findByTask(task);
        return records;
    }
    
    //get all records since start time
    @GetMapping("/since")
    public List<Records> getAllSince(@RequestParam 
                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) 
                                    /*Needs to be in the format 2000-10-31T01:30:00.000-05:00 */
                                     OffsetDateTime startTime) {
    
    List<Records> records = this.recordsRepository.findByStartTimeGreaterThanEqual(startTime);
    return records;
    }

    //get all records before end time
    @GetMapping("/before")
    public List<Records> getAllBefore(@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime endTime) {
        List<Records> records = this.recordsRepository.findByEndTimeLessThanEqual(endTime);
        return records;
    }

    //get all records based on search input
    @GetMapping("/search")
    public ResponseEntity<List<Records>> getAllSearch(@RequestParam(required = false) String task,
                                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime startTime,
                                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime endTime,
                                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime sinceTime,
                                     @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) OffsetDateTime beforeTime) {
        List<Records> records = null;
        if (task != null && startTime != null && endTime != null) {
            records = this.recordsRepository.findByTaskAndStartTimeAndEndTime(task, startTime, endTime);
            return new ResponseEntity<>(records, HttpStatus.OK);
        } else if (task != null && startTime != null) {
            records = this.recordsRepository.findByTaskAndStartTime(task, startTime);
            return new ResponseEntity<>(records, HttpStatus.OK);
        } else if (task != null && endTime != null) {
            records = this.recordsRepository.findByTaskAndEndTime(task, endTime);
            return new ResponseEntity<>(records, HttpStatus.OK);
        } else if (startTime != null && endTime != null) {
            records = this.recordsRepository.findByStartTimeAndEndTime(startTime, endTime);
            return new ResponseEntity<>(records, HttpStatus.OK);
        } else if (task != null) {
            records = this.recordsRepository.findByTask(task);
            return new ResponseEntity<>(records, HttpStatus.OK);
        } else if (startTime != null) {
            records = this.recordsRepository.findByStartTime(startTime);
            return new ResponseEntity<>(records, HttpStatus.OK);
        } else if (endTime != null) {
            records = this.recordsRepository.findByEndTime(endTime);
            return new ResponseEntity<>(records, HttpStatus.OK);
        } else if (sinceTime != null) {
            records = this.recordsRepository.findByStartTimeGreaterThanEqual(sinceTime);
            return new ResponseEntity<>(records, HttpStatus.OK);
        } else if (beforeTime != null) {
            records = this.recordsRepository.findByEndTimeLessThanEqual(beforeTime);
            return new ResponseEntity<>(records, HttpStatus.OK);
        } 
        
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } 

    }

    //correct records
    @PutMapping("/fix/{id}")
    public ResponseEntity<Records> correctRecord(@PathVariable Long id, @RequestBody Records body) {
        Records record = this.recordsRepository.findById(id).orElse(null);
        if (record != null) {
            record.setTask(body.getTask());
            record.setStartTime(body.getStartTime());
            record.setEndTime(body.getEndTime());
            Records savedRecord = this.recordsRepository.save(record);
            return new ResponseEntity<>(savedRecord, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //delete records
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Records> deleteRecord(@PathVariable Long id) {
        Records record = this.recordsRepository.findById(id).orElse(null);
        if (record != null) {
            this.recordsRepository.deleteById(id);
            return new ResponseEntity<>(record, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}

package com.example.pmdr.controller;

import com.example.pmdr.model.Records;
import com.example.pmdr.repository.RecordsRepository;
import com.example.pmdr.util.DateUtils;

import java.util.List;
import java.time.OffsetDateTime;
import java.time.format.DateTimeParseException;

import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<?> getAllSince(@RequestParam String startTime) {
    OffsetDateTime dateTime;
    try {
        dateTime = DateUtils.parseDate(startTime);
    } catch(IllegalArgumentException e) {
        return new ResponseEntity<>("Invalid date format", HttpStatus.BAD_REQUEST);
    }
    List<Records> records = this.recordsRepository.findByStartTimeGreaterThanEqual(dateTime);
    if(records.isEmpty()){
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    } else {
        return new ResponseEntity<>(records, HttpStatus.OK);
        }
   
    }
    

    //get all records before end time
    @GetMapping("/before")
    public ResponseEntity<?> getAllBefore(@RequestParam String endTime) {
        OffsetDateTime dateTime;
        try {
            dateTime = DateUtils.parseDate(endTime);
        } catch(DateTimeParseException e) {
            return new ResponseEntity<>("Invalid date format", HttpStatus.BAD_REQUEST);
        }
        List<Records> records = this.recordsRepository.findByEndTimeLessThanEqual(dateTime);
        if(records.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            return new ResponseEntity<>(records, HttpStatus.OK);
            }
       
        }
    

    //get all records based on search input
    @GetMapping("/search")
    public ResponseEntity<?> getAllSearch(@RequestParam(required = false) String task,
                                     @RequestParam(required = false) String startTimeStr,
                                     @RequestParam(required = false) String endTimeStr,
                                     @RequestParam(required = false) String sinceTimeStr,
                                     @RequestParam(required = false) String beforeTimeStr) {
        try {
            OffsetDateTime startTime = startTimeStr != null ? DateUtils.parseDate(startTimeStr) : null;
            OffsetDateTime endTime = endTimeStr != null ? DateUtils.parseDate(endTimeStr) : null;
            OffsetDateTime sinceTime = sinceTimeStr != null ? DateUtils.parseDate(sinceTimeStr) : null;
            OffsetDateTime beforeTime = beforeTimeStr != null ? DateUtils.parseDate(beforeTimeStr) : null;
        
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
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
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

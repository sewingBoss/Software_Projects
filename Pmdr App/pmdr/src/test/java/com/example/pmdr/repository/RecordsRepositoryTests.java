package com.example.pmdr.repository;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;


import org.assertj.core.api.Assertions;



import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import com.example.pmdr.model.Records;

@DataJpaTest // This allowes Spring to recognise and run this test
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2) // This tells it to use H2 and H2 will configure the rest for us. We will change this later to use Google Cloud or smth
@TestInstance(Lifecycle.PER_CLASS) // JUnit will create one instance for all tests instead of one each. We will use this because none of our methods alter the data.
public class RecordsRepositoryTests {
    
    @Autowired // This makes our repository class easy to use and initialise
    RecordsRepository recordsRepository;

    //CORRECTION??=====We probably don't need to delete and resave them everytime since all we ever do here is retrieve and not modify
    private Records record1;
    private Records record2;
    private Records record3;
    private Records record4;
    private Records record5;
    private Records record6;
    private Records record7;

    @BeforeAll //I'm using this because none of the repository methods are data altering and only retrieve data
    public void setUp(){
    recordsRepository.deleteAll();    

    record1 = Records.builder()
        .task("Battle of Azukizaka")
        .startTime(OffsetDateTime.parse("1542-09-19T10:00+09:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME))
        .endTime(OffsetDateTime.parse("1542-09-19T12:00+09:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME))
        .build();

    record2 = Records.builder()
        .task("Battle of Azukizaka")
        .startTime(OffsetDateTime.parse("1564-02-15T10:00+09:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME))
        .endTime(OffsetDateTime.parse("1564-02-15T12:00+09:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME))
        .build();

    record3 = Records.builder()
        .task("Battle of Kawanakajima")
        .startTime(OffsetDateTime.parse("1553-09-04T10:00+09:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME))
        .endTime(OffsetDateTime.parse("1553-09-04T12:00+09:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME))
        .build();
    
    record4 = Records.builder()
        .task("Battle of Kawanakajima")
        .startTime(OffsetDateTime.parse("1555-09-04T17:00+09:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME))
        .endTime(OffsetDateTime.parse("1555-09-04T20:00+09:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME))
        .build();

    record5 = Records.builder()
        .task("Battle of Kawanakajima")
        .startTime(OffsetDateTime.parse("1557-09-04T21:00+09:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME))
        .endTime(OffsetDateTime.parse("1557-09-04T23:00+09:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME))
        .build();
    
    record6 = Records.builder()
        .task("Fernão Mendes Pinto arrival in Tanegashima")
        .startTime(OffsetDateTime.parse("1542-09-19T10:00+09:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME))
        .endTime(OffsetDateTime.parse("1542-09-19T10:35+09:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME))
        .build();

    record7 = Records.builder()
        .task("Fernão Mendes Pinto arrival in Tanegashima")
        .startTime(OffsetDateTime.parse("1542-09-19T10:00+09:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME))
        .endTime(OffsetDateTime.parse("1542-09-19T10:45+09:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME))
        .build();
    
    recordsRepository.save(record1);
    recordsRepository.save(record2);
    recordsRepository.save(record3);
    recordsRepository.save(record4);
    recordsRepository.save(record5);
    recordsRepository.save(record6);
    recordsRepository.save(record7);
    }
    @Test
    /*  NOTE: the test method name should be in a similar format as below
        NOTE: the test method should never return anything */

    /*  NOTE: the test method should be in the following format, but the `setUp` method will complete most of the Arrange & Act steps
            Arrange
            Act
            Assert */

        
    public void RecordsRepository_save_ReturnsListRecords (){
        //Arrange
            //`setUp()`
        //Act
            //`setUp()`
        List<Records> foundRecords = new ArrayList<>();
        recordsRepository.findAll().forEach(foundRecords::add);
        //Arrange
        for(Records foundRecord: foundRecords){
            Assertions.assertThat(foundRecord).isNotNull();
            Assertions.assertThat(foundRecord.getId()).isGreaterThan(0);
        }
    }

    @Test
    public void RecordsRepository_findByTask_ReturnsListRecords (){
        //Arrange
            //`setUp()`
        //Act
            //`setUp()`
            List<Records> foundRecords = recordsRepository
                                    .findByTask("Battle of Azukizaka");

        //Assert
            Assertions.assertThat(foundRecords)
                        .isNotNull()
                        .contains(record1, record2);
    }

    @Test
    public void RecordsRepository_findByStartTime_ReturnsListRecirds (){
        //Arrange
            //`setUp()`
        //Act
            //`setUp()`
            List<Records> foundRecords = recordsRepository
                                    .findByStartTime(OffsetDateTime.parse("1542-09-19T10:00+09:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        //Assert
            Assertions.assertThat(foundRecords)
                        .isNotNull()
                        .contains(record1, record6); //This is the AssertJ version of `contains`, not the List version. I had to 
    }

    @Test
    public void RecordsRepository_findByEndTime_ReturnsListRecords (){
        //Arrange
            //`setUp()`
        //Act
            //`setUp()`
            List<Records> foundRecords = recordsRepository
                                    .findByEndTime(OffsetDateTime.parse("1542-09-19T12:00+09:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        //Assert
            Assertions.assertThat(foundRecords)
                        .isNotNull()
                        .contains(record1);
    }

    @Test
    public void RecordsRepository_findByTaskAndStartTime_ReturnsListRecords (){
        //Arrange
            //`setUp()`
        //Act
            //`setUp()`
            List<Records> foundRecords = recordsRepository
                                    .findByTaskAndStartTime("Battle of Azukizaka", OffsetDateTime.parse("1542-09-19T10:00+09:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        //Assert
            Assertions.assertThat(foundRecords)
                        .isNotNull()
                        .contains(record1);
    }

    @Test
    public void RecordsRepository_findByTaskAndEndTime_ReturnsListRecords (){
        //Arrange
            //`setUp()`
        //Act
            //`setUp()`
            List<Records> foundRecords = recordsRepository
                                    .findByTaskAndEndTime("Battle of Azukizaka", OffsetDateTime.parse("1564-02-15T12:00+09:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        //Assert
            Assertions.assertThat(foundRecords)
                        .isNotNull()
                        .contains(record2);
    }
    @Test
    public void RecordsRepository_findByTaskAndStartTimeAndEndTime_ReturnsListRecords (){
        //Arrange
            //`setUp()`
        //Act
            //`setUp()`
            List<Records> foundRecords = recordsRepository
                                    .findByTaskAndStartTimeAndEndTime("Fernão Mendes Pinto arrival in Tanegashima", OffsetDateTime.parse("1542-09-19T10:00+09:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME), OffsetDateTime.parse("1542-09-19T10:45+09:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        //Assert
            Assertions.assertThat(foundRecords)
                        .isNotNull()
                        .contains(record7);
    }

    @Test
    public void RecordsRepository_findByStartTimeAndEndTime_ReturnsListRecords (){
        //Arrange
            //`setUp()`
        //Act
            //`setUp()`
            List<Records> foundRecords = recordsRepository
                                    .findByStartTimeAndEndTime(OffsetDateTime.parse("1542-09-19T10:00+09:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME), OffsetDateTime.parse("1542-09-19T10:45+09:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        //Assert
            Assertions.assertThat(foundRecords)
                        .isNotNull()
                        .contains(record7);
    }

    @Test
    public void RecordsRepository_findByStartTimeGreaterThanEqual_ReturnsListRecords (){
        //Arrange
            //`setUp()`
        //Act
            //`setUp()`
            List<Records> foundRecords = recordsRepository
                                    .findByStartTimeGreaterThanEqual(OffsetDateTime.parse("1542-09-19T10:00+09:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        //Assert
            Assertions.assertThat(foundRecords)
                        .isNotNull()
                        .contains(record2, record3, record4, record5);
    }

    @Test
    public void RecordsRepository_findByEndTimeLessThanEqual_ReturnsListRecords (){
        //Arrange
            //`setUp()`
        //Act
            //`setUp()`
            List<Records> foundRecords = recordsRepository
                                    .findByEndTimeLessThanEqual(OffsetDateTime.parse("1564-02-14T12:00+09:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        //Assert
            Assertions.assertThat(foundRecords)
                        .isNotNull()
                        .contains(record1, record3, record4, record5, record6, record7);
    }

    @Test
    public void RecordsRepository_findByTaskAndStartTimeAndEndTime_ReturnsEmptyList (){
        //Arrange
            //`setUp()`
        //Act
            //`setUp()`
            List<Records> foundRecords = recordsRepository
                                    .findByTaskAndStartTimeAndEndTime("Fernão Mendes Pinto arrival in Tanegashima", OffsetDateTime.parse("1542-09-19T10:00+09:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME), OffsetDateTime.parse("1542-09-19T10:35+09:00", DateTimeFormatter.ISO_OFFSET_DATE_TIME));

        //Assert
            Assertions.assertThat(foundRecords)
                        .contains(record6);
    }
}

//NOTE that if there are any tests that make sense to be tested as one, then use `assertAll()`

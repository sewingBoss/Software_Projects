// package com.example.pmdr;

// //import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.boot.test.mock.mockito.MockBean;
// import org.springframework.test.web.servlet.MockMvc;

// import com.example.pmdr.model.Records;

// import net.bytebuddy.implementation.bind.annotation.IgnoreForBinding;

// import com.example.pmdr.controller.RecordsController;

// import static org.mockito.Mockito.when;

// @SpringBootTest
// @AutoConfigureMockMvc
// public class HttpRequestsTest {

//     //@BeforeEach
//     /*once necessary move the record here...? */

//     @Autowired
//     private MockMvc mockMvc;

//     @MockBean
//     private RecordsController recordsController;


// @Ignore
//     @Test
//     public void testSaveSession() throws Exception {
//         Records record = new Records();
//         Records savedRecord = this.recordsController.saveSession(record);

//         when(recordsController.saveSession(record)).thenReturn(savedRecord);
        
//     }
//     /*  
//     THIS IS THE CONTROLLER CLASS
//     @PutMapping
//     public Records saveSession(@RequestBody Records body) {
//         Records savedRecord = this.recordsRepository.save(body);
//         return savedRecord;
//     } 
//     THIS IS THE ENTITY CLASS
//     @Id
//     @GeneratedValue(strategy = GenerationType.IDENTITY)
//     private Long id;
//     private String task;
//     private OffsetDateTime startTime;
//     private OffsetDateTime endTime;
    
//     */
// }
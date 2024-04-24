package com.example.pmdr.contoller;

import com.example.pmdr.controller.RecordsController;

import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(controllers = RecordsController.class)
public class RecordsControllerTests {

    @Autowired
    MockMvc mockMvc;

}

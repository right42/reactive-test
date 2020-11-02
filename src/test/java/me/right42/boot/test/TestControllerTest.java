package me.right42.boot.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest
class TestControllerTest {

    @Autowired
    MockMvc mockMvc;

    // test fail
    @Test
    void multiThreadTest() throws Exception {

        ExecutorService executorService = Executors.newFixedThreadPool(10);

        for (int i = 0; i < 10; i++){
            executorService.submit(() -> {
                try {
                    System.out.println(Thread.currentThread().getName() + " request");
                    mockMvc.perform(
                        get("/add-count")
                    );
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }

        mockMvc.perform(get("/counts"))
            .andExpect(jsonPath("$.body").value(10))
        ;
    }

}
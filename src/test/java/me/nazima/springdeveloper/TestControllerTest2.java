package me.nazima.springdeveloper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class TestControllerTest2 {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GET /test2 요청 시 Hello World 반환")
    void getTestAPI() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders.post("/test2"))
                .andExpect(status().isCreated())   // 201 (post 요청)
                .andExpect(content().string("Hello World"))
                .andDo(print());
    }
}
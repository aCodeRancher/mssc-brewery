package guru.springframework.msscbrewery.web.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@RunWith(SpringRunner.class)
@AutoConfigureMockMvc
public class ValidationTest {

    @Autowired
    MockMvc mockMvc;
    @Test
    public void handleValidationTest() throws Exception {
        mockMvc.perform(get("/api/v1/customer/hi/"+ "aaa"))
                .andExpect(status().isBadRequest());
    }
}

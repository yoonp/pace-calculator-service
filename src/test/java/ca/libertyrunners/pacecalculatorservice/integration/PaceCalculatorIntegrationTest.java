package ca.libertyrunners.pacecalculatorservice.integration;

import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class PaceCalculatorIntegrationTest {

    private static final String GET_CALCULATE_PACE_ENDPOINT = "/calculate-pace";

    @Autowired
    private MockMvc mockMvc;

    @Test
    void test_calculate_pace_ok() throws Exception {

        val response = mockMvc.perform(get(GET_CALCULATE_PACE_ENDPOINT)
                .param("distance", "10")
                .param("hour", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("distance").value(10.0))
                .andExpect(jsonPath("time").value("PT1H"))
                .andExpect(jsonPath("pace").value("PT6M"))
                .andReturn().getResponse();

        assertNotNull(response);
    }

}

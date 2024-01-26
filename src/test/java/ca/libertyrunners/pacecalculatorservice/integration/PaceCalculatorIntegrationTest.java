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
class PaceCalculatorIntegrationTest {

    //initialize endpoint
    private static final String GET_CALCULATE_PACE_ENDPOINT = "/calculate-pace";

    //create mock MVN(model, view, controller)
    @Autowired
    private MockMvc mockMvc;

    @Test
    void test_calculate_pace_ok() throws Exception {

        val response = mockMvc.perform(get(GET_CALCULATE_PACE_ENDPOINT)
                        .param("distance", "10")
                        .param("hour", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("distance").value("10.0 km"))
                .andExpect(jsonPath("time").value("1 : 00 : 00"))
                .andExpect(jsonPath("pace").value("6 : 00 /km"))
                .andReturn().getResponse();

        assertNotNull(response);
    }

    @Test
    void test_calculate_pace_invalid_one_input() throws Exception {
        val response = mockMvc.perform(get(GET_CALCULATE_PACE_ENDPOINT)
                        .param("distance", "10"))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();
    }

    @Test
    void test_calculate_pace_invalid_three_inputs() throws Exception {
        val response = mockMvc.perform(get(GET_CALCULATE_PACE_ENDPOINT)
                        .param("distance", "10")
                        .param("hour", "1")
                        .param("minute", "30")
                        .param("second", "15")
                        .param("paceMinute", "5")
                        .param("paceSecond", "30"))
                .andExpect(status().isBadRequest())
                .andReturn().getResponse();
    }


}

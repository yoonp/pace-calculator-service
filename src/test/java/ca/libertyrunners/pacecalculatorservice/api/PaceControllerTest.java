package ca.libertyrunners.pacecalculatorservice.api;

import ca.libertyrunners.pacecalculatorservice.core.PaceCalculatorRequest;
import ca.libertyrunners.pacecalculatorservice.core.PaceCalculatorResponse;
import ca.libertyrunners.pacecalculatorservice.core.PaceCalculatorService;
import lombok.val;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PaceController.class)
class PaceControllerTest {

    private static final String GET_CALCULATE_PACE = "/calculate-pace?distance=%s&hour=%s&minute=%s&second=%s&paceMinute=%s&paceSecond=%s";

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PaceCalculatorService paceCalculatorService;

    @Test
    void testCalculatePace() throws Exception {

        val distance = 42.2;
        val hour = 3;
        val minute = 28;
        val second = 37;
        val paceMinute = 4;
        val paceSecond = 58;

        PaceCalculatorRequest request = PaceCalculatorRequest.builder().distance(distance)
                .hour(hour).minute(minute).second(second).paceMinute(paceMinute).paceSecond(paceSecond).build();

        Duration time = Duration.ofHours(request.getHour()).plusMinutes(request.getMinute()).plusSeconds(request.getSecond());
        Duration pace = Duration.ofHours(request.getPaceMinute()).plusSeconds(request.getPaceSecond());
        PaceCalculatorResponse response = PaceCalculatorResponse.builder().distance(request.getDistance() + " km").time(hour + " : " + minute + " : " + second).pace(paceMinute + " : " + paceSecond + " /km").build();

        when(paceCalculatorService.calculatePace(request)).thenReturn(response);

        val uri = String.format(GET_CALCULATE_PACE, distance, hour, minute, second, paceMinute, paceSecond);
        mockMvc.perform(get(uri))
                .andExpect(status().isOk());

        verify(paceCalculatorService, times(1)).calculatePace(request);
    }
}

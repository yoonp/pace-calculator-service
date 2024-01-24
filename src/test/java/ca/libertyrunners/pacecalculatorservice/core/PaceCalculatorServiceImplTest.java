package ca.libertyrunners.pacecalculatorservice.core;


import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PaceCalculatorServiceImplTest {

    @Mock
    private PaceCalculatorService paceCalculatorService;

    @Mock
    private PaceCalculatorValidator paceCalculatorValidator;

    @BeforeEach
    void setup() {
        paceCalculatorService = new PaceCalculatorServiceImpl(paceCalculatorValidator);
    }

    @Test
    void testCalculatePace() {
        val hour = 0;
        val minute = 50;
        val second = 0;
        val distance = 10D;

        val request = PaceCalculatorRequest.builder().hour(hour).minute(minute).second(second).distance(distance).build();
        val result = paceCalculatorService.calculatePace(request);

        assertEquals("5 : 00 /km", result.getPace());
    }

    @Test
    void testCalculateTime() {
        val distance = 10D;
        val paceMinute = 5;

        val request = PaceCalculatorRequest.builder().paceMinute(paceMinute).distance(distance).build();
        val result = paceCalculatorService.calculatePace(request);

        assertEquals("50 : 00", result.getTime());
    }

    @Test
    void testCalculateDistance() {
        val paceMinute = 5;
        val hour = 1;

        val request = PaceCalculatorRequest.builder().paceMinute(paceMinute).hour(hour).build();
        val result = paceCalculatorService.calculatePace(request);

        assertEquals("12.0 km", result.getDistance());
    }
}

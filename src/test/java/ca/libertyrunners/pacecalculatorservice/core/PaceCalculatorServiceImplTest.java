package ca.libertyrunners.pacecalculatorservice.core;


import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ca.libertyrunners.pacecalculatorservice.core.PaceCalculatorRequest;
import ca.libertyrunners.pacecalculatorservice.core.PaceCalculatorService;
import ca.libertyrunners.pacecalculatorservice.core.PaceCalculatorServiceImpl;
import ca.libertyrunners.pacecalculatorservice.core.PaceCalculatorValidator;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class PaceCalculatorServiceImplTest {

    @Mock
    private PaceCalculatorService paceCalculatorService;

    @Mock
    private PaceCalculatorValidator paceCalculatorValidator;

    @BeforeEach
    void setup(){
        paceCalculatorService = new PaceCalculatorServiceImpl(paceCalculatorValidator);
    }

    @Test
    void testCalculatePace(){
        val hour = 0;
        val minute = 50;
        val second = 0;
        val distance = 10D;

        val request = PaceCalculatorRequest.builder().hour(hour).minute(minute).second(second).distance(distance).build();
        val result = paceCalculatorService.calculatePace(request);

        assertEquals(5, result.getPace().toMinutes());
    }

    @Test
    void testCalculateTime(){
        val distance = 10D;
        val paceMinute = 5;

        val request = PaceCalculatorRequest.builder().paceMinute(paceMinute).distance(distance).build();
        val result = paceCalculatorService.calculatePace(request);

        assertEquals(50, result.getTime().toMinutes());
    }

    @Test
    void testCalculateDistance(){
        val paceMinute = 5;
        val distance = 10D;

        val request = PaceCalculatorRequest.builder().paceMinute(5).distance(distance).build();
        val result = paceCalculatorService.calculatePace(request);

        assertEquals(10D, result.getDistance());
    }
}

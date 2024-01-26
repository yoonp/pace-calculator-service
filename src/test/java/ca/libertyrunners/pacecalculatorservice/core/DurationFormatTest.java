package ca.libertyrunners.pacecalculatorservice.core;


import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class DurationFormatTest {

    @Mock
    private PaceCalculatorService paceCalculatorService;

    @Mock
    private PaceCalculatorValidator paceCalculatorValidator;

    @BeforeEach
    void setup() {
        paceCalculatorService = new PaceCalculatorServiceImpl(paceCalculatorValidator);
    }

    @Test
    void testFormat_1() {
        val hour = 1;
        val minute = 0;
        val second = 0;
        val distance = 10D;

        val request = PaceCalculatorRequest.builder().hour(hour).minute(minute).second(second).distance(distance).build();
        val result = paceCalculatorService.calculatePace(request);

        assertEquals("1 : 00 : 00", result.getTime());
        assertEquals("6 : 00 /km", result.getPace());
    }

    @Test
    void testFormat_2() {
        val paceMinute = 5;
        val distance = 1000D;

        val request = PaceCalculatorRequest.builder().paceMinute(paceMinute).distance(distance).build();
        val result = paceCalculatorService.calculatePace(request);

        assertEquals("3 days 11 : 20 : 00", result.getTime());
        assertEquals("5 : 00 /km", result.getPace());
    }

    @Test
    void testFormat_3() {
        val paceMinute = 5;
        val paceSecond = 14;
        val distance = 13D;

        val request = PaceCalculatorRequest.builder().paceMinute(paceMinute).paceSecond(paceSecond).distance(distance).build();
        val result = paceCalculatorService.calculatePace(request);

        assertEquals("1 : 08 : 02", result.getTime());
        assertEquals("5 : 14 /km", result.getPace());
    }

    @Test
    void testFormat_4() {
        val paceMinute = 3;
        val paceSecond = 1;
        val distance = 2D;

        val request = PaceCalculatorRequest.builder().paceMinute(paceMinute).paceSecond(paceSecond).distance(distance).build();
        val result = paceCalculatorService.calculatePace(request);

        assertEquals("6 : 02", result.getTime());
        assertEquals("3 : 01 /km", result.getPace());
    }

    @Test
    void testFormat_5() {
        val paceMinute = 5;
        val distance = 0.1D;

        val request = PaceCalculatorRequest.builder().paceMinute(paceMinute).distance(distance).build();
        val result = paceCalculatorService.calculatePace(request);

        assertEquals("30 s", result.getTime());
        assertEquals("5 : 00 /km", result.getPace());
    }

    @Test
    void testFormat_6() {
        val paceSecond = 59;
        val distance = 0.1D;

        val request = PaceCalculatorRequest.builder().paceSecond(paceSecond).distance(distance).build();
        val result = paceCalculatorService.calculatePace(request);

        assertEquals("5 s", result.getTime());
        assertEquals("59 s/km", result.getPace());
    }

    @Test
    void testFormat_7() {
        val hour = 9999;
        val distance = 2D;

        val request = PaceCalculatorRequest.builder().hour(hour).distance(distance).build();
        val result = paceCalculatorService.calculatePace(request);

        assertEquals("416 days 15 : 00 : 00", result.getTime());
        assertEquals("208 days 7 : 30 : 00 /km", result.getPace());
    }

}

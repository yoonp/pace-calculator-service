package ca.libertyrunners.pacecalculatorservice.core;


import ca.libertyrunners.pacecalculatorservice.core.error.ErrorMessage;
import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class PaceCalculatorValidatorImplTest {

    @Autowired
    PaceCalculatorValidatorImpl validator;

    @BeforeEach
    void setup() {
        validator = new PaceCalculatorValidatorImpl();
    }

    @Test
    void testValidate_successful() {
        Double distance = 0.0;
        Duration time = Duration.ofHours(3).plusMinutes(28).plusSeconds(37);
        Duration pace = Duration.ofMinutes(4).plusSeconds(58);

        val result = validator.validate(distance, time, pace);

        assertEquals(null, result);
    }

    @Test
    void testValidateInputCount_unsuccessful() {
        Double distance = 42.2;
        Duration time = Duration.ofHours(3).plusMinutes(28).plusSeconds(37);
        Duration pace = Duration.ofMinutes(4).plusSeconds(58);

        val result = validator.validate(distance, time, pace);

        assertEquals(ErrorMessage.ERROR_INVALID_REQUEST, result);
    }

    @Test
    void testValidateInputNumber_unsuccessful() {
        Double distance = -42.2;
        Duration time = Duration.ofHours(-3).plusMinutes(28).plusSeconds(37);
        Duration pace = Duration.ofMinutes(4).plusSeconds(58);

        val result = validator.validate(distance, time, pace);

        assertEquals(ErrorMessage.ERROR_INVALID_REQUEST, result);
    }


}

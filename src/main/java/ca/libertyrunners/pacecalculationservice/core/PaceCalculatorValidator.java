package ca.libertyrunners.pacecalculationservice.core;

import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.List;

public interface PaceCalculatorValidator {

    String validate(Double distance, Duration time, Duration Pace);
}

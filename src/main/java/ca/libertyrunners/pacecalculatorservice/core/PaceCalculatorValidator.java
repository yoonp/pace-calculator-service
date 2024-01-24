package ca.libertyrunners.pacecalculatorservice.core;

import java.time.Duration;

public interface PaceCalculatorValidator {

    String validate(Double distance, Duration time, Duration pace);
}

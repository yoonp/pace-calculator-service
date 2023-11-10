package ca.libertyrunners.pacecalculationservice.core;

import lombok.Builder;
import lombok.Data;

import java.time.Duration;

@Data
@Builder
public class PaceCalculatorResponse {

    Double distance;

    Duration time;

    Duration pace;

    String error;

}

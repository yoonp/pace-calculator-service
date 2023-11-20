package ca.libertyrunners.pacecalculatorservice.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.Duration;

@Data
@Builder
public class PaceCalculatorResponse {

    Double distance;

    Duration time;

    Duration pace;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String error;

}

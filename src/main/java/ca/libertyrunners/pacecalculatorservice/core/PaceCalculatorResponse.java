package ca.libertyrunners.pacecalculatorservice.core;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PaceCalculatorResponse {

    String distance;

    String time;

    String pace;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    String error;

}

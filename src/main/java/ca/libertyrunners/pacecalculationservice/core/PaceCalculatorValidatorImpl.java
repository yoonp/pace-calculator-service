package ca.libertyrunners.pacecalculationservice.core;

import org.springframework.stereotype.Component;
import ca.libertyrunners.pacecalculationservice.core.error.ErrorMessage;

import java.time.Duration;

@Component
public class PaceCalculatorValidatorImpl implements PaceCalculatorValidator {

    public String validate(Double distance, Duration time, Duration pace) {

        if(validateInputCount(distance, time, pace)){
            return ErrorMessage.ERROR_INVALID_REQUEST;
        }
        if(validateInputFields(distance, time, pace)){
            return ErrorMessage.ERROR_INVALID_NUMBER;
        }

        return null;
    }

    private boolean validateInputCount(Double distance, Duration time, Duration pace){

        int count = 0;
        if(distance != null && distance != 0){
            count++;
        }
        if(!time.isZero()){
            count++;
        }
        if(!pace.isZero()){
            count++;
        }
        if(count !=2){
            return true;
        }
        return false;
    }

    private boolean validateInputFields(Double distance, Duration time, Duration pace){

        int count = 0;
        if(distance < 0 || time.isNegative() || pace.isNegative()){
            return true;
        }

        return false;
    }

}
package ca.libertyrunners.pacecalculatorservice.core;

import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.Duration;

@AllArgsConstructor
@Service
public class PaceCalculatorServiceImpl implements PaceCalculatorService{

    private final PaceCalculatorValidator validator;

    public PaceCalculatorResponse calculatePace(PaceCalculatorRequest request) {

        Double distance = request.getDistance() == null ? 0D : request.getDistance();
        Duration time = setupTime(request);
        Duration pace = setupPace(request);

        val error = validator.validate(distance, time, pace);
        val response = PaceCalculatorResponse.builder().distance(distance).time(time).pace(pace).build();
        if(error!= null){
            response.setError(error);
            return response;
        }

        val result = calculate(distance, time, pace);

        return result;
    }

    private PaceCalculatorResponse calculate(Double distance, Duration time, Duration pace) {
        if(distance == 0D) {
            distance = (double) time.dividedBy(pace);
        }
        distance = distance*100;
        if(time.isZero()) {
            time = pace.multipliedBy(Double.valueOf(distance).longValue());
            time = time.dividedBy(100);
        }

        if(pace.isZero()){
            pace = time.dividedBy(Double.valueOf(distance).longValue());
            pace = pace.multipliedBy(100);
        }
        distance = distance/100;
        return PaceCalculatorResponse.builder().distance(distance).time(time).pace(pace).build();
    }

    private Duration setupTime(PaceCalculatorRequest request) {
        Duration time = Duration.ZERO;

        if(request.getHour() != null) {
            time = time.plusHours(request.getHour());
        }
        if(request.getMinute() != null) {
            time = time.plusMinutes(request.getMinute());
        }
        if(request.getSecond() != null) {
            time = time.plusSeconds(request.getSecond());
        }

        return time;
    }

    private Duration setupPace(PaceCalculatorRequest request) {
        Duration pace = Duration.ZERO;

        if(request.getPaceMinute() != null) {
            pace = pace.plusMinutes(request.getPaceMinute());
        }
        if(request.getPaceSecond() != null) {
            pace = pace.plusSeconds(request.getPaceSecond());
        }

        return pace;
    }

}
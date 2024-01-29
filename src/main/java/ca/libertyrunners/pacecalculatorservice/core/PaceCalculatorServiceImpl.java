package ca.libertyrunners.pacecalculatorservice.core;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.stereotype.Service;

import java.time.Duration;

@AllArgsConstructor
@Service
@Slf4j
public class PaceCalculatorServiceImpl implements PaceCalculatorService {

    private final PaceCalculatorValidator validator;

    public PaceCalculatorResponse calculatePace(PaceCalculatorRequest request) {


        log.info(String.valueOf(request));
        Double distance = request.getDistance() == null ? 0D : request.getDistance();
        Duration time = setupTime(request);
        Duration pace = setupPace(request);

        val error = validator.validate(distance, time, pace);
        val response = PaceCalculatorResponse.builder().distance(String.valueOf(distance)).time(String.valueOf(time)).pace(String.valueOf(pace)).build();
        if (error != null) {
            response.setError(error);
            return response;
        }

        return calculate(distance, time, pace);
    }

    private PaceCalculatorResponse calculate(Double distance, Duration time, Duration pace) {
        if (distance == 0D) {
            distance = (double) time.toMillis() / pace.toMillis();
        }
        distance = distance * 100;
        if (time.isZero()) {
            time = pace.multipliedBy(distance.longValue());
            time = time.dividedBy(100);
        }

        if (pace.isZero()) {
            pace = time.dividedBy(distance.longValue());
            pace = pace.multipliedBy(100);
        }
        distance = Math.round(distance) / 100.0;
        return PaceCalculatorResponse.builder().distance(distance + " km").time(formatDuration(time, false)).pace(formatDuration(pace, true)).build();
    }

    private String formatDuration(Duration duration, boolean isPace) {
        long days = duration.toDays();
        long hours = duration.toHoursPart();
        long minutes = duration.toMinutesPart();
        long secondsInt = duration.toSecondsPart();
        long secondsLong = duration.toSeconds();
        String formattedDuration = "";

        if (hours != 0) {
            formattedDuration = hours + " : " + String.format("%02d", minutes) + " : " + String.format("%02d", secondsInt);
        } else {
            formattedDuration = minutes != 0 ? minutes + " : " + String.format("%02d", secondsInt) : secondsLong + " s";
        }

        if (days != 0) {
            formattedDuration = days + " days " + formattedDuration;
        }

        if (isPace) {
            boolean isFast = formattedDuration.indexOf('s') == formattedDuration.length()-1;
            formattedDuration = isFast ? formattedDuration.replace("s", "s/km") : formattedDuration + " /km";
        }

        return formattedDuration;
    }

    private Duration setupTime(PaceCalculatorRequest request) {
        Duration time = Duration.ZERO;

        if (request.getHour() != null) {
            time = time.plusHours(request.getHour());
        }
        if (request.getMinute() != null) {
            time = time.plusMinutes(request.getMinute());
        }
        if (request.getSecond() != null) {
            time = time.plusSeconds(request.getSecond());
        }

        return time;
    }

    private Duration setupPace(PaceCalculatorRequest request) {
        Duration pace = Duration.ZERO;

        if (request.getPaceMinute() != null) {
            pace = pace.plusMinutes(request.getPaceMinute());
        }
        if (request.getPaceSecond() != null) {
            pace = pace.plusSeconds(request.getPaceSecond());
        }

        return pace;
    }

}
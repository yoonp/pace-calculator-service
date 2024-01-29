package ca.libertyrunners.pacecalculatorservice.api;


import ca.libertyrunners.pacecalculatorservice.core.PaceCalculatorRequest;
import ca.libertyrunners.pacecalculatorservice.core.PaceCalculatorResponse;
import ca.libertyrunners.pacecalculatorservice.core.PaceCalculatorService;
import lombok.AllArgsConstructor;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class PaceController {

    private final PaceCalculatorService paceCalculatorService;

    @GetMapping(value = "/calculate-pace")
    public ResponseEntity<PaceCalculatorResponse> calculatePace(@RequestParam(required = false) Double distance, @RequestParam(required = false) Integer hour,
                                                                @RequestParam(required = false) Integer minute, @RequestParam(required = false) Integer second,
                                                                @RequestParam(required = false) Integer paceMinute, @RequestParam(required = false) Integer paceSecond) {

        PaceCalculatorRequest request = PaceCalculatorRequest.builder().distance(distance)
                .hour(hour).minute(minute).second(second).paceMinute(paceMinute).paceSecond(paceSecond).build();

        val result = paceCalculatorService.calculatePace(request);
        if (result.getError() != null) {
            return new ResponseEntity<>(result, HttpStatus.BAD_REQUEST);
        }

        return ResponseEntity.ok(result);
    }

}

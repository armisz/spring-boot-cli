package ch.armisz.cli.service;

import ch.armisz.cli.event.ValidateEvent;
import ch.armisz.cli.event.ValidationResult;
import ch.armisz.cli.event.internal.EventHandler;
import ch.armisz.cli.event.internal.EventResult;
import ch.armisz.cli.event.internal.EventResult.Level;
import ch.armisz.cli.event.internal.EventResults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class EventService {

    public static final int DEFAULT_PRECEDENCE = 5;

    @Autowired
    @Lazy
    private List<EventHandler<ValidateEvent, ValidationResult>> validators;

    public EventResults trigger(ValidateEvent event) {
        EventResults results = new EventResults();
        for (EventHandler<ValidateEvent, ValidationResult> validator : validators) {
            EventResult result = null;
            try {
                result = validator.handle(event);
            } catch (Exception e) {
                result = new EventResult(Level.ERROR, e.getMessage());
            } finally {
                String validatorClassName = validator.getClass().getSimpleName();
                if (result != null) {
                    result.setOrigin(validatorClassName);
                    results.add(result);

                    log.info("{}", result);
                } else {
                    log.info("Result from {} is null", validatorClassName);
                }
            }
        }
        return results;
    }
}

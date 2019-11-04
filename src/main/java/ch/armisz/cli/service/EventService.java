package ch.armisz.cli.service;

import ch.armisz.cli.event.ValidateEvent;
import ch.armisz.cli.event.ValidationResult;
import ch.armisz.cli.event.internal.ErrorResult;
import ch.armisz.cli.event.internal.Event;
import ch.armisz.cli.event.internal.EventHandler;
import ch.armisz.cli.event.internal.EventResult;
import ch.armisz.cli.event.internal.EventResult.Level;
import ch.armisz.cli.event.internal.EventResults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.List;

@Slf4j
@Service
public class EventService {

    public static final int DEFAULT_PRECEDENCE = 5;

    @Autowired
    @Lazy
    private List<EventHandler<ValidateEvent, ValidationResult>> validators;

    public EventResults trigger(ValidateEvent validateEvent) {
        return trigger(validators, validateEvent);
    }

    private <I extends Event, O extends EventResult> EventResults trigger(List<EventHandler<I, O>> handlers, I event) {
        EventResults results = new EventResults();
        for (EventHandler<I, O> handler : handlers) {
            EventResult result = null;
            try {
                result = handler.handle(event);
            } catch (Exception e) {
                result = new ErrorResult(e);
            } finally {
                String validatorClassName = handler.getClass().getSimpleName();
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

    public void throwExceptionOnError(EventResults results) {
        if (results.hasLevel(Level.ERROR)) {
            throw new IllegalStateException(results.getResults(Level.ERROR)
                .stream()
                .map(r -> String.format("%s: %s", r.getOrigin(), r.getMessage()))
                .collect(Collectors.joining(", ")));
        }
    }


}

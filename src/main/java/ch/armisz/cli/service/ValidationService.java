package ch.armisz.cli.service;

import ch.armisz.cli.event.ValidateEvent;
import ch.armisz.cli.event.ValidationResult;
import ch.armisz.cli.event.internal.EventHandler;
import ch.armisz.cli.event.internal.EventResult.Level;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Order(EventService.DEFAULT_PRECEDENCE)
public class ValidationService implements EventHandler<ValidateEvent, ValidationResult> {

    @Override
    public ValidationResult handle(ValidateEvent event) {
        String target = event.getTarget();
        log.info("target={}", target);

        if ("ex".equalsIgnoreCase(target)) {
            throw new IllegalStateException("Fail with exception");
        } else if ("fail".equalsIgnoreCase(target)) {
            return new ValidationResult(Level.ERROR, "Fail");
        } else {
            return null;
        }

    }
}

package ch.armisz.cli.service;

import ch.armisz.cli.event.ValidateEvent;
import ch.armisz.cli.event.ValidationResult;
import ch.armisz.cli.event.internal.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Order(EventService.DEFAULT_PRECEDENCE - 1)
public class ValidationServiceWithHighPriority implements EventHandler<ValidateEvent, ValidationResult> {

    @Override
    public ValidationResult handle(ValidateEvent event) {
        log.info("target={}", event.getTarget());
        return ValidationResult.OK;
    }
}

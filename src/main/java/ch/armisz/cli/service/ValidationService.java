package ch.armisz.cli.service;

import ch.armisz.cli.event.ValidateEvent;
import ch.armisz.cli.event.internal.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Order(EventService.DEFAULT_ORDER)
public class ValidationService implements EventHandler<ValidateEvent> {

    @Override
    public void handle(ValidateEvent event) {
        if ("Boom".equalsIgnoreCase(event.getTarget())) {
            throw new IllegalStateException("Boom!");
        }
        log.info("ValidationService target={}", event.getTarget());
    }
}

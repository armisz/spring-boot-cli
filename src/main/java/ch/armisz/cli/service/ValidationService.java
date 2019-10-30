package ch.armisz.cli.service;

import ch.armisz.cli.event.EventHandler;
import ch.armisz.cli.event.ValidateEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Order(5)
public class ValidationService implements EventHandler<ValidateEvent> {

    @Override
    public void trigger(ValidateEvent event) {
        if ("Boom".equalsIgnoreCase(event.getTarget())) {
            throw new IllegalStateException("Boom!");
        }
        log.info("ValidationService target={}", event.getTarget());
    }
}

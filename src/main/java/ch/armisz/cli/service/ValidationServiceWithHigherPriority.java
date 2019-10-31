package ch.armisz.cli.service;

import ch.armisz.cli.event.ValidateEvent;
import ch.armisz.cli.event.internal.EventHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Order(EventService.DEFAULT_ORDER - 1)
public class ValidationServiceWithHigherPriority implements EventHandler<ValidateEvent> {

    @Override
    public void handle(ValidateEvent event) {
        log.info("ValidationServiceWithHigherPriority target={}", event.getTarget());
    }
}

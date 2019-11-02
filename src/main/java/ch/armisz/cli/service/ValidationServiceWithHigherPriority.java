package ch.armisz.cli.service;

import ch.armisz.cli.event.ValidateEvent;
import ch.armisz.cli.event.internal.EventHandler;
import ch.armisz.cli.event.internal.EventResult;
import ch.armisz.cli.event.internal.EventResult.Level;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@Order(EventService.DEFAULT_PRECEDENCE - 1)
public class ValidationServiceWithHigherPriority implements EventHandler<ValidateEvent, EventResult> {

  @Override
  public EventResult handle(ValidateEvent event) {
    log.info("ValidationServiceWithHigherPriority target={}", event.getTarget());
    return EventResult.builder()
        .origin(getClass().getSimpleName())
        .level(Level.INFO)
        .message("OK")
        .build();
  }
}

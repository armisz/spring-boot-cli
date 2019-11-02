package ch.armisz.cli.service;

import ch.armisz.cli.event.ValidateEvent;
import ch.armisz.cli.event.internal.EventHandler;
import ch.armisz.cli.event.internal.EventResult;
import ch.armisz.cli.event.internal.EventResult.Level;
import ch.armisz.cli.event.internal.EventResults;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class EventService {

  public static final int DEFAULT_PRECEDENCE = 5;

  @Autowired
  @Lazy
  private List<EventHandler<ValidateEvent, EventResult>> validators;

  public EventResults trigger(ValidateEvent event) {
    EventResults results = new EventResults();
    for (EventHandler<ValidateEvent, EventResult> validator : validators) {
      EventResult result = null;
      try {
        result = validator.handle(event);
      } catch (Exception e) {
        result = EventResult.builder()
            .origin(validator.getClass().getSimpleName())
            .level(Level.ERROR)
            .message(e.getMessage())
            .build();
      } finally {
        if (result != null) {
          results.add(result);
        }
      }
    }
    return results;
  }
}

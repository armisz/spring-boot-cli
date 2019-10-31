package ch.armisz.cli.service;

import ch.armisz.cli.event.ValidateEvent;
import ch.armisz.cli.event.internal.EventHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EventService {

    public static final int DEFAULT_ORDER = 5;

    @Autowired
    private List<EventHandler<ValidateEvent>> validators;

    public void trigger(ValidateEvent event) {
        validators.forEach(v -> v.handle(event));
    }
}

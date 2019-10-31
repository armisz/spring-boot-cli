package ch.armisz.cli.service.internal;

import ch.armisz.cli.service.ValidateEvent;
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

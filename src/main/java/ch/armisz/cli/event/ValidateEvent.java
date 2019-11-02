package ch.armisz.cli.event;

import ch.armisz.cli.event.internal.Event;
import lombok.Value;

@Value
public class ValidateEvent implements Event {

    private final String target;
}

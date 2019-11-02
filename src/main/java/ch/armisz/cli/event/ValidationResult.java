package ch.armisz.cli.event;

import ch.armisz.cli.event.internal.EventResult;

public class ValidationResult extends EventResult {

    public ValidationResult(Level level, String message) {
        super(level, message);
    }
}

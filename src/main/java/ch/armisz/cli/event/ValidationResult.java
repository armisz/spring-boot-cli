package ch.armisz.cli.event;

import ch.armisz.cli.event.internal.EventResult;

public class ValidationResult extends EventResult {
	
	  public static final ValidationResult OK = new ValidationResult(Level.INFO, "OK");

    public static final ValidationResult OK = new ValidationResult(Level.INFO, "OK");

    public ValidationResult(Level level, String message) {
        super(level, message);
    }
}

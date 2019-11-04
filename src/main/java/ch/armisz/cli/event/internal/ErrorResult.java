package ch.armisz.cli.event.internal;

import lombok.Getter;

public class ErrorResult extends EventResult {

    @Getter
    private final Exception cause;

    public ErrorResult(Exception cause) {
        super(Level.ERROR, cause.getMessage());
        this.cause = cause;
    }
}

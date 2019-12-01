package ch.armisz.cli.event.internal;

import lombok.Getter;
import lombok.NonNull;

import java.util.Objects;

public class ErrorResult extends EventResult {

    @Getter
    private final Exception cause;

    public ErrorResult(@NonNull Exception cause) {
        super(Level.ERROR, Objects.toString(cause.getMessage(), cause.getClass().getName()));
        this.cause = cause;
    }
}

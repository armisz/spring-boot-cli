package ch.armisz.cli.event.internal;

import java.util.Objects;
import lombok.Getter;
import lombok.NonNull;

public class ErrorResult extends EventResult {

    @Getter
    private final Exception cause;

    public ErrorResult(@NonNull Exception cause) {
        super(Level.ERROR, Objects.toString(cause.getMessage(), cause.getClass().getName()));
        this.cause = cause;
    }
}

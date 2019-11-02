package ch.armisz.cli.event.internal;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.Objects;

public class EventResult {

    public enum Level {
        INFO, WARNING, ERROR
    }

    @Getter
    @Setter
    @NonNull
    private String origin;
    @Getter
    private final Level level;
    @Getter
    private final String message;

    public EventResult(Level level, String message) {
        this.level = Objects.requireNonNull(level);
        this.message = Objects.requireNonNull(message);
    }

    @Override
    public String toString() {
        return String.format("%s %s: %s", level, Objects.toString(origin, ""), message);
    }
}

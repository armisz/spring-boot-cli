package ch.armisz.cli.event.internal;

import ch.armisz.cli.event.internal.EventResult.Level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class EventResults {

    private final List<EventResult> results = new ArrayList<>();

    public void add(EventResult eventResult) {
        results.add(Objects.requireNonNull(eventResult));
    }

    public boolean hasLevel(Level level) {
        return results.stream()
            .anyMatch(r -> r.getLevel().equals(level));
    }

    public List<EventResult> getResults(Level level) {
        return results.stream()
            .filter(r -> r.getLevel().equals(level))
            .collect(Collectors.toList());
    }

    public List<EventResult> getResults() {
        return Collections.unmodifiableList(results);
    }
}


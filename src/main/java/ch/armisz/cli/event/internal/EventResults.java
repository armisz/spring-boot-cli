package ch.armisz.cli.event.internal;

import ch.armisz.cli.event.internal.EventResult.Level;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class EventResults {

  private List<EventResult> results = new ArrayList<>();

  public void add(EventResult eventResult) {
    results.add(eventResult);
  }

  public boolean hasResult(Level level) {
    return results.stream()
        .anyMatch(i -> level.equals(i.getLevel()));
  }

  public List<EventResult> getResults(Level level) {
    return results.stream()
        .filter(i -> level.equals(i.getLevel()))
        .collect(Collectors.toList());
  }

  public List<EventResult> getResults() {
    return Collections.unmodifiableList(results);
  }
}


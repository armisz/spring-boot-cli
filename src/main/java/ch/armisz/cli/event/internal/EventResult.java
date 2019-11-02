package ch.armisz.cli.event.internal;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class EventResult {

  public enum Level {
    INFO, WARNING, ERROR
  }

  private final String origin;
  private final Level level;
  private final String message;

}

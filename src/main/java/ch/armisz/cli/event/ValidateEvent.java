package ch.armisz.cli.event;

public class ValidateEvent {

  private final String target;

  public ValidateEvent(String target) {
    this.target = target;
  }

  public String getTarget() {
    return target;
  }
}

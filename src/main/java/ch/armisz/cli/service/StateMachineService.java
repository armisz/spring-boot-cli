package ch.armisz.cli.service;

import ch.armisz.cli.state.RomeEvents;
import ch.armisz.cli.state.RomeStates;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;

@Service
public class StateMachineService {

  @Autowired
  private StateMachine<RomeStates, RomeEvents> stateMachine;

  public void sendEvent(RomeEvents event) {
    reconcile();
    stateMachine.sendEvent(event);
  }

  public void sendEvent(Message<RomeEvents> message) {
    reconcile();
    stateMachine.sendEvent(message);
  }

  public RomeStates getState(boolean reconcile) {
    if (reconcile) {
      reconcile();
    }
    return stateMachine.getState().getId();
  }

  public boolean hasState(RomeStates state) {
    return getState(true).equals(state);
  }

  private void reconcile() {
    Optional.ofNullable(getProperty("state"))
        .map(RomeStates::valueOf)
        .ifPresent((newState) -> {
          stateMachine.stop();
          stateMachine.getStateMachineAccessor().doWithAllRegions(
              sma -> sma.resetStateMachine(
                  new DefaultStateMachineContext<RomeStates, RomeEvents>(newState, null, null,
                      null))
          );
          stateMachine.start();
        });
  }

  private String getProperty(String key) {
    try (InputStream stream = new FileInputStream("cli-state.properties")) {
      Properties properties = new Properties();
      properties.load(stream);
      return properties.getProperty(key);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

}

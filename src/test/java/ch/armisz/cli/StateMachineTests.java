package ch.armisz.cli;

import ch.armisz.cli.state.RomeEvents;
import ch.armisz.cli.state.RomeStates;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;

public class StateMachineTests extends AbstractTests {

  @Autowired
  private StateMachine<RomeStates, RomeEvents> stateMachine;

  @Test
  public void initTest() {
    assertThat(stateMachine).isNotNull();
    assertThat(stateMachine.getState().getId()).isEqualTo(RomeStates.STARTED);
  }

  @Test
  public void testGreenFlow() {
    stateMachine.sendEvent(RomeEvents.FETCH);
    stateMachine.sendEvent(RomeEvents.CONFIGURE);
    stateMachine.sendEvent(RomeEvents.DEPLOY);

    assertThat(stateMachine.getState().getId()).isEqualTo(RomeStates.DEPLOYED);
  }
}
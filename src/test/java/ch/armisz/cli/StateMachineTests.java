package ch.armisz.cli;

import static org.assertj.core.api.Assertions.assertThat;

import ch.armisz.cli.state.RomeEvents;
import ch.armisz.cli.state.RomeStates;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StateMachineTests {

  @Autowired
  private StateMachine<RomeStates, RomeEvents> stateMachine;

  @Test
  @Ignore  // TODO fix
  public void initTest() {
    assertThat(stateMachine).isNotNull();
    assertThat(stateMachine.getState().getId()).isEqualTo(RomeStates.STARTED);
  }

  @Test
  @Ignore  // TODO fix
  public void testGreenFlow() {
    stateMachine.sendEvent(RomeEvents.FETCH);
    stateMachine.sendEvent(RomeEvents.CONFIGURE);
    stateMachine.sendEvent(RomeEvents.DEPLOY);

    assertThat(stateMachine.getState().getId()).isEqualTo(RomeStates.DEPLOYED);
  }
}
package ch.armisz.cli;

import ch.armisz.cli.state.RomeEvents;
import ch.armisz.cli.state.RomeStates;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.support.DefaultStateMachineContext;

import static org.assertj.core.api.Assertions.assertThat;

public class StateMachineTests extends AbstractTests {

    @Autowired
    private StateMachine<RomeStates, RomeEvents> stateMachine;

    @Before
    public void setup() {
        stateMachine.stop();
        stateMachine.getStateMachineAccessor().doWithAllRegions(
                sma -> sma.resetStateMachine(
                        new DefaultStateMachineContext<RomeStates, RomeEvents>(stateMachine.getInitialState().getId(), null, null, null))
        );
        stateMachine.start();
    }

    @Test
    public void initTest() {
        assertThat(stateMachine).isNotNull();
        assertThat(stateMachine.getState().getId()).isEqualTo(RomeStates.STARTED);
    }

    @Test
    public void testGreenFlow() {
        stateMachine.sendEvent(RomeEvents.FETCH);
        assertThat(stateMachine.getState().getId()).isEqualTo(RomeStates.FETCHED);
        stateMachine.sendEvent(RomeEvents.CONFIGURE);
        assertThat(stateMachine.getState().getId()).isEqualTo(RomeStates.CONFIGURED);
        stateMachine.sendEvent(RomeEvents.DEPLOY);
        assertThat(stateMachine.getState().getId()).isEqualTo(RomeStates.DEPLOYED);
    }
}
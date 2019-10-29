package ch.armisz.cli.configuration;

import ch.armisz.cli.service.RomeFilter;
import ch.armisz.cli.service.RomeService;
import ch.armisz.cli.state.RomeEvents;
import ch.armisz.cli.state.RomeStates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.action.Action;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;

import java.util.Optional;

@Slf4j
@Configuration
@EnableStateMachine
public class StateMachineConfiguration extends EnumStateMachineConfigurerAdapter<RomeStates, RomeEvents> {

    @Autowired
    private RomeService romeService;

    @Override
    public void configure(StateMachineConfigurationConfigurer<RomeStates, RomeEvents> config) throws Exception {
        config.withConfiguration()
                .autoStartup(true)
                .listener(new StateMachineListenerAdapter<>() {
                    @Override
                    public void stateChanged(State<RomeStates, RomeEvents> from, State<RomeStates, RomeEvents> to) {
                        log.info("state changed from {} to {}", ofNullableState(from), ofNullableState(to));
                    }
                });
    }

    @Override
    public void configure(StateMachineStateConfigurer<RomeStates, RomeEvents> states) throws Exception {
        states.withStates()
                .initial(RomeStates.STARTED)
                .state(RomeStates.FETCHED)
                .state(RomeStates.CONFIGURED)
                .state(RomeStates.DEPLOYED);
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<RomeStates, RomeEvents> transitions) throws Exception {
        transitions
                .withExternal().source(RomeStates.STARTED).target(RomeStates.FETCHED).event(RomeEvents.FETCH).action(fetchAction())
                .and()
                .withExternal().source(RomeStates.FETCHED).target(RomeStates.FETCHED).event(RomeEvents.FETCH).action(fetchAction())
                .and()
                .withExternal().source(RomeStates.FETCHED).target(RomeStates.CONFIGURED).event(RomeEvents.CONFIGURE).action(configureAction())
                .and()
                .withExternal().source(RomeStates.FETCHED).target(RomeStates.DEPLOYED).event(RomeEvents.DEPLOY).action(configureAndDeployAction())
                .and()
                .withExternal().source(RomeStates.CONFIGURED).target(RomeStates.DEPLOYED).event(RomeEvents.DEPLOY).action(deployAction())
                .and()
                .withExternal().source(RomeStates.CONFIGURED).target(RomeStates.CONFIGURED).event(RomeEvents.CONFIGURE).action(configureAction())
                .and()
                .withExternal().source(RomeStates.CONFIGURED).target(RomeStates.FETCHED).event(RomeEvents.FETCH).action(fetchAction())
                .and()
                .withExternal().source(RomeStates.DEPLOYED).target(RomeStates.DEPLOYED).event(RomeEvents.DEPLOY).action(deployAction())
                .and()
                .withExternal().source(RomeStates.DEPLOYED).target(RomeStates.FETCHED).event(RomeEvents.FETCH).action(fetchAction())
                .and()
                .withExternal().source(RomeStates.DEPLOYED).target(RomeStates.CONFIGURED).event(RomeEvents.CONFIGURE).action(configureAction());
    }

    private Action<RomeStates, RomeEvents> fetchAction() {
        return context -> {
            log.info("fetchAction: {}", context.getEvent());
            romeService.fetch();
        };
    }

    private Action<RomeStates, RomeEvents> configureAction() {
        return context -> {
            log.info("configureAction: {}", context.getEvent());
            romeService.configure((RomeFilter) context.getMessageHeader("filter"));
        };
    }

    private Action<RomeStates, RomeEvents> configureAndDeployAction() {
        return context -> {
            log.info("configureAndDeployAction: {}", context.getEvent());
            RomeFilter filter = (RomeFilter) context.getMessageHeader("filter");
            romeService.configure(filter);
            romeService.deploy(filter, false, false);
        };
    }

    private Action<RomeStates, RomeEvents> deployAction() {
        return context -> {
            log.info("deployAction: {}", context.getEvent());

            romeService.deploy(
                    (RomeFilter) context.getMessageHeader("filter"),
                    (Boolean) context.getMessageHeader("ignoreFetch"),
                    (Boolean) context.getMessageHeader("ignoreConfigure")
            );
        };
    }

    private Object ofNullableState(State<RomeStates, RomeEvents> state) {
        return Optional.ofNullable(state)
                .map(State::getId)
                .orElse(null);
    }
}

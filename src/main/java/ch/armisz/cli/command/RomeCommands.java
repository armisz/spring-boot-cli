package ch.armisz.cli.command;

import ch.armisz.cli.event.ValidateEvent;
import ch.armisz.cli.event.internal.EventResults;
import ch.armisz.cli.service.EventService;
import ch.armisz.cli.service.Filter;
import ch.armisz.cli.service.RomeService;
import ch.armisz.cli.service.StateMachineService;
import ch.armisz.cli.state.RomeEvents;
import ch.armisz.cli.state.RomeStates;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

@Slf4j
@ShellComponent
public class RomeCommands {

    private static final String PRODUCT_FILTER = "--product-filter";
    private static final String COMPONENT_FILTER = "--component-filter";

    @Autowired
    private RomeService romeService;
    @Autowired
    private StateMachineService stateMachineService;
    @Autowired
    private EventService eventService;

    @ShellMethod("Fetch components from repository")
    public void fetch() {
        stateMachineService.sendEvent(RomeEvents.FETCH);
    }

    @ShellMethod("Manage parameters")
    public void parameters(
        @ShellOption(help = "The product to filter for",
            defaultValue = ShellOption.NULL,
            value = PRODUCT_FILTER)
            String productFilter,
        @ShellOption(help = "The component to filter for",
            defaultValue = ShellOption.NULL,
            value = COMPONENT_FILTER)
            String componentFilter) {

        Filter filter = Filter.builder()
            .component(componentFilter)
            .product(productFilter)
            .build();
        romeService.parameters(filter);
    }

    Availability parametersAvailability() {
        return stateMachineService.hasState(RomeStates.STARTED)
            ? Availability.unavailable("command [fetch] must be executed first.")
            : Availability.available();
    }

    @ShellMethod("Applies configuration")
    public void configure(
        @ShellOption(help = "The product to filter for",
            defaultValue = ShellOption.NULL,
            value = PRODUCT_FILTER)
            String productFilter,
        @ShellOption(help = "The component to filter for",
            defaultValue = ShellOption.NULL,
            value = COMPONENT_FILTER)
            String componentFilter) {

        EventResults results = eventService.trigger(new ValidateEvent(productFilter));
        eventService.throwExceptionOnError(results);

        Filter filter = Filter.builder()
            .component(componentFilter)
            .product(productFilter)
            .build();
        Message<RomeEvents> configureMessage = MessageBuilder.withPayload(RomeEvents.CONFIGURE)
            .setHeader("filter", filter)
            .build();
        stateMachineService.sendEvent(configureMessage);
    }

    @ShellMethod("Lists all kubectl commands necessary to deploy the new configuration")
    public void deploy(
        @ShellOption(help = "The product to filter for",
            defaultValue = ShellOption.NULL,
            value = PRODUCT_FILTER)
            String productFilter,
        @ShellOption(help = "The component to filter for",
            defaultValue = ShellOption.NULL,
            value = COMPONENT_FILTER)
            String componentFilter,
        @ShellOption(help = "Ignores fetch step",
            defaultValue = "false")
            boolean ignoreFetch,
        @ShellOption(help = "Ignores configure step",
            defaultValue = "false")
            boolean ignoreConfigure) {

        Filter filter = Filter.builder()
            .component(componentFilter)
            .product(productFilter)
            .build();
        Message<RomeEvents> deployMessage = MessageBuilder.withPayload(RomeEvents.DEPLOY)
            .setHeader("filter", filter)
            .setHeader("ignoreFetch", ignoreFetch)
            .setHeader("ignoreConfigure", ignoreConfigure)
            .build();
        stateMachineService.sendEvent(deployMessage);
    }

    @ShellMethod("YAML test")
    public void yaml() {
        romeService.yaml();
    }
}
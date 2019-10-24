package ch.armisz.cli.command;

import ch.armisz.cli.service.TerminalService;
import ch.armisz.cli.state.RomeEvents;
import ch.armisz.cli.state.RomeStates;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import org.springframework.statemachine.StateMachine;

import java.math.BigInteger;
import java.util.Arrays;

@ShellComponent
public class CliCommands {

    @Autowired
    ApplicationContext ctx;
    @Autowired
    private StateMachine<RomeStates, RomeEvents> stateMachine;
    @Autowired
    TerminalService terminalService;

    @ShellMethod(value = "Division", group = "Arithmetic")
    public void div(
            String dividend,
            String divisor,
            @ShellOption(defaultValue = "false", help = "Show rest if present") boolean showRest) {

        BigInteger bigDividend = new BigInteger(dividend);
        BigInteger bigDivisor = new BigInteger(divisor);
        BigInteger[] result = bigDividend.divideAndRemainder(bigDivisor);

        terminalService.write(showRest ? "%s / %s = %s rest %s" : "%s / %s = %s",
                bigDividend, bigDivisor, result[0], result[1]);

        if (!showRest && !BigInteger.ZERO.equals(result[1])) {
            terminalService.write(new AttributedString("Rest is available", AttributedStyle.DEFAULT.foreground(AttributedStyle.CYAN)));
        }
    }

    @ShellMethod(value = "Beans", group = "SpringBoot")
    public void beans() {
        Arrays.stream(ctx.getBeanDefinitionNames())
                .sorted()
                .forEach(terminalService::write);
    }

    @ShellMethod(value = "State Machine State", group = "SpringBoot")
    public void state() {
        terminalService.write(stateMachine.getState().getId().name());
    }
}
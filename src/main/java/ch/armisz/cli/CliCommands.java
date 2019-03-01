package ch.armisz.cli;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.math.BigInteger;

@ShellComponent
public class CliCommands {

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

}
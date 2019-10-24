package ch.armisz.cli.configuration;

import ch.armisz.cli.state.RomeEvents;
import ch.armisz.cli.state.RomeStates;
import org.jline.reader.History;
import org.jline.reader.LineReader;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.PromptProvider;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.support.DefaultStateMachineContext;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.util.Optional;
import java.util.Properties;

@Configuration
public class CliConfiguration {

    @Bean
    public PromptProvider promptProvider() {
        return () -> new AttributedString("\nCLI> ", AttributedStyle.BOLD.foreground(AttributedStyle.WHITE));
    }

    @Bean
    public History history(LineReader lineReader) {
        lineReader.setVariable(LineReader.HISTORY_FILE, Paths.get("cli-history.log"));
        return new DefaultHistory(lineReader);
    }

    @Bean
    public LoggingExceptionResultHandler loggingExceptionResultHandler() {
        return new LoggingExceptionResultHandler();
    }

    @Bean
    public CommandLineRunner commandLineRunner(StateMachine<RomeStates, RomeEvents> stateMachine) {
        return new CliCommandLineRunner(stateMachine);
    }

    @Order(InteractiveShellApplicationRunner.PRECEDENCE - 2)
    class CliCommandLineRunner implements CommandLineRunner {
        private final StateMachine<RomeStates, RomeEvents> stateMachine;

        CliCommandLineRunner(StateMachine<RomeStates, RomeEvents> stateMachine) {
            this.stateMachine = stateMachine;
        }

        @Override
        public void run(String... args) {
            RomeStates startState = Optional.ofNullable(getProperty("startState"))
                    .map(RomeStates::valueOf)
                    .orElse(RomeStates.STARTED);
            stateMachine.stop();
            stateMachine.getStateMachineAccessor().doWithAllRegions(
                    sma -> sma.resetStateMachine(new DefaultStateMachineContext<RomeStates, RomeEvents>(startState, null, null, null))
            );
            stateMachine.start();
        }
    }

    public static String getProperty(String key) {
        try (InputStream stream = new FileInputStream("cli-appl.properties")) {
            Properties properties = new Properties();
            properties.load(stream);
            return properties.getProperty(key);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}

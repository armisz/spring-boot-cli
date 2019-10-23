package ch.armisz.cli.configuration;

import java.nio.file.Paths;
import org.jline.reader.History;
import org.jline.reader.LineReader;
import org.jline.reader.impl.history.DefaultHistory;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.PromptProvider;

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

}

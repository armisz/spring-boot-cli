package ch.armisz.cli;

import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.shell.jline.PromptProvider;

@Configuration
public class CliConfiguration {

    @Bean
    public PromptProvider promptProvider() {
        return () -> new AttributedString("\ncli> ", AttributedStyle.BOLD.foreground(AttributedStyle.WHITE));
    }

}

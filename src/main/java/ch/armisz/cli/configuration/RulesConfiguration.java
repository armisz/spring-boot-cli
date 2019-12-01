package ch.armisz.cli.configuration;

import org.jeasy.rules.core.DefaultRulesEngine;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;
import java.util.Properties;

@Configuration
public class RulesConfiguration {

    @Bean
    public DefaultRulesEngine rulesEngine() {
        return new DefaultRulesEngine();
    }

    @Bean
    public Properties parameters() {
        try (InputStream stream = new FileInputStream("parameters.properties")) {
            Properties properties = new Properties();
            properties.load(stream);
            return properties;
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }

    }
}

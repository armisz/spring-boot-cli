package ch.armisz.cli.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

@Configuration
public class YamlConfiguration {

    @Bean
    public Yaml yaml() {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setLineBreak(DumperOptions.LineBreak.getPlatformLineBreak());
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        return new Yaml(dumperOptions);
    }
}

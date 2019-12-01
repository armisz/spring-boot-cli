package ch.armisz.cli.service;

import ch.armisz.cli.service.internal.RuleService;
import ch.armisz.cli.service.internal.TerminalService;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Optional;

/**
 * All roads lead to Rome.
 */
@Service
public class RomeService {

    @Autowired
    private Yaml yaml;
    @Autowired
    private RuleService ruleService;
    @Autowired
    private TerminalService terminalService;

    public void fetch() {
        terminalService.write(new AttributedString(
            "fetch",
            AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN)));
    }

    public void parameters(Filter filter) {
        terminalService.write(new AttributedString(
            String.format("parameters, filter=%s", filter),
            AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN)));
    }

    public void configure(Filter filter) {
        terminalService.write(new AttributedString(
            String.format("configure, filter=%s", filter),
            AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN)));
    }

    public void deploy(Filter filter, Boolean ignoreFetch, Boolean ignoreConfigure) {
        terminalService.write(new AttributedString(
            String.format("deploy, filter=%s, ignoreFetch=%s, ignoreConfigure=%s", filter, ignoreFetch, ignoreConfigure),
            AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN)));

        if (Optional.ofNullable(ignoreConfigure).orElse(Boolean.FALSE)) {
            throw new IllegalStateException("Boom!");
        }
    }

    public void yaml() {
        try {
            Path inFile = Path.of("some.yml");
            String yamlString = Files.readString(inFile);

            Map<String, Object> yamlMap = yaml.load(yamlString);
            Map<String, Object> appliedRulesYamlMap = ruleService.applyRules(yamlMap);

            Path outFile = Path.of("some-resolved.yml");
            Files.deleteIfExists(outFile);
            Files.writeString(outFile, yaml.dump(appliedRulesYamlMap));

            terminalService.write(String.format("%s => %s", inFile.toString(), outFile.toFile()));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

}
package ch.armisz.cli.service;

import lombok.extern.slf4j.Slf4j;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * All roads lead to Rome.
 */
@Service
@Slf4j
public class RomeService {

    @Autowired
    TerminalService terminalService;

    public void fetch() {
        terminalService.write(new AttributedString(
            "fetch",
            AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN)));
    }

    public void parameters(RomeFilter filter) {
        terminalService.write(new AttributedString(
            String.format("parameters, filter=%s", filter),
            AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN)));
    }

    public void configure(RomeFilter filter) {
        terminalService.write(new AttributedString(
            String.format("configure, filter=%s", filter),
            AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN)));
    }

    public void deploy(RomeFilter filter, boolean ignoreFetch, boolean ignoreConfigure) {
        terminalService.write(new AttributedString(
            String.format("deploy, filter=%s, ignoreFetch=%s, ignoreConfigure=%s", filter, ignoreFetch, ignoreConfigure),
            AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN)));
    }

    public void images(RomeFilter filter) {
        terminalService.write(new AttributedString(
            String.format("images, filter=%s", filter),
            AttributedStyle.DEFAULT.foreground(AttributedStyle.GREEN)));
    }
}
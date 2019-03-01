package ch.armisz.cli;

import org.jline.terminal.Terminal;
import org.jline.utils.AttributedString;
import org.jline.utils.AttributedStyle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TerminalService {

    @Autowired
    Terminal terminal;

    public void write(String msg, Object... args) {
        write(new AttributedString(
                String.format(msg, args),
                AttributedStyle.DEFAULT));
    }

    public void write(AttributedString msg) {
        terminal.writer()
                .append(msg.toAnsi())
                .append(AttributedString.NEWLINE)
                .flush();
    }
}

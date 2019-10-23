package ch.armisz.cli.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.result.ThrowableResultHandler;

public class LoggingThrowableResultHandler extends ThrowableResultHandler {

    @Autowired
    @Lazy
    private InteractiveShellApplicationRunner interactiveRunner;

    private Logger logger = LoggerFactory.getLogger(LoggingThrowableResultHandler.class);

    @Override
    protected void doHandleResult(Throwable result) {
        if (interactiveRunner.isEnabled()) {
            logger.error(result.getMessage(), result);
        }
        super.doHandleResult(result);
    }

}

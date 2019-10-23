package ch.armisz.cli.configuration;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.shell.ResultHandler;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.result.ThrowableResultHandler;
import org.springframework.util.StringUtils;

@Slf4j
public class LoggingExceptionResultHandler implements ResultHandler<Exception> {

  @Autowired
  @Lazy
  private InteractiveShellApplicationRunner interactiveRunner;
  @Autowired
  private ThrowableResultHandler throwableResultHandler;

  @Override
  public void handleResult(Exception result) {
    if (interactiveRunner.isEnabled()) {
      String toPrint =
          StringUtils.hasLength(result.getMessage()) ? result.getMessage() : result.toString();
      log.error(toPrint, result);
    }
    throwableResultHandler.handleResult(result);
  }
}

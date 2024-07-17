package pl.kerpson.license.utilites.logger;

import java.util.logging.Logger;

public class DefaultLoggerProvider implements LoggerProvider {

  private final Logger logger = Logger.getLogger("mLicense");

  @Override
  public void log(LogType type, String message) {
    switch (type) {
      case ERROR:
        logger.severe(message);
        break;
      case INFO:
        logger.info(message);
        break;
      case WARNING:
        logger.warning(message);
        break;
    }
  }

  @Override
  public void log(LogType type, String message, Throwable throwable) {
    switch (type) {
      case ERROR:
        logger.severe(message + " " + throwable);
        break;
      case INFO:
        logger.info(message + " " + throwable);
        break;
      case WARNING:
        logger.warning(message + " " + throwable);
        break;
    }
  }
}

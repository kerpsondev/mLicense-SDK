package pl.kerpson.license.utilites.logger;

public interface LoggerProvider {

  LoggerProvider DEFAULT_LOGGER = new DefaultLoggerProvider();

  void log(LogType type, String message);

  void log(LogType type, String message, Throwable throwable);

  enum LogType {

    INFO,
    ERROR,
    WARNING
  }
}

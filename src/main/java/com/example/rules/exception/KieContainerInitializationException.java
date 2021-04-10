package com.example.rules.exception;

/**
 * KieContainerInitializationException.
 */
@SuppressWarnings("unused")
public class KieContainerInitializationException extends RuntimeException {

  private static final long serialVersionUID = -546464606821621996L;

  public KieContainerInitializationException() {
    super();
  }

  public KieContainerInitializationException(String message,
                                             Throwable cause,
                                             boolean enableSuppression,
                                             boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public KieContainerInitializationException(String message, Throwable cause) {
    super(message, cause);
  }

  public KieContainerInitializationException(String message) {
    super(message);
  }

  public KieContainerInitializationException(Throwable cause) {
    super(cause);
  }

}

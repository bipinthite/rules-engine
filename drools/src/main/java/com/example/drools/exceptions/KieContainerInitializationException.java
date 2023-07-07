package com.example.drools.exceptions;

import java.io.Serial;

/**
 * KieContainerInitializationException.
 */
@SuppressWarnings("unused")
public class KieContainerInitializationException extends RuntimeException {

  @Serial
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

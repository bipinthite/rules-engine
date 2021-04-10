package com.example.rules.exception;

/**
 * RulesBuildException.
 */
@SuppressWarnings("unused")
public class RulesBuildException extends RuntimeException {

  private static final long serialVersionUID = -546464606821621996L;

  public RulesBuildException() {
    super();
  }

  public RulesBuildException(String message, Throwable cause,
                             boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public RulesBuildException(String message, Throwable cause) {
    super(message, cause);
  }

  public RulesBuildException(String message) {
    super(message);
  }

  public RulesBuildException(Throwable cause) {
    super(cause);
  }

}

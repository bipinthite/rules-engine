package com.example.rules.exception;

import java.io.Serial;

/**
 * KnowledgeBaseNotFoundException.
 */
@SuppressWarnings("unused")
public class KnowledgeBaseNotFoundException extends RuntimeException {

  @Serial
  private static final long serialVersionUID = -5975449849625674132L;

  public KnowledgeBaseNotFoundException() {
    super();
  }

  public KnowledgeBaseNotFoundException(String message, Throwable cause, boolean enableSuppression,
                                        boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public KnowledgeBaseNotFoundException(String message, Throwable cause) {
    super(message, cause);
  }

  public KnowledgeBaseNotFoundException(String message) {
    super(message);
  }

  public KnowledgeBaseNotFoundException(Throwable cause) {
    super(cause);
  }

}

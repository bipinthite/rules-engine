package com.example.rules.models;

/**
 * ApiError record.
 *
 * @param status status
 * @param message message
 * @param code code
 * @param description description
 */
public record ApiError(String message, String status, String code, String description) {
  public ApiError(String message) {
    this(message, null, null, null);
  }
}

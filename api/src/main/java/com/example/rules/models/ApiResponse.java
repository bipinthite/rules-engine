package com.example.rules.models;

/**
 * Response record.
 *
 * @param data data object
 * @param error error
 */
public record ApiResponse<T>(T data, ApiError error) {
  public ApiResponse(T data) {
    this(data, null);
  }
}

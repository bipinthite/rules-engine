package com.example.rules.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * APIResponse.
 *
 * @author Bipin Thite
 */
@Data
@NoArgsConstructor
public class ApiResponse {

  private Object data;
  private List<Error> errors;

  @JsonProperty("status_code")
  private String statusCode;

  @JsonProperty("status_message")
  private String statusMessage;

  public ApiResponse(final Object data) {
    this.data = data;
  }

  public ApiResponse(
      final Object data, final Error error, final String statusCode, final String statusMessage) {
    this(data, Collections.singletonList(error), statusCode, statusMessage);
  }

  /**
   * Create new ApiResponse.
   *
   * @param data data object
   * @param errors list of errors
   * @param statusCode status code
   * @param statusMessage status message
   */
  public ApiResponse(
      final Object data,
      final List<Error> errors,
      final String statusCode,
      final String statusMessage) {
    this.data = data;
    this.errors = errors == null ? null : new ArrayList<>(errors);
    this.statusCode = statusCode;
    this.statusMessage = statusMessage;
  }

  public List<Error> getErrors() {
    return errors == null ? null : Collections.unmodifiableList(errors);
  }

  public void setErrors(final List<Error> errors) {
    this.errors = errors == null ? null : new ArrayList<>(errors);
  }

  /** Error. */
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  public static class Error {

    @JsonProperty("error_code")
    private String errorCode;

    @JsonProperty("error_status")
    private String errorStatus;

    @JsonProperty("error_msg")
    private String errorMessage;

    @JsonProperty("error_description")
    private String errorDescription;

    public Error(String errorMessage) {
      this.errorMessage = errorMessage;
    }
  }
}

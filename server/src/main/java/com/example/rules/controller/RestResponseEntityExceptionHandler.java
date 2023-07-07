package com.example.rules.controller;

import com.example.drools.exceptions.KieContainerInitializationException;
import com.example.drools.exceptions.KnowledgeBaseNotFoundException;
import com.example.drools.exceptions.RuleEvaluationException;
import com.example.drools.exceptions.RulesBuildException;
import com.example.rules.models.ApiError;
import com.example.rules.models.ApiResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/** Controller advice. */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @SuppressWarnings("unused")
  @ExceptionHandler({KnowledgeBaseNotFoundException.class})
  protected ResponseEntity<ApiResponse<Object>> handleKbNotFound(
      final RuntimeException ex, final WebRequest request) {
    return buildResponseEntity(buildApiResponse(ex), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({
    RuleEvaluationException.class,
    RulesBuildException.class,
    KieContainerInitializationException.class
  })
  protected ResponseEntity<ApiResponse<Object>> handleRuleEvaluationError(
      final RuntimeException ex, final WebRequest request) {
    return buildResponseEntity(buildApiResponse(ex), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler({Exception.class})
  protected ResponseEntity<ApiResponse<Object>> handleRuntimeException(
      final RuntimeException ex, final WebRequest request) {
    return buildResponseEntity(buildApiResponse(ex), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  // Utility methods
  private ApiResponse<Object> buildApiResponse(final RuntimeException ex) {
    final ApiError error = new ApiError(ex.getLocalizedMessage());
    return new ApiResponse<>(null, error);
  }

  private ResponseEntity<ApiResponse<Object>> buildResponseEntity(
      final ApiResponse<Object> responseBody, final HttpStatus status) {
    return new ResponseEntity<>(responseBody, new HttpHeaders(), status);
  }
}

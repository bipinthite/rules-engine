package com.example.rules.controller;

import com.example.drools.exceptions.KieContainerInitializationException;
import com.example.drools.exceptions.KnowledgeBaseNotFoundException;
import com.example.drools.exceptions.RuleEvaluationException;
import com.example.drools.exceptions.RulesBuildException;
import com.example.rules.dto.ApiResponse;
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
  protected ResponseEntity<ApiResponse> handleKbNotFound(
      final RuntimeException ex, final WebRequest request) {
    return buildResponseEntity(buildApiResponse(ex), HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({
    RuleEvaluationException.class,
    RulesBuildException.class,
    KieContainerInitializationException.class
  })
  protected ResponseEntity<ApiResponse> handleRuleEvaluationError(
      final RuntimeException ex, final WebRequest request) {
    return buildResponseEntity(buildApiResponse(ex), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler({Exception.class})
  protected ResponseEntity<ApiResponse> handleRuntimeException(
      final RuntimeException ex, final WebRequest request) {
    return buildResponseEntity(buildApiResponse(ex), HttpStatus.INTERNAL_SERVER_ERROR);
  }

  // Utility methods

  private ApiResponse buildApiResponse(final RuntimeException ex) {
    final ApiResponse.Error error = new ApiResponse.Error(ex.getLocalizedMessage());
    return new ApiResponse(null, error, null, null);
  }

  private ResponseEntity<ApiResponse> buildResponseEntity(
      final ApiResponse responseBody, final HttpStatus status) {
    return new ResponseEntity<>(responseBody, new HttpHeaders(), status);
  }
}

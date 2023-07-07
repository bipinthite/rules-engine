package com.example.rules.controller.v1;

import static com.example.rules.api.RulesServiceEndpoints.ENDPOINT_FIRE_RULE;
import static com.example.rules.api.RulesServiceEndpoints.PARAM_HEADER_KB;
import static com.example.rules.api.RulesServiceEndpoints.V1;

import com.example.rules.models.ApiResponse;
import com.example.rules.service.RulesService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * RulesController.
 *
 * @author Bipin Thite
 */
@SuppressWarnings("unused")
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping(V1)
public class RulesController {

  private static final String CLASS_NAME = RulesController.class.getSimpleName();

  private final RulesService service;

  /** Fire rules. */
  @ResponseBody
  @PostMapping(
      path = ENDPOINT_FIRE_RULE,
      consumes = MediaType.APPLICATION_JSON_VALUE,
      produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<ApiResponse<Map<String, Object>>> fireRules(
      @RequestBody final Map<String, Object> facts,
      @RequestHeader(name = PARAM_HEADER_KB) final String kb) {
    log.trace("ENTRY {} {}", CLASS_NAME, "fireRules");

    final Map<String, Object> results = service.fireRules(facts, kb);

    final ResponseEntity<ApiResponse<Map<String, Object>>> response =
        new ResponseEntity<>(new ApiResponse<>(results), HttpStatus.OK);

    log.trace("EXIT {} {}", CLASS_NAME, "fireRules");
    return response;
  }
}

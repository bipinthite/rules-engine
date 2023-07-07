package com.example.rules.controller.v1;

import static com.example.rules.api.RulesServiceEndpoints.ADMIN;
import static com.example.rules.api.RulesServiceEndpoints.ENDPOINT_ADMIN_RELOAD_RULES;
import static com.example.rules.api.RulesServiceEndpoints.V1;

import com.example.rules.service.RulesAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * RulesAdminController.
 *
 * @author Bipin Thite
 */
@SuppressWarnings("unused")
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@RestController
@RequestMapping(V1 + ADMIN)
public class RulesAdminController {

  private final RulesAdminService service;

  @PostMapping(ENDPOINT_ADMIN_RELOAD_RULES)
  public void reloadRules() {
    // Secure this end-point
    service.reload();
  }
}

package com.example.rules.controller.v1;

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
@RequestMapping("/v1/admin")
public class RulesAdminController {

  private final RulesAdminService service;

  @PostMapping("/reload")
  public void reloadRules() {
    // Secure this end-point
    service.reload();
  }
}

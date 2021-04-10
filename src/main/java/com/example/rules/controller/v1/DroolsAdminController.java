package com.example.rules.controller.v1;

import com.example.rules.service.DroolsAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * DroolsAdminController.
 */
@SuppressWarnings("unused")
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@RestController
@RequestMapping("/v1/admin")
public class DroolsAdminController {

  private final DroolsAdminService service;

  @PostMapping("/reload")
  public void reloadRules() {
    // Secure this end-point
    service.reload();
  }

}

package com.example.rules.service;

import com.example.drools.services.DroolsAdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Drools Admin service.
 *
 * @author Bipin Thite
 */
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Service
public class RulesAdminService {

  private final DroolsAdminService droolsAdminService;

  /**
   * Reload rules.
   */
  public void reload() {
    droolsAdminService.reload();
  }
}

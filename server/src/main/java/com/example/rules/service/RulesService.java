package com.example.rules.service;

import com.example.drools.services.DroolsRulesService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Rules Service.
 *
 * @author Bipin Thite
 */
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Service
public class RulesService {

  private final DroolsRulesService droolsRulesService;

  /**
   * Fire the rules.
   *
   * @param facts one or more facts
   * @param kb name of the knowledge base
   * @return results
   */
  public Map<String, Object> fireRules(final Map<String, Object> facts, final String kb) {
    return droolsRulesService.fireRules(facts, kb);
  }
}

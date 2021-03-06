package com.example.rules.service;

import com.example.rules.exception.KnowledgeBaseNotFoundException;
import com.example.rules.exception.RuleEvaluationException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.drools.core.common.DefaultFactHandle;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Rules Service.
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@Service
public class RulesService {

  private static final String CLASS_NAME = RulesService.class.getSimpleName();

  private final DroolsAdminService droolsAdminService;

  /**
   * Fire rules.
   */
  public Map<String, Object> fireRules(final Map<String, Object> facts, final String kb) {
    log.trace("ENTRY {} {}", CLASS_NAME, "fireRules");
    log.debug("facts={}, kb={}", facts, kb);

    // Get KieSession
    final KieSession kieSession = getKieSession(kb);

    try {
      // Insert the facts
      kieSession.insert(facts);

      // Fire the rules
      final int ruleFired = kieSession.fireAllRules();
      log.info("Rules fired: {}", ruleFired);

      final Map<String, Object> results = getResultsFrom(kieSession);
      log.debug("Results: {}", results);

      return results;
    } catch (Exception e) {
      throw new RuleEvaluationException("Error occured while evaluating the rules", e);
    } finally {
      // Destroy the KieSession
      kieSession.destroy();
      log.trace("EXIT {} {}", CLASS_NAME, "fireRules");
    }
  }

  /**
   * Get KieSession instance for given KnowledgeBase name.
   *
   * @param kb The name of the knowledge base
   * @return Instance of KieSession
   * @throws KnowledgeBaseNotFoundException if knowledge base doesn't exist with given name
   */
  private KieSession getKieSession(final String kb) {
    // Get Kie Session from KieContainer
    final KieSession kieSession =
            droolsAdminService.getKieContainer().getKieBase(kb).newKieSession();

    if (kieSession == null) {
      throw new KnowledgeBaseNotFoundException(
              "Could not create Kie Session for knowledge base " + kb);
    }

    return kieSession;
  }

  private Map<String, Object> getResultsFrom(final KieSession kieSession) {
    final Collection<DefaultFactHandle> factHandles = kieSession.getFactHandles();

    final Map<String, Object> results = new HashMap<>(factHandles.size());

    factHandles.iterator()
               .forEachRemaining(
                       factHandle -> results.put(String.valueOf(factHandle.getId()),
                                                 factHandle.getObject()));

    return results;
  }
}

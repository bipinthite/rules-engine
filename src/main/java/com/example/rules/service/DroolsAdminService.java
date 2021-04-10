package com.example.rules.service;

import com.example.rules.exception.KieContainerInitializationException;
import com.example.rules.exception.RulesBuildException;
import com.example.rules.model.KnowledgeBase;
import com.example.rules.model.Rule;
import com.example.rules.repository.KnowledgeBaseRepository;
import com.example.rules.repository.RuleRepository;
import java.nio.charset.StandardCharsets;
import java.util.List;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.builder.model.KieBaseModel;
import org.kie.api.builder.model.KieModuleModel;
import org.kie.api.io.Resource;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Drools Admin service.s
 */
@Slf4j
@RequiredArgsConstructor(onConstructor = @__({@Autowired}))
@Service
public class DroolsAdminService {

  private static final String CLASS_NAME = DroolsAdminService.class.getSimpleName();

  private static final String RULES_PATH = "src/main/resources/rules";

  private final KnowledgeBaseRepository knowledgeBaseRepository;
  private final RuleRepository ruleRepository;

  private KieContainer kieContainer;

  /**
   * Initialize the service.
   */
  @SuppressWarnings("unused")
  @PostConstruct
  public void init() {
    if (kieContainer == null) {
      reload();
    }
  }

  public KieContainer getKieContainer() {
    return kieContainer;
  }

  public void setKieContainer(final KieContainer newKieContainer) {
    this.kieContainer = newKieContainer;
  }

  /**
   * Reload rules.
   */
  public void reload() {
    try {
      log.info("Loading KieContainer from DB...");
      setKieContainer(loadContainerFromDb());
      log.info("KieContainer is loaded from DB");
    } catch (Exception e) {
      throw new KieContainerInitializationException(
              "Error occurred while loading the KIE container", e);
    }
  }

  private KieContainer loadContainerFromDb() {
    log.trace("ENTRY {} {}", CLASS_NAME, "loadContainerFromDb");
    final KieServices kieServices = KieServices.Factory.get();

    // Create new KieModule
    final KieModuleModel kieModule = kieServices.newKieModuleModel();

    // Build Knowledge Bases
    buildKnowledgeBases(kieModule);

    final KieFileSystem kfs = kieServices.newKieFileSystem();

    kfs.writeKModuleXML(kieModule.toXML());

    buildRules(kfs);

    final KieBuilder kieBuilder = kieServices.newKieBuilder(kfs).buildAll();

    final List<Message> buildErrors = kieBuilder.getResults().getMessages(Message.Level.ERROR);
    if (!buildErrors.isEmpty()) {
      throw new RulesBuildException("Error while building the rules. " + buildErrors);
    }

    // Create new KieContainer
    final KieContainer newKieContainer =
            kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());

    log.trace("RETURN {} {}", CLASS_NAME, "loadContainerFromDB");
    return newKieContainer;
  }

  private void buildKnowledgeBases(final KieModuleModel kieModuleModel) {
    final List<KnowledgeBase> knowledgeBases = knowledgeBaseRepository.findAll();
    for (final KnowledgeBase knowledgeBase : knowledgeBases) {
      final KieBaseModel kieBase = kieModuleModel.newKieBaseModel(knowledgeBase.getName());
      kieBase.addPackage(knowledgeBase.getPackages());
      kieBase.newKieSessionModel(knowledgeBase.getName());
    }
  }

  private Resource buildResourceFromRuleData(final Rule rule) {
    Resource resource;

    final Rule.ResourceType resourceType = rule.getResourceType();

    switch (resourceType) {
      // Decision table
      case DTABLE:
        resource =
                ResourceFactory.newByteArrayResource(rule.getResourceData())
                               .setResourceType(ResourceType.DTABLE);
        break;
      // DRL
      case DRL:
      default:
        resource =
                ResourceFactory.newByteArrayResource(
                        rule.getResourceContents().getBytes(StandardCharsets.UTF_8))
                               .setResourceType(ResourceType.DRL);
        break;
    }

    return resource;
  }

  private String buildRulePath(final Rule rule) {
    return RULES_PATH
           + '/'
           + rule.getPackageName().replace('.', '/')
           + '/'
           + rule.getResourceName();
  }

  private void buildRules(final KieFileSystem kfs) {
    final List<Rule> rules = ruleRepository.findAll();
    for (final Rule rule : rules) {
      kfs.write(buildRulePath(rule), buildResourceFromRuleData(rule));
    }
  }
}

package com.example.drools.services;

import com.example.drools.exceptions.KieContainerInitializationException;
import com.example.drools.exceptions.RulesBuildException;
import com.example.persistence.dao.KnowledgeBaseDao;
import com.example.persistence.dao.RuleDao;
import com.example.persistence.models.KnowledgeBase;
import com.example.persistence.models.Rule;
import jakarta.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.Getter;
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
 * Drools Admin service.
 *
 * @author Bipin Thite
 */
@Slf4j
@RequiredArgsConstructor(onConstructor_ = @Autowired)
@Service
@SuppressWarnings("PMD.BeanMembersShouldSerialize")
public class DroolsAdminService {

  private static final String RULES_PATH = "src/main/resources/rules";

  private final KnowledgeBaseDao knowledgeBaseDao;
  private final RuleDao ruleDao;

  @Getter private KieContainer kieContainer;

  /** Initialize the service. */
  @SuppressWarnings("unused")
  @PostConstruct
  public void init() {
    if (kieContainer == null) {
      reload();
    }
  }

  /** Reload rules. */
  public void reload() {
    try {
      log.info("Loading KieContainer from DB...");
      this.kieContainer = loadContainerFromDb();
      log.info("KieContainer is loaded from DB");
    } catch (Exception e) {
      throw new KieContainerInitializationException(
          "Error occurred while loading the KIE container", e);
    }
  }

  private KieContainer loadContainerFromDb() {

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
    // empty list indicates that rules are compiled successfully
    if (!buildErrors.isEmpty()) {
      throw new RulesBuildException("Error while building the rules. " + buildErrors);
    }

    // Create new KieContainer
    return kieServices.newKieContainer(kieServices.getRepository().getDefaultReleaseId());
  }

  private void buildKnowledgeBases(final KieModuleModel kieModuleModel) {
    final List<KnowledgeBase> knowledgeBases = knowledgeBaseDao.findAllKnowledgeBases();
    knowledgeBases.forEach(
        knowledgeBase -> {
          final KieBaseModel kieBase = kieModuleModel.newKieBaseModel(knowledgeBase.getName());
          kieBase.addPackage(knowledgeBase.getPackages());
          kieBase.newKieSessionModel(knowledgeBase.getName());
        });
  }

  private Optional<Resource> buildResourceFromRuleData(final Rule rule) {
    Resource resource;

    final Rule.ResourceType resourceType = rule.getResourceType();

    // Decision table
    if (Objects.requireNonNull(resourceType) == Rule.ResourceType.DTABLE) {
      resource =
          ResourceFactory.newByteArrayResource(rule.getResourceData())
              .setResourceType(ResourceType.DTABLE);
      // DRL
    } else if (Objects.requireNonNull(resourceType) == Rule.ResourceType.DRL) {
      resource =
          ResourceFactory.newByteArrayResource(
                  rule.getResourceContents().getBytes(StandardCharsets.UTF_8))
              .setResourceType(ResourceType.DRL);
    } else {
      log.warn("Unsupported or unknown resource type {} in rule {}", resourceType, rule);
      return Optional.empty();
    }

    return Optional.of(resource);
  }

  private String buildRulePath(final Rule rule) {
    return new StringBuilder(RULES_PATH)
        .append('/')
        .append(rule.getPackageName().replace('.', '/'))
        .append('/')
        .append(rule.getResourceName())
        .toString();
  }

  private void buildRules(final KieFileSystem kfs) {
    final List<Rule> rules = ruleDao.findAllRules();
    rules.forEach(
        rule -> buildResourceFromRuleData(rule).ifPresent(r -> kfs.write(buildRulePath(rule), r)));
  }
}

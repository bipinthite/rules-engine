package com.example.persistence.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * Rule.
 */
@Data
@Entity(name = "rules")
@SuppressWarnings("PMD.ProperCloneImplementation")
public class Rule implements Serializable, Cloneable {

  @Serial private static final long serialVersionUID = -8107471483688479908L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @ManyToOne
  @JoinColumn(name = "knowledgeBaseId", referencedColumnName = "id")
  private KnowledgeBase knowledgeBase;

  @Column(nullable = false, length = 256)
  private String packageName;

  @Column(nullable = false, length = 256)
  private String resourceName;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false, length = 20)
  private ResourceType resourceType;

  @Column(length = 4000)
  private String resourceContents;

  @Lob @Column private byte[] resourceData;

  @Column(nullable = false)
  private String version;

  @Column(nullable = false, insertable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;

  @Column(nullable = false, insertable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  public byte[] getResourceData() {
    return resourceData == null ? null : resourceData.clone();
  }

  public void setResourceData(final byte[] resourceData) {
    this.resourceData = resourceData == null ? null : resourceData.clone();
  }

  public Date getCreatedAt() {
    return createdAt == null ? null : new Date(createdAt.getTime());
  }

  public void setCreatedAt(final Date createdAt) {
    this.createdAt = createdAt == null ? null : new Date(createdAt.getTime());
  }

  public Date getUpdatedAt() {
    return updatedAt == null ? null : new Date(updatedAt.getTime());
  }

  public void setUpdatedAt(final Date updatedAt) {
    this.updatedAt = updatedAt == null ? null : new Date(updatedAt.getTime());
  }

  /**
   * Get knowledgeBase.
   *
   * @return KnowledgeBase
   */
  public KnowledgeBase getKnowledgeBase() {
    try {
      return knowledgeBase == null ? null : knowledgeBase.clone();
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException("Failed to clone knowledge base object", e);
    }
  }

  /**
   * Set knowledgeBase.
   *
   * @param knowledgeBase Knowledge base object
   */
  public void setKnowledgeBase(final KnowledgeBase knowledgeBase) {
    try {
      this.knowledgeBase = knowledgeBase == null ? null : knowledgeBase.clone();
    } catch (CloneNotSupportedException e) {
      throw new RuntimeException("Failed to clone knowledge base object", e);
    }
  }

  @Override
  public Rule clone() throws CloneNotSupportedException {
    super.clone();
    final Rule rule = new Rule();
    rule.setId(getId());
    rule.setName(getName());
    rule.setCreatedAt(getCreatedAt());
    rule.setUpdatedAt(getUpdatedAt());
    rule.setPackageName(getPackageName());
    rule.setResourceName(getResourceName());
    rule.setResourceData(getResourceData());
    rule.setResourceContents(getResourceContents());
    rule.setKnowledgeBase(getKnowledgeBase() == null ? null : getKnowledgeBase());
    return rule;
  }

  /**
   * Resource Type.
   */
  public enum ResourceType {
    DRL, GDRL, RDRL, XDRL, DSL, DSLR, RDSLR,
    DRF, BPMN2, CMMN, DTABLE, PKG, BRL, CHANGE_SET,
    XSD, PMML, DESCR, JAVA, PROPERTIES, SCARD, BAYES,
    TDRL, TEMPLATE, DRT, GDST, SCGD, SOLVER, DMN, FEEL
  }
}

package com.example.rules.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.ToString;

/**
 * Rule.
 */
@SuppressWarnings("unused")
@ToString
@Entity(name = "rules")
public class Rule implements Serializable {

  /**
   * Resource Type.
   */
  public enum ResourceType {
    DRL, GDRL, RDRL, XDRL, DSL, DSLR, RDSLR,
    DRF, BPMN2, CMMN, DTABLE, PKG, BRL, CHANGE_SET,
    XSD, PMML, DESCR, JAVA, PROPERTIES, SCARD, BAYES,
    TDRL, TEMPLATE, DRT, GDST, SCGD, SOLVER, DMN, FEEL
  }

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

  @Lob
  @Column(columnDefinition = "BLOB")
  private byte[] resourceData;

  @Column(nullable = false)
  private String version;

  @Column(nullable = false, insertable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;

  @Column(nullable = false, insertable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public KnowledgeBase getKnowledgeBase() {
    return knowledgeBase;
  }

  public void setKnowledgeBase(KnowledgeBase knowledgeBase) {
    this.knowledgeBase = knowledgeBase;
  }

  public String getPackageName() {
    return packageName;
  }

  public void setPackageName(String packageName) {
    this.packageName = packageName;
  }

  public String getResourceName() {
    return resourceName;
  }

  public void setResourceName(String resourceName) {
    this.resourceName = resourceName;
  }

  public ResourceType getResourceType() {
    return resourceType;
  }

  public void setResourceType(ResourceType resourceType) {
    this.resourceType = resourceType;
  }

  public String getResourceContents() {
    return resourceContents;
  }

  public void setResourceContents(String resourceContents) {
    this.resourceContents = resourceContents;
  }

  public byte[] getResourceData() {
    return resourceData;
  }

  public void setResourceData(byte[] resourceData) {
    this.resourceData = resourceData;
  }

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public Date getUpdatedAt() {
    return updatedAt;
  }

  public void setUpdatedAt(Date updatedAt) {
    this.updatedAt = updatedAt;
  }

  public Date getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }

}

package com.example.rules.model;

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
public class Rule implements Serializable {

  @Serial
  private static final long serialVersionUID = -8107471483688479908L;

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
  @Column
  private byte[] resourceData;

  @Column(nullable = false)
  private String version;

  @Column(nullable = false, insertable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;

  @Column(nullable = false, insertable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;
}

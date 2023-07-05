package com.example.rules.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import java.io.Serial;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * KnowledgeBase.
 */
@Data
@Entity(name = "knowledgeBases")
public class KnowledgeBase implements Serializable {

  @Serial
  private static final long serialVersionUID = -7590121515103561978L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false, unique = true)
  private String name;

  @Column(nullable = false)
  private String packages;

  @Column(unique = true, insertable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date updatedAt;

  @Column(unique = true, insertable = false, updatable = false)
  @Temporal(TemporalType.TIMESTAMP)
  private Date createdAt;
}

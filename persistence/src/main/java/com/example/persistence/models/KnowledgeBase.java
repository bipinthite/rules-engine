package com.example.persistence.models;

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
@SuppressWarnings("PMD.ProperCloneImplementation")
public class KnowledgeBase implements Serializable, Cloneable {

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

  @Override
  public KnowledgeBase clone() throws CloneNotSupportedException {
    super.clone();
    final KnowledgeBase knowledgeBase = new KnowledgeBase();
    knowledgeBase.setId(getId());
    knowledgeBase.setName(getName());
    knowledgeBase.setPackages(knowledgeBase.getPackages());
    knowledgeBase.setCreatedAt(getCreatedAt());
    knowledgeBase.setUpdatedAt(getUpdatedAt());
    return knowledgeBase;
  }
}

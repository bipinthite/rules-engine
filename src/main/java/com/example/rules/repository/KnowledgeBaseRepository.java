package com.example.rules.repository;

import com.example.rules.model.KnowledgeBase;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * KnowledgeBaseRepository.
 */
@SuppressWarnings("unused")
public interface KnowledgeBaseRepository extends JpaRepository<KnowledgeBase, Long> {
  KnowledgeBase findKnowledgeBaseByName(String name);
}

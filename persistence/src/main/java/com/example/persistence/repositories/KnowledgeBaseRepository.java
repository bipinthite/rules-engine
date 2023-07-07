package com.example.persistence.repositories;

import com.example.persistence.models.KnowledgeBase;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * KnowledgeBaseRepository.
 *
 * @author Bipin Thite
 */
@SuppressWarnings("unused")
public interface KnowledgeBaseRepository extends JpaRepository<KnowledgeBase, Long> {

  KnowledgeBase findKnowledgeBaseByName(String name);
}

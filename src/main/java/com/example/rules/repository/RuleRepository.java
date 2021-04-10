package com.example.rules.repository;

import com.example.rules.model.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * RuleRepository.
 */
@SuppressWarnings("unused")
public interface RuleRepository extends JpaRepository<Rule, Long> {
  Rule findByName(String name);
}

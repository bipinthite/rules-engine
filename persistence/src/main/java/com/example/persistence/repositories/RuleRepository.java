package com.example.persistence.repositories;

import com.example.persistence.models.Rule;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * RuleRepository.
 *
 * @author Bipin Thite
 */
@SuppressWarnings("unused")
public interface RuleRepository extends JpaRepository<Rule, Long> {

  Rule findByName(String name);
}

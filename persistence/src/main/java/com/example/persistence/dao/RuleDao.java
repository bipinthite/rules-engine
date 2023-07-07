package com.example.persistence.dao;

import com.example.persistence.models.Rule;
import com.example.persistence.repositories.RuleRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * RuleDao.
 *
 * @author Bipin Thite
 */
@Repository
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RuleDao {

  private final RuleRepository repository;

  public List<Rule> findAllRules() {
    return repository.findAll();
  }
}

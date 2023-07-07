package com.example.persistence.dao;

import com.example.persistence.models.KnowledgeBase;
import com.example.persistence.repositories.KnowledgeBaseRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * KnowledgeBaseDao.
 *
 * @author Bipin Thite
 */
@Repository
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class KnowledgeBaseDao {

  private final KnowledgeBaseRepository repository;

  public KnowledgeBase findKnowledgeBaseByName(String name) {
    return repository.findKnowledgeBaseByName(name);
  }

  public List<KnowledgeBase> findAllKnowledgeBases() {
    return repository.findAll();
  }
}

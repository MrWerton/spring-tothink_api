package com.notrew.tothink.modules.think.repositories;

import com.notrew.tothink.modules.think.entities.Think;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ThinkDAO {
    private EntityManager entityManager;

    @Autowired
    public ThinkDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<Think> findAllBySimpleQuery(LocalDateTime createdAt, String title){
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Think> criteriaQuery = criteriaBuilder.createQuery(Think.class);
        Root<Think> root = criteriaQuery.from(Think.class);
        Predicate titlePredicate = criteriaBuilder.like(root.get("title"), "¨%" + title+"%");
        Predicate createdPredicate = criteriaBuilder.like(root.get("createdAt"), "¨%" + createdAt+"%");
        Predicate orPredicate = criteriaBuilder.or(createdPredicate, titlePredicate);
        criteriaQuery.where(orPredicate);

        TypedQuery<Think> query= entityManager.createQuery(criteriaQuery);

        return  query.getResultList();

    }
}

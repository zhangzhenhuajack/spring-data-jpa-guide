package com.example.example2.repository.custom;

import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import javax.persistence.EntityManager;

@NoRepositoryBean
public class BaseRepositoryCustom<T, ID> extends SimpleJpaRepository<T, ID> {
    public BaseRepositoryCustom(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
    }

    public BaseRepositoryCustom(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
    }

}

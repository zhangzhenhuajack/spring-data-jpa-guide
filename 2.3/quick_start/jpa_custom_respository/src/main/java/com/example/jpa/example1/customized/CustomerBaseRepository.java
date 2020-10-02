package com.example.jpa.example1.customized;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
@Transactional(readOnly = true)
public class CustomerBaseRepository<T extends BaseEntity,ID> extends SimpleJpaRepository<T,ID>  {
    private final JpaEntityInformation<T, ?> entityInformation;
    private final EntityManager em;
    public CustomerBaseRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityInformation = entityInformation;
        this.em = entityManager;
    }

    public CustomerBaseRepository(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        entityInformation = null;
        this.em = em;
    }

    @Transactional
    @Override
    public void delete(T entity) {
        entity.setDeleted(Boolean.TRUE);
        em.merge(entity);
    }
}

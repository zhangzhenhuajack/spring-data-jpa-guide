package com.example.example2.repository.custom;

import com.example.example2.entity.User;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

/**
 * 用@Repository 将此实现交个Spring bean加载
 * 咱们模仿SimpleJpaRepository 默认将所有方法都开启一个事务
 */
@Repository
@Transactional(readOnly = true)
public class UserRepositoryCustomImpl implements UserRepositoryCustom {
    @PersistenceContext
    EntityManager entityManager;

    /**
     * 自定义一个查询firstname的方法
     * @param firstName
     * @return
     */
    @Override
    public List<User> customerMethodNamesLike(String firstName) {
        Query query = entityManager.createNativeQuery("SELECT u.* FROM user as u " +
                "WHERE u.name LIKE ?", User.class);
        query.setParameter(1, firstName + "%");
        return query.getResultList();
    }
}

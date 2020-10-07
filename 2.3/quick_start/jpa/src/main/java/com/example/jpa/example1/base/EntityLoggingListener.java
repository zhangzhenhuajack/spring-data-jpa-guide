package com.example.jpa.example1.base;

import com.example.jpa.example1.User;
import lombok.extern.log4j.Log4j2;

import javax.persistence.*;

@Log4j2
public class EntityLoggingListener {
    @PrePersist
    private void prePersist(BaseEntity entity) {
//        entity.setVersion(1); 如果注释了，测试用例这个地方的验证也需要去掉
        log.info("prePersist::{}",entity.toString());
    }

    @PostPersist
    public void postPersist(Object entity) {
        log.info("postPersist::{}",entity.toString());
    }
    @PreUpdate
    public void preUpdate(BaseEntity entity) {
//        entity.setCreateUserId(200); 如果注释了，测试用例这个地方的验证也需要去掉
        log.info("preUpdate::{}",entity.toString());
    }

    @PostUpdate
    public void postUpdate(Object entity) {
        log.info("postUpdate::{}",entity.toString());
    }

    @PreRemove
    public void preRemove(Object entity) {
        log.info("preRemove::{}",entity.toString());
    }

    @PostRemove
    public void postRemove(Object entity) {
        log.info("postRemove::{}",entity.toString());
    }

    @PostLoad
    public void postLoad(Object entity) {
//        查询方法方法里面可以对一些敏感信息做一些日志
        if (User.class.isInstance(entity)) {
            log.info("postLoad::{}",entity.toString());
        }
    }
}

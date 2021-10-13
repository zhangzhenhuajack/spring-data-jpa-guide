package com.example.jpa.demo.db;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QUserInfo is a Querydsl query type for UserInfo
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUserInfo extends EntityPathBase<UserInfo> {

    private static final long serialVersionUID = -579531742L;

    public static final QUserInfo userInfo = new QUserInfo("userInfo");

    public final com.example.jpa.demo.core.QBaseEntity _super = new com.example.jpa.demo.core.QBaseEntity(this);

    public final NumberPath<Integer> ages = createNumber("ages", Integer.class);

    //inherited
    public final DateTimePath<java.time.Instant> createTime = _super.createTime;

    //inherited
    public final NumberPath<Integer> createUserId = _super.createUserId;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    public final StringPath emailAddress = createString("emailAddress");

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.Instant> lastModifiedTime = _super.lastModifiedTime;

    //inherited
    public final NumberPath<Integer> lastModifiedUserId = _super.lastModifiedUserId;

    public final StringPath lastName = createString("lastName");

    public final StringPath name = createString("name");

    public final StringPath telephone = createString("telephone");

    public final StringPath url = createString("url");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QUserInfo(String variable) {
        super(UserInfo.class, forVariable(variable));
    }

    public QUserInfo(Path<? extends UserInfo> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserInfo(PathMetadata metadata) {
        super(UserInfo.class, metadata);
    }

}


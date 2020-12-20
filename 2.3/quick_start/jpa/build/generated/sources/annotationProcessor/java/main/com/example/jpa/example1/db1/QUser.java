package com.example.jpa.example1.db1;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -2134860023L;

    public static final QUser user = new QUser("user");

    public final com.example.jpa.example1.base.QBaseEntity _super = new com.example.jpa.example1.base.QBaseEntity(this);

    public final ListPath<UserAddress, QUserAddress> addresses = this.<UserAddress, QUserAddress>createList("addresses", UserAddress.class, QUserAddress.class, PathInits.DIRECT2);

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    //inherited
    public final DateTimePath<java.time.Instant> createTime = _super.createTime;

    //inherited
    public final NumberPath<Integer> createUserId = _super.createUserId;

    public final BooleanPath deleted = createBoolean("deleted");

    public final StringPath email = createString("email");

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.Instant> lastModifiedTime = _super.lastModifiedTime;

    //inherited
    public final NumberPath<Integer> lastModifiedUserId = _super.lastModifiedUserId;

    public final StringPath name = createString("name");

    public final EnumPath<SexEnum> sex = createEnum("sex", SexEnum.class);

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}


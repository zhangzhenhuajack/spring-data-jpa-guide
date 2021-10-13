package com.example.jpa.demo.db;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QRoom is a Querydsl query type for Room
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QRoom extends EntityPathBase<Room> {

    private static final long serialVersionUID = 1735889508L;

    public static final QRoom room = new QRoom("room");

    public final com.example.jpa.demo.core.QBaseEntity _super = new com.example.jpa.demo.core.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.Instant> createTime = _super.createTime;

    //inherited
    public final NumberPath<Integer> createUserId = _super.createUserId;

    //inherited
    public final BooleanPath deleted = _super.deleted;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final DateTimePath<java.time.Instant> lastModifiedTime = _super.lastModifiedTime;

    //inherited
    public final NumberPath<Integer> lastModifiedUserId = _super.lastModifiedUserId;

    public final StringPath title = createString("title");

    //inherited
    public final NumberPath<Integer> version = _super.version;

    public QRoom(String variable) {
        super(Room.class, forVariable(variable));
    }

    public QRoom(Path<? extends Room> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRoom(PathMetadata metadata) {
        super(Room.class, metadata);
    }

}


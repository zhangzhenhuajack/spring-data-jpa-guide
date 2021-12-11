package com.example.jpa.quick.core;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Optional;

/**
 * Base service, used to make the base configuration for the service layer class.
 *
 * @param <T>
 * @param <ID>
 */
public interface BaseService<T, ID> {

    /**
     * Get domain class, used to get domain class controlled by the service.
     *
     * @return
     */
    Class<T> getDomainClass();

    /**
     * Save, used to save a entity object.
     *
     * @param entity
     * @param <S>
     * @return
     */
    <S extends T> S save(S entity);

    /**
     * Save all, used to save some entity object.
     *
     * @param entities
     * @param <S>
     * @return
     */
    <S extends T> List<S> saveAll(Iterable<S> entities);

    /**
     * Delete, used to delete a entity object.
     *
     * @param entity
     */
    void delete(T entity);

    /**
     * Delete by id, used to delete a entity through its id.
     *
     * @param id
     */
    void deleteById(ID id);

    /**
     * Delete all, used to delete all generic T type entity.
     */
    void deleteAll();

    /**
     * Delete all, used to delete some entity object.
     *
     * @param entities
     */
    void deleteAll(Iterable<? extends T> entities);

    /**
     * Delete in batch, used to delete some entity object in a batch.
     *
     * @param entities
     */
    void deleteInBatch(Iterable<T> entities);

    /**
     * Delete all in batch, used to delete all generic T type entity in a batch.
     */
    void deleteAllInBatch();

    /**
     * Get one, used to get a entity object through its id.
     *
     * @param id
     * @return
     */
    T getOne(ID id);

    /**
     * Find one, used find a entity object through a example, entity object may not exist.
     *
     * @param example
     * @param <S>
     * @return
     */
    <S extends T> Optional<S> findOne(Example<S> example);

    /**
     * Find by id, used find a entity object through its id, entity object may not exist.
     *
     * @param id
     * @return
     */
    Optional<T> findById(ID id);

    /**
     * Find all, used to find all generic T type entity object.
     *
     * @return
     */
    List<T> findAll();

    /**
     * Find all, used to find all generic T type entity object and sort it.
     *
     * @param sort
     * @return
     */
    List<T> findAll(Sort sort);

    /**
     * Find all, used to find all generic T type entity object and page it.
     *
     * @param pageable
     * @return
     */
    Page<T> findAll(Pageable pageable);

    /**
     * Find all, used to find all generic T type entity object through a example.
     *
     * @param example
     * @param <S>
     * @return
     */
    <S extends T> List<S> findAll(Example<S> example);

    /**
     * Find all, used to find all generic T type entity object through a example and sort it.
     *
     * @param example
     * @param sort
     * @param <S>
     * @return
     */
    <S extends T> List<S> findAll(Example<S> example, Sort sort);

    /**
     * Find all, used to find all generic T type entity object through a example and page it.
     *
     * @param example
     * @param pageable
     * @param <S>
     * @return
     */
    <S extends T> Page<S> findAll(Example<S> example, Pageable pageable);

    /**
     * Find all by id, used to find some entity object through its some id.
     *
     * @param ids
     * @return
     */
    List<T> findAllById(Iterable<ID> ids);

    /**
     * Count, used to count the number of all generic T type entity.
     *
     * @return
     */
    long count();

    /**
     * Count, used to count the number of some entity through a example.
     *
     * @param example
     * @param <S>
     * @return
     */
    <S extends T> long count(Example<S> example);

    /**
     * Exists, used to determine whether an entity exists through a example.
     *
     * @param example
     * @param <S>
     * @return
     */
    <S extends T> boolean exists(Example<S> example);

    /**
     * Exists by id, used to determine whether an entity exists through its id.
     *
     * @param id
     * @return
     */
    boolean existsById(ID id);

    /**
     * Flush, used to flush all pending change to the database.
     */
    void flush();

    /**
     * Save and flush, used to save an entity object and flush change instantly.
     *
     * @param entity
     * @param <S>
     * @return
     */
    <S extends T> S saveAndFlush(S entity);

}

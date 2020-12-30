package com.example.data.es.demo.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface TopicRepository extends ElasticsearchRepository<Topic,Long> {
    List<Topic> findByTitle(String title);
}

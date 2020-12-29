package com.example.data.es.demo.es;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface TopicRepository extends ElasticsearchRepository<Topic,Long> {
}

package com.example.data.es.demo;

import com.example.data.es.demo.es.Author;
import com.example.data.es.demo.es.Topic;
import com.example.data.es.demo.es.TopicRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class ElasticSearchRepositoryTest {
    @Autowired
    private TopicRepository topicRepository;
    @Test
    public void testTopic() {
        Topic topic = Topic.builder().id(1L).title("jacktest").authors(Lists.newArrayList(Author.builder().name("jk1").build())).build();
        topicRepository.save(topic);

        Iterable<Topic> topics = topicRepository.findAll();
        topics.forEach(topic1 -> {
            System.out.println(topic1);
        });

    }
}

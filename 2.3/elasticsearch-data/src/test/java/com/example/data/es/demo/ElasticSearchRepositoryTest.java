package com.example.data.es.demo;

import com.example.data.es.demo.es.Author;
import com.example.data.es.demo.es.Topic;
import com.example.data.es.demo.es.TopicRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.List;

@SpringBootTest
@TestPropertySource(properties = {"logging.level.org.springframework.data.elasticsearch.core=TRACE", "logging.level.org.springframework.data.elasticsearch.client=trace", "logging.level.org.elasticsearch.client=TRACE", "logging.level.org.apache.http=TRACE"})
public class ElasticSearchRepositoryTest {
    @Autowired
    private TopicRepository topicRepository;

    @BeforeEach
    public void init() {
//        topicRepository.deleteAll();
        Topic topic = Topic.builder().id(11L).title("jacktest").authors(Lists.newArrayList(Author.builder().name("jk1").build())).build();
        topicRepository.save(topic);
        Topic topic1 = Topic.builder().id(14L).title("jacktest").authors(Lists.newArrayList(Author.builder().name("jk1").build())).build();
        topicRepository.save(topic1);
        Topic topic2 = Topic.builder().id(15L).title("jacktest").authors(Lists.newArrayList(Author.builder().name("jk1").build())).build();
        topicRepository.save(topic2);
    }

    @Test
    public void testTopic() {

        Iterable<Topic> topics = topicRepository.findAll();
        topics.forEach(topic1 -> {
            System.out.println(topic1);
        });
        List<Topic> topicList = topicRepository.findByTitle("jacktest");
        topicList.forEach(t -> {
            System.out.println(t);
        });

        List<Topic> topicList2 = topicRepository.findByTitle("xxx");
        topicList2.forEach(t -> {
            System.out.println(t);
        });

    }
}

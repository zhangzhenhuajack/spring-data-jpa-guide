package com.example.data.es.demo;

import com.example.data.es.demo.es.Topic;
import com.example.data.es.demo.es.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TopicController {
    @Autowired
    private TopicRepository topicRepository;
    //查询topic的所有索引
    @GetMapping("topics")
    public List<Topic> query(@Param("title") String title) {
        return topicRepository.findByTitle(title);
    }
    //保存 topic索引
    @PostMapping("topics")
    public Topic create(@RequestBody Topic topic) {
        return topicRepository.save(topic);
    }
}

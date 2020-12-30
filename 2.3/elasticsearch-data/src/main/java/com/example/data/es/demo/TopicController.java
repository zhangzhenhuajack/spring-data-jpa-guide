package com.example.data.es.demo;

import com.example.data.es.demo.es.Topic;
import com.example.data.es.demo.es.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TopicController {
    @Autowired
    private TopicRepository topicRepository;
    @GetMapping("topics")
    public List<Topic> query() {
        return topicRepository.findByTitle("jacktest");
    }
}

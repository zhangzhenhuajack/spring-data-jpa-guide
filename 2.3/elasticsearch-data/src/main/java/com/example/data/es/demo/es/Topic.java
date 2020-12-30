package com.example.data.es.demo.es;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Data
@Builder
@Document(indexName = "topic")
@ToString(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
public class Topic {
    @Id
    private Long id;
    private String title;
    @Field(type = FieldType.Nested, includeInParent = true)
    private List<Author> authors;
}


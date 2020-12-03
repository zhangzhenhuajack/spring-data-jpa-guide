package com.example.jpa.example1;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import org.assertj.core.util.Maps;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.util.StringUtils;

import javax.transaction.Transactional;
import java.io.IOException;
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserJsonRepositoryTest {
    @Autowired
    private UserJsonRepository userJsonRepository;

    @BeforeAll
    @Rollback(false)
    @Transactional
    void init() {
        UserJson user = UserJson.builder()
                .name("jackxx").createDate(Instant.now()).updateDate(new Date()).sex("men").email("123456@126.com").build();
        userJsonRepository.saveAndFlush(user);
    }
    /**
     * 测试用User关联关系操作
     *
     */
    @Test
    @Rollback(false)
    public void testUserJson() throws JsonProcessingException {
        UserJson userJson = userJsonRepository.findById(1L).get();
        userJson.setOther(Maps.newHashMap("address","shanghai"));
        //自定义 myInstant解析序列化和反序列化DateTimeFormatter.ISO_ZONED_DATE_TIME这种格式
       SimpleModule myInstant = new SimpleModule("instant", Version.unknownVersion())
                .addSerializer(java.time.Instant.class, new JsonSerializer<Instant>() {
                    @Override
                    public void serialize(java.time.Instant instant,
                                          JsonGenerator jsonGenerator,
                                          SerializerProvider serializerProvider)
                            throws IOException {
                        if (instant == null) {
                            jsonGenerator.writeNull();
                        } else {
                            jsonGenerator.writeObject(instant.toString());
                        }
                    }
                })
                .addDeserializer(Instant.class, new JsonDeserializer<Instant>() {
                    @Override
                    public Instant deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
                        Instant result = null;
                        String text = jsonParser.getText();
                        if (!StringUtils.isEmpty(text)) {
                            result = ZonedDateTime.parse(text, DateTimeFormatter.ISO_ZONED_DATE_TIME).toInstant();
                        }
                        return result;
                    }
                });


        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(myInstant);
        String json = objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(userJson);
        System.out.println(json);
    }
}

package com.example.jpa.example1.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate5.Hibernate5Module;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class MyConfig  implements WebMvcConfigurer {
    @Autowired
    ObjectMapper mapper;
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.forEach(c->{
            if(c instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter cc = (MappingJackson2HttpMessageConverter) c;
                Hibernate5Module module = new Hibernate5Module();
                module.disable(Hibernate5Module.Feature.REPLACE_PERSISTENT_COLLECTIONS);
                mapper.registerModule(module);
                cc.setObjectMapper(mapper);
            }

        });
    }

    @Bean
    public Hibernate5Module hibernate5Module() {
        return new Hibernate5Module().configure(Hibernate5Module.Feature.REPLACE_PERSISTENT_COLLECTIONS,false);
    }

//    @Bean
//    public Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder() {
//        return new Jackson2ObjectMapperBuilder().modules(hibernate5Module());
//    }
    @Bean
    @Primary
    public MappingJackson2HttpMessageConverter jacksonMessageConverter(ObjectMapper mapper){
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        Hibernate5Module module = new Hibernate5Module();
        module.disable(Hibernate5Module.Feature.REPLACE_PERSISTENT_COLLECTIONS);
        mapper.registerModule(module);
        messageConverter.setObjectMapper(mapper);
        return messageConverter;
    }
}

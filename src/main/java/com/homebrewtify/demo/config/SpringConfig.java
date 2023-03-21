package com.homebrewtify.demo.config;

import com.github.dozermapper.core.Mapper;
import com.github.dozermapper.core.MapperModelContext;
import com.github.dozermapper.core.MappingException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {
    @Bean
    public com.github.dozermapper.core.Mapper mapper(){
        return new Mapper() {
            @Override
            public <T> T map(Object source, Class<T> destinationClass) throws MappingException {
                return null;
            }

            @Override
            public void map(Object source, Object destination) throws MappingException {

            }

            @Override
            public <T> T map(Object source, Class<T> destinationClass, String mapId) throws MappingException {
                return null;
            }

            @Override
            public void map(Object source, Object destination, String mapId) throws MappingException {

            }

            @Override
            public MapperModelContext getMapperModelContext() {
                return null;
            }
        };
    }
}

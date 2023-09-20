package com.nttdata.bootcamp.msdocumentype;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.ReactiveRedisConnectionFactory;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import com.nttdata.bootcamp.msdocumentype.model.DocumentType;

@SpringBootApplication
public class MsDocumentypeApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsDocumentypeApplication.class, args);
	}
	
	@Bean
    public ReactiveRedisTemplate<String, DocumentType> reactiveJsonPostRedisTemplate(
        ReactiveRedisConnectionFactory connectionFactory) {

        RedisSerializationContext<String, DocumentType> serializationContext = RedisSerializationContext
            .<String, DocumentType>newSerializationContext(new StringRedisSerializer())
            .hashKey(new StringRedisSerializer())
            .hashValue(new Jackson2JsonRedisSerializer<>(DocumentType.class))
            .build();
        return new ReactiveRedisTemplate<>(connectionFactory, serializationContext);
    }

}

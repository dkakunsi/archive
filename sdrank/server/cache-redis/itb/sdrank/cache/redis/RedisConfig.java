package itb.sdrank.cache.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories("itb.sdrank.cache.redis")
@ComponentScan("itb.sdrank.cache.redis")
public class RedisConfig {

  @Bean(name = "redisTemplate")
  public RedisTemplate<String, String> sessionRedisTemplate() {
    JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();

    RedisTemplate<String, String> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(jedisConnectionFactory);
    redisTemplate.setKeySerializer(new StringRedisSerializer());
    redisTemplate.setValueSerializer(new StringRedisSerializer());
    return redisTemplate;
  }

}

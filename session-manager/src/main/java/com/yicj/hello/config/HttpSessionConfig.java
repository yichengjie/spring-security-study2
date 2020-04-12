package com.yicj.hello.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;

//启动基于Redis的httpSession实现
@EnableRedisHttpSession
public class HttpSessionConfig {

    //提供redis连接，默认localhost:6379
//    @Bean
//    public RedisConnectionFactory connectionFactory(){
//        RedisStandaloneConfiguration redisStandaloneConfiguration =
//                new RedisStandaloneConfiguration();
//        redisStandaloneConfiguration.setHostName("127.0.0.1");
//        redisStandaloneConfiguration.setDatabase(0);
//        redisStandaloneConfiguration.setPassword(RedisPassword.of("123456"));
//        redisStandaloneConfiguration.setPort(6379);
//        return new JedisConnectionFactory(redisStandaloneConfiguration) ;
//    }


    @Bean
    public JedisConnectionFactory connectionFactory() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName("localhost");
        redisStandaloneConfiguration.setDatabase(0);
        redisStandaloneConfiguration.setPassword(RedisPassword.of("123456"));
        redisStandaloneConfiguration.setPort(6379);
        return new JedisConnectionFactory(redisStandaloneConfiguration);
    }


    @Autowired
    private FindByIndexNameSessionRepository sessionRepository ;

    //SpringSessionBackedSessionRegistry是session为Spring Security提供的
    //用于在集群环境下控制会话并发的会话注册实现类
    @Bean
    public SpringSessionBackedSessionRegistry sessionRegistry(){
        return new SpringSessionBackedSessionRegistry(sessionRepository) ;
    }

    //httpSession 的事件监听，改用session提供的会话注册表
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher(){
        return new HttpSessionEventPublisher() ;
    }
}

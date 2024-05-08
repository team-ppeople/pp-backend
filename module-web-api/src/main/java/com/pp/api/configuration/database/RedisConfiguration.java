package com.pp.api.configuration.database;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.scripting.support.ResourceScriptSource;

@Configuration
public class RedisConfiguration {

    @Bean
    public RedisScript<Void> postThumbsUpScript() {
        ClassPathResource classPathResource = new ClassPathResource("script/lua/post-thumbs-up.lua");

        ResourceScriptSource resourceScriptSource = new ResourceScriptSource(classPathResource);

        DefaultRedisScript<Void> redisScript = new DefaultRedisScript<>();

        redisScript.setScriptSource(resourceScriptSource);
        redisScript.setResultType(Void.class);

        return redisScript;
    }

    @Bean
    public RedisScript<Void> postThumbsSidewaysScript() {
        ClassPathResource classPathResource = new ClassPathResource("script/lua/post-thumbs-sideways.lua");

        ResourceScriptSource resourceScriptSource = new ResourceScriptSource(classPathResource);

        DefaultRedisScript<Void> redisScript = new DefaultRedisScript<>();

        redisScript.setScriptSource(resourceScriptSource);
        redisScript.setResultType(Void.class);

        return redisScript;
    }

}

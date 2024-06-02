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
    public RedisScript<Void> thumbsUpPostScript() {
        ClassPathResource classPathResource = new ClassPathResource("script/lua/thumbs-up-post.lua");

        ResourceScriptSource resourceScriptSource = new ResourceScriptSource(classPathResource);

        DefaultRedisScript<Void> redisScript = new DefaultRedisScript<>();

        redisScript.setScriptSource(resourceScriptSource);
        redisScript.setResultType(Void.class);

        return redisScript;
    }

    @Bean
    public RedisScript<Void> thumbsSidewaysPostScript() {
        ClassPathResource classPathResource = new ClassPathResource("script/lua/thumbs-sideways-post.lua");

        ResourceScriptSource resourceScriptSource = new ResourceScriptSource(classPathResource);

        DefaultRedisScript<Void> redisScript = new DefaultRedisScript<>();

        redisScript.setScriptSource(resourceScriptSource);
        redisScript.setResultType(Void.class);

        return redisScript;
    }

    @Bean
    public RedisScript<Void> deleteThumbsUpPostScript() {
        ClassPathResource classPathResource = new ClassPathResource("script/lua/delete-thumbs-up-post.lua");

        ResourceScriptSource resourceScriptSource = new ResourceScriptSource(classPathResource);

        DefaultRedisScript<Void> redisScript = new DefaultRedisScript<>();

        redisScript.setScriptSource(resourceScriptSource);
        redisScript.setResultType(Void.class);

        return redisScript;
    }

}

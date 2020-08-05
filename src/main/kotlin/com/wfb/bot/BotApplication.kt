package com.wfb.bot

import com.wfb.bot.handle.MainHandle
import kotlinx.coroutines.runBlocking
import org.mybatis.spring.annotation.MapperScan
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@SpringBootApplication
@MapperScan(basePackages = ["com.wfb.bot.mapper"])
class BotApplication {

    @Autowired
    private lateinit var mainHandle: MainHandle

    @Bean
    fun commandLineRunner(): CommandLineRunner {
        return CommandLineRunner {
            runBlocking {
                mainHandle.doHandle()
            }
        }
    }

    @Bean
    fun redisTemplate(redisConnectionFactory: RedisConnectionFactory): RedisTemplate<String, Triple<Long, String, Boolean>> {
        val redisTemplate = RedisTemplate<String, Triple<Long, String, Boolean>>()
        redisTemplate.setConnectionFactory(redisConnectionFactory)
        redisTemplate.keySerializer = StringRedisSerializer() // key序列化
        redisTemplate.valueSerializer = JdkSerializationRedisSerializer() // value序列化
        redisTemplate.afterPropertiesSet()
        return redisTemplate
    }
}

fun main(args: Array<String>) {
    runApplication<BotApplication>(*args)
}

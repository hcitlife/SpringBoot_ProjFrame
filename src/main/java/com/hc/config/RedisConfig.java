package com.hc.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.hc.bean.Dept;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Field;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@EnableCaching //启用Redis缓存，这个注释必须加上，即使单元测试也得加上
@Configuration
//继承CachingConfigurerSupport，为了自定义生成KEY的策略，可以不继承。
public class RedisConfig extends CachingConfigurerSupport {

    //过期时间1天
    private Duration timeToLive = Duration.ofDays(1);

    private StringRedisSerializer keySerializer = new StringRedisSerializer();
    //使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值（默认使用JDK的序列化方式）
    private Jackson2JsonRedisSerializer valueSerializer = new Jackson2JsonRedisSerializer(Dept.class);

    { //解决缓存转换异常的问题
        ObjectMapper objectMapper = new ObjectMapper();
        //下面两行解决Java8新日期API序列化问题
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.registerModule(new JavaTimeModule());
        // 指定要序列化的域，field,get和set,以及修饰符范围，ANY是都有包括private和public
        //设置所有访问权限以及所有的实际类型都可序列化和反序列化
        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 指定序列化输入的类型，类必须是非final修饰的，final修饰的类，比如String,Integer等会跑出异常
        objectMapper.activateDefaultTyping(LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.PROPERTY);
        valueSerializer.setObjectMapper(objectMapper);
    }

    @Bean(name = "cacheManager")
    public RedisCacheManager cacheManager(RedisConnectionFactory factory) {
        // 配置序列化（解决乱码的问题），通过config对象对缓存进行自定义配置
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(this.timeToLive)// 设置缓存的默认过期时间
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(keySerializer))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(valueSerializer))
                .disableCachingNullValues();// 不缓存空值

        //缓存配置
        Map<String, RedisCacheConfiguration> cacheConfig = new HashMap<>();
        //自定义缓存名，后面使用的@Cacheable的CacheName
        cacheConfig.put("dept", config);

        //根据redis缓存配置和reid连接工厂生成redis缓存管理器
        RedisCacheManager redisCacheManager = RedisCacheManager.builder(factory)
                .cacheDefaults(config)
                .transactionAware()
                .withInitialCacheConfigurations(cacheConfig)
                .build();
        return redisCacheManager;
    }

    //缓存键自动生成器
    @Override
    @Bean(name = "myKeyGen")
    public KeyGenerator keyGenerator() { //设置自定义key{ClassName + methodName + params}
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(".");
            sb.append(method.getName());
            sb.append("(");
            for (int i = 0; i < params.length; i++) {
                sb.append(params[i].toString());
                if (i != (params.length - 1)) {
                    sb.append(",");
                }
            }
            sb.append(")");
            return sb.toString();
        };
    }

    //自定义keyGenerator，Key生成器
    @Bean(name = "updateByIdKeyGen")
    public KeyGenerator updateByIdkeyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(".");
            sb.append("findDeptByDeptno(");
            try {
                Field id = params[0].getClass().getDeclaredField("deptno");
                id.setAccessible(true);
                sb.append(id.get(params[0]).toString());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
            sb.append(")");
            return sb.toString();
        };
    }

    //自定义keyGenerator，Key生成器
    @Bean(name = "deleteByIdKeyGen")
    public KeyGenerator deleteByIdkeyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(".");
            sb.append("findDeptByDeptno(");
            for (int i = 0; i < params.length; i++) {
                sb.append(params[i].toString());
                if (i != (params.length - 1)) {
                    sb.append(",");
                }
            }
            sb.append(")");
            return sb.toString();
        };
    }


    /**
     * 在SpringBoot2.0之后，spring容器自动的生成了StringRedisTemplate和RedisTemplate<Object,Object>，可以直接注入
     * 但是在实际使用中，大多不会直接使用RedisTemplate<Object,Object>，而是会对key,value进行序列化，所以我们还需要新增一个配置类
     * 换句话说，由于原生的redis自动装配，在存储key和value时，没有设置序列化方式，故自己创建redisTemplate实例
     *
     * @param factory
     * @return
     */
    @Bean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory) {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        //配置连接工厂
        redisTemplate.setConnectionFactory(factory);

        //设置key的序列化规则
        redisTemplate.setKeySerializer(keySerializer);
        // hash的key也采用String的序列化方式
        redisTemplate.setHashKeySerializer(keySerializer);
        //设置value的序列化规则
        redisTemplate.setValueSerializer(valueSerializer);
        // hash的value序列化方式采用jackson
        redisTemplate.setHashValueSerializer(valueSerializer);

        redisTemplate.afterPropertiesSet();

        return redisTemplate;
    }

}

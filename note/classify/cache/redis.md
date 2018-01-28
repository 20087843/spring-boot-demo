# Redis


## introduce

## dependency
  ```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-redis</artifactId>
    </dependency>
  ```


## configuration
  ```text
    spring.redis.host=localhost
    spring.redis.port=6379
    spring.redis.pool.max-idle=8
    spring.redis.pool.min-idle=0
    spring.redis.pool.max-active=8
    spring.redis.pool.max-wait=-1
  ```

## reference
  1. [Spring Boot中使用Redis做集中式缓存](http://blog.didispace.com/springbootcache2/)
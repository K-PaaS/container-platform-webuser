package org.paasta.container.platform.web.user.login;


import org.paasta.container.platform.web.user.login.model.UsersLoginMetaData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

/**
 * Login Repository Impl 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2021.03.16
 **/
@Repository
public class LoginRepositoryImpl implements LoginRepository {

    @Value("${spring.redis.timeout}")
    private Integer redisTimeout;


    private RedisTemplate<String, UsersLoginMetaData> redisTemplate;
    private ValueOperations valueOperations;

    public LoginRepositoryImpl(RedisTemplate<String, UsersLoginMetaData> redisTemplate) {
        this.redisTemplate = redisTemplate;
        valueOperations = redisTemplate.opsForValue();
    }

    @Override
    public void save(UsersLoginMetaData usersLoginMetaData) {
        valueOperations.set(usersLoginMetaData.getUserId(), usersLoginMetaData, redisTimeout, TimeUnit.MINUTES);
    }


    @Override
    public UsersLoginMetaData findByUserId(String userId) {
        return (UsersLoginMetaData) valueOperations.get(userId);
    }

    @Override
    public void update(UsersLoginMetaData usersLoginMetaData) {
        save(usersLoginMetaData);
    }

    @Override
    public void delete(String userId) {
        redisTemplate.delete(userId);
    }
}

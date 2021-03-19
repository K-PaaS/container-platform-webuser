package org.paasta.container.platform.web.user.login;

import org.paasta.container.platform.web.user.common.Constants;
import org.paasta.container.platform.web.user.login.model.UsersLoginMetaData;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class LoginRepositoryImpl implements LoginRepository {

    private RedisTemplate<String, UsersLoginMetaData> redisTemplate;
    private HashOperations hashOperations;

    public LoginRepositoryImpl(RedisTemplate<String, UsersLoginMetaData> redisTemplate) {
        this.redisTemplate = redisTemplate;
        hashOperations = redisTemplate.opsForHash();
    }

    @Override
    public void save(UsersLoginMetaData usersLoginMetaData) {
        hashOperations.put(Constants.CP_USER_LOGIN_METADATA_REDIS_KEY, usersLoginMetaData.getUserId(), usersLoginMetaData);
    }

    @Override
    public Map<String, UsersLoginMetaData> findAll() {
       return hashOperations.entries(Constants.CP_USER_LOGIN_METADATA_REDIS_KEY);
    }

    @Override
    public UsersLoginMetaData findByUserId(String userId) {
        return (UsersLoginMetaData) hashOperations.get(Constants.CP_USER_LOGIN_METADATA_REDIS_KEY, userId);
    }

    @Override
    public void update(UsersLoginMetaData usersLoginMetaData) {
       save(usersLoginMetaData);
    }

    @Override
    public void delete(String userId) {
        hashOperations.delete(Constants.CP_USER_LOGIN_METADATA_REDIS_KEY, userId);
    }
}

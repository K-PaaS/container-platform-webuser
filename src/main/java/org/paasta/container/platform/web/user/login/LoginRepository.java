package org.paasta.container.platform.web.user.login;

import org.paasta.container.platform.web.user.login.model.UsersLoginMetaData;

import java.util.Map;

public interface LoginRepository {

    void save(UsersLoginMetaData usersLoginMetaData);

    Map<String, UsersLoginMetaData> findAll();

    UsersLoginMetaData findByUserId(String userId);

    void update(UsersLoginMetaData usersLoginMetaData);

    void delete(String userId);
}

package org.paasta.container.platform.web.user.login;

import org.paasta.container.platform.web.user.login.model.UsersLoginMetaData;

/**
 * Login Repository 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2021.03.16
 **/
public interface LoginRepository {

    void save(UsersLoginMetaData usersLoginMetaData);

    UsersLoginMetaData findByUserId(String userId);

    void update(UsersLoginMetaData usersLoginMetaData);

    void delete(String userId);
}

package org.paasta.container.platform.web.user.login;

import com.google.gson.Gson;
import org.paasta.container.platform.web.user.common.CommonUtils;
import org.paasta.container.platform.web.user.common.Constants;
import org.paasta.container.platform.web.user.common.model.ResultStatus;
import org.paasta.container.platform.web.user.login.model.UsersLoginMetaData;
import org.paasta.container.platform.web.user.users.Users;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.List;

/**
 * Login Service 클래스
 *
 * @author kjhoon
 * @version 1.0
 * @since 2021.03.16
 **/
@Service
public class LoginService {

    private LoginRepository loginRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginService.class);

    @Autowired
    public LoginService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    /**
     * Users 로그인 메타정보 Redis 저장 (Create Users Login Meta-Information in Redis)
     *
     * @param usersLoginMetaData the UsersLoginData
     * @return the UsersLoginData
     */
    public UsersLoginMetaData saveUsersLoginMetaData(UsersLoginMetaData usersLoginMetaData) {
        loginRepository.save(usersLoginMetaData);
        return loginRepository.findByUserId(usersLoginMetaData.getUserId());
    }

    /**
     * Users 로그인 메타정보 Redis 조회 (Get Users Login Meta-Information in Redis)
     *
     * @param userId the userId
     * @return the UsersLoginMetaData
     */
    public UsersLoginMetaData getUsersLoginMetaData(String userId) {
        return loginRepository.findByUserId(userId);
    }


    /**
     * Users 로그인 메타정보 Redis 수정 (Update Users Login Meta-Information in Redis)
     *
     * @param usersLoginMetaData the usersLoginMetaData
     * @return the UsersLoginMetaData
     */
    public UsersLoginMetaData updateUsersLoginMetaData(UsersLoginMetaData usersLoginMetaData) {
        loginRepository.update(usersLoginMetaData);
        return loginRepository.findByUserId(usersLoginMetaData.getUserId());
    }

    /**
     * Users 로그인 메타정보 Redis 삭제 (Delete Users Login Meta-Information in Redis)
     *
     * @param userId the userId
     */
    public void deleteUsersLoginMetaData(String userId) {
        loginRepository.delete(userId);
    }



    /**
     * Users 로그인 권한 인증 처리 (Users Login Permission Authentication Processing)
     *
     * @param users        the Users
     * @param resultStatus the ResultStatus
     */
    public void userAuthenticationHandler(Users users, ResultStatus resultStatus) {

        // 1. SecurityContextHolder 사용자 인증처리
        List<SimpleGrantedAuthority> roles = Arrays.asList(new SimpleGrantedAuthority(Constants.DEFAULT_AUTH));
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(resultStatus.getUserId(), users.getPassword(), roles);
        authentication.setDetails(authentication.getDetails());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        Gson gson = new Gson();
        String jsonLoginMetaData = gson.toJson(resultStatus.getLoginMetaData());


        // 2. Redis에 사용자 메타정보 저장
        UsersLoginMetaData usersLoginMetaData = new UsersLoginMetaData();
        usersLoginMetaData.setAccessToken(resultStatus.getToken());
        usersLoginMetaData.setClusterName(resultStatus.getClusterName());
        usersLoginMetaData.setUserId(resultStatus.getUserId());
        usersLoginMetaData.setSelectedNamespace("");
        usersLoginMetaData.setUserMetaData(jsonLoginMetaData);

        saveUsersLoginMetaData(usersLoginMetaData);
    }


    /**
     * 현재 로그인된 Users 메타 정보 조회 (Get Login Meta-Information of currently logged in users)
     *
     * @return the UsersLoginMetaData
     */
    public UsersLoginMetaData getAuthenticationUserMetaData() {

        String userId = null;

        try {
            userId = (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (NullPointerException e) {
            return null;
        }

        return getUsersLoginMetaData(userId);
    }


    /**
     * 현재 로그인된 Users 인증정보 변경 (Update the currently logged in user auth-Information)
     *
     * @param users the Users
     */
    public void updateAuthenticationUser(Users users) {
        List<SimpleGrantedAuthority> roles = Arrays.asList(new SimpleGrantedAuthority(Constants.DEFAULT_AUTH));
        Authentication current_authentication = SecurityContextHolder.getContext().getAuthentication();

        UsernamePasswordAuthenticationToken new_authentication = new UsernamePasswordAuthenticationToken(current_authentication.getPrincipal(), users.getPassword(), roles);
        new_authentication.setDetails(new_authentication.getDetails());

        SecurityContextHolder.getContext().setAuthentication(new_authentication);
    }


    /**
     * Users 로그아웃 처리 (Users Log-Out Process)
     *
     * @param session the HttpSession
     */
    public void userLogoutHandler(HttpSession session) {

        UsersLoginMetaData usersLoginMetaData = new UsersLoginMetaData();

        try {
            usersLoginMetaData = getAuthenticationUserMetaData();
            deleteUsersLoginMetaData(usersLoginMetaData.getUserId());

            SecurityContextHolder.clearContext();

            if (session != null) {
                session.invalidate();
            }

            LOGGER.info("The user [" + CommonUtils.loggerReplace(usersLoginMetaData.getUserId()) + "] Login MetaData deleted in Redis.");
            LOGGER.info("The user [" + CommonUtils.loggerReplace(usersLoginMetaData.getUserId()) + "] has logged out successfully.");

        } catch (Exception e) {
            LOGGER.info("THERE WAS A EXCEPTION DURING USER LOGOUT.");
        }


    }


}

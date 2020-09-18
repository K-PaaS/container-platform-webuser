package org.paasta.container.platform.web.user.refreshSession;

import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Enumeration;

/**
 * Refresh Session Service 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.17
 */
@Service
public class RefreshSessionService {

    public void removeRolesSession(HttpServletRequest request){

        // refresh session -> controller 로 만들어서 javascript 에서 호출.
        // 롤 변경해주는 시점에 변경 후 호출한 다음 callback 해줄 때 화면 reload.
        HttpSession session = request.getSession();

        Enumeration se = session.getAttributeNames();
        while(se.hasMoreElements()){
            String rsCode = (String)se.nextElement();

            if(rsCode.indexOf("RS_") == 0){
                session.removeAttribute(rsCode);
            }
        }

    }
}

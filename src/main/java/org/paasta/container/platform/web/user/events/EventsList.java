package org.paasta.container.platform.web.user.events;

import lombok.Data;

import java.util.List;

/**
 * Events List Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.14
 */
@Data
public class EventsList {

    private String resultCode;
    private String resultMessage;
    private List<Events> items;

    // FOR DASHBOARD
    private String resourceName;
    private String status;

}

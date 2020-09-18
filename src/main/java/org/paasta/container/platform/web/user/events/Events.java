package org.paasta.container.platform.web.user.events;

import lombok.Data;
import org.paasta.container.platform.web.user.common.model.CommonMetaData;
import org.paasta.container.platform.web.user.common.model.CommonObjectReference;


/**
 * Events Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.14
 */
@Data
public class Events {

    private String resultCode;
    private CommonMetaData metadata;
    //private String action;
    private int count;
    //private String eventTime;
    private String firstTimestamp;
    private String lastTimestamp;
    private String message;
    //private String reason;
    private EventSource source;
    private String type;
    private CommonObjectReference involvedObject;

    @Data
    public class EventSource {
        private String component;
        private String host;
    }
}

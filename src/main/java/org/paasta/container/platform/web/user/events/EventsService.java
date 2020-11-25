package org.paasta.container.platform.web.user.events;

import org.paasta.container.platform.web.user.common.Constants;
import org.paasta.container.platform.web.user.common.RestTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

/**
 * Events Service 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.14
 */
@Service
public class EventsService {

    private final RestTemplateService restTemplateService;

    /**
     * Instantiates a new Events service
     *
     * @param restTemplateService the rest template service
     */
    @Autowired
    public EventsService(RestTemplateService restTemplateService) {this.restTemplateService = restTemplateService;}

    /**
     * Events 목록 조회(Get Events list)
     *
     * @param cluster   the cluster
     * @param namespace the namespace Name
     * @param resourceUid the resource Uid
     * @param type the type
     * @return the events list
     */
    EventsList getEventsList(String cluster, String namespace, String resourceUid, String type) {
        if(type != null) {
            return restTemplateService.send(Constants.TARGET_CP_API, "/clusters/" + cluster + "/namespaces/" + namespace + "/events/resources/" + resourceUid + "?type=" + type, HttpMethod.GET, null, EventsList.class);
        }
        return restTemplateService.send(Constants.TARGET_CP_API, "/clusters/" + cluster + "/namespaces/" + namespace + "/events/resources/" + resourceUid, HttpMethod.GET, null, EventsList.class);
    }

    /**
     * Events 목록을 조회(Get Events namespace)
     *
     * @param cluster   the cluster
     * @param namespace the namespace Name
     * @return the events list
     */
    EventsList getNamespaceEventsList(String cluster, String namespace) {
        return restTemplateService.send(Constants.TARGET_CP_API, "/clusters/" + cluster + "/namespaces/" + namespace + "/events", HttpMethod.GET, null, EventsList.class);
    }
}

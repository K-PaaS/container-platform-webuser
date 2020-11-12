package org.paasta.container.platform.web.user.events;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.paasta.container.platform.web.user.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Events Controller 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.14
 */
@Api(value = "EventsController v1")
@Controller
public class EventsController {
    private final EventsService eventsService;

    /**
     * Instantiates a new Events controller
     *
     * @param eventsService the event service
     */
    @Autowired
    public EventsController(EventsService eventsService) {
        this.eventsService = eventsService;
    }

    /**
     * Events 목록 조회(Get Events list)
     *
     * @param namespace the namespace
     * @param resourceUid the resourceUid
     * @param type the type
     * @param  status the status
     * @return the events list
     */
    @ApiOperation(value = "Events 목록 조회(Get Events list)", nickname = "getEventsList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "resourceUid", value = "리소스 uid",  required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "type", value = "타입", required = false, dataType = "String", paramType = "query"),
            @ApiImplicitParam(name = "status", value = "상태", required = false, dataType = "String", paramType = "query")
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_EVENTS_LIST)
    @ResponseBody
    EventsList getEventsList(@PathVariable("namespace") String namespace,
                             @PathVariable("resourceUid") String resourceUid,
                             @RequestParam(value="type", required=false) String type,
                             @RequestParam(value="status", required=false) String status) {
        EventsList resultList = eventsService.getEventsList(namespace, resourceUid, type);

        // FOR DASHBOARD
        resultList.setResourceName(resourceUid);
        if(status != null) {
            resultList.setStatus(status);
        }
        return resultList;
    }

    /**
     * Events 목록 조회(Get Events namespace)
     *
     * @param namespace the namespace
     * @return the events list
     */
    @ApiOperation(value = "Events 목록 조회(Get Events namespace)", nickname = "getNamespaceEventsList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "namespace", value = "네임스페이스 명", required = true, dataType = "String", paramType = "path")
    })
    @GetMapping(value = Constants.API_URL + Constants.URI_API_NAMESPACE_EVENTS_LIST)
    @ResponseBody
    EventsList getNamespaceEventsList(@PathVariable("namespace") String namespace) {
        return eventsService.getNamespaceEventsList(namespace);
    }
}

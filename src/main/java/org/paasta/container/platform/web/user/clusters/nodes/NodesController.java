package org.paasta.container.platform.web.user.clusters.nodes;

import org.paasta.container.platform.web.user.common.CommonService;
import org.paasta.container.platform.web.user.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * Nodes Controller 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.01
 */
@RestController
public class NodesController {
    private static final String VIEW_URL = "/nodes";
    private final CommonService commonService;
    private final NodesService nodesService;

    /**
     * Instantiates a new Nodes controller
     *
     * @param commonService the common service
     * @param nodesService  the nodes service
     */
    @Autowired
    public NodesController(CommonService commonService, NodesService nodesService) {
        this.commonService = commonService;
        this.nodesService = nodesService;
    }

    /**
     * Nodes detail 페이지 이동(Move Namespaces detail page)
     *
     * @param httpServletRequest the http servlet request
     * @param nodeName the nodes name
     * @return the nodes detail
     */
    @GetMapping(value = Constants.URI_CLUSTER_NODES + "/{nodeName:.+}")
    public ModelAndView getNodesDetails(HttpServletRequest httpServletRequest, @PathVariable(value = "nodeName") String nodeName) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/details", new ModelAndView());
    }

    /**
     * Nodes summary 페이지 이동(Move Nodes summary page)
     *
     * @param httpServletRequest the http servlet request
     * @param nodeName the nodes name
     * @return the nodes summary
     */
    @GetMapping(value = Constants.URI_CLUSTER_NODES + "/{nodeName:.+}/summary")
    public ModelAndView getNodesSummary(HttpServletRequest httpServletRequest, @PathVariable("nodeName") String nodeName) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/summary", new ModelAndView());
    }

    /**
     * Nodes events 페이지 이동(Move Nodes event page)
     *
     * @param httpServletRequest the http servlet request
     * @param nodeName the nodes name
     * @return the nodes event
     */
    @GetMapping(value = Constants.URI_CLUSTER_NODES + "/{nodeName:.+}/events")
    public ModelAndView getNodesEvents(HttpServletRequest httpServletRequest, @PathVariable("nodeName") String nodeName) {
        return commonService.setPathVariables(httpServletRequest, VIEW_URL + "/events", new ModelAndView());
    }

    /**
     * Nodes 상세 조회(Get Nodes detail)
     *
     * @param nodeName the nodes name
     * @return the nodes detail
     */
    @GetMapping(value = Constants.API_URL + Constants.URI_API_NODES_LIST)
    public Nodes getNodes(@PathVariable("nodeName") String nodeName) {
        return nodesService.getNodes(nodeName);
    }
}
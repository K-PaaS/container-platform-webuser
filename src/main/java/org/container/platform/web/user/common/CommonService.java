package org.container.platform.web.user.common;

import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Common Service 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.08.25
 */
@Service
public class CommonService {

    /**
     * Sets path variables
     *
     * @param httpServletRequest the http servlet request
     * @param viewName           the view name
     * @param mv                 the mv
     * @return the path variables
     */
    public ModelAndView setPathVariables(HttpServletRequest httpServletRequest, String viewName, ModelAndView mv) {
        Map pathVariablesMap = (Map) httpServletRequest.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
        Object pathVariablesObject;
        String pathVariablesKey;

        RequestWrapper requestWrapper = new RequestWrapper(httpServletRequest);
        Map<String, String[]> parametersMap = requestWrapper.getParameterMap();
        String[] parametersObject;
        String parametersKey;

        for (int i = 0; i < PathVariablesList.values().length; i++) {
            pathVariablesKey = PathVariablesList.values()[i].actualValue;
            pathVariablesObject = pathVariablesMap.get(pathVariablesKey);

            if (pathVariablesObject != null) {
                mv.addObject(pathVariablesKey, String.valueOf(pathVariablesObject));
            }
        }

        for (int i = 0; i < ParametersList.values().length; i++) {
            parametersKey = ParametersList.values()[i].actualValue;
            parametersObject = parametersMap.get(parametersKey);

            if (parametersObject != null && !"".equals(parametersObject[0])) {
                mv.addObject(parametersKey, parametersObject[0]);
            }
        }

        mv.setViewName(viewName);

        return mv;
    }


    /**
     * The enum Path variables list
     */
    enum PathVariablesList {
        /**
         * Path service name path variables list
         */
        PATH_SERVICE_NAME("serviceName"),
        /**
         * Path variables id path variables list
         */
        PATH_VARIABLES_ID("id");

        private String actualValue;

        PathVariablesList(String actualValue) {
            this.actualValue = actualValue;
        }
    }


    /**
     * The enum Parameters list
     */
    enum ParametersList {
        /**
         * Parameters id parameters list
         */
        PARAMETERS_ID("id"),
        /**
         * Parameters name parameters list
         */
        PARAMETERS_NAME("name"),
        /**
         * Parameters page parameters list
         */
        PARAMETERS_PAGE("page"),
        /**
         * Parameters size parameters list
         */
        PARAMETERS_SIZE("size"),
        /**
         * Parameters sort parameters list
         */
        PARAMETERS_SORT("sort");

        private String actualValue;

        ParametersList(String actualValue) {
            this.actualValue = actualValue;
        }
    }

}

package org.container.platform.web.user.clusters.limitRanges;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.container.platform.web.user.common.model.CommonItemMetaData;
import org.container.platform.web.user.common.model.CommonMetaData;
import org.container.platform.web.user.common.model.CommonSpec;


import java.util.List;
import java.util.Map;

/**
 * LimitRanges Template List Model 클래스
 *
 * @author hrjin
 * @version 1.0
 * @since 2020.10.28
 **/
@Data
public class LimitRangesTemplateList {
    private String resultCode;
    private String resultMessage;
    private Integer httpStatusCode;
    private String detailMessage;
    private Map metadata;
    private CommonItemMetaData itemMetaData;

    private List<LimitRangesTemplateItem> items;
}

@Data
class LimitRangesTemplateItem {
    private String name;
    private String type;
    private String resource;
    private String min;
    private String max;
    private String defaultRequest;
    private String defaultLimit;

    private String checkYn;
    private String creationTimestamp;

    @JsonIgnore
    private CommonMetaData metadata;

    @JsonIgnore
    private CommonSpec spec;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getResource() {
        return resource;
    }

    public void setResource(String resource) {
        this.resource = resource;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getDefaultRequest() {
        return defaultRequest;
    }

    public void setDefaultRequest(String defaultRequest) {
        this.defaultRequest = defaultRequest;
    }

    public String getDefaultLimit() {
        return defaultLimit;
    }

    public void setDefaultLimit(String defaultLimit) {
        this.defaultLimit = defaultLimit;
    }

    public String getCheckYn() {
        return checkYn;
    }

    public void setCheckYn(String checkYn) {
        this.checkYn = checkYn;
    }

    public String getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(String creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public CommonMetaData getMetadata() {
        return metadata;
    }

    public void setMetadata(CommonMetaData metadata) {
        this.metadata = metadata;
    }

    public CommonSpec getSpec() {
        return spec;
    }

    public void setSpec(CommonSpec spec) {
        this.spec = spec;
    }
}
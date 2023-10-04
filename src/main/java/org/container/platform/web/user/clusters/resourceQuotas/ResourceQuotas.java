package org.container.platform.web.user.clusters.resourceQuotas;

import lombok.Data;
import org.container.platform.web.user.clusters.resourceQuotas.support.ResourceQuotasSpec;
import org.container.platform.web.user.clusters.resourceQuotas.support.ResourceQuotasStatus;
import org.container.platform.web.user.common.model.CommonMetaData;

import java.util.Map;

/**
 * ResourceQuotas Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.03
 */
@Data
public class ResourceQuotas {

  private String resultCode;
  private String resultMessage;

  private String apiVersion;
  private String kind;
  private CommonMetaData metadata;
  private ResourceQuotasSpec spec;
  private ResourceQuotasStatus status;
  private Map<String, Object> resourceQuotasStatus;
}


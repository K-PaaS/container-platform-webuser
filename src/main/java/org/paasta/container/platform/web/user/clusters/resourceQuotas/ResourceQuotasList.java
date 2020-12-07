package org.paasta.container.platform.web.user.clusters.resourceQuotas;

import lombok.Data;

import java.util.List;

/**
 * ResourceQuota List Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.03
 */
@Data
public class ResourceQuotasList {

  private String resultCode;
  private String resultMessage;
  private List<ResourceQuotas> items;

}


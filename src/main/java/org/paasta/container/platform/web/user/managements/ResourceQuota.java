package org.paasta.container.platform.web.user.managements;

import lombok.Data;
import org.paasta.container.platform.web.user.common.model.CommonMetaData;

/**
 * ResourceQuota Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.09.03
 */
@Data
public class ResourceQuota {

  private String resultCode;
  private String resultMessage;

  private String apiVersion;
  private String kind;
  private CommonMetaData metadata;
  private ResourceQuotaSpec spec;
  private ResourceQuotaStatus status;

}


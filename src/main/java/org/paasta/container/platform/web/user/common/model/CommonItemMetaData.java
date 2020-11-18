package org.paasta.container.platform.web.user.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Common Item Meta Data Model 클래스
 *
 * @author jjy
 * @version 1.0
 * @since 2020.11.18
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonItemMetaData {

    private Integer allItemCount;
    private Integer remainingItemCount;

}

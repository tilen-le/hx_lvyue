package com.hexing.system.domain.form;

import lombok.Data;

/**
 * @author firerock_tech
 */
@Data
public class SapFhdForm {
    /**
     * {
     *     "types": "E",
     *     "zmsg": "流程已成功执行！重复执行！",
     *     "ebeln": "4500202939",
     *     "vbeln": "1800213221"
     * }
     */
    private String types;
    private String zmsg;
    private Double ebeln;
    private Double vbeln;
}

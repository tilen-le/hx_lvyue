package com.hexing.system.domain.form;

import lombok.Data;

/**
 * @author jinguo
 */
@Data
public class SapJhkpForm {
    /**
     * {
     *     "data": {
     *         "ev_type": "S",
     *         "ev_message": "流程已成功执行！重复执行！",
     *         "vl": "7000036378",
     *         "vf": "9000047841"
     *     }
     * }
     */
    private String ev_type;
    private String ev_message;
    private Double vl;
    private Double vf;
}

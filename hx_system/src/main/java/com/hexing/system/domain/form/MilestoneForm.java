package com.hexing.system.domain.form;

import lombok.Data;


/**
 * @author 金国
 */

@Data
public class MilestoneForm {

    /**
     * {
     *    "xh":"1", //序号
     *    "zpayname":"到货日期", //
     *    "zpayscale":"1.0", //期待回款比例
     *    "vbeln":"PN23.04006", //订单编号
     *    "zpayday":"90",  //付款天数
     *    "zpaynode":"Z013", //里程碑编码
     *    "zkxname":"到货款" //里程碑类型
     *   }
     */
    private Integer xh;
    private String zpayname;
    private String zpayscale;
    private String vbeln;
    private String zpayday;
    private String zpaynode;
    private String zkxname;

}

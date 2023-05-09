package com.hexing.system.domain.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author firerock_tech
 */
@Data
public class PaymentForm {
    private String hsl;
    private String kursf;
    private String bukrs;
    private String rwcur;
    private String tslOa;
    private String tsl;
    private String blart;
    private String kunnr;
    private String landx;
    private String tsl01;
    private String zuonr;
    private String buzei;
    private String belnr;
    private String land1;
    private String name1;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date budat;


}

package com.hexing.system.domain.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

/**
 * @author firerock_tech
 */

@Data
public class OrderForm {

    private String flag;
    private String vsnmrV;
    private String posnr;
    private String posnrRe;
    private String vbelnT;
    private String matnr;
    private String vbelnRe;
    private String kunnrPy;
    private String netprZpr0;
    private String zywlx;
    private String kunnrBp;
    private String vbeln;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date vdatu;
    private String vkbur;
    private String zgj;
    private String kunnrSh;
    private String vkgrp;
    private String zmeng;
    private String auart;
    private String kunnrSp;
    private String kunnrSpT;
    private String zltcso;
    private String vtweg;
    private String spart;
    private String werks;
    private String zspare9;
    private String kdmat;
    private String kunnrEr;
    private String zspare2;
    private String zywzl;
    private String vkorg;
    private String amount;
    private String zsj;
    private String kunnrPyT;
    private String kunnrBpT;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date erdat;
}

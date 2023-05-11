package com.hexing.system.domain.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hexing.system.domain.FcOrderProduct;
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

    private String zgnw;
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
    private String kunnrShT;
    private String vkgrp;
    private String zmeng;
    private String auart;
    private String auartT;
    private String kunnrSp;
    private String kunnrSpT;
    private String zltcso;
    private String vtweg;
    private String vtwegT;
    private String spart;
    private String werks;
    private String zspare9;
    private String kdmat;
    private String kunnrEr;
    private String kunnrErT;
    private String zspare2;
    private String zywzl;
    private String vkorg;
    private String amount;
    private String zsj;
    private String kunnrPyT;
    private String kunnrBpT;
    private String maktx;
    private String waers;
    private String itext2;
    private String zsl;
    private String zspare5;
    private String zspare5T;
    private String vtext;
    private String bukrs;
    private String butxt;
    private String zcpxh;


    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    private Date erdat;

    private FcOrderProduct orderProduct;

}

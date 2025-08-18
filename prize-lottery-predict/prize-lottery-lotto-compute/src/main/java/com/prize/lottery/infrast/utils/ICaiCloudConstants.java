package com.prize.lottery.infrast.utils;

public class ICaiCloudConstants {


    public static final String DLT_OPEN_API    = "/open/dlt";
    public static final String SSQ_OPEN_API    = "/open/ssq";
    public static final String FC3D_OPEN_API   = "/open/fc3d";
    public static final String PL3_OPEN_API    = "/open/pl3";
    public static final String QLC_OPEN_API    = "/open/qlc";
    public static final String LAYER_OPEN_API  = "/open/layer";
    public static final String MASTER_OPEN_API = "/open/master/list";

    public static final String FC3D_LOTTERY_API      = "http://www.cwl.gov.cn/cwl_admin/front/cwlkj/search/kjxx/findDrawNotice?name=3d&issueCount=%d";
    public static final String SSQ_LOTTERY_API       = "http://www.cwl.gov.cn/cwl_admin/front/cwlkj/search/kjxx/findDrawNotice?name=ssq&issueCount=%d";
    public static final String QLC_LOTTERY_API       = "http://www.cwl.gov.cn/cwl_admin/front/cwlkj/search/kjxx/findDrawNotice?name=qlc&issueCount=%d";
    public static final String DLT_LOTTERY_API       = "https://webapi.sporttery.cn/gateway/lottery/getHistoryPageListV1.qry?gameNo=85&provinceId=0&pageSize=%d&isVerify=1&pageNo=1";
    public static final String PL3_LOTTERY_API       = "https://webapi.sporttery.cn/gateway/lottery/getHistoryPageListV1.qry?gameNo=35&provinceId=0&pageSize=%d&isVerify=1&pageNo=1";
    public static final String PL3_LOTTERY_RANGE_API = "https://webapi.sporttery.cn/gateway/lottery/getHistoryPageListV1.qry?gameNo=35&provinceId=0&pageSize=100&isVerify=1&pageNo=1&startTerm=%s&endTerm=%s";
    public static final String KL8_LOTTERY_API       = "http://www.cwl.gov.cn/cwl_admin/front/cwlkj/search/kjxx/findDrawNotice?name=kl8&issueCount=%d";

}

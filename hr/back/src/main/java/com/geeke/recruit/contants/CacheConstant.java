package com.geeke.recruit.contants;

/**
 * Created by yuy on 2017-11-29.
 * 出差行程Redis常量KEY
 */
public class CacheConstant {
    public static final String CACHE_NAME_EBTP_TRAVEL_NUMBER = "sc_ebtp_travel_travelNum";

    public static final String CACHE_NAME_HRM_INDUCTION = "hrm_induction";

    public static final String CACHE_NAME_HRM_SHAYUN_INDUCTION = "hrm_shayun_induction";

    public static final String CACHE_NAME_HRM_LAID_OFF = "hrm_laid_off";

    public static final String CACHE_NAME_HRM_LAID_OFF_FILE = "hrm_laid_off_file";

    public static final String CACHE_NAME_HRM_LAID_OFF_TRANSACTION = "hrm_laid_off_transaction";

    public static final String CACHE_NAME_HRM_INVOICE = "hrm_invoice";

    public static final String CACHE_NAME_HRM_CONTRACT = "hrm_contract";

    public static final String CACHE_NAME_HRM_CONTRACT_RENEW = "hrm_contract_renew";

    public static final String CACHE_THIRD_PARTY_CONTRACT = "third_party";

    public static final String CACHE_NAME_HRM_EXCEPTION_APPLY = "hrm_exception_apply";

    public static final String CACHE_NAME_HRM_RECRUIT_APPLY = "hrm_recruit_apply";

    public static final String CACHE_NAME_HRM_ATTEN_REPORT = "hrm_atten_report";

    public static final String CACHE_NAME_HRM_RECRUIT_CODE = "hrm_recruit_code";
    /*缓存保存时间 begin*/
    //一天
    public static final int CACHE_NAME_KEEP_LENGTH_ONEDAY = 1000*60*60*24;

}

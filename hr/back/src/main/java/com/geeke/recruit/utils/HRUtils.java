//package com.geeke.recruit.utils;
//
//
//import com.geeke.recruit.contants.CacheConstant;
//import com.geeke.recruit.enums.ApplyType;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.core.RedisTemplate;
//import org.springframework.stereotype.Component;
//
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//
///**
// * @Description 流程单号生成工具
// * @Author Chenhao
// * @Date 2020/9/3
// **/
//@Component
//public class HRUtils {
//    @Autowired
//    private RedisUtils redisUtils;
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    //每日排号位数
//    private static final int TRAVELNUM_LENGTH = 5;
//    private static final int RECRUIT_CODE_LENGTH = 2;
//    //每日排号起始号码
//    private static final int NUM_START = 1;
//    //号码池最大容量
//    private static final int MAXPOOL = 100;
//    //号码池需继续生成号码的临界数
//    private static final int MINPOOL = 10;
//    /**
//     * 共享锁默认失效时间
//     */
//    public static final int LOCKTIMEOUT =  1000*180;
//
//    /**
//     * 自动生成申请单号
//     * @return
//     * @throws Exception
//     */
//    public String makeFormNum(String applyType) throws Exception{
//
//        ApplyType type = ApplyType.getByMsg(applyType);
//        String key = "";
//        switch(type) {
//            case INDUCTION:
//                key = CacheConstant.CACHE_NAME_HRM_INDUCTION;
//                break;
//            case SHAYUN:
//                key = CacheConstant.CACHE_NAME_HRM_SHAYUN_INDUCTION;
//                break;
//            case LAIDOFF:
//                key = CacheConstant.CACHE_NAME_HRM_LAID_OFF;
//                break;
//            case INVOICE:
//                key = CacheConstant.CACHE_NAME_HRM_INVOICE;
//                break;
//            case CONTRACT:
//                key = CacheConstant.CACHE_NAME_HRM_CONTRACT;
//                break;
//            case CONTRACT_RENEW:
//                key = CacheConstant.CACHE_NAME_HRM_CONTRACT_RENEW;
//                break;
//            case LAIDOFF_FILE:
//                key = CacheConstant.CACHE_NAME_HRM_LAID_OFF_FILE;
//                break;
//            case LAIDOFF_TRANSACTION:
//                key = CacheConstant.CACHE_NAME_HRM_LAID_OFF_TRANSACTION;
//                break;
//            case THIRDPARTY:
//                key = CacheConstant.CACHE_THIRD_PARTY_CONTRACT;
//                break;
//            case EXCEPTIONAPPLY:
//                key = CacheConstant.CACHE_NAME_HRM_EXCEPTION_APPLY;
//                break;
//            case RECRUIT:
//                key = CacheConstant.CACHE_NAME_HRM_RECRUIT_APPLY;
//                break;
//            case GENERATE_ATTENDANCE:
//                key = CacheConstant.CACHE_NAME_HRM_ATTEN_REPORT;
//                break;
//            case RECRUIT_CODE:
//                key = CacheConstant.CACHE_NAME_HRM_RECRUIT_CODE;
//            default:
//        }
//
//        String travelNum = "";
//        String currentDate = new SimpleDateFormat("yyMMdd").format(new Date());
//        travelNum = type.getCode()+currentDate;
//
//        //自动按规则生成号码池
//        autoCodePool(travelNum,key,currentDate);
//        //取号码
//        if(IPUtils.serviceIp().equals("localhost")){
//            return "T-"+fetchCodeFromList(key);
//        }else{
//            return fetchCodeFromList(key);
//        }
//    }
//
//    /**
//     * 自动生成申请单号
//     * @return
//     * @throws Exception
//     */
//    public String makeFormNum1() throws Exception{
//
//        /**
//         * 判断是测试机还是正式机
//         * 测试机的话生成的单号前加T-
//         */
//
//        boolean isTest = false;
//        String key = "SUMMARY_OF_BILL";
//
//        String travelNum = "";
//        String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
//        travelNum = "BILL"+currentDate;
//
//        //自动按规则生成号码池
//        autoCodePool(travelNum,key,currentDate);
//        //取号码
//        if(IPUtils.serviceIp().equals("localhost"))
//        {
//            isTest = true;
//        }
//        if(isTest){
//            return "T-"+fetchCodeFromList(key);
//        }else{
//            return fetchCodeFromList(key);
//        }
//    }
//
//    /**
//     * 个税申请批次号
//     * @return
//     * @throws Exception
//     */
//    public String makeFormNum2() throws Exception{
//
//        /**
//         * 判断是测试机还是正式机
//         * 测试机的话生成的单号前加T-
//         */
//
//        boolean isTest = false;
//        String key = "SUMMARY_OF_BILL";
//
//        String travelNum = "";
//        String currentDate = new SimpleDateFormat("yyyyMMdd").format(new Date());
//        travelNum = "ITR"+currentDate;
//
//        //自动按规则生成号码池
//        autoCodePool(travelNum,key,currentDate);
//        //取号码
//        if(IPUtils.serviceIp().equals("localhost"))
//        {
//            isTest = true;
//        }
//        if(isTest){
//            return "T-"+fetchCodeFromList(key);
//        }else{
//            return fetchCodeFromList(key);
//        }
//    }
//
//    /**
//     * 自动按规则生成号码池
//     * @param travelNum
//     * @param key
//     * @param currentDate
//     */
//    private void autoCodePool(String travelNum,String key,String currentDate) {
//        List list = new ArrayList();
//        int maxNum = 0;
////        RedisMemoTool.lock(key, RedisMemoTool.LOCKTIMEOUT);
//
//        try {
//            //如果redis存在该key 取出号码池list
//            if (redisUtils.lock(key,LOCKTIMEOUT)) {
//                list = redisUtils.getList(key);
//                if (!list.get(list.size() - 1).toString().contains(currentDate)) {
//                    list.clear();//如果取出的号码池list的最后一个元素不含今天的日期，表示是昨天剩下的号码 则将list清空
//                }
//            }else{
//                //如果不存在该key或其它redis被清空的情况，则读取数据库当天最大单号，然后继续生成
//                List<HashMap> todayList = travelUtils.travelManager.getMaxTravelNumForToday();
//                if(todayList.size()>0){
//                    System.out.println(todayList.get(0).get("TRAVEL_NUM").toString().substring(todayList.get(0).get("TRAVEL_NUM").toString().length()-TRAVELNUM_LENGTH,todayList.get(0).get("TRAVEL_NUM").toString().length()));
//                    maxNum = 1+Integer.parseInt(todayList.get(0).get("TRAVEL_NUM").toString().substring(todayList.get(0).get("TRAVEL_NUM").toString().length()-TRAVELNUM_LENGTH,todayList.get(0).get("TRAVEL_NUM").toString().length()));
//                }
//            }
//
//
//            int beginNum = NUM_START + maxNum;//初始化list起始数字
//            int initLength = list.size();
//
//            //判断号码池是否需要重新生成号码
//            if (isCreateCodes(key, currentDate, list)) {
//                //根据list长度大于0 起始数字则变成list最后一个元素的数字+1
//                if (list.size() > 0) {
//                    beginNum = Integer.parseInt(list.get(list.size() - 1).toString().substring(10)) + 1;
//                }
//                if(CacheConstant.CACHE_NAME_HRM_RECRUIT_CODE.equals(key))
//                {
//                    //往后面继续增加，保证池里有MAXPOOL个号码
//                    for (int i = beginNum; i < MAXPOOL - initLength + beginNum; i++) {
//                        list.add(travelNum + String.format("%0" + RECRUIT_CODE_LENGTH + "d", i));//号码拼装
//                    }
//                }
//                else
//                {
//                    //往后面继续增加，保证池里有MAXPOOL个号码
//                    for (int i = beginNum; i < MAXPOOL - initLength + beginNum; i++) {
//                        list.add(travelNum + String.format("%0" + TRAVELNUM_LENGTH + "d", i));//号码拼装
//                    }
//                }
//
////                if(CacheConstant.CACHE_NAME_HRM_RECRUIT_CODE.equals(key))
////                {
////                    int year = Year.now().getValue();
////                    Date yearLast = TimeUtils.getYearLast(year);
////                    Integer millis = TimeUtils.getMillis(new Date(), yearLast);
////                    RedisMemoTool.push(key, list, millis);
////                }
//
//                    RedisMemoTool.push(key, list, CacheConstant.CACHE_NAME_KEEP_LENGTH_ONEDAY);
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            RedisMemoTool.unlock(key);
//        }
//        System.out.println("号码池内有："+RedisMemoTool.get(key,ArrayList.class));
//
//    }
//
//    /**
//     * 从redis取号码池的list然后取第一个元素作为编号
//     * @param key
//     * @return
//     */
//    private String fetchCodeFromList(String key) {
//        String code="";
//
//        //从redis取号码池的list
//        RedisMemoTool.lock(key, RedisMemoTool.LOCKTIMEOUT);
//        try {
//            List list = RedisMemoTool.get(key,ArrayList.class);//从redis取号码池的list
//            code = list.get(0).toString();//取list的第一个元素作为获取的编号
//            list.remove(0);//list移除第一个元素
//            RedisMemoTool.push(key,list,CacheConstant.CACHE_NAME_KEEP_LENGTH_ONEDAY);//重新将list放入redis
//        } catch (Exception e) {
//            e.printStackTrace();
//        } finally {
//            RedisMemoTool.unlock(key);
//        }
//        System.out.println("取走第一个元素 号码池内剩："+RedisMemoTool.get(key,ArrayList.class));
//        return code;
//    }
//
//    /**
//     * 判断号码池是否需要生成号码
//     * @param key
//     * @param currentDate
//     * @return
//     */
//    private boolean isCreateCodes(String key,String currentDate,List list){
//        boolean create = false;
//        //如果key不存在 或者 key里的编码list长度小于等于10 或者 list最后一个元素日期不是当天日期，则需要生成，否则不需要
//        if (!RedisMemoTool.exists(key) || list.size()<=MINPOOL || !list.get(list.size()-1).toString().contains(currentDate)) {
//            create = true;
//        }
//        return create;
//    }
//
//}

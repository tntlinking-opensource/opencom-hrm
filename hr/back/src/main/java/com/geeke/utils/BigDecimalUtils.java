package com.geeke.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class BigDecimalUtils {
    
    //默认金额格式化
    // java.text.DecimalFormat fmt = new java.text.DecimalFormat("###,###,###,###.####");
    public final static String DEFAULT_MONEY_FORMAT= "###,###,###,###.####";
    
    //默认的转换日期类型，Timestamp
    public final static String DEFAULT_TIMESTAMP = "yy-MM-dd";
    public final static String DEFAULT_DATETIME = "yyyy-MM-dd HH:mm:ss";
    public final static String DEFAULT_DATE = "yyyy-MM-dd";
    public final static String DEFAULT_DATE_2 = "yyMMdd";
    public final static String YEARANDMONTH = "yyyyMM";
    public final static BigDecimal ZERO = new BigDecimal("0");
    public final static String STRING = "";
    public final static BigDecimal TEN = new BigDecimal("10");
    public final static BigDecimal PERCENT_12  = new BigDecimal("0.12");//如果用new BigDecimal(0.12)，有精度丢失
    public final static BigDecimal PERCENT_24  = new BigDecimal("0.24");
    public final static String NOLOGIN = "未登陆或登陆已超时。";
    public final static String ERRORMSG = "系统异常，请联系IT。";
    //税前计算方式
    public final static int PRETAX = 1;
    public final static int AFTERTAX = 2;

    private void close(CallableStatement cs, ResultSet rs) {
        try {
            if (cs != null) {
                cs.close();
            }
            if (rs != null) {
                rs.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @功能描述： 把String 类型转换为Timestamp类型
     * @返回类型：Timestamp
     * @参数类型：@param date_s   字符串类型的日期
     * @参数类型@param format    转换的格式
     * @参数类型：@return
     * @参数类型：@throws ParseException
     *
     */
    public static Timestamp String2Timestamp (String date_s, String fromat) throws ParseException {
        if(date_s == null || date_s.equals("") || fromat == null || fromat.equals("")) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(fromat);
        Date date = format.parse(date_s);
        return new Timestamp(date.getTime());
    }


    /**
     *
     * @功能描述： 把String 类型转换为date类型
     * @返回类型：Date
     * @参数类型：@param date_s
     * @参数类型：@param fromat
     * @参数类型：@return
     * @参数类型：@throws ParseException
     *
     */
    public static Date String2Date (String date_s, String fromat) throws ParseException {
        if(date_s == null || date_s.equals("") || fromat == null || fromat.equals("")) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(fromat);
        Date date = format.parse(date_s);
        return date;
    }
    

    /**
     *
     * @功能描述： 把Timestamp 类型转换为String类型
     * @返回类型：String
     * @参数类型：@param date_s   字符串类型的日期
     * @参数类型@param format    转换的格式
     * @参数类型：@return
     * @参数类型：@throws ParseException
     *
     */
    public static String Timestamp2String (Timestamp date_t, String fromat) throws ParseException {
        if(date_t == null  || fromat == null || fromat.equals("")) {
            return null;
        }
        SimpleDateFormat format = new SimpleDateFormat(fromat);
        String str = format.format(date_t);
        return str;
    }
    
    /**
     *
     * @功能描述： 得到当前时间的年月日格式   舍去时分秒   
     * @返回类型：String
   
     * @参数类型：@return
     * @参数类型：@throws ParseException   
     *
     */
    public static Timestamp getCurTimestampFormat () {
    	String format_s = new SimpleDateFormat(DEFAULT_TIMESTAMP).format(new Date());
        SimpleDateFormat format = new SimpleDateFormat(DEFAULT_TIMESTAMP);
        Date date = null;
		try {
			date = format.parse(format_s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        return new Timestamp(date.getTime());
    }
    
    
    /**
     * 把日期类型转成字符串类型，应该重载上面timestamp的方法
     * @param d
     * @param format
     * @return
     */
    public static String date2String(Date d, String format) {
        if(d == null  || format == null || format.equals("")) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String str = sdf.format(d);
        return str;
    }
    
    /**
     *
     * @功能描述：  把金额类型按照参数进行格式化
     * @返回类型：String
     * @参数类型：@param val
     * @参数类型：@return   
     *
     */
    public static String Money2String(BigDecimal val, String format) {
        if(val == null || val.equals("") || format == null || format.equals("")) {
            return "";
        }
        java.text.DecimalFormat fmt = new java.text.DecimalFormat(format);
        String retVal = null;
        try {
            retVal = fmt.format(val);
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return retVal;
    }
    
    /**
     * 如果没有值，不传0到数据库，但在action或BS中，BigDecimal如果为null，容易出现空指针所以在set值时，用此方法过滤一次
     * 判断val是否为null或0，如果是null或0，则返回 null，否则返回val
     * @param val
     * @return
     */
     public static BigDecimal getIsNull(BigDecimal val){
         if (val == null) {
             return null;
         }
         if (val.compareTo(ZERO) == 0) {
             return null;
         }
         return val;
     }
     
     
     
     public static BigDecimal getIsZero(BigDecimal val){
         if (val == null) {
             return ZERO;
         }
         return val;
     }
     
     
    
     /**
      * 判断字符串是否为NULL或是空字符串<br>
      * 如果是空字符串或NULL，返回true
      * @param str
      * @return
      */
     public static boolean isNull(String str) {
         if (str == null || str.equals("") || str.toLowerCase().equals("null")
                 || str.trim().equals("")) {
             return true;
         }
         return false;
     }

    /**
     * 判断是否为空，
     * @param str
     * @return
     */
     public static String string(String str){
        if(str == null || str.equals("") || str.toLowerCase().equals("null") || str.trim().equals("")){
            return "";
        }
        return str;
     }

     
     /**
      *
      * @功能描述： 判断字符串数组是否为空，注：[""]也标识为NULL
      * @返回类型：boolean
      * @参数类型：@param strs
      * @参数类型：@return   
      *
      */
     public static boolean isNull(String[] strs) {
         if (strs == null || strs.length == 0) {
             return true;
         }
         if (strs.length == 1 && strs[0].equals("")) {
             return true;
         }
         return false;
     }
     
     
     /**
      *
      * @功能描述： 判断字符串数组是否为空，注：[""]也标识为NULL
      * @返回类型：boolean
      * @参数类型：@param strs
      * @参数类型：@return   
      *
      */
     public static boolean isNull(List list) {
         if (list == null || list.size() == 0) {
             return true;
         }
         return false;
     }
     
     /**
      *
      * @功能描述： String 转 BigDecimal取几位小数并四舍五入
      * @返回类型：BigDecimal
      * @参数类型：@param str 须要转化的字符串
      * @参数类型：@param i 设置小数位数
      * @参数类型：@return   
      *
      */
     public static BigDecimal StringToBigDecimal(String str,int i) {
//         if (isNull(str)) {
//             return null;
//         }
         if (isNull(str)) {
             str = "0.00";
         }
         BigDecimal bd = new BigDecimal(str); 
         //设置小数位数，第一个变量是小数位数，第二个变量是取舍方法(四舍五入) 
         bd = bd.setScale(i, BigDecimal.ROUND_HALF_UP);
         return bd;
     } 
     
     /**
      *
      * @功能描述： object 转 bigdecimal
      * @返回类型：BigDecimal
      * @参数类型：@param value
      * @参数类型：@return   
      *
      */
     public static BigDecimal object2Bigdecimal(Object value, int i) {
         BigDecimal ret = null;
         if( value != null ) {
             if(value instanceof BigDecimal) {
                 ret = (BigDecimal) value;
             } else if(value instanceof String) {
                 if("".equals(value)){
                     ret = new BigDecimal("0");
                     ret = ret.setScale(i, BigDecimal.ROUND_HALF_UP);
                 }else{
                     ret = new BigDecimal((String) value);
                     ret = ret.setScale(i, BigDecimal.ROUND_HALF_UP);
                 }

             } else if(value instanceof BigInteger) {
                 ret = new BigDecimal((BigInteger) value);
                 ret = ret.setScale(i, BigDecimal.ROUND_HALF_UP);
             } else if(value instanceof Number) {
                 ret = new BigDecimal(((Number)value).doubleValue()); 
                 ret = ret.setScale(i, BigDecimal.ROUND_HALF_UP);
             } else {
                 throw new ClassCastException(); 
             }
         }
         return ret;
     }
     //保留两位小数
    public static BigDecimal siShe(Object value, int i) {
        BigDecimal ret = null;
        if( value != null ) {
            ret = (BigDecimal) value;
            ret = ret.setScale(i, BigDecimal.ROUND_HALF_UP);
        }
        return ret;
    }
     /**
      *
      * @功能描述： 得到当前的timestamp
      * @返回类型：Timestamp
      * @参数类型：@return   
      *
      */
     public static Timestamp getCurrentTimestamp() {
         return new Timestamp(new Date().getTime());
     }
    
     /**
      * 
      * @throws ParseException
      * @功能描述： 得到当前的年年月月日日
      * @返回类型：String
      * @参数类型：@return   
      *
      */
     public static String getYymmdd() throws ParseException {
         return Timestamp2String(getCurrentTimestamp(), DEFAULT_DATE_2);
     }
     
     /**
      * 
      * @throws ParseException
      * @功能描述： 根据字符串yyyymm，得到上个月，同样为yyyymm
      * @返回类型：string
      * @参数类型：@param yyyymm
      * @参数类型：@return   
      *
      */
     public static String getPreMonth(String yyyymm) throws ParseException {
         Date d = String2Date(yyyymm, YEARANDMONTH);
         Calendar c = Calendar.getInstance();
         c.setTime(d);
         c.add(Calendar.MONTH, -1);
         String d_s = date2String(c.getTime(), YEARANDMONTH);
         return d_s;
     }
     
     public static void main(String[] args) {
         try {
            System.out.println(getPreMonth("201211"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
     }
//     /**
//      *
//      * @功能描述： 把异常消息写入文本日志
//      * @返回类型：String
//      * @参数类型：@param ex
//      * @参数类型：@param logger
//      * @参数类型：@return
//      *
//      */
//     public static void writeException(Exception ex, ILog logger) {
//         StackTraceElement[] ste = ex.getStackTrace();
//         StringBuilder sb = new StringBuilder();
//         sb.append(ex.toString()).append("\r\t");
//         if (ste != null && ste.length > 0) {
//             for (int i = 0; i < ste.length; i++) {
//                 //10行以内就能完整显示出在哪行抛的异常。
//                 if (i > 10) {
//                     break;
//                 } else {
//                     sb.append(ste[i]).append("\r\t");
//                 }
//             }
//         }
//         String msg = sb.toString();
//         logger.error(msg);
//     }
//
     
     
     public static boolean isLegitimateDate(String date) {
         date=date.replaceAll("-", "");
         date=date.replaceAll("/", "");
         date=date.replaceAll("", "");
         boolean falg=false;
         //String year = date.substring(0, 4);
         String month=date.substring(4, 6);
         String day=date.substring(6, 8);
         int i_month = Integer.parseInt(month, 10);
         int i_day=Integer.parseInt(day, 10);
         //月份在1到12之间
//         if(i_month>0||i_month<12){
//             falg=true;
//         }
//         
//         if(i_day>0||i_day<32){
//             falg=true;
//         }
//         System.out.println("i_month="+i_month);
//         System.out.println("i_day="+i_day);
         if(i_month==2&&(i_day==28||i_day==29)){
             falg=true;
         }else if((i_month>0&&i_month<=12)&&(i_day>0&&i_day<32)){
             falg=true;
         }
         
         return falg;
    }
     
     /**
      *
      * @功能描述： 从inputString 转成 String
      * @返回类型：String
      * @参数类型：@param builder
      * @参数类型：@param is
      * @参数类型：@return
      * @参数类型：@throws IOException   
      *
      */
     public String read2String(StringBuilder builder, InputStream is)
            throws IOException {
        String line; // 用来保存每行读取的内容
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        line = reader.readLine(); // 读取第一行
        while (line != null) { // 如果 line 为空说明读完了
            builder.append(line); // 将读到的内容添加到 buffer 中
            builder.append("/n"); // 添加换行符
            line = reader.readLine(); // 读取下一行
        }
        return builder.toString();
    }
     /**
      * 比较时间的大小
      * @param DATE1
      * @param DATE2
      * @return
      */
     public static int compare_date(String DATE1, String DATE2) {
         DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
         try {
             Date dt1 = df.parse(DATE1);
             Date dt2 = df.parse(DATE2);
             if (dt1.getTime() > dt2.getTime()) {
                 return 1;
             } else if (dt1.getTime() < dt2.getTime()) {
                 return -1;
             } else {
                 return 0;
             }
         } catch (Exception exception) {
             exception.printStackTrace();
         }
         return 0;
     }
     /**
      * 获取当期日期的最后一天
      * @param date
      * @return
      */
     public static String lastDateString(String date) {
	    	 Calendar lastDate = Calendar.getInstance();
	    	 SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE);
	         try {
	             lastDate.setTime(sdf.parse(date));
	         } catch (ParseException e) {
	             e.printStackTrace();
	         }
	         // 设置为这个月的第 1 天
	         lastDate.set(Calendar.DATE, 1);
	         //"第 1 步 - 设置为这个月的第 1 天";
	         lastDate.add(Calendar.MONTH, 1);
	         //"第 2 步 - 设置为加上 1 个月"
	         lastDate.add(Calendar.DATE, -1);
	         String format = sdf.format(lastDate.getTime());
		return format;
     }
    /**
     * 日期月份减一
     */
    public static String subDataMonthString(String data) throws ParseException {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YEARANDMONTH);
        Date parse = simpleDateFormat.parse(data);
        Calendar rightNow = Calendar.getInstance();
        rightNow.setTime(parse);
        rightNow.add(Calendar.MONTH,-1);//日期剪一个月
        Date dt1=rightNow.getTime();
        return simpleDateFormat.format(dt1);
    }


    /**
     * 验证参数是否为正数,是true
     * @param object
     * @return
     */
    public final static boolean isInteger(Object object) {
        try {
            Pattern p = Pattern.compile("^(([1-9]{1}\\d*)|([0]{1}))(\\.(\\d){0,2})?$");
            Matcher m = p.matcher(object.toString());
            return m.matches();
        } catch (Exception e) {
            return false;
        }
    }
}

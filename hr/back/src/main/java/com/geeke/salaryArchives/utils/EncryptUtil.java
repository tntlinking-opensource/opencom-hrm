package com.geeke.salaryArchives.utils;

import com.google.common.base.Strings;
import org.apache.commons.lang.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EncryptUtil {


    public static String idNumber(String idNumber){
        if (!Strings.isNullOrEmpty(idNumber)) {
            int length = idNumber.length();
            if (length == 15){
                return idNumber.replaceAll("(\\w{6})\\w*(\\w{4})", "$1*****$2");
            }else if (length == 18){
                return idNumber.replaceAll("(\\w{6})\\w*(\\w{4})", "$1********$2");
            }
        }
        return idNumber;
    }

    public static String bankNo(String acctNo) {
        if (StringUtils.isNotEmpty(acctNo)) {
            String regex = "(\\w{4})(.*)(\\w{4})";
            Matcher m = Pattern.compile(regex).matcher(acctNo);
            if (m.find()) {
                String rep = m.group(2);
                StringBuilder sb = new StringBuilder();
                for (int i = 0; i < rep.length(); i++) {
                    sb.append("*");
                }
                acctNo = acctNo.replaceAll(rep, sb.toString());
            }
        }
        return acctNo;
    }

}

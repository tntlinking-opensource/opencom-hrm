package com.geeke.salaryBatch.entity;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

public class CodeID {
    private static SimpleDateFormat fmt = new SimpleDateFormat("yyyyMMddHH");
    private static DecimalFormat decimalFormat = new DecimalFormat("00000");
    private static DecimalFormat decimalFormat19 = new DecimalFormat("000000000");
    private static volatile long NO = 0;

    public static String id() {
        String id = fmt.format(new Date());
        NO++;
        if (NO % 1000000 == 0)
            NO = 0;
        return id + geRandom();
    }

    public static String id19() {
        String id = fmt.format(new Date());
        NO++;
        if (NO % 1000000 == 0)
            NO = 0;
        return id + graphicRandom();
    }

    //5位随机数
    public static String geRandom() {
        int random = (int) ((Math.random() * 9 + 1) * 10000);
        return random + "";
    }

    //9位随机数
    public static String graphicRandom() {

        int code = (int) (Math.random() * (400000000 - 100000000)) + 100000000; // 产生1000-9999之间的一个随机数
        String codestr = String.valueOf(code);
        return codestr;
    }

    //19位随机数
    public static String graphicRandom19() {
        String id = getRandom4(10);
        int code = (int) (Math.random() * (400000000 - 100000000)) + 100000000; // 产生1000-9999之间的一个随机数
        String codestr = String.valueOf(code);
        return id + codestr;
    }

    public static String getRandom4(int len) {
        String source = "0123456789";
        Random r = new Random();
        StringBuilder rs = new StringBuilder();
        for (int j = 0; j < len; j++) {
            rs.append(source.charAt(r.nextInt(10)));
        }
        return rs.toString();
    }

    public static List<String> getDistinctRandom(int len, int num) {
        Set<String> s = new HashSet<>();
        while (s.size() < num) {
            s.add(getRandom4(len));
        }
        return new ArrayList<>(s);
    }
}
package com.targetmol.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author: HuYi.Zhang
 * @create: 2018-04-25 09:13
 **/
public class NumberUtils {
    private final static String str = "123456789";
    private final static int pixLen = str.length();
    private static volatile int pixOne = 0;
    private static volatile int pixTwo = 0;
    private static volatile int pixThree = 0;
    private static volatile int pixFour = 0;

    public static boolean isInt(Double num) {
        return num.intValue() == num;
    }

    /**
     * 判断字符串是否是数值格式
     * @param str
     * @return
     */
    public static boolean isDigit(String str){
        if(str == null || str.trim().equals("")){
            return false;
        }
        return str.matches("^\\d+$");
    }

    /**
     * 将一个小数精确到指定位数
     * @param num
     * @param scale
     * @return
     */
    public static double scale(double num, int scale) {
        BigDecimal bd = new BigDecimal(num);
        return bd.setScale(scale, RoundingMode.HALF_UP).doubleValue();
    }

    // 从字符串中根据正则表达式寻找，返回找到的数字数组
    public static Double[] searchNumber(String value, String regex){
        List<Double> doubles = new ArrayList<>();
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(value);
        if(matcher.find()) {
            MatchResult result = matcher.toMatchResult();
            for (int i = 1; i <= result.groupCount(); i++) {
                doubles.add(Double.valueOf(result.group(i)));
            }
        }
        return doubles.toArray(new Double[doubles.size()]);
    }

        //生成UUID
        public static String getUUID(){
            return UUID.randomUUID().toString().replace("-","");
        }

        //生成纯数字UUID
        public static Integer getUUIDInOrderId(){
            Integer orderId=UUID.randomUUID().toString().hashCode();
            orderId = orderId < 0 ? -orderId : orderId; //String.hashCode() 值会为空
            return orderId;
        }

    //保留小数
    public static Double round(Double value, Integer tenth){
        return   new BigDecimal(value).setScale(tenth,BigDecimal.ROUND_HALF_UP).doubleValue();
    }


}

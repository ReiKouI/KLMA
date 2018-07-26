package com.reikoui.klma.utils;


import org.apache.commons.codec.digest.DigestUtils;

import java.util.Random;

public class MD5Util {


    public static String formMD5toDBMD5(String formPass, String salt) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + formPass + salt.charAt(5) + salt.charAt(4);
        return md5(str);
    }

    public static String getRandomSalt() {
        return getRandomString(8);
    }


    private static String md5(String src) {
        return DigestUtils.md5Hex(src);
    }

    private static String getRandomString(int length) {
        //定义一个字符串（A-Z，a-z，0-9）即62位；
        String str = "zxcvbnmlkjhgfdsaqwertyuiopQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
        //由Random生成随机数
        Random random = new Random();
        StringBuffer stringBuffer = new StringBuffer();
        //长度为几就循环几次
        for (int i = 0; i < length; ++i) {
            //产生0-61的数字
            int number = random.nextInt(62);
            //将产生的数字通过length次承载到sb中
            stringBuffer.append(str.charAt(number));
        }
        //将承载的字符转换成字符串
        return stringBuffer.toString();
    }

    private static final String salt = "1a2b3c4d";

    public static String inputPassToFormPass(String inputPass) {
        String str = "" + salt.charAt(0) + salt.charAt(2) + inputPass + salt.charAt(5) + salt.charAt(4);
        System.out.println(str);
        return md5(str);
    }

    public static void main(String[] args) {
        System.out.println(inputPassToFormPass("123"));
        System.out.println(formMD5toDBMD5("d3b1294a61a07da9b49b6e22b2cbd7f9", "Y3WPRwu6"));

    }


}

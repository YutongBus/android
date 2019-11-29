package com.orange.yutongbus.lib.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by john on 2019/3/30.
 */

public class StringUtils {

    public static int toInt(String s){
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

    public static byte[] hexStrToByteArray(String str)
    {
        if (str == null) {
            return null;
        }
        if (str.length() == 0) {
            return new byte[]{0x00};
        }
        byte[] byteArray = new byte[str.length() / 2];
        for (int i = 0; i < byteArray.length; i++){
            String subStr = str.substring(2 * i, 2 * i + 2);
            if (subStr.trim() != "" && !subStr.contains("\n")) {
                byteArray[i] = ((byte) Integer.parseInt(subStr, 16));
            } else {
                continue;
            }
        }
        return byteArray;
    }

    //需要使用2字节表示b
    public static String numToHex16(int b) {
        return String.format("%04x", b);
    }

    //需要使用2字节表示b
    public static int hexStringToint(String s) {
        int iValue = Integer.parseInt(s, 16);
        return iValue;
    }

    /**
     * 不足部分补全0x00
     * @param str
     * @param len
     * @return
     */
    public static byte[] hexStrToByteArray (String str, int len) {
        byte[] bytes = hexStrToByteArray(str);
        byte[] r_bytes = new byte[len];
        if (bytes.length == r_bytes.length) {
            return bytes;
        } else if (bytes.length < r_bytes.length) {
            for (int i=0;i<(r_bytes.length-bytes.length);i++) {
                r_bytes[i] = 0x00;
            }
            // 剩下的长度用bytes替换
            int d_len = r_bytes.length-bytes.length;
            for (int i=(d_len);i<r_bytes.length;i++) {
                r_bytes[i] = bytes[i-d_len];
            }
            return r_bytes;
        } else {
            return bytes;
        }
    }

    public static String byteArrayToHexStr(byte[] byteArray) {
        if (byteArray == null){
            return null;
        }
        char[] hexArray = "0123456789ABCDEF".toCharArray();
        char[] hexChars = new char[byteArray.length * 2];
        for (int j = 0; j < byteArray.length; j++) {
            int v = byteArray[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }

}

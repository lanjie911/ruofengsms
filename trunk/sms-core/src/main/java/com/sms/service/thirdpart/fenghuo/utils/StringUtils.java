package com.sms.service.thirdpart.fenghuo.utils;

/**
 * Created by jerry lee on 2017/4/20.
 */
public class StringUtils {

    public StringUtils() {
    }

    public static boolean isEmpty(String value) {
        int strLen;
        if(value != null && (strLen = value.length()) != 0) {
            for(int i = 0; i < strLen; ++i) {
                if(!Character.isWhitespace(value.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    public static boolean isNotEmpty(String value) {
        return !isEmpty(value);
    }

    public static boolean areNotEmpty(String... values) {
        boolean result = true;
        if(values != null && values.length != 0) {
            for(int i = 0; i < values.length; i++) {
                String value = values[i];
                result &= !isEmpty(value);
            }
        } else {
            result = false;
        }
        return result;
    }
}


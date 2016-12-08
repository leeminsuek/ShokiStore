package com.dev.shoki.utils;

/**
 * Created by shoki on 16. 12. 8..
 */
public class Utils {

    public static int convertPrice(String price) {
        return Integer.valueOf(price.replaceAll(",", "").replaceAll("원", ""));
    }
}

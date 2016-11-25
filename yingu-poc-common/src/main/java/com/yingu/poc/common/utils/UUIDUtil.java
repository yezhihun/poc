package com.yingu.poc.common.utils;

import java.util.UUID;

/**
 * 生成UUID工具类
 */
public class UUIDUtil {


    public static String getUUID32(){
        return UUID.randomUUID().toString().replace("-","");
    }
}

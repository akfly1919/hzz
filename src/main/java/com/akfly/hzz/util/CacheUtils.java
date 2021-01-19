package com.akfly.hzz.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.TimeUnit;

/**
 * @program: hzz
 * @description:
 * @author: wangfei171
 * @create: 2021-01-19 12:08
 **/
public class CacheUtils {

    public static final String CACHEUTILS_SY ="cacheutils_sy";

    public static Cache<String, String> graphs = CacheBuilder.newBuilder()
            .maximumSize(1000).expireAfterWrite(10, TimeUnit.MINUTES).build();
}

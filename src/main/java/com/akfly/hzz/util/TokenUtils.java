package com.akfly.hzz.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @title: TokenUtils
 * @projectName hzz
 * @description 生成token的工具类
 * @author MLL
 * @date 2021/1/21 22:11
 */


public class TokenUtils {

    public static String getToken(String userId, String sec) throws UnsupportedEncodingException {

        // TODO 正式测试时候需要加上过期时间
        //return JWT.create().withAudience(userId).withExpiresAt(new Date(System.currentTimeMillis() + 2 * 24 * 3600 * 1000)).sign(Algorithm.HMAC256(sec));
        return JWT.create().withAudience(userId).sign(Algorithm.HMAC256(sec));
    }
}

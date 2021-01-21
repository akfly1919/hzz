package com.akfly.hzz.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

import java.io.UnsupportedEncodingException;

/**
 * @title: TokenUtils
 * @projectName hzz
 * @description 生成token的工具类
 * @author MLL
 * @date 2021/1/21 22:11
 */


public class TokenUtils {

    public static String getToken(String userId, String sec) throws UnsupportedEncodingException {
        return JWT.create().withAudience(userId).sign(Algorithm.HMAC256(sec));
    }
}

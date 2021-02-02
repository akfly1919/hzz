package com.akfly.hzz.util;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 */
@Slf4j
public class RequestUtil {

    /**
     * 从request Body中获取请求参数
     *
     * @param request
     * @return
     */
    public static String getRequestBodyString(HttpServletRequest request) {
        String reportContent = "";
        try {
            BufferedReader reader = request.getReader();
            StringBuilder reportBuilder = new StringBuilder();
            String tempStr = "";
            while ((tempStr = reader.readLine()) != null) {
                reportBuilder.append(tempStr);
            }
            reportContent = reportBuilder.toString();
        } catch (IOException e) {
            log.error("获取request body string 异常: ", e);
        }
        log.info("获取请求信息为: requestBodyString=[" + reportContent + "]");
        return reportContent;
    }

    /**
     * 分割字符串
     *
     * @param requestString
     * @return
     */
    public static Map<String, String> splitString(String requestString) {

        String[] strarray = requestString.split("&");
        Map<String, String> map = new HashMap<String, String>();

        for (int i = 0; i < strarray.length; i++) {
            String[] strarray2 = strarray[i].split("=");
            if (strarray2.length == 2) {
                String key = strarray2[0];
                String value = strarray2[1];
                if (StringUtils.isNotBlank(key) && StringUtils.isNotBlank(value)) {
                    try {
                        value = URLDecoder.decode(value, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        log.info("HttpUtil.splitString | urlDecoder.decode 失败 kye=" + key + ", value=" + value);
                    }
                    map.put(key, value);
                }
            }
        }
        log.info("HttpUtil.splitString | 分割字符串后的Map值: " + map.toString());
        return map;
    }

    public static String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        log.info("RequestUtil.getRemoteHost() ip=" + ip);
        return ip.equals("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
    }

    /**
     * 将数据映射表拼接成表单数据POST样式的字符串  key1=value1&key2=value2
     *
     * @param dataMap
     * @return
     */
    public static String postFormLinkReport(Map<String, String> dataMap) {

        if (CollectionUtils.isEmpty(dataMap)) {
            return "";
        }

        List<String> keyList = new ArrayList<String>(dataMap.keySet());
        Collections.sort(keyList);

        StringBuilder reportBuilder = new StringBuilder();
        try {
            for (String key : keyList) {
                Object object = (Object) dataMap.get(key);
                if (object instanceof JSONObject) {
                    JSONObject jsonObject = (JSONObject) object;
                    try {
                        reportBuilder.append(key + "=" + jsonObject.toString() + "&");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    continue;
                } else if (object instanceof String) {
                    if (!StringUtils.isBlank(dataMap.get(key))) {
                        try {
                            reportBuilder.append(key + "=" + dataMap.get(key) + "&");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        reportBuilder.deleteCharAt(reportBuilder.lastIndexOf("&"));
        return reportBuilder.toString();
    }

}

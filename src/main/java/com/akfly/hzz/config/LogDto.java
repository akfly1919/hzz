package com.akfly.hzz.config;

import lombok.Data;

@Data
public class LogDto {
    String requestTime;
    String requestUrl;
    String requestMethod;
    String classMethod;
    String  args;
    String returns;
    String costs;
}

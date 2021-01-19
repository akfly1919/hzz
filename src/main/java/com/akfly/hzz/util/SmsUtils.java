package com.akfly.hzz.util;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

/**
 * @program: hzz
 * @description:
 * @author: wangfei171
 * @create: 2021-01-19 17:05
 * AccessKey
 * AccessKey id : LTAIVMaqgUsuCqvI
 * AccessKey Secret：VzVlG8S2ajHEy9cmJmE5HxN6dfapJT
 **/
@Slf4j
public class SmsUtils {

    public static boolean smsSend(String phoneName,String code){
        DefaultProfile profile = DefaultProfile.
                getProfile("cn-hangzhou", "LTAIVMaqgUsuCqvI", "VzVlG8S2ajHEy9cmJmE5HxN6dfapJT");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phoneName);
        request.putQueryParameter("SignName", "红鲤鱼");
        request.putQueryParameter("TemplateCode", "SMS_204980070");
        request.putQueryParameter("TemplateParam", "{\"code\":\""+code+"\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
            Map<String,String> map = JsonUtils.decode(response.getData(), Map.class);
            return "OK".equals(map.get("Code"));
        } catch (Exception e) {
            log.error("SmsUtils error",e);
        }
        return false;
    }
    public static void main(String[] args) {
        SmsUtils.smsSend("15001115778","123456");
    }
}

package com.akfly.hzz.facade;

import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.util.HttpUtils;
import com.akfly.hzz.util.ImageUtil;
import com.akfly.hzz.util.JsonUtils;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Component
public class IDFacade {
    public static String IDSIDE_FRONT="front";
    public static String IDSIDE_BACK="back";
    @Value("id.ocr.url")
    private String ocrurl;
    public JSONObject idImageProcess(String imagepath,String type) throws HzzBizException{
        File f=new File(imagepath);
        return idImageProcess(f,type);
    }
    public JSONObject idImageProcess(File imageFile,String type) throws HzzBizException{
        byte[] images=ImageUtil.checkImage(imageFile,4096,15);
        if(images.length<=0){
            
        }
        BASE64Encoder encoder = new BASE64Encoder();
        String strNetImageToBase64 = encoder.encode(images);
        return idOcrVerify(strNetImageToBase64,type);
    }
    public JSONObject idOcrVerify(String image64,String side) throws HzzBizException {
        Map<String,String> map=new HashMap<String,String>();
        map.put("key","351b490560cb5de6aaff3a52276760a7");
        map.put("side",side);
        map.put("image",image64);
        String result = HttpUtils.postForm("http://apis.juhe.cn/idimage/verify", map);
        JSONObject resultOb = (JSONObject)JSONObject.parse(result);
        if("0".equalsIgnoreCase(resultOb.getString("error_code"))){
            return (JSONObject)resultOb.get("result");
        }else{
            throw new HzzBizException(HzzExceptionEnum.ID_OCR_ERROR);
        }

    }

    public JSONObject idRealAuth(String idcard,String realname) throws HzzBizException {
        Map<String,String> map=new HashMap<String,String>();
        map.put("idcard",idcard);
        map.put("realname",realname);
        map.put("orderid","1");
        map.put("key","6de4cec9e373c07804591c4b3d980d3a");
        String result = HttpUtils.postForm("http://op.juhe.cn/idcard/query", map);
        System.out.println(result);
        JSONObject resultOb = (JSONObject)JSONObject.parse(result);
        if("0".equalsIgnoreCase(resultOb.getString("error_code"))){
            JSONObject rrob= (JSONObject)resultOb.get("result");
            if("1".equalsIgnoreCase(rrob.getString("res"))){
                return rrob;
            }else{
                throw new HzzBizException(HzzExceptionEnum.ID_REAL_AUTH);
            }

        }else{

        }
        return resultOb;
    }

    public static void main(String[] args) throws HzzBizException {
        IDFacade f=new IDFacade();
        //f.idImageProcess("E:\\浏览器下载\\d085520c3b8c9484bc53ef155f00a3c5.jpeg",IDFacade.IDSIDE_FRONT);
        f.idRealAuth("210403198808092363","徐华");
    }
}

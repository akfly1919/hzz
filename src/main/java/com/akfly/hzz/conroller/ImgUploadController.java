package com.akfly.hzz.conroller;

import com.akfly.hzz.dto.BaseRspDto;
import com.akfly.hzz.exception.HzzBizException;
import com.akfly.hzz.exception.HzzExceptionEnum;
import com.akfly.hzz.service.CustomercardinfoService;
import com.akfly.hzz.service.CustomeridcardinfoService;
import com.akfly.hzz.util.JsonUtils;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

@Log4j2
@Controller(value = "/hzz")
public class ImgUploadController {

    @Resource
    private ThreadPoolTaskExecutor threadPool;

    private static final String path = "/opt/export/image/";

    @Resource
    private CustomeridcardinfoService customeridcardinfoService;
    /**
     * 图片上传
     *
     * @param file
     * @return
     */
    @PostMapping(value = "/imgUp")
    @ResponseBody
    public String imgUp(HttpSession session, @RequestParam MultipartFile file, HttpServletResponse response) {

        BaseRspDto rsp = new BaseRspDto();
        ServletOutputStream outputStream = null;
        String userId = "";
        try {
            userId = "";

            String fileName = file.getOriginalFilename();
            checkImgSuffixAndSize(file, fileName);

            String userId2 = userId;
            InputStream imageStream = file.getInputStream();
            threadPool.submit(() -> {
                try {
                    //SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
                    //String dateStr = sdf.format(new Date());
                    //File image = new File(path + userId2 + File.separator + userId2 +"_" + dateStr +  fileName);
                    File image = new File(path + userId2 + File.separator + fileName);
                    OutputStream outputStream1 = new FileOutputStream(image);
                    byte[] buffer = new byte[2048];
                    int i = -1;
                    while ((i = imageStream.read(buffer)) != -1) {
                        outputStream1.write(buffer, 0, i);
                    }
                } catch (Exception e) {
                    log.error("上传原图异常,userId={}" + userId2, e);
                }
            });

            response.reset();
            response.setHeader("Content-Type", "image/jpeg");
            outputStream = response.getOutputStream();
            byte[] buffer = new byte[2048];
            int i = -1;
            while ((i = imageStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, i);
            }
            log.info("图片上传接口成功");

        } catch (HzzBizException e) {
            rsp.setCode(e.getErrorCode());
            rsp.setMsg(e.getErrorMsg());
        } catch (Exception e) {
            log.error("图片上传接口请求Exception,userId=" + userId, e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        } finally {
            try {
                if (outputStream != null)
                    outputStream.flush();
                if (outputStream != null)
                    outputStream.close();
            } catch (IOException e) {
                log.error("读写文件异常,userId=" + userId, e);
            }
        }
        return JsonUtils.toJson(rsp);
    }

    /**
     * 图片展示
     *
     * @param
     * @param response
     * @return
     */
    @GetMapping(value = "/showImage")
    @ResponseBody
    public String showImage(HttpSession session, String fileName, HttpServletResponse response) {
        log.info("图片展示前端请求参数fileName:{}", fileName);
        BaseRspDto rsp = new BaseRspDto();
        ServletOutputStream outputStream = null;
        InputStream inputStream = null;
        String userId = "";
        try {
            userId = "";
            if (StringUtils.isBlank(fileName)) {
                log.info("图片名称为空");
                throw new HzzBizException(HzzExceptionEnum.PARAM_INVALID);
            }

            File image = new File(path + userId + File.separator +  fileName);
            inputStream = new FileInputStream(image);
            if (inputStream != null) {
                response.reset();
                response.setHeader("Content-Type", "image/jpeg");
                outputStream = response.getOutputStream();

                //复制InputStream流
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int len;
                while ((len = inputStream.read(buffer)) > -1) {
                    baos.write(buffer, 0, len);
                }
                baos.flush();

                //判断文件头
                InputStream stream1 = new ByteArrayInputStream(baos.toByteArray());

                byte[] bufferHeader = new byte[7];
                stream1.read(bufferHeader, 0, bufferHeader.length);
                String headString = bytesToHexString(bufferHeader);
                headString = headString.toUpperCase();
                //展示到前端
                InputStream stream2 = new ByteArrayInputStream(baos.toByteArray());
                byte[] buffer2Front = new byte[1024];
                int i = -1;
                while ((i = stream2.read(buffer2Front)) != -1) {
                    outputStream.write(buffer2Front, 0, i);
                }

            } else {
                throw new HzzBizException(HzzExceptionEnum.IMAGE_NOT_EXIST);
            }

            log.info("图片展示接口返回成功");
        } catch (HzzBizException e) {
            rsp.setCode(e.getErrorCode());
            rsp.setMsg(e.getErrorMsg());
        } catch (Exception e) {
            log.error("图片展示接口请求Exception,userId=" + userId, e);
            rsp.setCode(HzzExceptionEnum.SYSTEM_ERROR.getErrorCode());
            rsp.setMsg(HzzExceptionEnum.SYSTEM_ERROR.getErrorMsg());
        } finally {
            try {
                if (outputStream != null)
                    outputStream.flush();
                if (outputStream != null)
                    outputStream.close();
                if (inputStream != null)
                    inputStream.close();
            } catch (IOException e) {
                log.error("读写文件异常,userId=" + userId, e);
            }
        }
        return JsonUtils.toJson(rsp);
    }

    public String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString(v);
            if (hv.length() < 2) {
                stringBuilder.append(0);
            }
            stringBuilder.append(hv);
        }
        return stringBuilder.toString();
    }


    /**
     * 校验图片后缀及大小
     *
     * @param image
     * @param fileName
     * @throws HzzBizException
     */
    private void checkImgSuffixAndSize(@RequestParam MultipartFile image, String fileName) throws HzzBizException {
        if ((!fileName.toLowerCase().endsWith("pdf")
                && !fileName.toLowerCase().endsWith("jpg")
                && !fileName.toLowerCase().endsWith("jpeg")
                && !fileName.toLowerCase().endsWith("png"))) {
            log.info("文件格式错误");
            throw new HzzBizException(HzzExceptionEnum.PARAM_INVALID);
        }

        long fileSize = image.getSize();
        log.info("fileInfo:fileName=" + fileName + "&fileSize=" + fileSize);
        if (fileSize <= 0) {
            log.info("文件为空");
            throw new HzzBizException(HzzExceptionEnum.PARAM_INVALID);
        } else if (fileSize > (2 * 1024 * 1024)) {
            log.error( "文件大小超出规格 fileName={}", fileName);
            throw new HzzBizException(HzzExceptionEnum.PARAM_INVALID);

        }
    }



}

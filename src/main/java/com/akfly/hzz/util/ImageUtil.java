package com.akfly.hzz.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ImageUtil {
    public static void main(String[] args) {
        /*reSize(new File("F:\\temp\\1\\qrcode_258_258.jpg"),
                320, 320, true);
        reSize(new File("F:\\temp\\1\\qrcode_258_258.jpg"),
                320, 340, false);*/
    }


    public static byte[] checkImage(File srcImg,int max,int min){
        String type = getImageType(srcImg);
        if (type == null) {
            return new byte[0];
        }
        BufferedImage srcImage = null;
        try {
            srcImage = ImageIO.read(srcImg);
            System.out.println("srcImg size=" + srcImage.getWidth() + "X" + srcImage.getHeight());
        } catch (IOException e) {
            e.printStackTrace();
            return new byte[0];
        }
        int iwidth=srcImage.getWidth();
        int ilength=srcImage.getHeight();
        if(iwidth>=min&&iwidth<=max&&ilength>=min&&ilength<=max){
            return reSize(srcImage,type,iwidth,ilength,true);
        }else if(iwidth>max||ilength>max){
            return reSize(srcImage,type,max,max,true);
        }else if(iwidth<min||ilength<min){
            return reSize(srcImage,type,min,min,true);
        }
        return new byte[0];
    }
    public static byte[] reSize(BufferedImage srcImage,String type, int width,
                              int height, boolean equalScale) {
        if (width < 0 || height < 0) {
            return new byte[0];
        }


        if (srcImage != null) {
            // targetW，targetH分别表示目标长和宽
            BufferedImage target = null;
            double sx = (double) width / srcImage.getWidth();
            double sy = (double) height / srcImage.getHeight();
            // 等比缩放
            if (equalScale) {
                if (sx > sy) {
                    sx = sy;
                    width = (int) (sx * srcImage.getWidth());
                } else {
                    sy = sx;
                    height = (int) (sy * srcImage.getHeight());
                }
            }
            System.out.println("destImg size=" + width + "X" + height);
            ColorModel cm = srcImage.getColorModel();
            WritableRaster raster = cm.createCompatibleWritableRaster(width, height);
            boolean alphaPremultiplied = cm.isAlphaPremultiplied();

            target = new BufferedImage(cm, raster, alphaPremultiplied, null);
            Graphics2D g = target.createGraphics();
            // smoother than exlax:
            g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
            g.drawRenderedImage(srcImage, AffineTransform.getScaleInstance(sx, sy));
            g.dispose();
            // 将转换后的图片保存
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                ImageIO.write(target, type, baos);
                return baos.toByteArray();
                //FileOutputStream fos = new FileOutputStream(destImg);
                //fos.write(baos.toByteArray());
                //fos.flush();
                //fos.close();
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
        return new byte[0];
    }

    /**
     * 获取文件后缀不带.
     *
     * @param file 文件后缀名
     * @return
     */
    private static String getImageType(File file) {
        if (file != null && file.exists() && file.isFile()) {
            String fileName = file.getName();
            int index = fileName.lastIndexOf(".");
            if (index != -1 && index < fileName.length() - 1) {
                return fileName.substring(index + 1);
            }
        }
        return null;
    }

}

package com.tess4j.sample.service;

import com.tess4j.sample.config.Tess4jClient;
import com.tess4j.sample.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @description: TODO
 * @author: echo
 * @date: 2023/3/29
 */
@Service
@Slf4j
public class Tess4jService {

    @Autowired
    private Tess4jClient tess4jClient;

    public String getChar(String imagesPath){
        String resultStr = "";
        try {
            byte[] bytes = FileUtils.fileToByteArray(imagesPath);
            //从byte[]转换为butteredImage
            ByteArrayInputStream in = new ByteArrayInputStream(bytes);
            BufferedImage imageFile = ImageIO.read(in);
            //识别图片的文字
            resultStr = tess4jClient.doOCR(imageFile);
        }catch (Exception e){
            e.printStackTrace();
        }
        return resultStr;
    }
}

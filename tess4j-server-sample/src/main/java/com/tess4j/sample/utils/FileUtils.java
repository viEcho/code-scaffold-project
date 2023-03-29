package com.tess4j.sample.utils;

import java.io.*;

/**
 * @description: TODO
 * @author: echo
 * @date: 2023/3/29
 */
public class FileUtils {

    /**
     * @description: 图片转字节数组
     * @author: echo
     * @date: 2023/3/29
     * @param: filePath
     * @return: byte[]
     */
    public static byte[] fileToByteArray(String filePath) {
        File file = new File(filePath);
        byte[] ds = null;
        InputStream zp = null;
        ByteArrayOutputStream boos =  new ByteArrayOutputStream();
        try {
            zp = new FileInputStream(file);
            byte[] frush = new byte[1024];//1024表示1k为一段
            int len = -1;
            while((len=zp.read(frush))!=-1) {
                boos.write(frush,0,len);//写出到字节数组中
            }
            boos.flush();
            return boos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(zp!=null) {
                try {
                    zp.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }
}

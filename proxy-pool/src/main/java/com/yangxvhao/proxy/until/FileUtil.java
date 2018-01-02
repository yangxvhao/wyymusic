package com.yangxvhao.proxy.until;

import org.apache.commons.io.IOUtils;

import java.io.*;

/**
 * @author yangxvhao
 * @date 18-1-2.
 */

public class FileUtil {

    /**
     * 读取结果为字节，主要用来读取图片等二进制文件
     * @param path
     * @return
     */
    public static byte[] read2Bytes(String path){
        byte[] data;
        File file = new File(path);
        InputStream inputStream;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            inputStream = new FileInputStream(file);
            int temp;
            byte[] bytes = new byte[2048];
            while ((temp = inputStream.read(bytes)) != -1){
                byteArrayOutputStream.write(bytes,0,temp);
            }
            inputStream.close();
            byteArrayOutputStream.close();
            data = byteArrayOutputStream.toByteArray();
            return data;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String read2Stirng(String path){
        File file = new File(path);
        try {
            return IOUtils.toString(new FileInputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

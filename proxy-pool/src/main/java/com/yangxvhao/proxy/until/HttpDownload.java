package com.yangxvhao.proxy.until;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.*;

/**
 * @author yangxvhao
 * @date 18-1-2.
 */

public class HttpDownload {

    private static String PHOTO_DIR = "/home/yangxvhao/yangxh/picture/";

    public static HttpDownload getInstance(){
        return new HttpDownload();
    }

    public String httpGet(String url){
        return this.httpGet(url, "utf-8");
    }

    public String httpGet(String url, String charset){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            byte[] content = IOUtils.toByteArray(entity.getContent());
            String raw = new String(content,charset);
            return raw;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void getPhoto(String url){
        CloseableHttpClient httpClient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        try {
            CloseableHttpResponse response= httpClient.execute(httpGet);
            InputStream inputStream = response.getEntity().getContent();
            String fileName = String.valueOf(System.currentTimeMillis());
            OutputStream outputStream = new FileOutputStream(new File(PHOTO_DIR,fileName + ".jpg"));
            int l = -1;
            byte[] tmp = new byte[4096];
            while ((l = inputStream.read(tmp)) != -1){
                outputStream.write(tmp,0,l);
            }
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

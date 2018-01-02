package com.yangxvhao.proxy.until;

import org.junit.Test;

/**
 * @author yangxvhao
 * @date 18-1-2.
 */

public class HttpDownloadTest {

    @Test
    public void getPhoto() {
        HttpDownload.getInstance().getPhoto("http://shop.10086.cn/i/authImg?t=0.11759182332629958");
    }

    @Test
    public void httpGet(){
        String result = HttpDownload.getInstance().httpGet("http://shop.10086.cn/i/authImg?t=0.11759182332629958");
        System.out.println(result);
    }
}
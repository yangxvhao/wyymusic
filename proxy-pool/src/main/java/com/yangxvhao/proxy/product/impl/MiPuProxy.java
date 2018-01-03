package com.yangxvhao.proxy.product.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yangxvhao.proxy.model.HttpProxy;
import com.yangxvhao.proxy.product.AbstractProduct;
import com.yangxvhao.proxy.until.DateUtils;
import com.yangxvhao.proxy.until.FileUtil;
import com.yangxvhao.proxy.until.HttpDownload;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yangxvhao
 * @date 17-12-29.
 */
@Component
@Configurable
public class MiPuProxy extends AbstractProduct {

    @Value("${proxy.mipu.url}")
    private String url;

    @Override
    public List<HttpProxy> doWork() {
        String todayTime = DateUtils.getTime();
        String path;

        /**
         * /home/yangxvhao/IdeaProjects/wyymusic/proxy-pool/src/main/resources/proxy/20180102.json
         */
        try {
            path = System.getProperty("user.dir") + "/proxy-pool/src/main/resources/proxy/" + todayTime + ".json";
//            path = Thread.currentThread().getContextClassLoader().getResource("proxy/" + todayTime +".json").getPath();
        }catch (Exception e){
            throw e;
        }


        return doWork(path);
    }

    private List<HttpProxy> doWork(String path){
        List<HttpProxy> proxyList = new ArrayList<>();
        String result;
        File file = new File(path);

        if(file.exists()){
            result = FileUtil.read2Stirng(path);
        }else {
            result = HttpDownload.getInstance().httpGet(url);
        }
        JSONObject jsonObject = JSON.parseObject(result);
        JSONArray jsonArray = jsonObject.getJSONArray("result");

        for (Object object : jsonArray) {
            HttpProxy httpProxy = new HttpProxy();
            JSONObject proxyJson = (JSONObject) object;
            String ip = proxyJson.getString("ip:port").split(":")[0];
            String port = proxyJson.getString("ip:port").split(":")[1];
            httpProxy.setHost(ip);
            httpProxy.setPort(port);
            proxyList.add(httpProxy);
        }

        return proxyList;
    }
}

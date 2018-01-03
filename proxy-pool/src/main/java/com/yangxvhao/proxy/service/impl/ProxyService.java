package com.yangxvhao.proxy.service.impl;

import com.yangxvhao.proxy.model.HttpProxy;
import com.yangxvhao.proxy.service.AbstractProxyService;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * @author yangxvhao
 * @date 18-1-3.
 */
@Service
public class ProxyService extends AbstractProxyService {

    private RMap<String,HttpProxy> rMap = null;

    private String prefix = "proxy:pool";

    @Autowired(required = false)
    RedissonClient redissonClient;

    @Override
    public void add(List<HttpProxy> proxyList) {
        for (HttpProxy httpProxy : proxyList) {
            if(!getMap().containsKey(httpProxy.getKey())) {
                getMap().put(httpProxy.getKey(),httpProxy);
            }
        }
    }

    @Override
    public HttpProxy get() {
        List<HttpProxy> proxyList = getProduct();
        if(proxyList.size() > 0){
            return proxyList.get(new Random().nextInt(proxyList.size()));
        }
        return null;
    }

    public List<HttpProxy> getProduct(){
        return new ArrayList<>(getMap().values());
    }

    public Map<String, HttpProxy> getMap(){
        rMap = redissonClient.getMap(prefix);
        return rMap;
    }
}

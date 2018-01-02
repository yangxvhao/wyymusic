package com.yangxvhao.proxy.service;

import com.yangxvhao.proxy.model.HttpProxy;
import org.redisson.api.RMap;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author yangxvhao
 * @date 17-12-28.
 */

public abstract class AbstractProxyService implements ProxyService {

    @Autowired
    RedissonClient redissonClient;

    private RMap<String,String> rMap = null;

    @Override
    public void add(List<HttpProxy> proxyList) {
        for (HttpProxy httpProxy : proxyList) {
            rMap.put(httpProxy.getHost(),httpProxy.getPort());
        }

    }

    @Override
    public HttpProxy get() {
        return null;
    }
}

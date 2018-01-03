package com.yangxvhao.proxy.service;

import com.yangxvhao.proxy.model.HttpProxy;

import java.util.List;

/**
 * @author yangxvhao
 * @date 17-12-28.
 */
public abstract class AbstractProxyService implements ProxyService {

    @Override
    public void add(List<HttpProxy> proxyList) {

    }

    @Override
    public HttpProxy get() {
        return null;
    }
}

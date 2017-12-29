package com.yangxvhao.proxy.service;

import com.yangxvhao.proxy.model.HttpProxy;

import java.util.List;

/**
 * @author yangxvhao
 * @date 17-12-28.
 */

public interface ProxyService {

    void add(List<HttpProxy> proxyList);

    HttpProxy get();
}

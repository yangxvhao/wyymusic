package com.yangxvhao.proxy.service;

import com.yangxvhao.proxy.model.HttpProxy;

/**
 * @author yangxvhao
 * @date 17-12-28.
 */

public interface ProxyService {

    void add(HttpProxy httpProxy);

    HttpProxy get();
}

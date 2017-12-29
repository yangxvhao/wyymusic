package com.yangxvhao.proxy.product;

import com.yangxvhao.proxy.model.HttpProxy;
import com.yangxvhao.proxy.service.ProxyService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 抽象产品
 * @author yangxvhao
 * @date 17-12-28.
 */

public abstract class AbstractProduct {

    @Autowired
    ProxyService service;

    public void produce(){
        List<HttpProxy> proxyList = doWork();
        service.add(proxyList);
    }

    public abstract List<HttpProxy> doWork();
}

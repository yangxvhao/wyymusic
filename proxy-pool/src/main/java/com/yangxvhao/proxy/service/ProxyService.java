package com.yangxvhao.proxy.service;

import com.yangxvhao.proxy.model.HttpProxy;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author yangxvhao
 * @date 17-12-28.
 */
@Service
public interface ProxyService {

    void add(List<HttpProxy> proxyList);

    HttpProxy get();
}

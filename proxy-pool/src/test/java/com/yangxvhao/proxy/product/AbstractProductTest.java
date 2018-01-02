package com.yangxvhao.proxy.product;

import com.yangxvhao.proxy.product.impl.MiPuProxy;
import org.junit.Test;
import org.redisson.api.RMap;

/**
 * @author yangxvhao
 * @date 18-1-2.
 */

public class AbstractProductTest {
    private RMap<String,String> rMap = null;
    @Test
    public void produce() {
        MiPuProxy miPuProxy = new MiPuProxy();
        miPuProxy.produce();
    }

    @Test
    public void doWork() {
    }
}
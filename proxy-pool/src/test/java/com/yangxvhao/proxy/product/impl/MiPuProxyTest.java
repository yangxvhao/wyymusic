package com.yangxvhao.proxy.product.impl;

import com.yangxvhao.proxy.model.HttpProxy;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

/**
 * @author yangxvhao
 * @date 18-1-2.
 */

public class MiPuProxyTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void doWork() {

        List<HttpProxy> list = new MiPuProxy().doWork();
        System.out.println();
    }
}
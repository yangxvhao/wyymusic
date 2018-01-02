package com.yangxvhao.proxy.until;

import org.junit.Test;

/**
 * @author yangxvhao
 * @date 18-1-2.
 */

public class DateUtilsTest {

    @Test
    public void getTimeFormat() {
        System.out.println(DateUtils.getTimeFormat("yyyy-MM-dd"));
    }

    @Test
    public void getTime() {
        System.out.println(DateUtils.getTime());
    }
}
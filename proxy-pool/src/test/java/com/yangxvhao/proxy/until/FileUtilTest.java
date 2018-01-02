package com.yangxvhao.proxy.until;

import org.junit.Test;

/**
 * @author yangxvhao
 * @date 18-1-2.
 */

public class FileUtilTest {

    String path = Thread.currentThread().getContextClassLoader().getResource("proxy/20180102.json").getPath();

    @Test
    public void read2Bytes(){
        System.out.println(new String(FileUtil.read2Bytes(path)));
    }

    @Test
    public void read2String() {

        System.out.println(FileUtil.read2Stirng(path));
    }
}
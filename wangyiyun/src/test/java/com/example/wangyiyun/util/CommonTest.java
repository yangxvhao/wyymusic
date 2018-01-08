package com.example.wangyiyun.util;

import org.junit.Test;

/**
 * @author yangxvhao
 * @date 18-1-4.
 */

public class CommonTest {

    @Test
    public void filterEmoji() {
        String string = "\"\"姑娘有心事？\"\"书生抿嘴品茶问到。\n" +
                "少女：\"\"公子与城东唐公子可是熟识？\"\"\n" +
                "书生：\"\"世交。\"\"\n" +
                "少女：\"\"那下次你与他一起来吧。\"\"\n" +
                "书生：\"\"他不喜品茶。\"\"\n" +
                "少女：\"\"没关系，那他喜欢什么，我可以慢慢学。\"\"\n" +
                "书生：\"\"我其实也不喜品茶。\"\"\n" +
                "少女：\"\"说谎，那你每天早晨都跑我这茶铺干嘛。\"\"";
        System.out.println(Common.filterEmoji(string));
    }
}
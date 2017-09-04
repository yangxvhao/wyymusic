package com.example.wangyiyun;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;

/**
 * Created by yangxvhao on 17-9-4.
 */
public class ProducerSpider extends CustomerSpider {

    private List<Request> requests;


    @Override
    protected void extractAndAddRequests(Page page) throws Exception {
        super.extractAndAddRequests(page);
    }

    @Override
    protected void init() {
        super.init();
    }

    public ProducerSpider(PageProcessor pageProcessor) {
        super(pageProcessor);
    }
}

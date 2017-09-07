package com.example.wangyiyun;

import com.example.wangyiyun.util.EventControllerUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.poplaris.rabbitmq.client.EventController;
import org.apache.commons.collections.CollectionUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.UrlUtils;

import java.util.List;

/**
 * Created by yangxvhao on 17-9-4.
 */
@Data
@Slf4j
public class ProducerSpider extends CustomerSpider {


    private List<Request> startRequests;


    @Override
    protected void extractAndAddRequests(Page page) throws Exception {
        if (CollectionUtils.isNotEmpty(page.getTargetRequests())){
            for (Request request : page.getTargetRequests()) {
                push(request);
            }
        }
    }

    @Override
    protected void init() throws Exception {
//        if (CollectionUtils.isNotEmpty(startRequests)){
//            synchronized (ProducerSpider.class){
//                if (CollectionUtils.isNotEmpty(startRequests)) {
//                    for (Request request : startRequests) {
//                        push(request);
//                    }
//                    startRequests.clear();
//                }
//            }
//        }
    }

    public void push(Request request) throws Exception {
        if (site.getDomain() == null && request != null && request.getUrl() != null) {
            site.setDomain(UrlUtils.getDomain(request.getUrl()));
        }
        eventController.getEopEventTemplate().send(EventControllerUtil.defaultQueue,EventControllerUtil.defaultExchange,request);
    }

    public ProducerSpider(PageProcessor pageProcessor, List<Request> requests, EventController eventController) {
        super(pageProcessor,eventController);
        this.startRequests = requests;
        this.site = pageProcessor.getSite();
    }
}

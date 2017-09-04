package com.example.wangyiyun;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import me.poplaris.rabbitmq.client.EventProcesser;
import org.apache.commons.collections.CollectionUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.utils.UrlUtils;

import java.util.List;



/**
 * Created by yangxvhao on 17-9-4.
 */
@Slf4j
@Builder
public class CustomerSpider implements EventProcesser{


    private Downloader downloader;

    private PageProcessor pageProcessor;

    private List<Pipeline> pipelines;

    private Site site;

    public CustomerSpider(PageProcessor pageProcessor){
        this.pageProcessor=pageProcessor;
        this.site=pageProcessor.getSite();
    }

    public static CustomerSpider create(PageProcessor pageProcessor){
        return new CustomerSpider(pageProcessor);
    }

    private void initComponent(){
        if (downloader == null){
            synchronized (CustomerSpider.class){
                if (downloader == null){
                    downloader = new HttpClientDownloader();
                }
            }
        }

        if (pipelines.isEmpty()){
            synchronized (CustomerSpider.class){
                if (pipelines.isEmpty()){
                    pipelines.add(new ConsolePipeline());
                }
            }
        }
        init();
    }

    protected void init(){}

    public void push(Request request) throws Exception {
        if (site.getDomain() == null && request != null && request.getUrl() != null) {
            site.setDomain(UrlUtils.getDomain(request.getUrl()));
        }
        scheduler.push(request,this);
    }

    protected void extractAndAddRequests(Page page)throws Exception{

        if (CollectionUtils.isNotEmpty(page.getTargetRequests())){
            for (Request request : page.getTargetRequests()) {
                push(request);
            }
        }
    }

    @Override
    public void process(Object e) {
        log.info("test:" + e);
    }
}

package com.example.wangyiyun;

import com.google.common.util.concurrent.RateLimiter;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import me.poplaris.rabbitmq.client.EventController;
import me.poplaris.rabbitmq.client.EventProcesser;
import sun.security.provider.MD5;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.downloader.Downloader;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.pipeline.Pipeline;
import us.codecraft.webmagic.processor.PageProcessor;

import java.util.List;



/**
 * Created by yangxvhao on 17-9-4.
 */
@Slf4j
@Data
public class CustomerSpider implements EventProcesser{

    protected Downloader downloader;

    protected PageProcessor pageProcessor;

    protected EventController eventController;

    protected List<Pipeline> pipelines;

    protected Site site;

    public CustomerSpider(PageProcessor pageProcessor ,EventController eventController){
        this.pageProcessor = pageProcessor;
        this.eventController = eventController;
        this.site = pageProcessor.getSite();
    }

    public static CustomerSpider create(PageProcessor pageProcessor ,EventController eventController){
        return new CustomerSpider(pageProcessor ,eventController);
    }

    private void initComponent() throws Exception {
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

    protected void init() throws Exception {}



    protected void extractAndAddRequests(Page page)throws Exception{
    }

    @Override
    public void process(Object e) throws Exception {
        initComponent();
        boolean isSuccess = false;
        Task task = new Task() {
            @Override
            public String getUUID() {
                return new MD5().toString();
            }

            @Override
            public Site getSite() {
                return site;
            }
        };
        RateLimiter.create(1.0).acquire();

        log.info("test:" + e);
        Page page = downloader.download((Request) e,task);
        if(page == null){
            log.info("等待");
            Thread.sleep(site.getSleepTime());
        }else {
            pageProcessor.process(page);
            extractAndAddRequests(page);
            isSuccess = true;
        }
        if(!isSuccess){
            log.info("重试");
            process(e);
        }
    }
}

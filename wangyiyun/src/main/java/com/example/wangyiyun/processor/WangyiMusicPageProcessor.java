package com.example.wangyiyun.processor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONPath;
import com.example.wangyiyun.ProducerSpider;
import com.example.wangyiyun.model.Comment;
import com.example.wangyiyun.model.Music;
import com.example.wangyiyun.pipeline.NetEaseMusicPipeline;
import com.example.wangyiyun.util.Common;
import com.example.wangyiyun.util.EventControllerUtil;
import lombok.extern.slf4j.Slf4j;
import me.poplaris.rabbitmq.client.EventController;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.downloader.HttpClientDownloader;
import us.codecraft.webmagic.processor.PageProcessor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@Slf4j
public class WangyiMusicPageProcessor implements PageProcessor {
    // 主域名
    public static final String BASE_URL = "http://music.163.com/";

    // 匹配专辑URL
    public static final String ALBUM_URL = "http://music\\.163\\.com/discover/toplist\\?id=\\d+";

    // 匹配歌曲URL
    public static final String MUSIC_URL = "http://music\\.163\\.com/song\\?id=\\d+";

    //用户主页
    private static final String USER_HOME_URL = "http://music.163.com/user/home?id=";

    // 初始地址, 热歌榜
    public static final String START_URL = "http://music.163.com/discover/toplist?id=3778678";

    public static final int ONE_PAGE = 20;
    private static final int TIME_OUT = 30 *1000;
    private static final int RETRY_TIME = 3;

    @Override
    public Site getSite() {
        return Site.me()
                .setDomain("http://music.163.com")
                .setSleepTime(1000)
                .setRetryTimes(30)
                .setCharset("utf-8")
                .setTimeOut(TIME_OUT)
                .setUserAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_7_2) AppleWebKit/537.31 (KHTML, like Gecko) Chrome/26.0.1410.65 Safari/537.31");
    }

    @Override
    public void process(Page page) {
        // 根据URL判断页面类型
        if (page.getUrl().regex(ALBUM_URL).match()) {
            System.out.println("歌曲总数----->" + page.getHtml().xpath("//*[@id=\"toplist\"]/div[2]/div/div[2]/div[1]/span/span/text()").toString());
            // 爬取歌曲URl加入队列
            page.addTargetRequests(page.getHtml().xpath("//div[@id=\"song-list-pre-cache\"]").links().regex(MUSIC_URL).all());
        } else {
            String url = page.getUrl().toString();
            String songId = url.substring(url.indexOf("id=") + 3);

            Music music = new Music();
            music.setSongId(songId);
            music.setTitle(page.getHtml().xpath("//em[@class='f-ff2']/text()").toString());
            music.setAuthor(page.getHtml().xpath("//p[@class='des s-fc4']/span/a/text()").toString());
            music.setAlbum(page.getHtml().xpath("//p[@class='des s-fc4']/a/text()").toString());
            music.setSongUrl(url);
            // 单独对AJAX请求获取评论数, 使用JSON解析返回结果
            page = getHotComment(page, songId);
            int commentCount = Integer.valueOf(page.getRequest().getExtra("commentCount").toString());
            music.setCommentCount(commentCount);
            page.putField("music",music);
        }
    }

    public static void main(String[] args) {

        List<Request> requests = new ArrayList<>();
        requests.add(new Request(START_URL));

        EventController eventController = EventControllerUtil.getInstance();

        ProducerSpider spider = new ProducerSpider(new WangyiMusicPageProcessor(),requests,eventController);

        spider.setPipelines(Arrays.asList(new NetEaseMusicPipeline()));

        eventController.add(EventControllerUtil.defaultQueue,EventControllerUtil.defaultExchange,spider);
        eventController.start();
        try {
            spider.push(new Request(START_URL));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    public static void start(WangyiMusicPageProcessor processor,NetEaseMusicPipeline pipeline) {
        HttpClientDownloader httpClientDownloader = new HttpClientDownloader();
//		Proxy proxy1 = new Proxy("125.38.39.43", 9797);
//		Proxy proxy2 = new Proxy("127.0.0.1", 1080);
//
//		httpClientDownloader.setProxyProvider(SimpleProxyProvider.from(proxy2));

        long start = System.currentTimeMillis();
        Request request = new Request(START_URL);
        request.setMethod("GET");
        Spider.create(processor)
                .addRequest(request)
                .addPipeline(pipeline)
                .setDownloader(httpClientDownloader)
                .run();
        long end = System.currentTimeMillis();
        System.out.println("爬虫结束,耗时--->" + Common.parseMillisecone(end - start));
    }

    private Page getHotComment(Page page,String songId){
        JSONObject jsonObject = new JSONObject();
        try {
             jsonObject=JSONObject.parseObject(crawlAjaxUrl(songId,0));
        }catch (Exception e){
            log.error("下载异常，重试。。。");
            for(int i = 0; i < RETRY_TIME; i++){
                jsonObject=JSONObject.parseObject(crawlAjaxUrl(songId,0));
            }
        }
        if(jsonObject == null){
            return null;
        }
        int commentCount = jsonObject.getIntValue("total");
        page.getRequest().putExtra("commentCount",commentCount);
        JSONArray jsonArray = jsonObject.getJSONArray("hotComments");
        List<Comment> commentList = new ArrayList<>();
        jsonArray.forEach((Object object) -> {
            JSONObject hotComment = (JSONObject)object;

            Comment comment = new Comment();
            comment.setSongId(songId);
            comment.setLikedCount(hotComment.getIntValue("likedCount"));
            comment.setComments(Common.filterEmoji(hotComment.getString("content")));
            comment.setCreateTime(Common.stampToDate(hotComment.getLong("time")));
            JSONObject userObject = hotComment.getJSONObject("user");
            comment.setNickName(userObject.getString("nickname"));
            comment.setAvatarUrl(userObject.getString("avatarUrl"));
            comment.setUserUrl(USER_HOME_URL+userObject.getString("userId"));
            log.info(comment.toString());
            commentList.add(comment);
        });
        page.putField("comments",commentList);
        return page;
    }

    private Page getAllComment(Page page, String songId, int offset) {
        JSONObject jsonObject = JSON.parseObject(crawlAjaxUrl(songId, offset));
        int commentCount = (Integer) JSONPath.eval(jsonObject, "$.total");
        page.getRequest().putExtra("commentCount",commentCount);
        log.info("评论总数：{}",commentCount);
        for (; offset < commentCount; offset = offset + ONE_PAGE) {

            log.info("评论页下载中：第{}条",offset);
            List<Comment> comments = new ArrayList<>();
            JSONObject obj = JSON.parseObject(crawlAjaxUrl(songId, offset));
            List<String> contents = (List<String>) JSONPath.eval(obj, "$.comments.content");
            List<Integer> likedCounts = (List<Integer>) JSONPath.eval(obj, "$.comments.likedCount");
            List<String> nicknames = (List<String>) JSONPath.eval(obj, "$.comments.user.nickname");
            List<Long> times = (List<Long>) JSONPath.eval(obj, "$.comments.time");

            for (int i = 0; i < contents.size(); i++) {
                Comment comment = new Comment();
                comment.setSongId(songId);
                comment.setComments(Common.filterEmoji(contents.get(i)));
                comment.setLikedCount(likedCounts.get(i));
                comment.setNickName(nicknames.get(i));
                comment.setCreateTime(Common.stampToDate(times.get(i)));
                log.info("comment:{}",comment.toString());
                comments.add(comment);
            }
            page.putField("comments",comments);
        }
        return page;
    }

    private String crawlAjaxUrl(String songId, int offset) {
        log.info("评论页下载中........");

        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse response = null;
        String first_param = "{rid:\"\", offset:\"offset_param\", total:\"true\", limit:\"20\", csrf_token:\"\"}";
        first_param = first_param.replace("offset_param", offset + "");
        //first_param = first_param.replace("limit_param", ONE_PAGE + "");
        try {
            // 参数加密
            // 16位随机字符串，直接FFF
//             String secKey = new BigInteger(100, new SecureRandom()).toString(32).substring(0, 16);
            String secKey = "FFFFFFFFFFFFFFFF";
            // 两遍ASE加密
            HttpHost httpHost = new HttpHost("200.165.208.42", 8080);
            String encText = Common.aesEncrypt(Common.aesEncrypt(first_param, "0CoJUm6Qyw8W8jud"), secKey);
            String encSecKey = Common.rsaEncrypt();
            //http://music.163.com/weapi/v1/resource/comments/R_SO_4_470573623?csrf_token=
            HttpPost httpPost = new HttpPost("http://music.163.com/weapi/v1/resource/comments/R_SO_4_" + songId + "/?csrf_token=");
            httpPost.addHeader("Referer", BASE_URL);
            httpPost.addHeader("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.108 Safari/537.36");
            RequestConfig config = RequestConfig.custom().setConnectTimeout(TIME_OUT)
                    .setConnectionRequestTimeout(TIME_OUT)
                    .setSocketTimeout(TIME_OUT)
                    .setProxy(httpHost)
                    .build()
                    ;
            httpPost.setConfig(config);

            List<NameValuePair> ls = new ArrayList<NameValuePair>();
            ls.add(new BasicNameValuePair("params", encText));
            ls.add(new BasicNameValuePair("encSecKey", encSecKey));

            UrlEncodedFormEntity paramEntity = new UrlEncodedFormEntity(ls, "utf-8");
            httpPost.setEntity(paramEntity);

            response = httpclient.execute(httpPost);

            //重试
            if(response.getStatusLine().getStatusCode() != HttpStatus.SC_OK){
                int i = 0;
                while (RETRY_TIME > i){
                    response = httpclient.execute(httpPost);
                    i++;
                }
            }

            HttpEntity entity = response.getEntity();

            if (entity != null) {
                return EntityUtils.toString(entity, "utf-8");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                response.close();
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return "";
    }

}

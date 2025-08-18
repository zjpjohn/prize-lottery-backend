package com.prize.lottery.infrast.spider.news;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.cloud.arch.http.HttpRequest;
import com.cloud.arch.oss.store.OssResult;
import com.cloud.arch.oss.store.OssStorageTemplate;
import com.cloud.arch.utils.IdWorker;
import com.google.common.hash.Hashing;
import com.prize.lottery.delay.AbsDelayTaskExecutor;
import com.prize.lottery.enums.LotteryEnum;
import com.prize.lottery.infrast.facade.impl.HttpRequestWrapper;
import com.prize.lottery.mapper.LotteryNewsMapper;
import com.prize.lottery.po.lottery.LotteryNewsPo;
import com.prize.lottery.value.NewsContent;
import com.prize.lottery.value.NewsParagraph;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.stream.Collectors;


public class SinaLotteryNewsSpider extends AbsDelayTaskExecutor<NewsTask> {

    public static final String SOURCE         = "新浪彩票";
    public static final String SINA_NEWS_LIST = "https://interface.sina.cn/pc_zt_api/pc_zt_press_news_doc.d.json?subjectID=155950&cat=&size=40&page=PAGE&channel=sports";

    private final OssStorageTemplate storageTemplate;
    private final LotteryNewsMapper  lotteryNewsMapper;
    private final HttpRequestWrapper requestWrapper;

    public SinaLotteryNewsSpider(Executor executor,
                                 HttpRequestWrapper requestWrapper,
                                 OssStorageTemplate storageTemplate,
                                 LotteryNewsMapper lotteryNewsMapper) {
        super(executor);
        this.requestWrapper    = requestWrapper;
        this.storageTemplate   = storageTemplate;
        this.lotteryNewsMapper = lotteryNewsMapper;
    }

    @Override
    public void executeRequest(NewsTask request) {
        LotteryNewsPo news = this.fetchNews(request);
        lotteryNewsMapper.addLotteryNews(news);
    }

    /**
     * 抓取新闻详情
     *
     * @param task 抓取任务
     */
    public LotteryNewsPo fetchNews(NewsTask task) {
        Element artibody = requestWrapper.getExec(task.getUrl(), response -> {
            Document document = Jsoup.parse(response);
            return document.getElementById("artibody");
        });
        List<NewsParagraph> paragraphs = artibody.children()
                                                 .stream()
                                                 .filter(e -> e.hasText()
                                                         && !e.hasClass("otherContent_01")
                                                         && e.select("span#ct-url-anchor").isEmpty())
                                                 .map(e -> {
                                                     NewsParagraph paragraph = new NewsParagraph();
                                                     if (e.hasClass("img_wrapper")) {
                                                         String img = e.getElementsByTag("img").first().attr("src");
                                                         paragraph.setType(2);
                                                         if (!img.contains("https")) {
                                                             img = "https:" + img;
                                                         }
                                                         paragraph.setContent(downImage(img));
                                                         return paragraph;
                                                     }
                                                     paragraph.setType(1);
                                                     paragraph.setContent(e.text().trim());
                                                     return paragraph;
                                                 })
                                                 .collect(Collectors.toList());
        LotteryNewsPo news = new LotteryNewsPo();
        news.setSource(SOURCE);
        news.setContent(NewsContent.of(paragraphs));
        news.setTitle(task.getTitle());
        news.setGmtCreate(task.getCreateTime());
        news.setSeq(String.valueOf(IdWorker.nextId()));
        news.setSha(Hashing.sha256().hashString(task.getUrl(), StandardCharsets.UTF_8).toString());
        //设置header图片
        String header = Optional.ofNullable(task.getImg())
                                .filter(StringUtils::isNotBlank)
                                .map(this::downImage)
                                .orElseGet(() -> paragraphs.stream()
                                                           .filter(e -> e.getType() == 2)
                                                           .findFirst()
                                                           .map(NewsParagraph::getContent)
                                                           .orElse(""));
        news.setHeader(header);

        //设置摘要
        String summary = paragraphs.stream()
                                   .filter(e -> e.getType() == 1)
                                   .findFirst()
                                   .map(NewsParagraph::getContent)
                                   .orElse("");
        news.setSummary(summary);

        //提取新闻类型:标题优先
        LotteryEnum.parseType(news.getTitle()).ifPresent(e -> news.setType(e.getType()));
        if (news.getType() == null) {
            LotteryEnum.parseType(news.getSummary()).ifPresent(e -> news.setType(e.getType()));
        }

        return news;
    }

    /**
     * 分页抓取新闻列表
     *
     * @param page   页码
     * @param millis 抓取起始时间
     * @param range  抓取时间范围
     */
    public void fetchNewsList(int page, long millis, int range) {
        String     pageApi  = SINA_NEWS_LIST.replace("PAGE", String.valueOf(page));
        String     response = HttpRequest.instance().get(pageApi);
        JSONObject root     = JSON.parseObject(response).getJSONObject("result");
        if (root.getIntValue("status") != 0 || root.getIntValue("count") == 0) {
            return;
        }
        JSONArray data   = root.getJSONArray("data");
        Random    random = new Random();
        data.forEach(e -> {
            JSONObject node = (JSONObject) e;
            NewsTask   task = new NewsTask();
            task.setTitle(node.getString("title"));
            task.setImg(node.getString("img"));
            task.setUrl(node.getString("url"));
            LocalDateTime createTime = Instant.ofEpochSecond(node.getLong("createtime"))
                                              .atZone(ZoneId.systemDefault())
                                              .toLocalDateTime();
            task.setCreateTime(createTime);
            task.setTimestamp(millis + random.nextInt(range) * 1000L);
            this.delayExe(task);
        });
    }

    /**
     * 下载图片保存值阿里云
     *
     * @param url 原始图片地址
     */
    public String downImage(String url) {
        try {
            URL           imageUrl   = new URL(url);
            URLConnection connection = imageUrl.openConnection();
            InputStream   stream     = connection.getInputStream();

            String suffix = url.substring(url.lastIndexOf("."));
            String key    = "news/" + System.currentTimeMillis() + "_" + (int) (Math.random() * 1000) + suffix;

            OssResult response = storageTemplate.store(stream, key);
            return response.getUrl();
        } catch (Exception e) {
            throw new RuntimeException("下载资讯图片错误", e);
        }
    }

}

package com.prize.lottery.infrast.spider.skill;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.cloud.arch.encrypt.AESKit;
import com.cloud.arch.oss.store.OssResult;
import com.cloud.arch.oss.store.OssStorageTemplate;
import com.cloud.arch.utils.IdWorker;
import com.cloud.arch.utils.JsonUtils;
import com.google.common.collect.Maps;
import com.prize.lottery.delay.AbsDelayTaskExecutor;
import com.prize.lottery.infrast.facade.impl.HttpRequestWrapper;
import com.prize.lottery.mapper.LotterySkillMapper;
import com.prize.lottery.po.lottery.LotterySkillPo;
import com.prize.lottery.value.NewsContent;
import com.prize.lottery.value.NewsParagraph;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.util.CollectionUtils;

import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;

@Slf4j
public class LotterySkillSpider extends AbsDelayTaskExecutor<SkillTask> {

    public static final String REQUEST_API = "https://api.caihongduoduo.com/carina/unct/public/handler";
    public static final String AES_KEY     = "d3YmI1BUOSE2S2YmalBVZUQ=";
    public static final String AES_IV      = "0000000000000000";

    private final HttpRequestWrapper requestWrapper;
    private final OssStorageTemplate storageTemplate;
    private final LotterySkillMapper lotterySkillMapper;

    public LotterySkillSpider(Executor executor,
                              HttpRequestWrapper requestWrapper,
                              OssStorageTemplate storageTemplate,
                              LotterySkillMapper lotterySkillMapper) {
        super(executor);
        this.requestWrapper     = requestWrapper;
        this.storageTemplate    = storageTemplate;
        this.lotterySkillMapper = lotterySkillMapper;
    }

    @Override
    public void executeRequest(SkillTask request) {
        LotterySkillPo skill = fetchSkill(request);
        if (skill != null) {
            lotterySkillMapper.addLotterySkill(skill);
        }
    }

    LotterySkillPo fetchSkill(SkillTask request) {
        Map<String, String> form = Maps.newHashMap();
        form.put("request", AESKit.CBC.pkc7Enc(request.jsonBody(), AES_KEY, AES_IV));
        Document document = requestWrapper.postFormExec(REQUEST_API, form, response -> {
            return Optional.ofNullable(JSON.parseObject(response))
                           .filter(node -> node.getIntValue("code") == 0)
                           .map(node -> node.getJSONObject("data"))
                           .map(node -> node.getString("content"))
                           .filter(StringUtils::isNotBlank)
                           .map(Jsoup::parse)
                           .orElse(null);
        });
        if (document == null) {
            return null;
        }
        LotterySkillPo skill = new LotterySkillPo();
        skill.setType(request.getType());
        skill.setTitle(request.getTitle());
        skill.setGmtCreate(request.getCreateTime());
        skill.setSeq(String.valueOf(IdWorker.nextId()));
        skill.setBrowse(request.getBrowse());
        skill.setSha(request.getRequestSha());
        skill.setHeader(downImage(request.getHeader()));

        //是否有文字内容
        AtomicBoolean hasContent = new AtomicBoolean(false);
        List<NewsParagraph> paragraphs = document.select("p").stream().map(e -> {
            NewsParagraph paragraph = new NewsParagraph();
            Element       img       = e.getElementsByTag("img").first();
            if (img != null) {
                String image = img.attr("src");
                paragraph.setType(2);
                paragraph.setContent(downImage(image));
            } else {
                paragraph.setType(1);
                paragraph.setContent(e.text().trim());
                if (StringUtils.isNotBlank(paragraph.getContent())) {
                    hasContent.set(true);
                }
            }
            return paragraph;
        }).filter(e -> StringUtils.isNotBlank(e.getContent())).collect(Collectors.toList());
        if (CollectionUtils.isEmpty(paragraphs) || !hasContent.get()) {
            return null;
        }
        skill.setContent(NewsContent.of(paragraphs));
        String summary = paragraphs.stream()
                                   .filter(e -> e.getType() == 1 && StringUtils.isNotBlank(e.getContent()))
                                   .findFirst()
                                   .map(NewsParagraph::getContent)
                                   .orElse("");
        skill.setSummary(summary);
        return skill;
    }

    /**
     * 抓取使用技巧列表
     *
     * @param skill  抓取类型
     * @param millis 抓取起始时间
     * @param range  批量抓取时间范围
     * @param size   批量大小
     */
    public void fetchList(LottoSkill skill, long millis, int range, Integer size) {
        ListRequest         request = new ListRequest(size, skill.getLabels());
        Map<String, String> form    = Maps.newHashMap();
        form.put("request", AESKit.CBC.pkc7Enc(JsonUtils.toJson(request), AES_KEY, AES_IV));
        List<SkillTask> skillTasks = requestWrapper.postFormExec(REQUEST_API, form, response -> {
            JSONObject root = JSON.parseObject(response);
            JSONArray  data = root.getJSONArray("data");
            if (root.getIntValue("code") != 0 || data.isEmpty()) {
                return null;
            }
            Random random = new Random();
            return data.stream().map(e -> {
                long executeAt = millis + random.nextInt(range) * 1000L;
                return new SkillTask((JSONObject) e, skill.getType(), executeAt);
            }).filter(this::taskFilter).collect(Collectors.toList());
        });
        this.delayExe(skillTasks);
    }

    private boolean taskFilter(SkillTask task) {
        return !task.getAuthor().equals("多多视频") && !task.getTitle().contains("最佳主播");
    }

    /**
     * 批量抓取各个彩种的实用技巧
     *
     * @param range 时间范围(秒)
     * @param size  抓取数量
     */
    public void batchLottoFetch(int range, int size) {
        long         millis = System.currentTimeMillis();
        LottoSkill[] skills = LottoSkill.values();
        for (int i = 0; i < skills.length; i++) {
            LottoSkill skill     = skills[i];
            long       timestamp = millis + i * range * 1000L;
            this.fetchList(skill, timestamp, range, size);
        }
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
            String key    = "skill/" + System.currentTimeMillis() + "_" + (int) (Math.random() * 1000) + suffix;

            OssResult response = storageTemplate.store(stream, key);
            return response.getUrl();
        } catch (Exception e) {
            throw new RuntimeException("下载资讯图片错误", e);
        }
    }

}

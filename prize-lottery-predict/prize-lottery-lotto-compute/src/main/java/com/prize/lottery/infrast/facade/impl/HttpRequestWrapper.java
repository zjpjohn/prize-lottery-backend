package com.prize.lottery.infrast.facade.impl;

import com.cloud.arch.http.HttpRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

@Slf4j
@Component
@RequiredArgsConstructor
public class HttpRequestWrapper {

    /**
     * http请求代理包装
     *
     * @param request 请求连接
     * @param builder 自定义响应构造器
     */
    public <Res> Res getExec(String request, Function<String, Res> builder) {
        return this.getExec(request, null, null, builder);
    }

    /**
     * http请求代理包装
     *
     * @param request 请求连接
     * @param header  自定义响应头
     * @param builder 自定义响应构造器
     */
    public <Res> Res getExec(String request, Map<String, String> header, Function<String, Res> builder) {
        return this.getExec(request, header, null, builder);
    }

    /**
     * http请求代理包装
     *
     * @param request 请求连接
     * @param charset 请求编码
     * @param builder 自定义响应构造器
     */
    public <Res> Res getExec(String request, Charset charset, Function<String, Res> builder) {
        return this.getExec(request, null, charset, builder);
    }


    /**
     * http请求代理包装
     *
     * @param request 请求连接
     * @param header  自定义请求头
     * @param charset 请求字符编码
     * @param builder 自定义响应构造器
     */
    public <Res> Res getExec(String request,
                             Map<String, String> header,
                             Charset charset,
                             Function<String, Res> builder) {
        try {
            this.setHttpProxy();
            charset = Optional.ofNullable(charset).orElse(StandardCharsets.UTF_8);
            String response = HttpRequest.instance().get(request, header, charset);
            return builder.apply(response);
        } catch (Exception error) {
            log.error(error.getMessage(), error);
        }
        return null;
    }


    /**
     * post表单请求代理ip包装
     *
     * @param request 请求连接
     * @param params  请求保单数据
     * @param builder 自定义响应构造器
     */
    public <Res> Res postFormExec(String request, Map<String, String> params, Function<String, Res> builder) {
        return this.postFormExec(request, null, params, null, builder);
    }

    /**
     * post表单请求代理ip包装
     *
     * @param request 请求连接
     * @param header  自定义请求头
     * @param params  请求保单数据
     * @param charset 请求编码
     * @param builder 自定义响应构造器
     */
    public <Res> Res postFormExec(String request,
                                  Map<String, String> header,
                                  Map<String, String> params,
                                  Charset charset,
                                  Function<String, Res> builder) {
        try {
            this.setHttpProxy();
            charset = Optional.ofNullable(charset).orElse(StandardCharsets.UTF_8);
            String response = HttpRequest.instance().postForm(request, header, params, charset);
            return builder.apply(response);
        } catch (Exception error) {
            log.error(error.getMessage(), error);
        }
        return null;
    }

    /**
     * 设置请求代理ip
     */
    private void setHttpProxy() {
    }
}

package com.prize.lottery.plugins.access.domain;

import lombok.Data;
import org.springframework.http.server.PathContainer;
import org.springframework.web.util.pattern.PathPattern;

import java.util.List;

@Data
public class WhitePattern {

    private PathPattern  pattern;
    private List<String> methods;

    public WhitePattern(PathPattern pattern, List<String> methods) {
        this.pattern = pattern;
        this.methods = methods;
    }

    /**
     * @param request 请求地址
     * @param method  请求方法
     */
    public boolean match(String request, String method) {
        PathContainer path = UriPathContainer.parsePath(request);
        return this.pattern.matches(path)
                && this.methods.contains(method.toLowerCase());
    }
}

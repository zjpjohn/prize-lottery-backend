package com.prize.lottery.infrast.persist.valobj;

import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import com.google.common.collect.Lists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TypeHandler(type = Type.JSON)
public class AppBanner {

    private List<BannerSpecs> items = Lists.newArrayList();

    public List<BannerSpecs> getItems() {
        return Collections.unmodifiableList(items);
    }

    public void push(BannerSpecs specs) {
        items.add(specs);
    }

    public void remove(BannerSpecs specs) {
        this.items.removeIf(s -> s.equals(specs));
    }
}

package com.prize.lottery.value;

import com.cloud.arch.mybatis.annotations.TypeHandler;
import com.cloud.arch.mybatis.core.Type;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@TypeHandler(type = Type.JSON)
public class NewsContent {

    private List<NewsParagraph> paragraphs;

    public static NewsContent of(List<NewsParagraph> paragraphs) {
        return new NewsContent(paragraphs);
    }
}

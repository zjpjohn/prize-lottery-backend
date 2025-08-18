package com.prize.lottery.value;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NewsParagraph {

    //段落类型:1-文本,2-图片
    private Integer type;
    //文本内容或图片链接
    private String  content;
}

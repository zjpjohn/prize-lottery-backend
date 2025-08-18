package com.prize.lottery.dto;

import lombok.Data;

import java.util.List;

@Data
public class N3BestDanDto {

    private String       period;
    private String       last;
    private String       best;
    private String       secondary;
    private List<String> moreList;

}

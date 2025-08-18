package com.prize.lottery.domain.share.valobj;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class ComWarnValue {

    private List<String>  danList;
    private List<String>  killList;
    private List<String>  twoMaList;
    private List<String>  zu6List;
    private List<String>  zu3List;
    private List<Integer> kuaList;
    private List<Integer> sumList;

}

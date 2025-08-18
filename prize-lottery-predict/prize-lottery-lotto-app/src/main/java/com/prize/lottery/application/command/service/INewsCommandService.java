package com.prize.lottery.application.command.service;


import com.prize.lottery.po.lottery.LotteryNewsPo;

public interface INewsCommandService {

    LotteryNewsPo browseNews(String seq, Long userId);
}

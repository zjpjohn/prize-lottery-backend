package com.prize.lottery.domain.browse.repository;


import com.prize.lottery.domain.browse.model.ForecastBrowse;

public interface IUserBrowseRepository {

    void save(ForecastBrowse browse);

    boolean isBrowsed(ForecastBrowse browse);

    Integer browses(ForecastBrowse browse);

    Integer browses(String type, Integer source);

}

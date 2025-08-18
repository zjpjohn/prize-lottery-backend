package com.prize.lottery.domain.pack.repository;


import com.cloud.arch.aggregate.Aggregate;
import com.prize.lottery.domain.pack.model.aggregate.PackInfoDo;

import java.util.Optional;

public interface IPackInfoRepository {

    /**
     * 保存套餐信息
     *
     * @param aggregate 套餐聚合信息
     */
    void save(Aggregate<Long, PackInfoDo> aggregate);

    /**
     * 删除套餐信息
     *
     * @param packNo 套餐编号
     */
    void delete(String packNo);

    /**
     * 查询套餐信息
     *
     * @param packNo 套餐编号
     */
    Aggregate<Long, PackInfoDo> ofNo(String packNo);

    /**
     * 判断套餐名是否已占用
     *
     * @param name 套餐名称
     */
    boolean existName(String name);

    /**
     * 查询指定编号的套餐
     *
     * @param packNo 套餐编号
     */
    Optional<PackInfoDo> ofPackNo(String packNo);

}

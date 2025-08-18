package com.prize.lottery.domain.voucher.model.aggregate;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.utils.IdWorker;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.prize.lottery.domain.voucher.model.entity.UserVoucher;
import com.prize.lottery.domain.voucher.valobj.DrawVoucherObj;
import com.prize.lottery.domain.voucher.valobj.VoucherOperation;
import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Data
public class BatchUserVoucherDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = 952696738583546266L;

    private Long                   id;
    //用户领取代金券集合
    private List<UserVoucher>      vouchers;
    //账户代金券批量操作
    private List<VoucherOperation> operations = Lists.newArrayList();
    //账户操作版本
    private Integer                version;

    public BatchUserVoucherDo() {
        this.id       = IdWorker.nextId();
        this.vouchers = Lists.newArrayList();
        this.version  = 0;
    }

    public BatchUserVoucherDo(List<UserVoucher> vouchers) {
        this.id       = IdWorker.nextId();
        this.vouchers = vouchers;
        this.version  = 1;
    }

    /**
     * 代金券集合是否为空
     */
    public boolean isEmpty() {
        return CollectionUtils.isEmpty(this.vouchers);
    }

    /**
     * 当前代金券数量
     */
    public Integer size() {
        if (!this.isEmpty()) {
            return this.vouchers.size();
        }
        return 0;
    }

    /**
     * 用户领券
     *
     * @param userId  用户标识
     * @param voucher 待领取代金券信息
     */
    public void drawVoucher(Long userId, DrawVoucherObj voucher) {
        UserVoucher userVoucher = new UserVoucher(userId, voucher.getSeqNo(), voucher.getVoucher(), voucher.getExpire());
        this.vouchers.add(userVoucher);
        //领券操作
        VoucherOperation operation = VoucherOperation.draw(userId, 1, voucher.getVoucher());
        this.operations.add(operation);
    }

    /**
     * 批量领取代金券
     *
     * @param userId   用户标识
     * @param vouchers 代金券集合
     */
    public void batchDraw(Long userId, List<DrawVoucherObj> vouchers) {
        int totalNum = 0;
        for (DrawVoucherObj voucher : vouchers) {
            UserVoucher userVoucher = new UserVoucher(userId, voucher.getSeqNo(), voucher.getVoucher(), voucher.getExpire());
            this.vouchers.add(userVoucher);
            totalNum = totalNum + voucher.getVoucher();
        }
        VoucherOperation operation = VoucherOperation.draw(userId, vouchers.size(), totalNum);
        this.operations.add(operation);
    }

    /**
     * 使用代金券
     *
     * @param amount 预计最多使用代金券金额
     * @return 返回已使用代金券金额
     */
    public Integer useVouchers(Long userId, Integer amount) {
        List<UserVoucher> vouchers = this.vouchers.stream()
                                                  .filter(UserVoucher::canUse)
                                                  .sorted(Comparator.comparing(UserVoucher::getGmtCreate))
                                                  .toList();
        if (CollectionUtils.isEmpty(vouchers)) {
            return 0;
        }
        //剩余未使用代金券金额
        Integer maintain = amount;
        //使用优惠券数量
        int used = 0;
        for (UserVoucher voucher : vouchers) {
            maintain = voucher.useVoucher(maintain);
            used     = used + 1;
            if (maintain == 0) {
                break;
            }
        }
        //已使用代金券金额
        int              usedNum   = amount - maintain;
        VoucherOperation operation = VoucherOperation.use(userId, used, usedNum);
        this.operations.add(operation);
        //返回已使用代金券总金额
        return usedNum;
    }

    /**
     * 计算过期代金券
     */
    public Map<Long, Integer> expireVouchers() {
        Map<Long, Integer> result = Maps.newHashMap();
        Map<Long, List<UserVoucher>> grouped = this.vouchers.stream()
                                                            .collect(Collectors.groupingBy(UserVoucher::getUserId));
        for (Map.Entry<Long, List<UserVoucher>> entry : grouped.entrySet()) {
            List<UserVoucher> expiredVouchers = entry.getValue().stream().filter(UserVoucher::isExpired).toList();
            //过期总金额
            Integer total = expiredVouchers.stream().map(UserVoucher::expireVoucher).reduce(0, Integer::sum);
            result.put(entry.getKey(), total);
            //账户过期操作
            VoucherOperation operation = VoucherOperation.expire(entry.getKey(), expiredVouchers.size(), total);
            this.operations.add(operation);
        }
        return result;
    }

    @Override
    public boolean isNew() {
        return this.version == 0;
    }

}

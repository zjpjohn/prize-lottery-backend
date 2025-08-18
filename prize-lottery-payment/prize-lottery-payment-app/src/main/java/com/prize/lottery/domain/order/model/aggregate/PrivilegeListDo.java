package com.prize.lottery.domain.order.model.aggregate;

import com.cloud.arch.aggregate.AggregateRoot;
import com.cloud.arch.utils.CollectionUtils;
import com.cloud.arch.web.utils.Assert;
import com.google.common.collect.Lists;
import com.prize.lottery.application.command.dto.PrivilegeCreateCmd;
import com.prize.lottery.application.command.dto.PrivilegeModifyCmd;
import com.prize.lottery.domain.order.model.entity.PackPrivilege;
import com.prize.lottery.infrast.error.handler.ResponseHandler;
import lombok.Getter;

import java.util.List;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.IntStream;

@Getter
public class PrivilegeListDo implements AggregateRoot<Long> {

    private static final long serialVersionUID = 7575064136670823442L;

    private final List<PackPrivilege> privileges;

    public PrivilegeListDo() {
        this.privileges = Lists.newArrayList();
    }

    public PrivilegeListDo(List<PackPrivilege> privileges) {
        this.privileges = privileges;
    }

    /**
     * 添加套餐特权信息
     */
    public void addPrivilege(PrivilegeCreateCmd command, Function<PrivilegeCreateCmd, PackPrivilege> converter) {
        Optional<PackPrivilege> privilege = this.privileges.stream()
                                                           .filter(p -> p.getName().equals(command.getName()))
                                                           .findFirst();
        Assert.state(privilege.isEmpty(), ResponseHandler.PACK_PRIVILEGE_EXIST);
        PackPrivilege packPrivilege = converter.apply(command);
        int           sorted        = 1;
        if (CollectionUtils.isNotEmpty(this.privileges)) {
            PackPrivilege maxPrivilege = this.privileges.get(this.privileges.size() - 1);
            sorted = maxPrivilege.getSorted() + 1;
        }
        packPrivilege.setSorted(sorted);
        this.privileges.add(packPrivilege);
    }

    /**
     * 编辑套餐特权
     */
    public void modifyPrivilege(PrivilegeModifyCmd command, BiConsumer<PrivilegeModifyCmd, PackPrivilege> converter) {
        PackPrivilege privilege = this.privileges.stream()
                                                 .filter(p -> p.getId().equals(command.getId()))
                                                 .findFirst()
                                                 .orElseThrow(ResponseHandler.PACK_PRIVILEGE_NULL);
        converter.accept(command, privilege);
    }

    /**
     * 删除套餐特权
     */
    public void removePrivilege(Long privilegeId) {
        boolean removed = this.privileges.removeIf(p -> p.getId().equals(privilegeId));
        Assert.state(removed, ResponseHandler.PACK_PRIVILEGE_NULL);
        if (CollectionUtils.isNotEmpty(this.privileges)) {
            for (int i = 0; i < this.privileges.size(); i++) {
                this.privileges.get(i).setSorted(i + 1);
            }
        }
    }

    public void sortPrivilege(Long privilegeId, Integer index) {
        Assert.state(index >= 0 && index < this.privileges.size(), ResponseHandler.PRIVILEGE_SORT_ERROR);
        int oldIndex = IntStream.range(0, this.privileges.size())
                                .filter(i -> this.privileges.get(i).getId().equals(privilegeId))
                                .findFirst()
                                .orElse(-1);
        Assert.state(oldIndex >= 0, ResponseHandler.PACK_PRIVILEGE_NULL);
        int           sorted        = this.privileges.get(index).getSorted();
        PackPrivilege sortPrivilege = this.privileges.get(oldIndex);
        sortPrivilege.setSorted(sorted);
        //默认向后移动
        int start = oldIndex + 1, end = index + 1, delta = -1;
        //向前移动排序
        if (oldIndex > index) {
            start = index;
            end   = oldIndex;
            delta = 1;
        }
        for (int i = start; i < end; i++) {
            PackPrivilege privilege = this.privileges.get(i);
            privilege.setSorted(privilege.getSorted() + delta);
        }
    }

    @Override
    public void setId(Long aLong) {
    }

    @Override
    public Long getId() {
        return 1L;
    }

}

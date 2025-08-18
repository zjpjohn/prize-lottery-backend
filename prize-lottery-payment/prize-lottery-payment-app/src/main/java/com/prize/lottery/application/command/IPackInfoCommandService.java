package com.prize.lottery.application.command;


import com.prize.lottery.application.command.dto.PackInfoCreateCmd;
import com.prize.lottery.application.command.dto.PackInfoModifyCmd;
import com.prize.lottery.application.command.dto.PrivilegeCreateCmd;
import com.prize.lottery.application.command.dto.PrivilegeModifyCmd;

public interface IPackInfoCommandService {

    void createPack(PackInfoCreateCmd command);

    void modifyPack(PackInfoModifyCmd command);

    void removePack(String packNo);

    void addPrivilege(PrivilegeCreateCmd command);

    void editPrivilege(PrivilegeModifyCmd command);

    void removePrivilege(Long privilegeId);

    void sortPrivilege(Long id, Integer index);

}

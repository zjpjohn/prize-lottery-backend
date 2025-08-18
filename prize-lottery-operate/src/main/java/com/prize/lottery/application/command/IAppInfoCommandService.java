package com.prize.lottery.application.command;


import com.prize.lottery.application.command.dto.*;

public interface IAppInfoCommandService {

    void createAppInfo(AppInfoCreateCmd command);

    void editAppInfo(AppInfoModifyCmd command);

    void issueAppMainVersion(Long id);

    void createAppVersion(VersionCreateCmd command);

    void editAppVersion(VersionModifyCmd command);

    void onlineVersion(Long id);

    void offlineVersion(Long id);

    void createContact(ContactCreateCmd command);

    void editContact(ContactEditCmd command);

    void removeContacts(String appNo);

}

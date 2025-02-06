package com.congthanh.messageservice.service;

import java.security.Principal;

public interface OnlineOfflineService {

    void addOnlineUser(Principal user);

    void removeOnlineUser(Principal user);
}

package com.congthanh.webhook.service;

import com.congthanh.webhook.model.viewmodel.EventVm;

import java.util.List;

public interface EventService {

    List<EventVm> findAllEvents();
}

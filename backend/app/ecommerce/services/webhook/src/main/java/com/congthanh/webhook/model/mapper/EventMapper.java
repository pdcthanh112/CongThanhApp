package com.congthanh.webhook.model.mapper;

import com.congthanh.webhook.model.entity.Event;
import com.congthanh.webhook.model.viewmodel.EventVm;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EventMapper {

    EventVm toEventVm(Event event);
}

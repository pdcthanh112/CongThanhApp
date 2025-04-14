package com.congthanh.webhook.model.viewmodel;

import com.congthanh.webhook.constain.enums.EventName;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class EventVm {

    private Long id;

    private EventName name;
}

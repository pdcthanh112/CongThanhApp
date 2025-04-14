package com.congthanh.webhook.service.serviceImpl;

import com.congthanh.webhook.model.entity.Event;
import com.congthanh.webhook.model.mapper.EventMapper;
import com.congthanh.webhook.model.viewmodel.EventVm;
import com.congthanh.webhook.repository.EventRepository;
import com.congthanh.webhook.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;

    @Override
    public List<EventVm> findAllEvents() {
        List<Event> events = eventRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
        return events.stream().map(eventMapper::toEventVm).toList();
    }
}

package com.congthanh.webhook.controller;

import com.congthanh.webhook.model.viewmodel.EventVm;
import com.congthanh.webhook.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/backoffice/events")
@RequiredArgsConstructor
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<EventVm>> listWebhooks() {
        return ResponseEntity.ok(eventService.findAllEvents());
    }
}

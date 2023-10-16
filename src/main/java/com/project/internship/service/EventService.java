package com.project.internship.service;

import com.project.internship.exception.NotFoundException;
import com.project.internship.model.Event;
import com.project.internship.repository.EventRepository;
import com.project.internship.repository.SubscriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class EventService {
    private final EventRepository eventRepository;
    private final SubscriptionRepository subscriptionRepository;

    public EventService(EventRepository eventRepository, SubscriptionRepository subscriptionRepository) {
        this.eventRepository = eventRepository;
        this.subscriptionRepository = subscriptionRepository;
    }

    @Transactional(readOnly = true)
    public Event getEventByID(Long id) {
        Event event = eventRepository.getById(id);
        if (event == null) {
            throw new NotFoundException("Event with ID " + id + " not found!");
        }

        return event;
    }

    public Event save(Event event) {
        int numberRowsAffected = eventRepository.update(event);
        if (numberRowsAffected == 0) {
            Long eventId = eventRepository.add(event);
            return eventRepository.getById(eventId);
        }

        return event;
    }

    @Transactional
    public void delete(Long id) {
        subscriptionRepository.deleteByEventId(id);
        eventRepository.delete(id);
    }
}

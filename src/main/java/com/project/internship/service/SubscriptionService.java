package com.project.internship.service;

import com.project.internship.exception.DuplicateKeyConstraintException;
import com.project.internship.exception.NotAvailableException;
import com.project.internship.exception.NotPermittedException;
import com.project.internship.model.Event;
import com.project.internship.model.Subscription;
import com.project.internship.repository.EmployeeRepository;
import com.project.internship.repository.EventRepository;
import com.project.internship.repository.SubscriptionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class SubscriptionService {
    private final SubscriptionRepository subscriptionRepository;

    private final EventRepository eventRepository;

    private final EmployeeRepository employeeRepository;


    public SubscriptionService(SubscriptionRepository subscriptionRepository, EventRepository eventRepository, EmployeeRepository employeeRepository) {
        this.subscriptionRepository = subscriptionRepository;
        this.eventRepository = eventRepository;
        this.employeeRepository = employeeRepository;
    }

    public void add(Subscription subscription) {
        validateSubscription(subscription);
        try {
            subscriptionRepository.add(subscription);
        } catch (DuplicateKeyException e) {
            throw new DuplicateKeyConstraintException("Employee already subscribed to event with ID " + subscription.getEventId() + "!");
        }
    }

    private void validateSubscription(Subscription subscription) {
        Long employeeId = subscription.getEmployeeId();
        Long eventId = subscription.getEventId();
        if (!checkSameDepartmentForEmployeeAndEvent(eventId, employeeId)) {
            throw new NotPermittedException("Forbidden access to event with ID " + eventId + "!");
        }
        if (!checkCapacityAvailable(eventId)) {
            throw new NotAvailableException("No more available slots for event with ID " + eventId + "!");
        }
    }

    private boolean checkSameDepartmentForEmployeeAndEvent(Long eventId, Long employeeId) {
        Long eventDepartmentId = getEventById(eventId).getDepartmentId();
        Long employeeDepartmentId = employeeRepository.getById(employeeId).getDepartmentId();

        return employeeDepartmentId.equals(eventDepartmentId);
    }

    private boolean checkCapacityAvailable(Long eventId) {
        int maxCapacity = getEventById(eventId).getCapacity();
        int numberOfSubscriptions = subscriptionRepository.findNumberOfSubscribersForEventId(eventId);
        return numberOfSubscriptions < maxCapacity;
    }

    private Event getEventById(Long id) {
        return eventRepository.getById(id);
    }

}

package com.project.internship.employeetest;

import com.project.internship.exception.DuplicateKeyConstraintException;
import com.project.internship.exception.NotAvailableException;
import com.project.internship.exception.NotPermittedException;
import com.project.internship.model.Employee;
import com.project.internship.model.Event;
import com.project.internship.model.Subscription;
import com.project.internship.repository.EmployeeRepository;
import com.project.internship.repository.EventRepository;
import com.project.internship.repository.SubscriptionRepository;
import com.project.internship.service.SubscriptionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DuplicateKeyException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class SubscriptionServiceTests {
    @InjectMocks
    private SubscriptionService subscriptionService;

    @Mock
    private SubscriptionRepository subscriptionRepository;

    @Mock
    private EventRepository eventRepository;

    @Mock
    private EmployeeRepository employeeRepository;

    @Test
    public void add_validSubscription() {
        Subscription expectedSubscription = instantiateSubscription();
        SubscriptionService subscriptionServiceMock = mock(SubscriptionService.class);

        subscriptionServiceMock.add(expectedSubscription);

        verify(subscriptionServiceMock, times(1)).add(expectedSubscription);
    }

    @Test
    public void add_subscription_throwsNotPermittedException() {
        Event event = instantiateEvent();
        Employee employee = instantiateEmployee();
        employee.setDepartmentId(2L);
        Mockito.when(eventRepository.getById(10L)).thenReturn(event);
        Mockito.when(employeeRepository.getById(20L)).thenReturn(employee);

        assertThrows(NotPermittedException.class, () -> {
            subscriptionService.add(new Subscription(20L, 10L));
        });
    }

    @Test
    public void add_subscription_throwsNotAvailableException() {
        Event event = instantiateEvent();
        Employee employee = instantiateEmployee();
        Mockito.when(eventRepository.getById(10L)).thenReturn(event);
        Mockito.when(employeeRepository.getById(20L)).thenReturn(employee);
        Mockito.when(subscriptionRepository.findNumberOfSubscribersForEventId(10L)).thenReturn(1000);

        assertThrows(NotAvailableException.class, () -> {
            subscriptionService.add(new Subscription(20L, 10L));
        });
    }

    @Test
    public void add_subscription_throwsDuplicateKeyConstraintException() {
        Event event = instantiateEvent();
        Employee employee = instantiateEmployee();
        Subscription subscription = instantiateSubscription();
        Mockito.when(eventRepository.getById(10L)).thenReturn(event);
        Mockito.when(employeeRepository.getById(20L)).thenReturn(employee);
        Mockito.when(subscriptionRepository.findNumberOfSubscribersForEventId(10L)).thenReturn(10);
        Mockito.doThrow(DuplicateKeyException.class).when(subscriptionRepository).add(subscription);

        assertThrows(DuplicateKeyConstraintException.class, () -> {
            subscriptionService.add(subscription);
        });
    }

    private Subscription instantiateSubscription() {
        return new Subscription(20L, 10L);
    }

    private Event instantiateEvent() {
        return new Event(10L, "Sport", 1L, 1L, "cycling", 100,
                "Cluj-Napoca", LocalDateTime.of(2023, 9, 15, 9, 0),
                LocalDateTime.of(2023, 9, 15, 18, 0), null);
    }

    private Employee instantiateEmployee() {
        return new Employee(20L, "Bia", "Onisa", "onibia", "onisabiaaa", "intern", 1L);
    }
}

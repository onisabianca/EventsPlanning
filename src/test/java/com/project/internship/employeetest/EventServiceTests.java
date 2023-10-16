package com.project.internship.employeetest;

import com.project.internship.exception.NotFoundException;
import com.project.internship.model.Event;
import com.project.internship.repository.EventRepository;
import com.project.internship.service.EventService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.EmptyResultDataAccessException;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EventServiceTests {

    @InjectMocks
    private EventService eventService;

    @Mock
    private EventRepository eventRepository;

    @Test
    public void find_existentEvent() {
        Event expectedEvent = instantiateEvent();
        Mockito.when(eventRepository.getById(expectedEvent.getId())).thenReturn(expectedEvent);

        Event foundEvent = eventService.getEventByID(100L);

        assertNotNull(foundEvent);
        assertEquals(foundEvent, expectedEvent);
    }

    @Test
    public void find_nonExistentEvent_throwsNotFoundException() {
        Mockito.when(eventRepository.getById(1000L)).thenThrow(EmptyResultDataAccessException.class);

        assertThrows(NotFoundException.class, () -> {
            eventService.getEventByID(1000L);
        });
    }

    @Test
    public void save_existentEvent() {
        Event updatedEvent = instantiateEvent();
        Mockito.when(eventRepository.update(updatedEvent)).thenReturn(1);

        Event savedEvent = eventService.save(updatedEvent);

        assertNotNull(savedEvent);
        assertEquals(savedEvent, updatedEvent);
    }

    @Test
    public void save_nonExistentEvent() {
        Event addedEvent = instantiateEvent();
        Mockito.when(eventRepository.update(addedEvent)).thenReturn(0);
        Mockito.when(eventRepository.add(addedEvent)).thenReturn(100L);
        Mockito.when(eventRepository.getById(100L)).thenReturn(addedEvent);

        Event savedEvent = eventService.save(addedEvent);

        assertNotNull(savedEvent);
        assertEquals(savedEvent, addedEvent);
    }

    @Test
    public void delete_event() {
        EventService eventServiceMock = mock(EventService.class);

        eventServiceMock.delete(100L);

        verify(eventServiceMock, times(1)).delete(100L);
    }

    private Event instantiateEvent() {
        return new Event(100L, "Sport", 1L, 1L, "cycling", 100,
                "Cluj-Napoca", LocalDateTime.of(2023, 9, 15, 9, 0),
                LocalDateTime.of(2023, 9, 15, 18, 0), null);
    }
}

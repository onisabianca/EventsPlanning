package com.project.internship.controller;

import com.project.internship.model.Event;
import com.project.internship.model.Subscription;
import com.project.internship.service.EventService;
import com.project.internship.service.SubscriptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Tag(description = "Event APIs to access event data", name = "Event Resources")
@Slf4j
@RestController
@RequestMapping("/event")
public class EventController {
    private final EventService eventService;
    private final SubscriptionService subscriptionService;

    public EventController(EventService eventService, SubscriptionService subscriptionService) {
        this.eventService = eventService;
        this.subscriptionService = subscriptionService;
    }

    @Operation(summary = "Get event by ID", description = "Returns the event with the given ID if it exists, otherwise throws a runtime exception.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event found"),
            @ApiResponse(responseCode = "404", description = "Event not found")})
    @GetMapping("/{id}")
    public Event getById(@PathVariable(name = "id") Long id) {
        log.info("Searching for event with ID {}", id);
        return eventService.getEventByID(id);
    }

    @Operation(summary = "Create event", description = "Create an event if doesn't exist or update the existent one otherwise.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Event created"),
            @ApiResponse(responseCode = "202", description = "Event updated"),
            @ApiResponse(responseCode = "400", description = "Event could not be created")})
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Event save(@Valid @RequestBody Event event) {
        Event eventFromDB = eventService.save(event);
        log.info("Saved event with ID {}", eventFromDB.getId());
        return eventFromDB;
    }

    @Operation(summary = "Delete event", description = "Delete an event if exists or throw a runtime exception otherwise.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Event deleted")})
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(name = "id") Long id) {
        log.info("Deleting event with ID {}", id);
        eventService.delete(id);
    }

    @Operation(summary = "Creating subscription", description = "API to create a subscription for a given event and employee")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Subscription created"),
            @ApiResponse(responseCode = "400", description = "Event has no available slots"),
            @ApiResponse(responseCode = "403", description = "Forbidden access to the given event"),
            @ApiResponse(responseCode = "409", description = "Already subscribed to the event")})
    @PostMapping("/subscribe")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void subscribe(@Valid @RequestBody Subscription subscription) {
        log.info("Creating subscription for employee with ID {} for event with ID {}", subscription.getEmployeeId(), subscription.getEventId());
        subscriptionService.add(subscription);
    }
}

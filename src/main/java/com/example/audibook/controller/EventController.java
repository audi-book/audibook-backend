package com.example.audibook.controller;
import com.example.audibook.dto.*;
import com.example.audibook.entity.Event;
import com.example.audibook.exception.CustomException;
import com.example.audibook.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/events")
@RequiredArgsConstructor
public class EventController {

    @Autowired
    private EventService eventService;

    @GetMapping("/{id}")
    public ResponseEntity<ResponseDTO<EventResponseDTO>> getEventById(@PathVariable Long id) {
        try {
            EventResponseDTO event = eventService.getEventById(id);
            ResponseDTO<EventResponseDTO> response = new ResponseDTO<>(true, event, "Event retrieved successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<EventResponseDTO> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<EventResponseDTO> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping
    public ResponseEntity<ResponseDTO<List<EventResponseDTO>>> getAllEvents() {
        try {
            List<EventResponseDTO> events = eventService.getAllEvents();
            ResponseDTO<List<EventResponseDTO>> response = new ResponseDTO<>(true, events, "Events retrieved successfully");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ResponseDTO<List<EventResponseDTO>> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<ResponseDTO<Event>> createEvent(@RequestBody CreateEventDTO eventDTO) {
        try {
            Event event = eventService.createEvent(eventDTO);
            ResponseDTO<Event> response = new ResponseDTO<>(true, event, "Event created successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<Event> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<Event> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseDTO<Event>> updateEvent(@PathVariable Long id, @RequestBody UpdateEventDTO eventDTO) {
        try {
            eventDTO.setId(id);
            Event event = eventService.updateEvent(eventDTO);
            ResponseDTO<Event> response = new ResponseDTO<>(true, event, "Event updated successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<Event> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<Event> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseDTO<Void>> deleteEvent(@PathVariable Long id) {
        try {
            eventService.deleteEvent(id);
            ResponseDTO<Void> response = new ResponseDTO<>(true, null, "Event deleted successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<Void> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<Void> response = new ResponseDTO<>(false, null, "An unexpected error occurred");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
    @PutMapping("/status")
    public ResponseEntity<ResponseDTO<Event>> updateEventStatus(@RequestBody UpdateEventStatusDTO statusDTO) {
        try {
            Event event = eventService.updateEventStatus(statusDTO);
            ResponseDTO<Event> response = new ResponseDTO<>(true, event, "Event status updated successfully");
            return ResponseEntity.ok(response);
        } catch (CustomException e) {
            ResponseDTO<Event> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        } catch (Exception e) {
            ResponseDTO<Event> response = new ResponseDTO<>(false, null, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }
}
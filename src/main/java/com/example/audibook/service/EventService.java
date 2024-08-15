package com.example.audibook.service;
import com.example.audibook.dto.CreateEventDTO;
import com.example.audibook.dto.UpdateEventDTO;
import com.example.audibook.dto.UpdateEventStatusDTO;
import com.example.audibook.entity.Event;
import com.example.audibook.exception.CustomException;
import com.example.audibook.repository.EventRepository;
import com.example.audibook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    public Event createEvent(CreateEventDTO createEventDTO) {
        var user = userRepository.findById(createEventDTO.getUserId())
                .orElseThrow(() -> new CustomException("User not found"));

        if (!"USER".equals(user.getRole())) {
            throw new CustomException("Only users with role USER can create events");
        }
        Event event = new Event().build(
                0L,
                createEventDTO.getEventName(),
                createEventDTO.getEventDate(),
                createEventDTO.getStartTime(),
                createEventDTO.getEndTime(),
                createEventDTO.getPurpose(),
                "Pending",
                createEventDTO.getUserId()
        );
        Event savedEvent = eventRepository.save(event);
        return savedEvent;
    }

    public Event updateEvent(UpdateEventDTO updateEventDTO) {
        Event event = eventRepository.findById(updateEventDTO.getId())
                .orElseThrow(() -> new CustomException("Event not found"));
        event.setEventName(updateEventDTO.getEventName());
        event.setEventDate(updateEventDTO.getEventDate());
        event.setStartTime(updateEventDTO.getStartTime());
        event.setEndTime(updateEventDTO.getEndTime());
        event.setPurpose(updateEventDTO.getPurpose());
        Event updatedEvent = eventRepository.save(event);
        return updatedEvent;
    }

    public void deleteEvent(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new CustomException("Event not found"));
        eventRepository.delete(event);
    }

    public List<Event> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Event getEventById(Long id) {
        Event event = eventRepository.findById(id)
                .orElseThrow(() -> new CustomException("Event not found"));
        return convertToDTO(event);
    }

    private Event convertToDTO(Event event) {
        Event dto = new Event();
        dto.setId(event.getId());
        dto.setEventName(event.getEventName());
        dto.setEventDate(event.getEventDate());
        dto.setStartTime(event.getStartTime());
        dto.setEndTime(event.getEndTime());
        dto.setPurpose(event.getPurpose());
        dto.setStatus(event.getStatus());
        dto.setUserId(event.getUserId());
        return dto;
    }

    public Event updateEventStatus(UpdateEventStatusDTO statusDTO) {
        Event event = eventRepository.findById(statusDTO.getEventId())
                .orElseThrow(() -> new CustomException("Event not found"));
        var user = userRepository.findById(statusDTO.getUserId())
                .orElseThrow(() -> new CustomException("User not found"));

        if (!"ADMIN".equals(user.getRole())) {
            throw new CustomException("Only users with role USER can create events");
        }
        event.setStatus(statusDTO.getStatus());
        eventRepository.save(event);
        return event;
    }
}
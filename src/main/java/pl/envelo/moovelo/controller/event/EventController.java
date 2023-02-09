package pl.envelo.moovelo.controller.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import pl.envelo.moovelo.controller.dto.CommentDto;
import pl.envelo.moovelo.controller.dto.CommentRequestDto;
import pl.envelo.moovelo.controller.dto.EventDto;
import pl.envelo.moovelo.controller.dto.event.DisplayEventResponseDto;
import pl.envelo.moovelo.controller.dto.event.EventListResponseDto;
import pl.envelo.moovelo.controller.mapper.CommentMapper;
import pl.envelo.moovelo.controller.mapper.EventListResponseMapper;
import pl.envelo.moovelo.controller.mapper.event.EventMapper;
import pl.envelo.moovelo.entity.Comment;
import pl.envelo.moovelo.entity.actors.BasicUser;
import pl.envelo.moovelo.entity.events.CyclicEvent;
import pl.envelo.moovelo.entity.events.Event;
import pl.envelo.moovelo.entity.events.ExternalEvent;
import pl.envelo.moovelo.entity.events.InternalEvent;
import pl.envelo.moovelo.service.CommentService;
import pl.envelo.moovelo.service.actors.UserService;
import pl.envelo.moovelo.service.event.EventService;
import springfox.documentation.spi.service.contexts.SecurityContext;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("api/v1")
@Slf4j
public class EventController {

    private EventService eventService;
    private CommentService commentService;

    @Autowired
    public EventController(EventService eventService, CommentService commentService) {
        this.eventService = eventService;
        this.commentService = commentService;
    }


    @GetMapping("/events")
    public ResponseEntity<List<EventListResponseDto>> getAllEvents() {
        log.info("EventController - getAllEvents()");
        List<? extends Event> allEvents = eventService.getAllEvents();

        List<EventListResponseDto> eventsDto = mapEventToEventListResponseDto(allEvents);

        log.info("EventController - getAllEvents() return {}", eventsDto);
        return ResponseEntity.ok(eventsDto);
    }

    @GetMapping("events/eventOwners/{userId}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('BASIC_USER')")
    public ResponseEntity<List<EventListResponseDto>> getAllEventsByEventOwnerBasicUserId(@PathVariable("userId") Long basicUserId) {
        log.info("EventController - getAllEventsByEventOwnerBasicUserId() - basicUserId = {}", basicUserId);

        //TODO: Sprawdzić zachowanie po ogarnięciu Spring Security w aplikacji
        /*Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("BASIC_USER"))) {
            BasicUser user = (BasicUser) auth.getPrincipal();

            if (!user.getId().equals(basicUserId)) {
                throw new UnauthorizedRequestException("The ID you passed does not belong to your account");
            }
        }*/

        List<? extends Event> allEvents = eventService.getAllEventsByEventOwnerBasicUserId(basicUserId);

        List<EventListResponseDto> eventsDto = mapEventToEventListResponseDto(allEvents);

        return ResponseEntity.ok(eventsDto);
    }

    private List<EventListResponseDto> mapEventToEventListResponseDto(List<? extends Event> allEvents) {
        List<EventListResponseDto> eventsDto = allEvents.stream().map(event -> switch (event.getEventType()) {
            case EVENT -> EventListResponseMapper.mapBasicEventToEventListResponseDto(event);
            case INTERNAL_EVENT ->
                    EventListResponseMapper.mapInternalEventToEventListResponseDto((InternalEvent) event);
            case CYCLIC_EVENT -> EventListResponseMapper.mapCyclicEventToEventListResponseDto((CyclicEvent) event);
            case EXTERNAL_EVENT ->
                    EventListResponseMapper.mapExternalEventToEventListResponseDto((ExternalEvent) event);
        }).toList();
        return eventsDto;
    }

    @GetMapping("/events/{eventId}")
    public ResponseEntity<DisplayEventResponseDto> getEventById(@PathVariable Long eventId) {
        log.info("EventController - getEventById()");
        Event eventById = eventService.getEventById(eventId);
        DisplayEventResponseDto displayEventResponseDto = null;
        switch (eventById.getEventType()) {
            case EVENT -> displayEventResponseDto = EventMapper.mapEventToEventResponseDto(eventById);
            case EXTERNAL_EVENT ->  displayEventResponseDto = EventMapper.mapExternalEventToEventResponseDto((ExternalEvent) eventById);
            case INTERNAL_EVENT ->
                    displayEventResponseDto = EventMapper.mapInternalEventToEventResponseDto((InternalEvent) eventById);
            case CYCLIC_EVENT ->
                    displayEventResponseDto = EventMapper.mapCyclicEventToEventResponseDto((CyclicEvent) eventById);
        }
        log.info("EventController - getEventById() return {}", displayEventResponseDto);
        return displayEventResponseDto == null ? ResponseEntity.badRequest().build() : ResponseEntity.ok(displayEventResponseDto);
    }

    @GetMapping("/events/{eventId}/comments")
    public ResponseEntity<List<Comment>> getAllComments(@PathVariable Long eventId){ //zapytać o paginacje w wybieraniu komentarzy do wydarzenia
        log.info("EventController - getAllComments()");
        Event eventById = eventService.getEventById(eventId);

        List<Comment> comments = eventService.getAllComments(eventById);
        return ResponseEntity.ok(comments);
    }

    @PostMapping("/events/{eventId}/comments") //
    public ResponseEntity<?> addCommentWithoutAttachmentToEvent(@PathVariable Long eventId, @RequestBody CommentRequestDto commentRequestDto){
        log.info("EventController - addCommentToEvent()");
        Event eventById = eventService.getEventById(eventId);
        BasicUser basicUser = (BasicUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Comment comment = new Comment();
        comment.setEvent(eventById);
        comment.setBasicUser(basicUser);
        comment.setText(commentRequestDto.getText());
        comment.setDate(LocalDateTime.now());

        Comment savedComment = commentService.addComment(comment);
        CommentDto newCommentDto = CommentMapper.mapFromCommentToCommentDto(savedComment);

        return ResponseEntity.ok(newCommentDto);
    }
}

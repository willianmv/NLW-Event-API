package br.com.nlw.events.controller;

import br.com.nlw.events.model.Event;
import br.com.nlw.events.service.EventService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/events")
public class EventController {

    @Autowired
    private EventService eventService;

    @PostMapping
    public Event addNewEvent(@RequestBody @Valid Event newEvent){
        return eventService.addNewEvent(newEvent);
    }

    @GetMapping
    public List<Event> getAllEvents(){
        return eventService.getAllEvents();
    }

    @GetMapping("/{prettyName}")
    public ResponseEntity<Event> getByPrettyName(@PathVariable String prettyName){
        return eventService.getByPrettyName(prettyName);
    }

}

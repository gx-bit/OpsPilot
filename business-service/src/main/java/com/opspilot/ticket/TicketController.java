package com.opspilot.ticket;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController @RequestMapping("/api/tickets") @RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class TicketController {
    private final TicketService service;
    @PostMapping @ResponseStatus(HttpStatus.CREATED)
    Ticket create(@Valid @RequestBody TicketDtos.CreateRequest request) { return service.create(request); }
    @GetMapping List<Ticket> list() { return service.list(); }
    @GetMapping("/{id}") Ticket get(@PathVariable long id) { return service.get(id); }
}


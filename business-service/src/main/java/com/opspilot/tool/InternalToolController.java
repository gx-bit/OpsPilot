package com.opspilot.tool;

import com.opspilot.ticket.Ticket;
import com.opspilot.ticket.TicketRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Map;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@RestController @RequestMapping("/internal/tools") @RequiredArgsConstructor
public class InternalToolController {
    private final JdbcClient jdbc;
    private final TicketRepository tickets;
    @Value("${opspilot.internal-api-key}") private String expectedKey;

    @GetMapping("/knowledge")
    List<Map<String, Object>> knowledge(@RequestHeader("X-Internal-Api-Key") String key,
                                        @RequestParam(defaultValue = "OTHER") String category) {
        verify(key);
        return jdbc.sql("SELECT id,title,content,category FROM knowledge_article WHERE category = :category OR category = 'OTHER' LIMIT 5")
                .param("category", category).query().listOfRows();
    }

    @GetMapping("/similar-tickets")
    List<Ticket> similar(@RequestHeader("X-Internal-Api-Key") String key, @RequestParam String keyword) {
        verify(key);
        return tickets.findTop5ByDescriptionContainingIgnoreCaseOrderByCreatedAtDesc(keyword);
    }

    private void verify(String key) {
        if (!expectedKey.equals(key)) throw new ResponseStatusException(UNAUTHORIZED, "invalid internal key");
    }
}


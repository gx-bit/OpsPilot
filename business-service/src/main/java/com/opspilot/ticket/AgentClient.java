package com.opspilot.ticket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestClient;
import java.util.Map;

@Component
public class AgentClient {
    private final RestClient client;
    private final ObjectMapper objectMapper;
    AgentClient(RestClient.Builder builder,
                ObjectMapper objectMapper,
                @Value("${opspilot.agent-base-url}") String baseUrl,
                @Value("${opspilot.internal-api-key}") String apiKey) {
        this.objectMapper = objectMapper;
        this.client = builder.baseUrl(baseUrl).defaultHeader("X-Internal-Api-Key", apiKey).build();
    }
    TicketDtos.TriageResult triage(Ticket ticket) {
        try {
            String json = objectMapper.writeValueAsString(Map.of(
                        "ticketId", ticket.getId(),
                        "title", ticket.getTitle(),
                        "description", ticket.getDescription()));
            return client.post().uri("/api/v1/triage")
                    .contentType(MediaType.APPLICATION_JSON)
                    .body(json)
                    .retrieve().body(TicketDtos.TriageResult.class);
        } catch (JsonProcessingException ex) {
            throw new IllegalStateException("Cannot serialize triage request", ex);
        }
    }
}

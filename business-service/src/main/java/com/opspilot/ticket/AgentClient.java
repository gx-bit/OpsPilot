package com.opspilot.ticket;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class AgentClient {
    private final RestClient client;
    AgentClient(RestClient.Builder builder,
                @Value("${opspilot.agent-base-url}") String baseUrl,
                @Value("${opspilot.internal-api-key}") String apiKey) {
        this.client = builder.baseUrl(baseUrl).defaultHeader("X-Internal-Api-Key", apiKey).build();
    }
    TicketDtos.TriageResult triage(Ticket ticket) {
        return client.post().uri("/api/v1/triage")
                .body(new TicketDtos.TriageRequest(ticket.getId(), ticket.getTitle(), ticket.getDescription()))
                .retrieve().body(TicketDtos.TriageResult.class);
    }
}


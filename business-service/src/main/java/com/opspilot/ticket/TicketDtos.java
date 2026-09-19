package com.opspilot.ticket;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public final class TicketDtos {
    private TicketDtos() {}
    public record CreateRequest(
            @NotBlank @Size(max = 160) String title,
            @NotBlank @Size(max = 5000) String description) {}
    public record TriageRequest(Long ticketId, String title, String description) {}
    public record TriageResult(String category, String priority, String assignedGroup,
                               String summary, String suggestion, String mode) {}
}


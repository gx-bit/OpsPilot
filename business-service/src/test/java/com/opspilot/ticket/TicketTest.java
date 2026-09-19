package com.opspilot.ticket;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

class TicketTest {
    @Test void defaultsAreSafe() {
        Ticket ticket = new Ticket();
        assertThat(ticket.getStatus()).isEqualTo(Ticket.Status.PENDING);
        assertThat(ticket.getCategory()).isEqualTo(Ticket.Category.OTHER);
        assertThat(ticket.getPriority()).isEqualTo(Ticket.Priority.MEDIUM);
    }
}


package com.opspilot.ticket;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "ticket")
@Getter @Setter @NoArgsConstructor
public class Ticket {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 160) private String title;
    @Column(nullable = false, columnDefinition = "text") private String description;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private Status status = Status.PENDING;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private Category category = Category.OTHER;
    @Enumerated(EnumType.STRING) @Column(nullable = false) private Priority priority = Priority.MEDIUM;
    private String assignedGroup;
    @Column(columnDefinition = "text") private String aiSummary;
    @Column(columnDefinition = "text") private String aiSuggestion;
    @Column(nullable = false) private Instant createdAt;
    @Column(nullable = false) private Instant updatedAt;
    @Version private long version;

    @PrePersist void create() { createdAt = Instant.now(); updatedAt = createdAt; }
    @PreUpdate void update() { updatedAt = Instant.now(); }

    public enum Status { PENDING, PROCESSING, WAITING_CONFIRMATION, COMPLETED, SUSPENDED }
    public enum Category { NETWORK, ACCOUNT, SOFTWARE, HARDWARE, SERVER, OTHER }
    public enum Priority { LOW, MEDIUM, HIGH, URGENT }
}


package com.opspilot.ticket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service @RequiredArgsConstructor @Slf4j
public class TicketService {
    private final TicketRepository repository;
    private final AgentClient agentClient;

    @Transactional
    public Ticket create(TicketDtos.CreateRequest request) {
        Ticket ticket = new Ticket();
        ticket.setTitle(request.title());
        ticket.setDescription(request.description());
        ticket = repository.saveAndFlush(ticket);
        try {
            var result = agentClient.triage(ticket);
            ticket.setCategory(parse(Ticket.Category.class, result.category(), Ticket.Category.OTHER));
            ticket.setPriority(parse(Ticket.Priority.class, result.priority(), Ticket.Priority.MEDIUM));
            ticket.setAssignedGroup(result.assignedGroup());
            ticket.setAiSummary(result.summary());
            ticket.setAiSuggestion(result.suggestion());
        } catch (RuntimeException ex) {
            log.warn("Agent triage failed for ticket {}", ticket.getId(), ex);
            ticket.setAiSummary("Agent 暂时不可用，工单已保存并等待人工分诊。");
        }
        return repository.save(ticket);
    }

    @Transactional(readOnly = true) public List<Ticket> list() { return repository.findAll(); }
    @Transactional(readOnly = true) public Ticket get(long id) {
        return repository.findById(id).orElseThrow(() -> new IllegalArgumentException("工单不存在: " + id));
    }
    private static <E extends Enum<E>> E parse(Class<E> type, String value, E fallback) {
        try { return Enum.valueOf(type, value); } catch (Exception ignored) { return fallback; }
    }
}

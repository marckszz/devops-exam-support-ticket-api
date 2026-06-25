package edu.exam.devops.tickets.service;

import edu.exam.devops.tickets.dto.CreateTicketRequest;
import edu.exam.devops.tickets.dto.TicketSummaryResponse;
import edu.exam.devops.tickets.model.Priority;
import edu.exam.devops.tickets.model.Ticket;
import edu.exam.devops.tickets.model.TicketStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class TicketService {

    private final List<Ticket> tickets = new ArrayList<>();
    private final AtomicLong sequence = new AtomicLong(1);

    public TicketService() {
        tickets.add(new Ticket(sequence.getAndIncrement(), "Ingenio Learning", "No puedo acceder al campus virtual", Priority.HIGH, TicketStatus.OPEN, LocalDateTime.now()));
        tickets.add(new Ticket(sequence.getAndIncrement(), "Colegio San Juan", "Solicitud de cambio de horario", Priority.MEDIUM, TicketStatus.IN_PROGRESS, LocalDateTime.now()));
        tickets.add(new Ticket(sequence.getAndIncrement(), "Cliente Demo", "Consulta sobre certificado", Priority.LOW, TicketStatus.RESOLVED, LocalDateTime.now()));
    }

    public List<Ticket> findAll() {
        return List.copyOf(tickets);
    }

    public Ticket create(CreateTicketRequest request) {
        Ticket ticket = new Ticket(
                sequence.getAndIncrement(),
                request.getCustomerName().trim(),
                request.getSubject().trim(),
                request.getPriority(),
                TicketStatus.OPEN,
                LocalDateTime.now()
        );
        tickets.add(ticket);
        return ticket;
    }

    public Ticket updateStatus(Long id, TicketStatus newStatus) {
        Ticket ticket = tickets.stream()
                .filter(current -> current.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Ticket no encontrado: " + id));
        ticket.setStatus(newStatus);
        return ticket;
    }

    public TicketSummaryResponse getSummary() {
        long total = tickets.size();
        long open = tickets.stream().filter(ticket -> TicketStatus.OPEN.equals(ticket.getStatus())).count();
        long highPriority = tickets.stream().filter(ticket -> Priority.HIGH.equals(ticket.getPriority())).count();
        return new TicketSummaryResponse(total, open, highPriority);
    }
}

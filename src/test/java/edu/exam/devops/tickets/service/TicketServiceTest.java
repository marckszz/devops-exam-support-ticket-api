package edu.exam.devops.tickets.service;

import edu.exam.devops.tickets.dto.CreateTicketRequest;
import edu.exam.devops.tickets.dto.TicketSummaryResponse;
import edu.exam.devops.tickets.model.Priority;
import edu.exam.devops.tickets.model.Ticket;
import edu.exam.devops.tickets.model.TicketStatus;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TicketServiceTest {

    @Test
    void createShouldRegisterTicketWithOpenStatus() {
        TicketService service = new TicketService();
        CreateTicketRequest request = new CreateTicketRequest();
        request.setCustomerName("  Cliente UPC  ");
        request.setSubject("Error al ingresar al sistema");
        request.setPriority(Priority.HIGH);

        Ticket created = service.create(request);

        assertEquals("Cliente UPC", created.getCustomerName());
        assertEquals(TicketStatus.OPEN, created.getStatus());
        assertEquals(4, service.findAll().size());
    }

    @Test
    void updateStatusShouldChangeTicketStatus() {
        TicketService service = new TicketService();

        Ticket updated = service.updateStatus(1L, TicketStatus.RESOLVED);

        assertEquals(TicketStatus.RESOLVED, updated.getStatus());
    }

    @Test
    void updateStatusShouldThrowWhenTicketDoesNotExist() {
        TicketService service = new TicketService();

        assertThrows(NoSuchElementException.class, () -> service.updateStatus(99L, TicketStatus.RESOLVED));
    }

    @Test
    void summaryShouldCountTotalOpenAndHighPriorityTickets() {
        TicketService service = new TicketService();

        TicketSummaryResponse summary = service.getSummary();

        assertEquals(3, summary.getTotal());
        assertTrue(summary.getOpen() >= 1);
        assertEquals(1, summary.getHighPriority());
    }
}

package edu.exam.devops.tickets.model;

import java.time.LocalDateTime;

public class Ticket {

    private Long id;
    private String customerName;
    private String subject;
    private Priority priority;
    private TicketStatus status;
    private LocalDateTime createdAt;

    public Ticket() {
    }

    public Ticket(Long id, String customerName, String subject, Priority priority, TicketStatus status, LocalDateTime createdAt) {
        this.id = id;
        this.customerName = customerName;
        this.subject = subject;
        this.priority = priority;
        this.status = status;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public TicketStatus getStatus() {
        return status;
    }

    public void setStatus(TicketStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

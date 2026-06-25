package edu.exam.devops.tickets.dto;

public class TicketSummaryResponse {

    private long total;
    private long open;
    private long highPriority;

    public TicketSummaryResponse(long total, long open, long highPriority) {
        this.total = total;
        this.open = open;
        this.highPriority = highPriority;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getOpen() {
        return open;
    }

    public void setOpen(long open) {
        this.open = open;
    }

    public long getHighPriority() {
        return highPriority;
    }

    public void setHighPriority(long highPriority) {
        this.highPriority = highPriority;
    }
}

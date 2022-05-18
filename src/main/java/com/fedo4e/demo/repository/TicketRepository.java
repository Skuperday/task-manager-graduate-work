package com.fedo4e.demo.repository;

import com.fedo4e.demo.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}

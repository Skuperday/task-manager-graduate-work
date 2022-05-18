package com.fedo4e.demo.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.GregorianCalendar;

@Entity
@Table(name="tickets")
@Data
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ticket_id")
    private Long id;
    private String topic;
    private String text;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private GregorianCalendar ticketDate;
    private String status;


    public Ticket(String topic, String text) {
        this.topic = topic;
        this.text = text;
    }
}

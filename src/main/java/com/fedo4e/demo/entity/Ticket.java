package com.fedo4e.demo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.GregorianCalendar;
import java.util.Set;

@Entity
@Table(name="tickets")
@Data
@EqualsAndHashCode(exclude="user")
@NoArgsConstructor
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name="ticket_id")
    private Long id;
    private String topic;
    private String text;
    private GregorianCalendar ticketDate;
    private String status;
    @Transient
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;


    public Ticket(String topic, String text) {
        this.topic = topic;
        this.text = text;
    }
}

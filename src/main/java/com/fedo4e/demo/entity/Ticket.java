package com.fedo4e.demo.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.util.GregorianCalendar;

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
    private GregorianCalendar ticketExpirationDate;
    private String status;
    @Transient
    @ManyToOne(fetch = FetchType.EAGER)
    private User user;
    @Transient
    @ManyToOne(fetch = FetchType.EAGER)
    private User responsible;



    public Ticket(String topic, String text) {
        this.topic = topic;
        this.text = text;
    }
    public boolean isExpired() {
        if(ticketExpirationDate != null) {
            return ticketExpirationDate.after(GregorianCalendar.getInstance());
        } else return false;
    }
}

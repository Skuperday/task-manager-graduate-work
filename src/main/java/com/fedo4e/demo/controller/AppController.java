package com.fedo4e.demo.controller;

import com.fedo4e.demo.entity.Ticket;
import com.fedo4e.demo.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class AppController {

    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping("/")
    public String home(Model model){
        model.addAttribute("name", "Главная страница")
        .addAttribute("title", "Home page");
        return "mainPage";
    }
    @GetMapping("/tickets/all")
    public String allTicket(Model model) {
        Iterable<Ticket> tickets = ticketRepository.findAll();
        model.addAttribute("tickets",tickets);
        return "ticketBrowser";
    }

    @GetMapping("/tickets/add")
    public String ticketForm(Model model){
        return "ticketForm";
    }
    @PostMapping("/tickets/add")
    public String addNewTicket(@RequestParam String topic,
                               @RequestParam String text,
                               @RequestParam String contact,
                               Model model){
        Ticket ticket = new Ticket(topic,text);
        return "mainPage";
    }
    @GetMapping("/login")
    public String loginForm(Model model) {
        return "login";
    }
}

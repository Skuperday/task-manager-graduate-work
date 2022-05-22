package com.fedo4e.demo.controller;

import com.fedo4e.demo.entity.Ticket;
import com.fedo4e.demo.entity.User;
import com.fedo4e.demo.repository.TicketRepository;
import com.fedo4e.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.GregorianCalendar;
import java.util.Optional;

@Controller
public class AppController {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private UserRepository userRepository;

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
        model.addAttribute("ticketForm", new Ticket());
        model.addAttribute("ticketDate", new GregorianCalendar());
        model.addAttribute("status", "");
        return "ticketForm";
    }

    @PostMapping("/tickets/add")
    public String addNewTicket(@ModelAttribute("ticketForm") Ticket ticketForm,
                               @ModelAttribute("ticketDate") GregorianCalendar ticketDate,
                               @ModelAttribute("status") String status,
                               @RequestParam(value = "contact", required = false) String checkboxValue,
                               Model model){
        ticketForm.setTicketDate(ticketDate);
        status = "New";
        ticketForm.setStatus(status);
        if (checkboxValue != null){
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            Long userId = user.getId();
        }
        ticketRepository.save(ticketForm);
        return "mainPage";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        return "login";
    }



}

package com.fedo4e.demo.controller;

import com.fedo4e.demo.entity.Ticket;
import com.fedo4e.demo.entity.User;
import com.fedo4e.demo.repository.TicketRepository;
import com.fedo4e.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


import java.util.GregorianCalendar;
import java.util.Set;

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
        return "ticketForm";
    }

    @PostMapping("/tickets/add")
    public String addNewTicket(@ModelAttribute("ticketForm") Ticket ticketForm,
                               @RequestParam(value = "contact", required = false) String checkboxValue,
                               Model model){
        ticketForm.setTicketDate(new GregorianCalendar());
        ticketForm.setStatus("New");
        ticketRepository.save(ticketForm);

        if (checkboxValue != null){
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            ticketForm.setUser(user);
            Set<Ticket> ticketSet = user.getTickets();
            ticketSet.add(ticketForm);
            user.setTickets(ticketSet);
            userRepository.save(user);
            user.clearTickets();
        }
        return "mainPage";
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        return "login";
    }


    @GetMapping("ticket/{ticketId}")
    public String  getTicket(@PathVariable("ticketId") Long ticketId, Model model) {
        model.addAttribute("ticket", ticketRepository.findById(ticketId));
        model.addAttribute("employees", userRepository.findAll().stream().map(User :: getAuthorities).filter(role -> !role.equals("ROLE_USER")));
        return "ticket";
    }

    @PostMapping("ticket/{ticketId}")
    public String editResponsible(@PathVariable("ticketId") Long ticketId, Model model) {
        return "redirect:/ticketBrowser";
    }

}

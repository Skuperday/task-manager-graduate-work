package com.fedo4e.demo.controller;

import com.fedo4e.demo.entity.Role;
import com.fedo4e.demo.entity.User;
import com.fedo4e.demo.repository.RoleRepository;
import com.fedo4e.demo.repository.UserRepository;
import com.fedo4e.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@Controller
public class AdminController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/admin")
    public String userList(Model model) {
        model.addAttribute("users", userService.allUsers());
        return "admin";
    }

    @PostMapping("/admin")
    public String  deleteUser(@RequestParam(required = true, defaultValue = "" ) Long userId,
                              @RequestParam(required = true, defaultValue = "" ) String action,
                              Model model) {
        if (action.equals("delete")){
            userService.deleteUser(userId);
        }
        return "redirect:/admin";
    }

    @GetMapping("/user/{userId}")
    public String  getUser(@PathVariable("userId") Long userId, Model model) {
        model.addAttribute("user", userService.findUserById(userId));
        model.addAttribute("roles", roleRepository.findAll());
        return "user";
    }

    @PostMapping("/user/{userId}")
    public String editUser(@PathVariable("userId") Long userId,
                           @ModelAttribute ("user") User user,
                           @RequestParam Map<String, String> roles,
                           Model model){

        user.setId(userId);
        user.setRoles(userRepository.getById(userId).getRoles());
        user.setTickets(userRepository.getById(userId).getTickets());
        user.getRoles().clear();
        Set<String> roleSet = roleRepository.findAll()
                .stream()
                .map(Role::getName)
                .collect(Collectors.toSet());

        for(String key : roles.keySet()) {
            for(Role role : roleRepository.findAll()) {
                if (key.equals(role.getAuthority())) {
                    user.getRoles().add(role);
                }
            }
        }

        userRepository.save(user);

        return "redirect:/admin";
    }
    @GetMapping("/user/{userId}/delete")
    public String deleteUser(@PathVariable("userId") Long userId, Model model) {
        userRepository.delete(userService.findUserById(userId));
        return "redirect:/admin";
    }

}
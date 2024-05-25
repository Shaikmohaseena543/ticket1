package com.example.demo.controller;

import com.example.demo.db.InMemoryDatabase;
import com.example.demo.model.Ticket;
import com.example.demo.model.User;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/tickets")
public class TicketController {

    private InMemoryDatabase database = new InMemoryDatabase();
    private int seatCounter = 1;

    @PostMapping("/purchase")
    public Ticket purchaseTicket(@RequestBody User user) {
        String seat = allocateSeat();
        Ticket ticket = new Ticket();
        ticket.setFrom("London");
        ticket.setTo("France");
        ticket.setUser(user);
        ticket.setPricePaid(20.0);
        ticket.setSeat(seat);
        database.getUsers().put(user.getEmail(), user);
        database.getTickets().put(user.getEmail(), ticket);
        return ticket;
    }

    @GetMapping("/receipt/{email}")
    public Ticket getReceipt(@PathVariable String email) {
        return database.getTickets().get(email);
    }

    @GetMapping("/seats/{section}")
    public List<Ticket> getSeatsBySection(@PathVariable String section) {
        return database.getTickets().values().stream()
                       .filter(ticket -> ticket.getSeat().startsWith(section))
                       .collect(Collectors.toList());
    }

    @DeleteMapping("/remove/{email}")
    public String removeUser(@PathVariable String email) {
        database.getUsers().remove(email);
        database.getTickets().remove(email);
        return "User removed";
    }

    @PutMapping("/modifySeat")
    public Ticket modifySeat(@RequestParam String email, @RequestParam String newSeat) {
        Ticket ticket = database.getTickets().get(email);
        if (ticket != null) {
            ticket.setSeat(newSeat);
        }
        return ticket;
    }

    private String allocateSeat() {
        String section = (seatCounter % 2 == 0) ? "B" : "A";
        String seat = section + "-" + seatCounter;
        seatCounter++;
        return seat;
    }
}

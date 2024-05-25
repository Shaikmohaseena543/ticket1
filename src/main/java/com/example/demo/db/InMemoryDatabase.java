package com.example.demo.db;

import com.example.demo.model.Ticket;
import com.example.demo.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InMemoryDatabase {
    private Map<String, Ticket> tickets = new HashMap<>();
    private Map<String, User> users = new HashMap<>();
}

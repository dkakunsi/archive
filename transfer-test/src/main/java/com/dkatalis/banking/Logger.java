package com.dkatalis.banking;

import java.util.ArrayList;
import java.util.List;

public class Logger {
    
    private List<String> messages;

    public Logger() {
        this.messages = new ArrayList<>();
    }

    public void add(String message) {
        messages.add(message);
    }

    public void print() {
        this.messages.forEach(System.out::println);
    }

    public List<String> getMessages() {
        return this.messages;
    }

    public void clear() {
        this.messages.clear();
    }
}

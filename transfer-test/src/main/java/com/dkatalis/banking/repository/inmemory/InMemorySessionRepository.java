package com.dkatalis.banking.repository.inmemory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import com.dkatalis.banking.model.Session;
import com.dkatalis.banking.repository.SessionRepository;

public class InMemorySessionRepository implements SessionRepository {

    private Map<String, List<Session>> map;

    public InMemorySessionRepository() {
        this.map = new HashMap<>();
    }

    @Override
    public int count(String username) {
        var existingAccountSession = getExistingAccountSession(username).orElse(List.of());
        return existingAccountSession.size();
    }

    private Optional<List<Session>> getExistingAccountSession(String username) {
        var existingAccountSession = this.map.get(username);
        return existingAccountSession == null ? Optional.empty() : Optional.of(existingAccountSession);
    }

    @Override
    public Optional<Session> getActiveSession(String username) {
        var existingAccountSession = getExistingAccountSession(username);
        if (existingAccountSession.isEmpty()) {
            return Optional.empty();
        }

        var activeAccountSessions = existingAccountSession.get().stream().filter(session -> session.isActive()).collect(Collectors.toList());

        // Just return the first one. Ideally there is only 1 active session per account.
        return activeAccountSessions.isEmpty() ? Optional.empty() : Optional.of(activeAccountSessions.get(0));
    }

    @Override
    public Session save(Session session) {
        var accountSessions = this.map.get(session.getAccountName());
        if (accountSessions == null) {
            accountSessions = new ArrayList<>();
        }
        accountSessions.add(session);
        this.map.put(session.getAccountName(), accountSessions);

        return session;
    }
}

package com.dkatalis.banking;

import com.dkatalis.banking.model.Session;

public class Context {
    
    private Session session;

    public Context(Session session) {
        this.session = session;
    }

    public String getAccountName() {
        return this.session.getAccountName();
    }

    public static Context of(Session session) {
        return new Context(session);
    }
}

package org.redrock.gayligayli.service;

import com.sun.org.apache.regexp.internal.RE;

public abstract class Command {
    protected Receiver receiver = null;

    public abstract void exectue();

    public String getResponseJson() {
        return receiver.getResponse();
    }
}

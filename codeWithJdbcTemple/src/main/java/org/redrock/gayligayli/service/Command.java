package org.redrock.gayligayli.service;


public abstract class Command {
    protected Receiver receiver = null;

    public abstract void exectue();

    public String getResponseJson() {
        return receiver.getResponse();
    }
}

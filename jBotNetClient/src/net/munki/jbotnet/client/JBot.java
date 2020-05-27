package net.munki.jbotnet.client;

import net.munki.jbotnet.interfaces.JBotInterface;

import java.io.Serializable;

public class JBot implements JBotInterface, Serializable {

    private static final long serialVersionUID = 225L;

    @Override
    public boolean execute() {
        return false;
    }

}

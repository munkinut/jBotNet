package net.munki.jbotnet.client;

import net.munki.jbotnet.interfaces.JBotInterface;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class JBotExporter implements Runnable {

    private String name;
    private Thread t;
    private JBotInterface jBot;
    private Registry registry;

    public String getName() {
        return name;
    }

    public Thread getT() {
        return t;
    }

    public JBotExporter(String name, JBotInterface jBot, Registry registry) throws RemoteException {
        this.name = name;
        this.jBot = jBot;
        this.registry = registry;
        t = new Thread(this, name);
    }

    @Override
    public void run() {
        try {
            String name = jBot.getRealName();
            JBotInterface stub =
                    (JBotInterface) UnicastRemoteObject.exportObject(jBot, 0);
            registry.rebind(name, stub);
            System.out.println("JBotInterface bound");
            Thread.sleep(1000);
        } catch (RemoteException | InterruptedException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }

    }

}

package net.munki.jbotnet.client;

import net.munki.jbotnet.interfaces.JBotInterface;
import net.munki.jbotnet.interfaces.JBotNetServerInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.CopyOnWriteArrayList;

public class JBotNetClient {

    private JBotNetServerInterface jBotNetServerInterface;
    private final static String SERVICE = "JBotNetServerInterface";
    private Registry registry;

    public JBotNetClient(String rmiRegistryHost) throws JBotNetClientException {
        try {
            registry = LocateRegistry.getRegistry(rmiRegistryHost);
            jBotNetServerInterface = (JBotNetServerInterface)registry.lookup(SERVICE);
        } catch (RemoteException e) {
            throw new JBotNetClientException(e);
        } catch (NotBoundException e) {
            throw new JBotNetClientException(e);
        }
    }

    public boolean register(JBotInterface jBot) throws JBotNetClientException {
        System.out.println("Registering...");
        try {
            return jBotNetServerInterface.register(jBot);
        } catch (RemoteException e) {
            throw new JBotNetClientException(e);
        }
    }

    public boolean deregister(JBotInterface jBot) throws JBotNetClientException {
        System.out.println("Deregistering...");
        try {
            return jBotNetServerInterface.deregister(jBot);
        } catch (RemoteException e) {
            throw new JBotNetClientException(e);
        }
    }

    public CopyOnWriteArrayList<JBotInterface> getBots() throws JBotNetClientException {
        try {
            return jBotNetServerInterface.getBots();
        } catch (RemoteException e) {
            throw new JBotNetClientException(e);
        }
    }

    void exportBot(JBotInterface jBot) {
        System.out.println("Exporting...");
        JBotExporter nm1 = null;
        try {
            nm1 = new JBotExporter("One", jBot, registry);
            nm1.getT().start();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        try {
            nm1.getT().join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public JBotInterface getBot(String botName) throws JBotNetClientException {
        try {
            return jBotNetServerInterface.getABot(botName);
        } catch (RemoteException e) {
            throw new JBotNetClientException(e);
        }
    }
}

package net.munki.jbotnet.client;

import net.munki.jbotnet.interfaces.JBotNetServerInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class JBotNetClient {

    private JBotNetServerInterface jBotNetServerInterface;
    private final static String SERVICE = "JBotNetServerInterface";

    public JBotNetClient(String rmiRegistryHost) throws JBotNetClientException {
        try {
            Registry registry = LocateRegistry.getRegistry(rmiRegistryHost);
            jBotNetServerInterface = (JBotNetServerInterface)registry.lookup(SERVICE);
        } catch (RemoteException e) {
            throw new JBotNetClientException(e);
        } catch (NotBoundException e) {
            throw new JBotNetClientException(e);
        }
    }

    public boolean register(JBot jBot) throws JBotNetClientException {
        try {
            return jBotNetServerInterface.register(jBot);
        } catch (RemoteException e) {
            throw new JBotNetClientException(e);
        }
    }

    public boolean deregister(JBot jBot) throws JBotNetClientException {
        try {
            return jBotNetServerInterface.deregister(jBot);
        } catch (RemoteException e) {
            throw new JBotNetClientException(e);
        }
    }
}

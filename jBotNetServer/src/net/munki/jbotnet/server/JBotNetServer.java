package net.munki.jbotnet.server;

import net.munki.jbotnet.interfaces.JBotInterface;
import net.munki.jbotnet.interfaces.JBotNetServerInterface;

import java.util.concurrent.CopyOnWriteArrayList;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class JBotNetServer implements JBotNetServerInterface {

    private CopyOnWriteArrayList<JBotInterface> jBots;

    public JBotNetServer() {
        super();
        jBots = new CopyOnWriteArrayList<>();
    }

    @Override
    public boolean register(JBotInterface jBotInterface) throws RemoteException {
        return jBots.addIfAbsent(jBotInterface);
    }

    @Override
    public boolean deregister(JBotInterface jBotInterface) throws RemoteException {
        return jBots.remove(jBotInterface);
    }

    public static void main(String[] args) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "JBotNetServerInterface";
            JBotNetServerInterface jBotNetServer = new JBotNetServer();
            JBotNetServerInterface stub =
                    (JBotNetServerInterface) UnicastRemoteObject.exportObject(jBotNetServer, 0);
            //Registry registry = LocateRegistry.createRegistry(1099);
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            System.out.println("JBotNetServer bound");
        } catch (Exception e) {
            System.err.println("JBotNetServer exception:");
            e.printStackTrace();
        }
    }

}

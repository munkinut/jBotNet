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
        jBotInterface.setDescription("Registered");
        return jBots.addIfAbsent(jBotInterface);
    }

    @Override
    public boolean deregister(JBotInterface jBotInterface) throws RemoteException {
        return jBots.remove(jBotInterface);
    }

    @Override
    public CopyOnWriteArrayList<JBotInterface> getBots() throws RemoteException {
        return jBots;
    }

    @Override
    public JBotInterface getABot(String name) throws RemoteException {
        //Registry registry = LocateRegistry.getRegistry("localhost");
        //try {
        //    JBotInterface jif = (JBotInterface)registry.lookup(name);
        //    return jif;
        //} catch (NotBoundException e) {
        //    throw new RemoteException(e.getMessage());
        //}
        if (this.jBots.isEmpty()) throw new RemoteException("Bots list was empty.");
        return jBots.get(0);
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
            Registry registry = LocateRegistry.getRegistry();
            registry.rebind(name, stub);
            System.out.println("JBotNetServer bound");
        } catch (Exception e) {
            System.err.println("JBotNetServer exception:");
            e.printStackTrace();
        }
    }

}

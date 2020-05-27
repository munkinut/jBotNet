package net.munki.jbotnet.server;

import net.munki.jbotnet.interfaces.JBotInterface;
import net.munki.jbotnet.interfaces.JBotNetServerInterface;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class JBotNetServer implements JBotNetServerInterface {

    public JBotNetServer() {
        super();
    }

    @Override
    public boolean register(JBotInterface jBotInterface) throws RemoteException {
        return false;
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

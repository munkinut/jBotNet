package net.munki.jbotnet.client;

import net.munki.jbotnet.interfaces.JBotNetServerInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class JBotNetClient {

    public static void main(String args[]) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "JBotNetServerInterface";
            Registry registry = LocateRegistry.getRegistry(args[0]);
            JBotNetServerInterface jBotNetServerInterface = (JBotNetServerInterface)registry.lookup(name);
            JBot jBot = new JBot("javamunk", "javamunk", "", "", "localhost", "#javamunk", "", 0);
            boolean b = jBotNetServerInterface.register(jBot);
            System.out.println("Registered = " + b);
            b = jBotNetServerInterface.deregister(jBot);
            System.out.println("Deregistered = " + b);
        } catch (Exception e) {
            System.err.println("JBotNetClient exception:");
            e.printStackTrace();
        }
    }

}

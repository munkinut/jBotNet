package net.munki.jbotnet.client;

import net.munki.jbotnet.interfaces.JBotNetServerInterface;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class JBotNetClient {

    public static void main(String args[]) {
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try {
            String name = "JBotNetServerInterface";
            Registry registry = LocateRegistry.getRegistry(args[0]);
            JBotNetServerInterface jBotNetServerInterface = (JBotNetServerInterface)registry.lookup(name);
            boolean b;
            List<JBot> botList = new CopyOnWriteArrayList<>();
            for (int i = 0; i < 5; i++) {
                String botName = new StringBuilder("javamunk_").append(i).toString();
                JBot jBot = new JBot(botName, botName, "", "", "localhost", "#javamunk", "", 0);
                b = jBotNetServerInterface.register(jBot);
                if (b) botList.add(jBot);
                System.out.println("Registered = " + b + " " + jBot.getName());
            }
            for (JBot jBot : botList) {
                b = jBotNetServerInterface.deregister(jBot);
                System.out.println("De-Registered = " + b + " " + jBot.getName());
            }
            for (JBot jBot : botList) {
                botList.remove(jBot);
            }

        } catch (Exception e) {
            System.err.println("JBotNetClient exception:");
            e.printStackTrace();
        }
    }

}

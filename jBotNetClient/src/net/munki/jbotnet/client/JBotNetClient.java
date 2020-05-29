package net.munki.jbotnet.client;

import net.munki.jbotnet.interfaces.JBotInterface;
import net.munki.jbotnet.interfaces.JBotNetServerInterface;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
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

    private CopyOnWriteArrayList<JBotInterface> getBots() throws JBotNetClientException {
        try {
            return jBotNetServerInterface.getBots();
        } catch (RemoteException e) {
            throw new JBotNetClientException(e);
        }
    }

    public void exportBot(JBotInterface jBot) throws JBotNetClientException {
        System.out.println("Exporting...");
        JBotExporter nm1 = null;
        try {
            nm1 = new JBotExporter("One", jBot, registry);
            nm1.getT().start();
        } catch (RemoteException e) {
            throw new JBotNetClientException(e);
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

    public void waitForMessages(JBotInterface jBot, String oldMessage) throws JBotNetClientException {
        // Start polling for new messages.
        System.out.println("Waiting for messages for 1 minute...");
        long startTime = System.currentTimeMillis();
        while (false||(System.currentTimeMillis()-startTime)<(60000 * 1)) {
            String message = poll(jBot);
            if (!message.equals(oldMessage)) {
                sendMessage(jBot, message); // here, jBot is to be excluded
                oldMessage = message;
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("No longer waiting for messages...");

    }

    public String poll(JBotInterface jBot) throws JBotNetClientException {
        try {
            return jBot.getMessage();
        } catch (RemoteException e) {
            throw new JBotNetClientException(e);
        }
    }

    public void sendMessage(JBotInterface excludeBot, String message) throws JBotNetClientException {
        try {
            // TODO Send message to all bots apart from jb
            CopyOnWriteArrayList<JBotInterface> bots = getBots();
            for(JBotInterface bot : bots) {
                if (!bot.equals(excludeBot)) bot.setMessage(message);
            }
        } catch (RemoteException e) {
            throw new JBotNetClientException(e);
        }
    }
}

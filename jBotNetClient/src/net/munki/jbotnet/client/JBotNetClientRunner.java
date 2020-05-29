package net.munki.jbotnet.client;

import net.munki.jbotnet.interfaces.JBotInterface;

import java.util.concurrent.CopyOnWriteArrayList;

public class JBotNetClientRunner {

    static JBotNetClient jbnc;

    public static void main(String[] args) {
        try {
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }

            jbnc = new JBotNetClient(args[0]);
            CopyOnWriteArrayList<JBotInterface> botList = new CopyOnWriteArrayList<>();

            String oldMessage = "";
            String botName = new StringBuilder("javamunk").toString();
            JBotInterface jBot = new JBot(botName, botName, "", "", "localhost", "#javamunk", "", 0, oldMessage);

            jbnc.exportBot(jBot);

            if (jbnc.register(jBot)) botList.add(jBot);

            jbnc.waitForMessages(jBot, oldMessage);

            if (jbnc.deregister(jBot)) botList.remove(jBot);


        } catch (JBotNetClientException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

}


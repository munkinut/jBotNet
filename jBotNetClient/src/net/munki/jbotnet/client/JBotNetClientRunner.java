package net.munki.jbotnet.client;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class JBotNetClientRunner {

    static JBotNetClient jbnc;

    public static void main(String args[]) {
        try {
            if (System.getSecurityManager() == null) {
                System.setSecurityManager(new SecurityManager());
            }

            jbnc = new JBotNetClient(args[0]);
            boolean b;
            List<JBot> botList = new CopyOnWriteArrayList<>();
            for (int i = 0; i < 5; i++) {
                String botName = new StringBuilder("javamunk_").append(i).toString();
                JBot jBot = new JBot(botName, botName, "", "", "localhost", "#javamunk", "", 0);
                b = jbnc.register(jBot);
                if (b) botList.add(jBot);
                System.out.println("Registered = " + b + " " + jBot.getName());
            }
            for (JBot jBot : botList) {
                b = jbnc.deregister(jBot);
                System.out.println("De-Registered = " + b + " " + jBot.getName());
            }
            for (JBot jBot : botList) {
                botList.remove(jBot);
            }

        } catch (JBotNetClientException e) {
            System.err.println(e.getMessage());
            e.printStackTrace();
        }
    }

}

